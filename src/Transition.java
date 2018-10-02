import java.util.Stack;

public class Transition {
  private int state_1;
  private int state_2;
  private char symbol;
  private Stack<Transition> t_list;

  public Transition(int s1, int s2, char sym){
    state_1 = s1;
    state_2 = s2;
    symbol = sym;
    t_list = new Stack<Transition>();

  }

  public int getState_1(){
    return state_1;
  }

  public int getState_2(){
    return state_2;
  }

  public char getSymbol(){
    return symbol;
  }
}
