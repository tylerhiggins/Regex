import java.io.*;
import java.util.Stack;

public class Regex {

  public static void main(String[] args){
    // Load up the file and place expressions on the stack
    Stack<String> expressions = readRegex();
    // Loop through each expression on the stack and generate a transition list.
    for (int i = 0; i < expressions.size();i++) {
      System.out.println("Transitions for postfix regex " + expressions.get(i) + ":");
      createNFA(expressions.get(i));
      System.out.println();
    }
  }
  // Used to open the file
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
  // this method is used to generate the nfa.
  public static void createNFA(String expression) {
    char c;    // variable to read the character
    Stack<NFA> nfa = new Stack<NFA>();  // stack for each nfa
    NFA fa1;  // Create two nfa instances
    NFA fa2; 
    NFA newNFA;  // used for the new nfa to be pushed onto the stack.
    Transition changeState;  // used to manipulate the transition.
    int newFinal = 0;        // used to create a new final state.
    for(int i = 0; i < expression.length(); i++){
      c = expression.charAt(i);
      if(c == '\n'){
        break;
      }
      // tests to make sure character is part of the language, or if its a valid
      // manipulator.
      try{
         if ((c < 'a' || c > 'e') && c != 'E' && c != '&' && c != '|' && c != '*'){
          throw new Exception("\nCharacter " + c + " is not part of the language.");
        }
          // Concantination
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
            nfa.push(newNFA);
          }// Union
          else if (c == '|'){
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            newNFA = new NFA(fa1.getStart(),fa2.getFinal());
            for(int j = 0; j < fa1.t_list.size(); j++){
              newNFA.t_list.push(fa1.t_list.get(j));
            }
            for(int j = 0; j < fa2.t_list.size(); j++){
              newNFA.t_list.push(fa2.t_list.get(j));
            }
            nfa.push(newNFA);
          } // Repeat.
          else if (c == '*'){
            fa2 = nfa.pop();
            newNFA = new NFA(fa2.getStart(),fa2.getFinal());
            for (int j = 0; j < fa2.t_list.size(); j++){
              newNFA.t_list.push(fa2.t_list.get(j));
            }
            newNFA.t_list.push(new Transition(newNFA.getFinal(),newNFA.getStart(),'E'));
            nfa.push(newNFA);
          }
          // character.
          else {
            newNFA = new NFA(0,1);
            newNFA.t_list.push(new Transition(0,1,c));
           nfa.push(newNFA);
          }
        // catches character error and exits program.
      } catch(Exception ex){
        System.out.println(ex.getMessage());
        System.exit(1);
      }
    }
    // displays the resulting NFA(s)
    for(int j = 0; j < nfa.size(); j++){
      NFA display = nfa.get(j);
      for(int i = 0; i < display.t_list.size(); i++){
        if(display.getStart() == display.t_list.get(i).getState_1()){
          System.out.println("S (q"+display.t_list.get(i).getState_1()+
              ", "+ display.t_list.get(i).getSymbol()+") -> q"+display.t_list.get(i).getState_2());
        }
        else if(display.getFinal() == display.t_list.get(i).getState_1()){
          System.out.println("F (q"+display.t_list.get(i).getState_1()+
              ", "+ display.t_list.get(i).getSymbol()+") -> q"+display.t_list.get(i).getState_2());
        }
        else{
          System.out.println("(q"+display.t_list.get(i).getState_1()+
              ", "+ display.t_list.get(i).getSymbol()+") -> q"+display.t_list.get(i).getState_2());
        }
      }
      // Outputs the final (Accepting) State
      System.out.println("F (q"+ display.getFinal()+", E)");
    }
    }

}
