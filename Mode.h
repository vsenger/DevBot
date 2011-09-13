#include "Task.h"

class Mode {

 private:
  typedef void (*ISR)();
  ISR setupFunction;
  int hasSetup;
public:
   char modeName[];
   Task tasks[10];
   int numberOfTasks;
   Mode();
   Mode(ISR);
   Mode(ISR, char[]);
   Mode(char[]);
   void callSetup();
   void registerTask(ISR functionTask);

};

