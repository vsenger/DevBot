class Task {

  private:
      typedef void (*ISR)();
      ISR taskFunction;

  public:
      Task(ISR);
      Task();
      void callTask();
};
