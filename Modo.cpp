#include <Modo.h>
#include "HardwareSerial.h"
Modo::Modo() {
 hasSetup=0;
}

Modo::Modo(ISR mode) {
  modeFunction=mode;
  hasSetup=0;
}
Modo::Modo(ISR mode, ISR setup) {
  modeFunction=mode;
  setupFunction=setup;
  hasSetup=1;
}

void Modo::configurar(){
  if(hasSetup) setupFunction();
}
void Modo::executar(){
  modeFunction();
}

