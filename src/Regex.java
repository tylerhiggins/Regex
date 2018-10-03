import java.io.*;
import java.util.Stack;

public class Regex {

  public static void main(String[] args){
    // Load up the file and place expressions on the stack
    Stack<String> expressions = readRegex();
    for (int i = 0; i < expressions.size(); i++){
      System.out.println("Transitions for postfix regex " + expressions.get(i)+":");
      createNFA(expressions.get(i));
    }
  }

  public static Stack<String> readRegex(){
    String file = "regexpressions.txt";
    String line = null;
    Stack<String> regex = new Stack<String>();

    try {
      FileReader reader = new FileReader(file);
      BufferedReader buffRead = new BufferedReader(reader);
      while((line = buffRead.readLine()) != null){
        regex.push(line);
      }
      reader.close();
    }
    catch (FileNotFoundException fex){
      System.out.println("ERROR: Could not find file '" + file +"'.");
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }

    return regex;
  }

  public static void createNFA(String expression) {
    char c;
    Stack<NFA> nfa = new Stack<NFA>();
    NFA fa1;  // Create two nfa instances
    NFA fa2;
    for(int i = 0; i < expression.length(); i++){
      c = expression.charAt(i);
      if(c == '\n'){
        break;
      }
      else {
        if (c == '&') {
          fa2 = nfa.pop();
          fa1 = nfa.pop();
          nfa.push(new NFA(fa1.getStart(), fa2.getFinal(), (fa1.getSymbol()+fa2.getSymbol()));
        }
        else if (c == '|'){
          fa2 = nfa.pop();
          fa1 = nfa.pop();
          nfa.push();
        }
        else if (c == '*'){
          fa1 = nfa.pop();
          nfa.push();
        }
        else {
          nfa.push(new NFA(0,1, Character.toString(c)));
        }
      }
    }
  }

}
