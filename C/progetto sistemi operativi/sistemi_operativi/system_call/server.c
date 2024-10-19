// @file sender_manager.c
/// @brief Contiene l'implementazione del sender_manager.

#include <stdio.h>
#include <stdlib.h>
#define _OPEN_SYS_ITOA_EXT
#include "err_exit.h"
#include "defines.h"
#include "shared_memory.h"
#include "semaphore.h"
#include "fifo.h"
#include <sys/msg.h>
#include <string.h>
#include <strings.h> 
#include <sys/wait.h>

sigset_t mySet;

#include <sys/shm.h>
#include <sys/sem.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>

#define sem_key 53

//VARIABILI GLOBALI
int FIFO1;
int FIFO2;
int ShdMem;
struct parte *ptr_shdm;
int msqid;
int semid;
char cwdbuf[150];
char * ptrpath;
int flg = 0;

void IPC_remove (){
  //stacco la shmem
  if(shmdt(ptr_shdm)==-1)
    ErrExit("chiusura della shmem fallita\n");
  //elimino la shmem
  if (shmctl(ShdMem, IPC_RMID, NULL) == -1)
    ErrExit("shmctl failed");
  else
    printf("shared memory segment removed successfully\n");

  //chiudo ed elimino le FIFO
  close(FIFO1);
  close(FIFO2);
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  unlink((strcat(ptrpath,"/tmp/FIFO1")));
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  unlink((strcat(ptrpath,"/tmp/FIFO2")));
  printf("fifos removed succesfully\n");
  fflush(stdout);

  //chiudo ed elimino msgQueue
  
 if (msgctl(msqid, IPC_RMID, NULL) == -1)
    ErrExit("msgctl failed");
  else
    printf("message queue removed successfully\n");
 
  //chiudo set semafori
  if (semctl(semid, 0/*ignored*/, IPC_RMID, 0/*ignored*/) == -1)
    ErrExit("semctl failed");
  else{
    printf("semaphore set removed successfully\n");
    }
}

void sigHandler(int sig) {
  IPC_remove();
}

int main(int argc, char * argv[]) {

  INIZIO2:

  for(int i=0; i<50; i++){
    shmarr[i] = 0;
  }

  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));

  printf("\nAspetto che il Client si sblocchi...\n\n");
  //ho preso il path corrente

  sigfillset(&mySet);
  // rimuovo SIGINT e SIGUSR1 da myset
  sigdelset(&mySet, SIGINT);
  // blocco tutti i segnali tranne SIGINT e SIGUSR1
  sigprocmask(SIG_SETMASK, &mySet, NULL);
  // assegno ai segnali il proprio nuovo handler  
  if (signal(SIGINT, sigHandler) == SIG_ERR) //ctrl+c
     ErrExit("change signal handler failed");

  FIFO1 = mkfifo((strcat(ptrpath,"/tmp/FIFO1")), IPC_CREAT | IPC_EXCL |S_IRUSR | S_IWUSR| S_IWGRP);
  if ( FIFO1 == -1)
        ErrExit("mkfifo 1 failed");

  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  
  FIFO2 = mkfifo((strcat(ptrpath,"/tmp/FIFO2")), IPC_CREAT | IPC_EXCL | S_IRUSR | S_IWUSR | S_IWGRP);
  if ( FIFO2 == -1)
    ErrExit("mkfifo 2 failed");
 
  //creazione shared memory
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  key_t key_sharedmemory = ftok((strcat(ptrpath,"/chiaveshm")), 'a');
  size_t size = 50*sizeof(struct parte);
  ShdMem = shmget(key_sharedmemory, size , IPC_CREAT | S_IRUSR | S_IWUSR);

  ptr_shdm = (struct parte*) shmat(ShdMem, NULL, 0);
  if (ptr_shdm == (void*) -1 )
    ErrExit("shmat failed");
  //--------------------------------

  // creazione msgqueue
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  key_t key_msgqueue = ftok((strcat(ptrpath,"/chiavemsgq")), 'b');
  msqid = msgget(key_msgqueue, IPC_CREAT | S_IRUSR | S_IWUSR);

  semid = semget((key_t) sem_key, 11, IPC_CREAT | S_IRUSR | S_IWUSR);
  
  //-------------------------0--1--2--3--4---5--6---7--8--9--10-----
  unsigned short values[] = {0, 0, 0, 0, 50, 0, 50, 0, 1, 50, 0};
  union semun arg;
  arg.array = values;
  if (semctl(semid, 0/*ignored*/, SETALL, arg) == -1)
    ErrExit("semctl SETALL");

  //metto il server in attesa sulla FIFO1
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  FIFO1 = open((strcat(ptrpath,"/tmp/FIFO1")), O_RDWR );
  if (FIFO1 == -1)
    ErrExit("open failed");
  //leggo il valore n
  int n = 0;

  semOp(semid, 1, -1);

  int num_byte = read(FIFO1, &n, sizeof(n));
  if( num_byte == -1)
    ErrExit("lettura di n non riuscita");
  close (FIFO1);

  //setto il semaforo 3 a n
  values[3] = n;
  arg.array = values;
  if (semctl(semid, 0, SETALL, arg) == -1)
    ErrExit("semctl SETALL");

  printf("\nHo ricevuto su FIFO 1 dal Client_0 n = %d\n", n);
  fflush(stdout);

  //scrivo su shmem
  ptr_shdm[0].pid = n;
  shmarr[0] = 1;
  printf("\nInvio messaggio di conferma al Client_0 \n\n");

  //imposto il semaforo 0 a 1
  semOp(semid, 0, 1); 

  //ATTESA CICLICA DEI 4 CANALI

  struct struttura struttura[n];
  for(int i =0; i<n; i++)
    struttura[i].pid = -1;
  
  int count = 0;  //contatore n strutture
  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  FIFO1 = open((strcat(ptrpath,"/tmp/FIFO1")), O_RDONLY );
  if (FIFO1 == -1)
    ErrExit("open fifo 1 failed");

  ptrpath = getcwd( cwdbuf, (size_t) sizeof(cwdbuf));
  FIFO2 = open((strcat(ptrpath,"/tmp/FIFO2")), O_RDONLY );
  if (FIFO1 == -1)
    ErrExit("open fifo 2 failed");

/*----------------------RICEZIONE CICLICA----------------------------*/
  
  int shcount =0;

  struct parte tmp1, tmp2;
  
  semOp(semid,5, -1);
  
  while (count < (n*4)){
    
    //LEGGO DA FIFO 1
    if(read(FIFO1, &tmp1, sizeof(struct parte)) == -1){
         ErrExit("lettura fifo 1 non riuscita");
      }
    else {
        for(int i=0; i<n; i++){
          if(tmp1.pid == struttura[i].pid){
            struttura[i].parte1 = tmp1;
            count++;
            i=n;
          }
         else
           if(struttura[i].pid == -1) {
              struttura[i].pid = tmp1.pid;
              struttura[i].parte1 = tmp1;
              count++;
             i=n;
             }
           }
        }   
    semOp(semid, 4, 1);

    //LEGGO DA FIFO2
    if(read(FIFO2, &tmp2, sizeof(struct parte)) == -1){
         ErrExit("lettura fifo 2 non riuscita");
      }
    else{
        for(int i=0; i<n; i++){
          if(tmp2.pid == struttura[i].pid){
            struttura[i].parte2 = tmp2;
            count++;
             i=n;
          }
          else
            if(struttura[i].pid == -1 ){
              struttura[i].pid = tmp2.pid;
              struttura[i].parte2 = tmp2;
              count++;
              i=n;
            }
        }
    } 
    semOp(semid, 6, 1);
    
    //LEGGO DA MSGQUEUE
    
    struct mymsg mexRecived;
    mexRecived.mytype=1;
    size_t msize = sizeof(struct mymsg) - sizeof(long);
    if(msgrcv(msqid, &mexRecived, msize,1 ,0) == -1){
      ErrExit("lettura su msgqueue non riuscita");
    }
    else
        for(int i=0; i<n; i++){
          if(mexRecived.mex.pid == struttura[i].pid){
            struttura[i].parte3 = mexRecived.mex;
            count++;
            i=n;
          }
          else
            if((struttura[i].pid == -1) && (mexRecived.mex.pid != 0)){
              struttura[i].pid = mexRecived.mex.pid;
              struttura[i].parte3 = mexRecived.mex;
              count++;
              i=n;
            }
        }
    semOp(semid, 9, 1);
   
    //LEGGO DA SHARED MEMORY
    
    semOp(semid,10,-1);

    for(int j=0; j<50; j++){
      if(shmarr[j]==0){
        continue;
        }
      else{
        shcount= j;
        break;
      }
      
    }
    
    for(int i=0; i<n; i++){
    
        if(ptr_shdm[shcount].pid == struttura[i].pid){
          struttura[i].parte4 = ptr_shdm[shcount];
          count++;
          shmarr[shcount] = 0;
          i=n;
        }
        else{
          if((struttura[i].pid == -1) && (ptr_shdm[shcount].pid != 0)){
            struttura[i].pid = ptr_shdm[shcount].pid;
            struttura[i].parte4 = ptr_shdm[shcount];
            count++;
            shmarr[shcount] = 0;
            i=n;
          }
        }
    }
    semOp(semid,8,1);

  }

  //RIUNIONE DELLE PARTI

  for(int i =0; i<n; i++){
      char PID[10];
      char intestazione[200];
      char pathname[100];
      char pathname_out[100];
      sprintf(pathname, struttura[i].parte1.path,0);
      pathname[(strlen(struttura[i].parte1.path)-4)] = '\0';
      strcat(pathname, "_out.txt");
      sprintf(pathname_out, pathname,0);
      pathname_out[(strlen(pathname_out)+1)]= '\0';
      printf("\t>>>%s\n\n", pathname_out);

      int fdout = open(pathname_out,O_CREAT|O_RDWR|O_TRUNC,S_IRWXU);
  
      //write
      //prima parte 
      sprintf(intestazione,"[Parte 1 del file ");
      strcat(intestazione,pathname_out);
      strcat(intestazione,", spedita dal processo ");
      sprintf(PID, "%d", struttura[i].pid);
      strcat(intestazione,PID);
      strcat(intestazione," tramite FIFO1]\n");
  
      if( write(fdout, intestazione, (size_t)(strlen(intestazione)+1))==-1){
        ErrExit("Scrittura fallita");
      }
      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
      if( write(fdout, strcat(struttura[i].parte1.partei,"\n"), (size_t)(strlen(struttura[i].parte1.partei)+2))==-1){
        ErrExit("Scrittura fallita");
      }
      // RESET
      memset(intestazione, '\0', sizeof(intestazione));
      //
      // seconda parte
      sprintf(intestazione,("[Parte 2 del file "));
      strcat(intestazione,pathname_out);
      strcat(intestazione,", spedita dal processo ");
      sprintf(PID, "%d", struttura[i].pid);
      strcat(intestazione,PID);
      strcat(intestazione," tramite FIFO2]\n");

      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
  
      if( write(fdout, intestazione, (size_t)(strlen(intestazione)+1))==-1){
        ErrExit("Scrittura fallita");
      }
      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
      if( write(fdout, strcat(struttura[i].parte2.partei,"\n"), (size_t)(strlen(struttura[i].parte2.partei)+2))==-1){
        ErrExit("Scrittura fallita");
      }
      // RESET
      memset(intestazione, '\0', sizeof(intestazione));
      //
      // terza parte
      sprintf(intestazione,("[Parte 3 del file "));
      strcat(intestazione,pathname_out);
      strcat(intestazione,", spedita dal processo ");
      sprintf(PID, "%d", struttura[i].pid);
      strcat(intestazione,PID);
      strcat(intestazione," tramite MsgQueue]\n");

      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
  
      if( write(fdout, intestazione, (size_t)(strlen(intestazione)+1))==-1){
        ErrExit("Scrittura fallita");
      }
      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
      if( write(fdout, strcat(struttura[i].parte3.partei, "\n"), (size_t)(strlen(struttura[i].parte3.partei)+2))==-1){
        ErrExit("Scrittura fallita");
      }
      // RESET
      memset(intestazione, '\0', sizeof(intestazione));
      //
      // quarta parte
      sprintf(intestazione,("[Parte 4 del file "));
      strcat(intestazione,pathname_out);
      strcat(intestazione,", spedita dal processo ");
      sprintf(PID, "%d", struttura[i].pid);
      strcat(intestazione,PID);
      strcat(intestazione," tramite ShdMem]\n");

      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
  
      if( write(fdout, intestazione, (size_t)(strlen(intestazione)+1))==-1){
        ErrExit("Scrittura fallita");
      }
      if(lseek(fdout, (off_t)-1 , (int)SEEK_CUR )==-1){
        ErrExit("lseek fallita");
      }
      if( write(fdout, strcat(struttura[i].parte4.partei, "\n"), (size_t)(strlen(struttura[i].parte4.partei)+1))==-1){
        ErrExit("Scrittura fallita");
      }
      continue;
  }

  // DOPO AVER CREATO I FILE "..._out" E SCRITTO AL LORO INTERNO

  /*MANDA UN MESSAGGIO DI TERMINAZIONE SULLA CODA DI MESSAGGI*/

  msqid = msgget(key_msgqueue, IPC_CREAT | S_IRUSR | S_IWUSR);
  // Get the size of the mtext field***ho aggiunto una parte dentro la struttura

  // Wait for a message having type equals to 1
  struct mymsg mextosend;
  mextosend.mytype=2;
  char bufbuf[25]="Qui fatto tutto!";
  sprintf(mextosend.mex.path, bufbuf, 0);
  size_t size_buf = sizeof(struct mymsg) - sizeof(long);
  if (msgsnd(msqid, &mextosend, size_buf, 0) == -1)//prende il primo messaggio, senza flag
  ErrExit("msgsnd failed");
  semOp(semid, 7, 1);

  semOp(semid, 2, -1);

  IPC_remove();

  goto INIZIO2;
  return 0;
}