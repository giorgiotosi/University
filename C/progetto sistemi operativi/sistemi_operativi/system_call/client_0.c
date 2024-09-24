/// @file client.c
/// @brief Contiene l'implementazione del client.

/*INCLUDE*/
#include "defines.h"
#include "err_exit.h"
#include "semaphore.h"
#include "shared_memory.h"
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <sys/shm.h>
#include <sys/msg.h>

#define sem_key 53
#define HOME $HOME
#define LEN 1024


//-----------------------VARIABILI  GLOBALI----------------------------
//n è il contatore per i file che cominciano con sendme_
int n = 0; 
//seachPath è array di stringhe che contiene i path dei file compatibili
char seachPath[110][110]; 
//buf contiene argv[1] ovvero il path passato da linea di comando
char buf[150];
/*set of signals (N.B. it is not initialized!)*/
sigset_t mySet;
extern char **environ;
char *pathDiArgv1 = NULL;
int j=0; //indice
int a = 0;
//---------------------------------------------------------------------


//----------------------------------SIGHANDLER-------------------------
/*Implementazione dei due signalhandler*/
void sigHandler1(int sig) {
  printf("catch sigusr1");
   exit(1);
}
void sigHandler2(int sig){ 
  // blocca tutti i segnali (compresi SIGUSR1 e SIGINT) modificando la maschera
  sigfillset(&mySet);
  sigprocmask(SIG_SETMASK, &mySet, NULL);
}
//---------------------------------------------------------------------

/*DICHIARAZIONE E DEFINIZIONE FUNZIONI**/

/*CHECKFILE*/
int checkFile(char *filename, off_t size) {  
  //se la funzione ritorna 1 i primi n caratteri sono uguali e la dim è corretta
  struct stat statbuf; 
  //struttura dati statbuf con tutte le caratteristiche
  // e le statistiche del file
  if (stat(filename, &statbuf) == -1){
    return 0;
  }
  return (filename == NULL)? 0 : ((strncmp(filename, "sendme_", 7) == 0) &&
  (statbuf.st_size < size));
  // strncmp() se i primi n caratteri sono uguali ritorna 0
  //controlla che la dimensione del file sia corretta
} 

/*SEARCH*/
//cerco ricorsivamente i file che iniziano con "sendme_"
void search(char *buffer) {
  
  DIR *dirp = opendir(buffer);   //apro la directory e ho un puntatore 
                                   //alla directory buffer = argv[1]
  if (dirp == NULL) return;      //se dirp nullo la directory non esiste
  // readdir returns NULL when end-of-directory is reached.
  // In oder to get when an error occurres, we set errno to zero, and the we
  // call readdir. If readdir returns NULL, and errno is different from zero,
  // an error must have occurred.
  errno = 0;
  // iter until NULL is returned as a result
  struct dirent *dentry; // puntatore alle entry
  while ( (dentry = readdir(dirp)) != NULL) { //scorro sulle entry 
    // Skippo
    if ( strcmp(dentry->d_name, ".") == 0 || 
      strcmp(dentry->d_name, "..") == 0) 
      {  continue;  }
      
      // is the current dentry a regular file?
      if (dentry->d_type == DT_REG) {  
        int match = checkFile(dentry->d_name, 4096*sizeof(char));
        // Se il match è 1 allora il percorso del file deve essere salvato
        if (match == 1){
          //dopo aver creato il path completo del file da inserire in seachpath
          //devo ripristinare il buffer togliendoil nome del file già inserito
          //salvo path prima di concatenare il file
          char path[150];
          char *string = &path[0];
          sprintf(string,(const char*) buffer, 0);
          //cosi ho salvato in path il buffer
          strcat(strcat(&buffer[strlen(buffer)], "/"), dentry->d_name);
          sprintf(&seachPath[n][0],(const char*) buffer, 0); 
          //inserisco il path del file nell'array e incremento
          n++;
          //resetto buffer
          memset(buffer, '\0', strlen(buffer));
          sprintf(buffer,(const char*) string, 0);
          // lo riporto a prima che si aggiungesse il nome del file
        }
        // is the current dentry a directory  
      } else if (dentry->d_type == DT_DIR ) {
         
      // exetend current seachPath with the directory name
      char path2[150];
      char *string2 = &path2[0];
      sprintf(string2,(const char*) buffer, 0);
      //salvo buffer prima della concatenazione
      // metto buffer dentro string2
      //concateno
      strcat(strcat(&buffer[strlen(buffer)], "/"), dentry->d_name);
        
      //posso richiamare la search
      if(chdir((const char*) buffer) == -1){;}
      search(buffer);
      //resetto buffer e lo riporto a prima della concatenazione
      memset(buffer, '\0', strlen(buffer));
      sprintf(buffer,(const char*) string2, 0);
      // lo riporto a prima che si aggiungesse il nome del file
      if(chdir((const char*) buffer) == -1){;}
      }
      errno = 0;
    }
    if (errno != 0)
      ErrExit("readdir failed");

    if (closedir(dirp) == -1)
      ErrExit("closedir failed");
}

//------------------------------------MAIN-----------------------------------------------------
int main(int argc, char * argv[]) {

  INIZIO:
    pathDiArgv1=argv[1];
    // inizializzo mySet contenente tutti i messaggi
    sigfillset(&mySet);
    // rimuovo SIGINT e SIGUSR1 da myset
    sigdelset(&mySet, SIGUSR1);
    sigdelset(&mySet, SIGINT);
    // blocco tutti i segnali tranne SIGINT e SIGUSR1
    sigprocmask(SIG_SETMASK, &mySet, NULL);
    // assegno ai segnali il proprio nuovo handler  
    if (signal(SIGUSR1, sigHandler1) == SIG_ERR) // kill -10 pid
        ErrExit("change signal handler failed");
    if (signal(SIGINT, sigHandler2) == SIG_ERR) //ctrl+c
        ErrExit("change signal handler failed");
    
    //ottengo pid del client
    printf("pid processo client : %i\n", getpid());
  
    //metto in attesa il  client_0 di un segnale qualsiasi
    printf("\nORA VADO IN PAUSA E ATTENDO SEGNALE\n");
    pause();
    printf("\n\n>>>Siamo usciti dalla pausa grazie al ctrl+c!!!\n\n");

    //imposta la sua directory corrente ad un path passato da linea di comando
    //all’avvio del programma
    // check command line input arguments
    if (argc != 2) {
        printf("Usage: %s path\n", argv[0]);
        exit(1);
    }

    //imposto il path passato da riga dicomando come working directory corrente 
    if(chdir(argv[1]) == -1)
      ErrExit("path non valido");

    //inseriamo in path la cwd che contiene argv[1]
    char *path = getcwd(buf, 150);

    char patharray[150];
    char *ptr = &patharray[0];
    sprintf(ptr, path,0);
    //ora ptr che punta ad patharray contiene argv[1] cioè cwd
    printf("Ciao %s,\n\tora inizio la ricerca dei file contenuti in %s\n",
      environ[2],path ); //$USER = getenv(USER);
    fflush(stdout);

    //chiamata alla  funzione search
    search(ptr);
  
    printf("\nHo trovato %i files\n", n);
    //inviamo n su FIFO1
    char pathdisupporto[150];
    sprintf(pathdisupporto, buf,0);
    int fd1 = open((strcat(buf,"/tmp/FIFO1")), O_RDWR);
    sprintf(buf, pathdisupporto, 0);
    if(fd1 == -1)
      ErrExit("apertura in scrittura non riuscita");
    
    if(write(fd1, &n, sizeof(int)) == -1)
      ErrExit("write non andata a buon fine");
    close(fd1);
  
    //usiamo un semaforo per aspettare 
    //la conferma di ricezione di n da parte del server
    
    int semid = semget((key_t) sem_key, 10, S_IRUSR | S_IWUSR);
  
    //imposto il semaforo 1 a 1
    semOp(semid, 1, 1);
  
    semOp(semid, 0, -1);  
  
    printf(">>> ho scritto n = %i su FIFO 1 e l'ho mandato al server\n", n);
    fflush(stdout);
  
    //leggo la conferma di ricezione di n da parte della shdmem
    key_t key_sharedmemory = ftok((strcat(buf,"/chiaveshm")), 'a');
    sprintf(buf, pathdisupporto, 0);
    key_t key_msgqueue = ftok((strcat(buf,"/chiavemsgq")), 'b');
    sprintf(buf, pathdisupporto, 0);
    size_t size = 50*sizeof(struct parte);
    int ShdMem = shmget(key_sharedmemory, size , IPC_CREAT | S_IRUSR | S_IWUSR);
  
    //attacco la shdmem in read mode
    struct parte *ptr_shdm = (struct parte*) shmat(ShdMem, NULL, 0);
    if (ptr_shdm == (void*) -1)
      ErrExit("shmat failed");
    
    if(ptr_shdm[0].pid == n)
      printf("il server ha letto e inviato su shm n = %i\n", ptr_shdm[0].pid);
    shmarr[0] = 0;

    // ORA CHE HO RICEVUTO LA CONFERMA DA PARTE DEL SERVER DI N 
    // PER OGNI FILE CON NOME CHE INIZIA CON LA STRINGA “sendme_” 
    // GENERO UN PROCESSO FIGLIO CLIENT_i

    struct struttura struttura[n];
    //struct mymsg msgQueue [n];

    int msgqid = msgget(key_msgqueue, IPC_CREAT | S_IRUSR | S_IWUSR);

    pid_t client_i;
    for (int i = 0; i < n; ++i) {
        // generate a subprocess
        client_i = fork();
        if (client_i == -1)
            printf("client_%d not created!\n", i);
        else if (client_i == 0) {
          
        //HO CREATO CLIENT_I
        //VANNO CONTATI I CARATTERI DEL FILE
        char percorso[100];//-->seachpath[i]
        char *ptrpercorso = &seachPath[i][0];
        sprintf(percorso, ptrpercorso,0);
        // ora su array percorso ho esattamente il path del file corrispondente
        // al processo figlio
        int fd= open((const char*)percorso, O_RDWR, S_IRWXU); //APRO IL FILE
        // CON LA READ CHE RITORNA NUM BYTE LETTI SAPENDO CHE 
        //UN CARATTERE = UN BYTE 
        //POSSO SAPERE QUANTI CARATTERI STANNO DENTRO AL FILE
        int num_caratteri=0;
        char stringa[4096];//-->inserirò caratteri del file
        num_caratteri = (int)read(fd,stringa,4096*sizeof(char));
        //ADESSO HO IL NUM DI CARATTERI DI UN FILE E IN STRINGA E' CONTENUTO
        //IL CONTENUTO DEL FILE
              
        int num_caratteri_diviso_quattro=0;
        if(num_caratteri % 4 != 0)
          num_caratteri_diviso_quattro= (num_caratteri/4)+1;
        else num_caratteri_diviso_quattro= num_caratteri/4;

        //ADESSO DEVO DIVIDERE IL FILE IN QUATTRO PARTI
        char parte1[1024];
        char parte2[1024];
        char parte3[1024];
        char parte4[1024];
        //CON LA READ INSERISCO LE PARTI DI FILE NEGLI OPPORTUNI ARRAY
        int numRead=0;
        //SPOSTO OFFSET
        if(lseek(fd, (off_t) 0, SEEK_SET)==-1){;};
        numRead = (int)read(fd,parte1,num_caratteri_diviso_quattro*sizeof(char));
        //INSERISCO CARATTERE NULLO PER CHIUDERE LA STRINGA
        parte1[numRead]='\0';
        //SPOSTO OFFSET
        if(lseek(fd, (off_t) numRead, SEEK_SET)==-1){;};
        numRead=0;
        numRead = (int)read(fd,parte2,num_caratteri_diviso_quattro*sizeof(char));
        //INSERISCO CARATTERE NULLO PER CHIUDERE LA STRINGA
        parte2[numRead]='\0';
        //SPOSTO OFFSET
        if(lseek(fd, (off_t) 0, SEEK_CUR)==-1){;};
        numRead=0;
        numRead = (int)read(fd,parte3,num_caratteri_diviso_quattro*sizeof(char));
        //INSERISCO CARATTERE NULLO PER CHIUDERE LA STRINGA
        parte3[numRead]='\0';
        //SPOSTO OFFSET
        if(lseek(fd, (off_t) 0, SEEK_CUR)==-1){;};
        numRead=0;
        numRead = (int)read(fd,parte4,num_caratteri_diviso_quattro*sizeof(char));
        //INSERISCO CARATTERE NULLO PER CHIUDERE LA STRINGA
        parte4[numRead]='\0';
        //ORA HO DIVISO IL FILE IN QUATTRO PARTI, IN QUATTRO ARRAY

        //RIEMPIO LA STRUTTURA
        struttura[i].parte1.pid = getpid();
        struttura[i].parte2.pid = getpid();
        struttura[i].parte3.pid = getpid();
        struttura[i].parte4.pid = getpid();
        sprintf(struttura[i].parte1.partei, parte1 ,0);
        sprintf(struttura[i].parte2.partei, parte2 ,0);
        sprintf(struttura[i].parte3.partei, parte3 ,0);
        sprintf(struttura[i].parte4.partei, parte4 ,0);
        
        sprintf(struttura[i].parte1.path, percorso,0);
        sprintf(struttura[i].parte2.path, percorso,0);
        sprintf(struttura[i].parte3.path, percorso,0);
        sprintf(struttura[i].parte4.path, percorso,0);

        semOp(semid, 3, -1);
        semOp(semid, 3, 0);   
      
        //--------------------------
        //CLIENT_I COMINCIA A *INVIARE* I MESSAGGI 
        //AI 4 CANALI DI COMUNICAZIONE
      
        //VAI CON FIFO1
    
        int fdfifo1 = open((strcat(buf,"/tmp/FIFO1")), O_WRONLY);
        sprintf(buf, pathdisupporto, 0);

        // writing the string on fifo
        if(write(fdfifo1, &struttura[i].parte1, sizeof(struttura[i].parte1)) == -1)
          ErrExit("scrittura su fifo 1 non riuscita");
        fflush(stdout);
        semOp(semid, 4, -1);
        
        //VAI CON FIFO2
        
        int fdfifo2 = open((strcat(buf,"/tmp/FIFO2")), O_WRONLY );
        sprintf(buf, pathdisupporto, 0);
       
        // writing the string on fifo
        if(write(fdfifo2, &struttura[i].parte2, sizeof(struttura[i].parte2)) == -1)
        ErrExit("scrittura su fifo 2 non riuscita");
        
        //printf("scrivo su fifo2 %i\n", getpid());
        fflush(stdout);

        semOp(semid, 6, -1);
    
        //VAI CON MESSAGE QUEUE
      
        msgqid = msgget(key_msgqueue, IPC_CREAT | S_IRUSR | S_IWUSR);

        struct mymsg mexSend = {1,struttura[i].parte3};

        size_t msize = sizeof(struct mymsg) - sizeof(long);
        if(msgsnd(msgqid, &mexSend, msize, 0) == -1)
          ErrExit("scrittura su msgqueue non riuscita");

        semOp(semid, 9, -1);

        //VAI CON SHARED MEMORY
        
        semOp(semid, 8, -1);
        int flag =0;

        for(j = 0 ; j<50; j++){
          if(shmarr[j]==1) // occupato-->locazione già scritta
            continue;
          else{
            flag=1;
            break;
          }
        }
        if(flag == 0){
          semOp(semid, 8, 1);
        }
        
        ptr_shdm[j] = struttura[i].parte4;
        shmarr[j]=1;

        semOp(semid, 10, 1);
        
        close(fd); 
       
        exit(EXIT_SUCCESS);
        }
    }
    a++;
    if(a==1){
      semOp(semid, 5, 1);
    }
    
   /*int status = 0;
    // get termination status of each created subprocess.
    while ( (client_i = wait(&status)) != -1)
      printf("Child %d exited\n", client_i);*/

    //mandate 4 parti e server le ha ricevute 
    //MI METTO IN ATTESA che il server tramite MESSAGE QUEUE mi dica che
    //informa che tutti i file di output sono stati creati dal server stesso
    //e che il server ha concluso le sue operazioni.
    
    semOp(semid, 7, -1);
    //lo devo rinizializzare perchè la faccio dentro ai processi figli
    //che hanno già terminato
    //int msgqid = msgget(key_msgqueue, IPC_CREAT | S_IRUSR | S_IWUSR);
    // Get the size of the mtext field***ho aggiunto una parte dentro la struttura
    size_t size_buf = sizeof(struct mymsg) -sizeof(long);
    // Wait for a message having type equals to 1
    //char bufbuf[25];
    struct mymsg mextorecive;
    if (msgrcv(msgqid, &mextorecive, size_buf, 2, 0) == -1)//prende il primo messaggio, senza flag
      ErrExit("msgrcv failed");

    //verifico che client_0 abbia ricevuto conferma correttamente
    printf("\n>>> Server scrive: %s\n",mextorecive.mex.path);

    //ORA SBLOCCO SIGINT E SIGUSR1

    // inizializzo mySet contenente tutti i messaggi
    sigfillset(&mySet);
    // rimuovo SIGINT e SIGUSR1 da myset
    sigdelset(&mySet, SIGUSR1);
    sigdelset(&mySet, SIGINT);
    // blocco tutti i segnali tranne SIGINT e SIGUSR1
    sigprocmask(SIG_SETMASK, &mySet, NULL);

    if (signal(SIGINT, sigHandler2) == SIG_ERR) //ctrl+c
      ErrExit("change signal handler failed");
    
    semOp(semid, 2, 1);
    pause();
    goto INIZIO;
    return 0;
}
