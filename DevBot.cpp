#include <DevBot.h>
#ifndef WIRING_H
#include <Arduino.h>
#endif
#include "HardwareSerial.h"

DevBot robot;
volatile long lastDebounce=0;
volatile long debounceDelay=50;
volatile long ntime=0;

void mudarModoPeloBotao() {

  if(millis()-lastDebounce>debounceDelay && ntime++>=100) {
  //if(lastDebounce>debounceDelay) {
    robot.ultimoModo=robot.modoAtual;
    robot.modoAtual = robot.modoAtual==robot.contadorModos-1 ? 0 : robot.modoAtual + 1;
    //robot.motores.parar();
    ntime=0;
    lastDebounce=millis();
  }
}
long DevBot::sensorLuz() {
  return analogRead(3);
}
long DevBot::sensorTemperatura() {
  return analogRead(2);
}      
void DevBot::mudarServo(int posicao) {
  /*servo.attach(5);
  delay(50);
  servo.write(posicao);
  delay(500);
  servo.detach();*/
}
DevBot::DevBot() {
  modoAtual=0;
  contadorModos=0;
  ultimoModo=0;
  portaSensorDistancia = 3;
  
}
void DevBot::iniciar() {
   attachInterrupt(0, mudarModoPeloBotao, LOW);
   /* welcome devbot code */
   Serial.begin(115200);
   pinMode(4, OUTPUT);
}

void DevBot::loop() { 
  if(ultimoModo!=modoAtual) {
    robot.motores.parar();
    ultimoModo = modoAtual;
    beep(modoAtual+1);
    modoMudou=1;
    modos[modoAtual].configurar();
  }
  modos[modoAtual].executar();
  modoMudou=0;
}

void DevBot::beep(int ntime) {
  for(int x=0;x<ntime;x++) {
    digitalWrite(4, HIGH);
    delay(100);
    digitalWrite(4, LOW);
    delay(100);
  }

}
void DevBot::modo(int numeroModo, void(*modo1)()) {
  Modo novoModo(modo1);
  modos[numeroModo]=novoModo;
  contadorModos++;

}

void DevBot::checarMudancaModo() {
  /*if(operationMode==0 || operationMode==2) {
    if(Serial.available()) { 
      byte lido;
      lastMode=mode;
      lido = Serial.read(); 
      char c=(char) lido;
      mode = atoi(&c);
    }
  }
  if(operationMode==0 || operationMode==3) {
    for(int x=0;x<numberOfCheckChangeModeMethods;x++) {
      checkChangeModeMethods[x]();
    }
  }*/ 
}

void DevBot::esperar(long milis) { 
  modoMudou=0;
  if(modoAtual!=ultimoModo) {
    modoMudou=1;
    return;
  }

  if(milis<10) delay(milis);
  else {
    for(long x=0;x<milis/10;x++) {
      delay(10);
      //checarMudancaModo();
      if(modoAtual!=ultimoModo) {
        modoMudou=1;
        return;
      }
    }
    delay(milis%10);
  }

}

byte DevBot::mudouModo() { return modoMudou;}
long DevBot::sensorDistancia() {
  pinMode(portaSensorDistancia, OUTPUT);
  digitalWrite(portaSensorDistancia, LOW);
  delayMicroseconds(2);
  digitalWrite(portaSensorDistancia, HIGH);
  delayMicroseconds(5);
  digitalWrite(portaSensorDistancia, LOW);

  pinMode(portaSensorDistancia, INPUT);
  long duration = pulseIn(portaSensorDistancia, HIGH);
  long cm = duration / 29 / 2;
  return cm;
}
void DevBot::configurar(Bateria b) { 

}
void DevBot::configurar(Motor b) { 

}
void DevBot::configurar(Placa b) {
  if(b==programME_v2) {
    //portas.xpto
  }
  else {
  }

}
void DevBot::configurar(Sensor sensor) { 

}

void DevBot::enviar(int x) { Serial.println(x);}
void DevBot::enviar(char x) { Serial.println(x);}
void DevBot::enviar(long x) { Serial.println(x);}
void DevBot::enviar(char* x) { Serial.println(x);}

/* 
 * Parte de recepção e envio de dados
 */
char comando[16];
int DevBot::recebeuDados() {  return Serial.available();}
char* DevBot::receber() {
  int counter=0;
  while(Serial.available()>0) // loop through all but the last
  {
    char c = Serial.read(); // receive byte as a character
    delay(1);
    comando[counter++]=c;
  }
  comando[counter]='\0';
  return comando;
}

