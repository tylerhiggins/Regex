import java.io.*;
import java.util.Stack;
import java.util.Scanner;

public class Regex {

  public static void main(String[] args){
    // Load up the file and place expressions on the stack
    //Stack<String> expressions = readRegex();
    Scanner keyboard = new Scanner(System.in);
    String expressions = keyboard.nextLine();
      System.out.println("Transitions for postfix regex " + expressions+":");
      createNFA(expressions);
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
    NFA newNFA;
    Transition changeState;
    int newFinal = 0;
    for(int i = 0; i < expression.length(); i++){
      c = expression.charAt(i);
      if(c == '\n'){
        break;
      }
      try{
         if ((c < 'a' || c > 'e') && c != 'E' && c != '&' && c != '|' && c != '*'){
          throw new Exception("\nCharacter " + c + " is not part of the language.");
        }
        System.out.print(c + ", ");
        /*else {
          if (c == '&') {
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            newFinal = fa1.getFinal()+fa2.getFinal();
            newNFA = new NFA(0, newFinal);
            for(int j = 0; j < fa1.t_list.size(); j++){
              newNFA.t_list.push(fa1.t_list.get(j));

            }
            for (int j = 0; j < fa2.t_list.size(); j++){
              changeState = fa2.t_list.get(j);
              changeState.setState_1(changeState.getState_1()+fa1.getFinal());
              changeState.setState_2(changeState.getState_2()+fa1.getFinal());
              newNFA.t_list.push(changeState);
            }


          }
          else if (c == '|'){
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            //nfa.push();
          }
          else if (c == '*'){
            fa1 = nfa.pop();
            //nfa.push();
          }
          else {
            newNFA = new NFA(0,1);
            //newNFA.addTransition(0,1,c);
           //nfa.push(newNFA);

          }
        }*/

      } catch(Exception ex){
        System.out.println(ex.getMessage());
        System.exit(1);
      }

    }
  }

}
