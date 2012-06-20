#ifndef WIRING_H
#include <Arduino.h>
#endif
#include "Modo.h"
#include "Motores.h"


enum Bateria { bateria_6_volts, 
                bateria_7_volts,
                bateria_9_volts,
                bateria_12_volts};

enum Motor   {  motor_6_volts, 
                motor_9_volts,
                motor_12_volts};

enum Placa   {  programME_v2, 
                arduino};

enum Sensor { sensor_distancia_parallax,
              sensor_distancia_tato,
              sensor_luz,
              sensor_temperatura};

struct Portas {
  byte motor1A;
  byte motor1B;
  byte motor1PWM;
  byte motor2A;
  byte motor2B;
  byte motor2PWM;
  byte servo;
  byte sensorLuz;
  byte sensorTemperatura;
  byte sensorDistancia;
};

class DevBot {

private:
  
public:
  DevBot();
  int modoAtual;
  int ultimoModo;
  int contadorModos;
  int portaSensorDistancia;
  Modo modos[5];
  Motores motores;
  Portas portas;
  void iniciar();
  void configurar(Bateria bateria);
  void configurar(Motor motor);
  void configurar(Placa placa);
  void configurar(Sensor sensor);
  void esperar(long);
  void loop();
  void checarMudancaModo();
  void modo(int numeroModo, void(*modo)());
  long sensorDistancia();
  long sensorLuz();
  long sensorTemperatura();
  void mudarServo(int posicao);
  void enviar(int);
  void enviar(long);
  void enviar(char);
  void enviar(char*);
  char* receber();
  int  recebeuDados();
  //void andar
};

extern DevBot robot;

