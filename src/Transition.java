public class Transition {
  private int state_1;
  private int state_2;
  private String symbol;

  public Transition(int s1, int s2, String sym){
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

  public String getSymbol(){
    return symbol;
  }
}
