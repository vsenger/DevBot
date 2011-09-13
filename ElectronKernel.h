#define MAX 20

#define ALL                  0
#define INTERRUPTION         1
#define SERIAL               2
#define CUSTOM               3


#include "Mode.h"


class ElectronKernel {

private:
  
public:
  
  int mode;
  int lastMode;
  int operationMode;
  int numberOfModes;
  int numberOfChangeModeListener;
  int numberOfCheckChangeModeMethods;
  int interruptionNumber;
  typedef void (*ISR)();
  ISR changeModeListeners[MAX];
  ISR checkChangeModeMethods[MAX];

  Mode modes[MAX];
  void checkModeChange();
  void registerChangeModeListener(void(*)());
  void registerMode(int, void(*)());
  void registerMode(int, char*, void(*)());
  void registerMode(char*, void(*)());
  void registerMode(int, char*);
  void registerMode(char*);
  void registerTask(void(*)());
  void registerTask(int, void(*)());
  void registerChangeModeMethod(ISR);
  void changeInterruptionModeFunction(void(*)());
  void wait(long);
  ElectronKernel();
  void changeMode();

  void loop();
  int abortMode();
  void callChangeModeListeners();
  void setup();
  void setup(int);
  Mode getCurrentMode();
};





extern ElectronKernel Kernel;
