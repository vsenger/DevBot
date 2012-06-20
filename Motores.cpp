#include "Motores.h"
#ifndef WIRING_H
#include <Arduino.h>
#endif
int sentido=0;

Motores::Motores() {
  configurar(7,8,10,12,13,11);
}

Motores::Motores(int mm1_a,int mm1_b,int  mm1_pwm,int  mm2_a,int  mm2_b,int  mm2_pwm) {
  configurar(mm1_a,mm1_b,mm1_pwm,mm2_a,mm2_b,mm2_pwm);
}

void Motores::configurar(int mm1_a,int mm1_b,int  mm1_pwm,int  mm2_a,int  mm2_b,int  mm2_pwm) {
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
  analogWrite(m1_pwm,intensidade*50);
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,sentido1);
  digitalWrite(m1_b,!sentido1);
  digitalWrite(m2_a,!sentido1);
  digitalWrite(m2_b,sentido1);
}

void Motores::girar(int intensidade) {
  int sentido1=random(0,2);
  analogWrite(m1_pwm,intensidade*50);
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,sentido1);
  digitalWrite(m1_b,!sentido1);
  digitalWrite(m2_a,!sentido1);
  digitalWrite(m2_b,sentido1);
}
void  Motores::parar() {  
  analogWrite(m1_pwm,0 );
  analogWrite(m2_pwm,0);
}

void  Motores::frente(int intensidade) {
  sentido = 1;
  analogWrite(m1_pwm,intensidade*50);
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  digitalWrite(m2_a,sentido);
  digitalWrite(m2_b,!sentido);
}
void  Motores::re(int intensidade) {
  sentido = 0;
  analogWrite(m1_pwm,intensidade*50 );
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  digitalWrite(m2_a,sentido);
  digitalWrite(m2_b,!sentido);
}
void  Motores::frenteEsquerda(int intensidade) {
  analogWrite(m1_pwm,intensidade*50 );
  analogWrite(m2_pwm,10*intensidade);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  digitalWrite(m2_a,sentido);
  digitalWrite(m2_b,!sentido);

}
void  Motores::frenteDireita(int intensidade) {
  analogWrite(m1_pwm,10*intensidade );
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,sentido);
  digitalWrite(m1_b,!sentido);
  digitalWrite(m2_a,sentido);
  digitalWrite(m2_b,!sentido);

}

void  Motores::reEsquerda(int intensidade) {
  analogWrite(m1_pwm,intensidade*50 );
  analogWrite(m2_pwm,10*intensidade);
  digitalWrite(m1_a,!sentido);
  digitalWrite(m1_b,sentido);
  digitalWrite(m2_a,!sentido);
  digitalWrite(m2_b,sentido);


}
void  Motores::reDireita(int intensidade) {
  analogWrite(m1_pwm,10*intensidade );
  analogWrite(m2_pwm,intensidade*50);
  digitalWrite(m1_a,!sentido);
  digitalWrite(m1_b,sentido);
  digitalWrite(m2_a,!sentido);
  digitalWrite(m2_b,sentido);

}

void Motores::movimentoAleatorio(int intensidade) {
  int m = random(1,9);
  if(m==1) frente(intensidade);
  if(m==2) re(intensidade);
  if(m==3) frenteEsquerda(intensidade);
  if(m==4) frenteDireita(intensidade);
  if(m==5) reEsquerda(intensidade);
  if(m==6) reDireita(intensidade);
  if(m==7) girar(intensidade, 0);
  if(m==8) girar(intensidade, 1);
}

