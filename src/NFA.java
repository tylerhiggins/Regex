import java.util.Stack;
public class NFA {

  private int start;
  private int final_s;
  private Stack<Transition> t_list;

  public NFA(int s, int f){
    start = s;
    final_s = f;
    t_list = new Stack<Transition>();
  }

  public void makeTransition() {

  }

  public int getStart(){
    return start;
  }

  public int getFinal() {
    return final_s;
  }

  public void addTransition() {

  }
}
