#ifndef WIRING_H
#include <Servo.h>
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

enum Servos {  servo_comum};

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
  byte sensorDistancia1;
  byte sensorDistancia2;
  byte speaker;
};

class DevBot {

private:
  
public:
  Servo servo;
  Portas portas;
  Motores motores;
  Modo modos[5];
  Placa placa;
  Sensor sensor;
  int temServo;

  int modoAtual;
  int ultimoModo;
  int contadorModos;
  byte modoMudou;

  DevBot();
  void iniciar();
  void configurar(Bateria bateria);
  void configurar(Motor motor);
  void configurar(Placa placa);
  void configurar(Portas portas);
  void configurar(Servos servo);
  void configurar(Sensor sensor);
  void esperar(long);
  void loop();
  void beep(int);
  byte mudouModo();
  void checarMudancaModo();
  void modo(int numeroModo, void(*modo)());
  long sensorDistancia1();
  long sensorLuz();
  long sensorTemperatura();
  void mudarServo(int posicao);
  void enviar(int);
  void enviar(long);
  void enviar(char);
  void enviar(char*);

  int recebeuComando(char*);
  void controleRemoto();
  char* receber();
  int  recebeuDados();
};

extern DevBot robot;
