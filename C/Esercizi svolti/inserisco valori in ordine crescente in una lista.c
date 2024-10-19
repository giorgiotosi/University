#include <stdio.h>
#include <stdlib.h>

// Dichiarazione delle strutture
struct node_t
{
    int valore;
    struct node_t *next;
};

struct list_t
{
    struct node_t *head;
};

// Dichiarazione delle funzioni
void check_malloc(void *p, int is_list);
struct list_t *inizializza_lista();
void insert_value(struct list_t *lista, int value);
void insert_into_list(struct list_t *list, int value);
void stampa(struct list_t *list);

// Funzione principale
int main()
{
    struct list_t *lista = inizializza_lista();  // Inizializza la lista
    insert_into_list(lista, 3);                   // Inserisce 3
    stampa(lista);                                 // Stampa la lista
    insert_into_list(lista, 1);                   // Inserisce 1
    stampa(lista);                                 // Stampa la lista
    insert_into_list(lista, 4);                   // Inserisce 4
    stampa(lista);                                 // Stampa la lista
    insert_into_list(lista, 5);                   // Inserisce 5
    stampa(lista);                                 // Stampa la lista
    insert_into_list(lista, 2);                   // Inserisce 2
    stampa(lista);                                 // Stampa la lista

    return 0;
}

// Funzione per controllare l'allocazione della memoria
void check_malloc(void *p, int is_list)
{
    if (p == NULL)
    {
        printf("Errore allocazione memoria \n");
        exit(-1);
    }
    if (is_list)
    {
        printf("Memoria allocata con successo.\n"); // Stampa solo per la lista
    }
}

// Funzione per inizializzare la lista
struct list_t *inizializza_lista()
{
    struct list_t *head;
    head = (struct list_t *)malloc(sizeof(struct list_t));
    check_malloc(head, 1); // Passa 1 per indicare che stai allocando la lista
    head->head = NULL; 
    return head;
}

// Funzione per inserire un valore nella lista
void insert_value(struct list_t *lista, int value)
{
    if (lista->head == NULL)
    {
        struct node_t *curr;
        curr = (struct node_t *)malloc(sizeof(struct node_t));
        check_malloc(curr, 0); // Passa 0 per indicare che stai allocando un nodo
        lista->head = curr;
        curr->valore = value;
        curr->next = NULL;
        return;
    }
    
    struct node_t *curr = lista->head;
    struct node_t *newNode;
    newNode = (struct node_t *)malloc(sizeof(struct node_t));
    check_malloc(newNode, 0); // Passa 0 per indicare che stai allocando un nodo

    // Inserimento nella posizione corretta
    if (curr->valore >= value)
    {
        newNode->valore = value;
        newNode->next = lista->head;
        lista->head = newNode;
        return;
    }

    while (curr->next != NULL && curr->next->valore < value)
    {
        curr = curr->next; // Scorro la lista
    }

    newNode->next = curr->next;
    curr->next = newNode;
    newNode->valore = value;
}

// Funzione per inserire un valore nella lista
void insert_into_list(struct list_t *list, int value)
{
    insert_value(list, value); 
}

// Funzione per stampare la lista
void stampa(struct list_t *list)
{
    struct node_t *curr = list->head;

    while (curr != NULL)
    {
        printf("%d -> ", curr->valore);
        curr = curr->next;
    }
    printf("NULL\n");
}
