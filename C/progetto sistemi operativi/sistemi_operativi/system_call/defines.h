/// @file defines.h
/// @brief Contiene la definizioni di variabili
///         e funzioni specifiche del progetto.

#ifndef _ERREXIT_HH
#define _ERREXIT_HH
#include <sys/types.h>
#include <stdio.h>

#pragma once

void ErrExit(const char *msg);

#endif

struct parte{
  char partei[1024];
  char path[100];
  pid_t pid;
};

struct struttura{
  pid_t pid;
  struct parte parte1;
  struct parte parte2;
  struct parte parte3;
  struct parte parte4;
};

struct mymsg{
   long mytype;
   struct parte mex;
 };

int shmarr[50];


