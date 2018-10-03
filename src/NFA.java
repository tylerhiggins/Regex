import java.util.Stack;
public class NFA {

  private int start;
  private int final_s;
  private String symbol;
  private Stack<Transition> t_list;

  public NFA(int s, int f, String sym){
    start = s;
    final_s = f;
    symbol = sym;
    t_list = new Stack<Transition>();
    addTransition(start, final_s, symbol);
  }

  public int getStart(){ return start; }

  public int getFinal() {
    return final_s;
  }

  public String getSymbol() { return symbol; }

  public void addTransition(int first, int next, String character) {
    t_list.push(new Transition(first,next,character));

  }
}
