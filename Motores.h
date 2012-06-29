
class Motores {
  private:

  public:
    int m1_a, m1_b, m1_pwm, m2_a, m2_b, m2_pwm;
    Motores();
    Motores(int, int, int, int, int, int);
    void configurar(int, int, int, int, int, int);
    void girar(int intensidade);

    void girar(int intensidade, int sentido);
    void parar();
    void frente(int intensidade);
    void re(int intensidade);
    void esquerda(int intensidade);
    void direita(int intensidade);
    void reEsquerda(int intensidade);
    void reDireita(int intensidade);
    void movimentoAleatorio(int intensidade);
};



