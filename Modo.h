
class Modo {

 private:
  typedef void (*ISR)();
  ISR setupFunction;
  ISR modeFunction;
  int hasSetup;
public:
   Modo();
   Modo(ISR);
   Modo(ISR, ISR);
   void executar();
   void configurar();

};

