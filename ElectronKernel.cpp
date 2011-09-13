//multimodo via serial
#include <ElectronKernel.h>

#ifndef WIRING_H
#include <wiring.h>
#endif
#include "HardwareSerial.h"

ElectronKernel Kernel;

long lastDebounce=0;
long debounceDelay=50;

void changeMode() {
    Kernel.lastMode=Kernel.mode;
    Kernel.mode = Kernel.mode==Kernel.numberOfModes-1 ? 0 : Kernel.mode + 1;
}

void changeModeWithDebounce() {
  if(millis()-lastDebounce>debounceDelay) {
    changeMode();
    lastDebounce=millis();
  } 
}

void ElectronKernel::setup() {
  setup(2); //default SERIAL
}

void ElectronKernel::setup(int operationMode1) {
  
  operationMode = operationMode1;
  
  if(operationMode==ALL || operationMode==INTERRUPTION) {
    attachInterrupt(interruptionNumber, changeModeWithDebounce, LOW);
  }

}

void ElectronKernel::wait(long milis) {
  if(milis<50) delay(milis);
  else {
    for(long x=0;x<milis/50;x++) {
      delay(50);
      checkModeChange();
      if(mode!=lastMode) return;
    }
    delay(milis%50);
  }
}

void ElectronKernel::registerChangeModeMethod(ISR changeModeMethod) {
  checkChangeModeMethods[numberOfCheckChangeModeMethods++]=changeModeMethod;
}
void ElectronKernel::changeInterruptionModeFunction(void(*newISR)()) {
  attachInterrupt(interruptionNumber, newISR, LOW);

}
ElectronKernel::ElectronKernel() {
  mode=0;
  lastMode=-1;
  numberOfModes=0;
  numberOfChangeModeListener=0;
  interruptionNumber=0;
}

void ElectronKernel::registerMode(int modeNumber, void(*modeSetup)()) {
  Mode modeNew(modeSetup);
  modes[modeNumber]=modeNew;
  numberOfModes++;
}
void ElectronKernel::registerMode(int modeNumber, char modeName[], void(*modeSetup)()) {
  Mode modeNew(modeSetup, modeName);
  modes[modeNumber]=modeNew;
  numberOfModes++;
}
void ElectronKernel::registerMode(int modeNumber, char modeName[]) {
  Mode modeNew(modeName);
  modes[modeNumber]=modeNew;
  numberOfModes++;
}
void ElectronKernel::registerMode(char modeName[]) {
  Mode modeNew(modeName);
  modes[numberOfModes++]=modeNew;  
}
void ElectronKernel::registerMode(char modeName[], void(*modeSetup)()) {
  Mode modeNew(modeSetup, modeName);
  modes[numberOfModes++]=modeNew;
}

//Allow to register a task to the last registred mode
Mode ElectronKernel::getCurrentMode() {
  return modes[mode];
}
void ElectronKernel::registerTask(void(*taskFunction)()) {
  modes[numberOfModes-1].registerTask(taskFunction);
}

void ElectronKernel::registerTask(int modeNumber, void(*taskFunction)()) {
  modes[modeNumber].registerTask(taskFunction);
}

void ElectronKernel::registerChangeModeListener(void(*event)()) {
  changeModeListeners[numberOfChangeModeListener]=event;
  numberOfChangeModeListener++;
}

void ElectronKernel::loop() {

  checkModeChange();

  while(lastMode!=mode) {
    callChangeModeListeners();
    lastMode=mode;
    checkModeChange();
    modes[mode].callSetup();
    checkModeChange();
  }
  lastMode=mode;
  for(int x=0;x<getCurrentMode().numberOfTasks && lastMode==mode;x++) {
    getCurrentMode().tasks[x].callTask();
    checkModeChange();
  }  
}
void ElectronKernel::checkModeChange() {
  if(operationMode==0 || operationMode==2) {
 
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
  }
 
}	


void ElectronKernel::callChangeModeListeners() {
 for(int x=0;x<numberOfChangeModeListener;x++) {
   changeModeListeners[x](); 
  }
}

int ElectronKernel::abortMode() { 
  return mode!=lastMode;
}
