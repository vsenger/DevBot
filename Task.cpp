#include <Task.h>

Task::Task(ISR f) {
  taskFunction=f;
}
Task::Task() {

}

void Task::callTask() {
  taskFunction();
}

