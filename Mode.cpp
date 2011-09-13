#include <Mode.h>
#include "HardwareSerial.h"
Mode::Mode(ISR setup) {
  setupFunction=setup;

}
Mode::Mode() {
  numberOfTasks=0;
  hasSetup=0;	
}
Mode::Mode(char name[]){
  //modeName=name;
  numberOfTasks=0;
  hasSetup=0;	
}

Mode::Mode(ISR setup, char name[]){
  hasSetup=1;
  setupFunction=setup;
  //modeName=name;
  numberOfTasks=0;
}
void Mode::callSetup(){
  if(hasSetup) setupFunction();
}
void Mode::registerTask(ISR functionTask){
  Task newTask(functionTask);
  tasks[numberOfTasks++]=newTask;
}
