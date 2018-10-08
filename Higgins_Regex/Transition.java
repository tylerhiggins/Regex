public class Transition {
  private int state_1;
  private int state_2;
  private char symbol;


  public Transition(int s1, int s2, char sym){
    state_1 = s1;
    state_2 = s2;
    symbol = sym;

  }

  public int getState_1(){
    return state_1;
  }

  public int getState_2(){
    return state_2;
  }

  public void setState_1(int s1){ state_1 = s1; }

  public void setState_2(int s2){ state_2 = s2; }

  public char getSymbol(){
    return symbol;
  }
}
