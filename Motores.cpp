#include "Motores.h"
#ifndef WIRING_H
#include <Arduino.h>
#endif
int sentido=0;

Motores::Motores() {
}

Motores::Motores(int ind, int mm1_a,int mm1_b,int  mm1_pwm,int  mm2_a,int  mm2_b,int  mm2_pwm) {
  configurar(ind, mm1_a,mm1_b,mm1_pwm,mm2_a,mm2_b,mm2_pwm);
}

void Motores::configurar(int independente1, int mm1_a,int mm1_b,int  mm1_pwm,int  mm2_a,int  mm2_b,int  mm2_pwm) {
  independente=independente1;
  m1_a=mm1_a;
  m1_b=mm1_b;
  m1_pwm=mm1_pwm;
  m2_a=mm2_a;
  m2_b=mm2_b;
  m2_pwm=mm2_pwm;

  pinMode(mm1_a, OUTPUT);
  pinMode(mm1_b, OUTPUT);
  pinMode(mm2_a, OUTPUT);
  pinMode(mm2_b, OUTPUT);
}


void Motores::girar(int intensidade, int sentido1) {
  noInterrupts();
  analogWrite(m2_pwm,intensidade*30);
  if(independente==1) analogWrite(m1_pwm,intensidade*30);
  digitalWrite(m1_a,sentido1);
  digitalWrite(m1_b,!sentido1);
  digitalWrite(m2_a,!sentido1);
  digitalWrite(m2_b,sentido1);
  interrupts();
}

void Motores::girar(int intensidade) {
  noInterrupts();
  int sentido1=random(0,2);
  girar(intensidade, sentido1);
  interrupts();
}
void  Motores::parar() {  
  noInterrupts();
  analogWrite(m1_pwm,0);
  analogWrite(m2_pwm,0);
  interrupts();
}
void Motores::frente_re(int intensidade, int sentido) {
  noInterrupts();
  analogWrite(m1_pwm,intensidade*30);
  if(independente==1) analogWrite(m2_pwm,intensidade*30);
  else analogWrite(m2_pwm,0);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  digitalWrite(m2_a,sentido);
  digitalWrite(m2_b,!sentido);
  interrupts();

}

void Motores::frente(int intensidade) {
  frente_re(intensidade, 0);
}

void Motores::re(int intensidade) {
  frente_re(intensidade, 1);
}

void Motores::lados(int intensidade, int lado, int sentido) {
  noInterrupts();  
  analogWrite(m1_pwm,lado==0 ? intensidade*30 : 15*intensidade);
  if(independente==1) analogWrite(m2_pwm,lado==0 ? 15*intensidade : 30*intensidade);
  else analogWrite(m2_pwm,30*intensidade);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  if(independente==1) {
    digitalWrite(m2_a,sentido);
    digitalWrite(m2_b,!sentido);
  }
  else {
    digitalWrite(m2_a,lado==0 ?  !sentido : sentido);
    digitalWrite(m2_b,lado==0 ?  sentido : !sentido);
  }
  interrupts();

}
void Motores::esquerda(int intensidade) {
  lados(intensidade,0,0);

}
void  Motores::direita(int intensidade) {
  lados(intensidade,1,0);

}

void  Motores::reEsquerda(int intensidade) {
  lados(intensidade,0,1);

}

void  Motores::reDireita(int intensidade) {
  lados(intensidade,1,1);

}

void Motores::movimentoAleatorio(int intensidade) {
  int m = random(1,9);
  if(m==1) frente(intensidade);
  if(m==2) re(intensidade);
  if(m==3) esquerda(intensidade);
  if(m==4) direita(intensidade);
  if(m==5) reEsquerda(intensidade);
  if(m==6) reDireita(intensidade);
  if(m==7) girar(intensidade, 0);
  if(m==8) girar(intensidade, 1);
}

