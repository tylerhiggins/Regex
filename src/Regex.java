import java.io.*;
import java.util.Stack;

public class Regex {

  public static void main(String[] args){
    // Load up the file and place expressions on the stack
    Stack<String> expressions = readRegex();
    // for loop to loop through each expression in the stack
    for (int i = 0; i < expressions.size();i++) {
      System.out.println("Transitions for postfix regex " + expressions.get(i) + ":");
      createNFA(expressions.get(i));  // method to create the NFA
      System.out.println();
    }
  }
  // this builds the expressions stack from file.
  public static Stack<String> readRegex(){
    String file = "regexpressions.txt"; // name of file
    String line = null;
    Stack<String> regex = new Stack<String>();
    // ensures sucessful opening of file.
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
      System.exit(2);
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }

    return regex;
  }

  // builds the NFA from an expression
  public static void createNFA(String expression) {
    char c;
    Stack<NFA> nfa = new Stack<NFA>();
    NFA fa1;  // Create two nfa instances
    NFA fa2;
    NFA newNFA; // create a newNFA
    Transition changeState; // used to update state numbers
    int newFinal = 0;
    // loops through the expression.
    for(int i = 0; i < expression.length(); i++){
      c = expression.charAt(i);
      // newLine instance.
      if(c == '\n'){
        break;
      }
      // try-catch to determine valid expression.
      try{
        // only accepts the following characters.
         if ((c < 'a' || c > 'e') && c != 'E' && c != '&' && c != '|' && c != '*'){
          throw new Exception("\nCharacter " + c + " is not part of the language.");
        }
        // concatination case.
          if (c == '&') {
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            // calculate the new final state by adding each old final state
            newFinal = fa1.getFinal()+fa2.getFinal()+1;
            // create the new concatinated NFA.
            newNFA = new NFA(fa1.getStart(),newFinal);
            // just push the first NFA onto the transition stack.
            for (int j = 0; j < fa1.t_list.size(); j++){
              newNFA.t_list.push(fa1.t_list.get(j));
            }
            // push an epsilon transition onto the stack from the first nfa to the other nfa
            newNFA.t_list.push(new Transition(fa1.getFinal(),fa1.getFinal()+1,'E'));
            // change state numbers on nfa2.
            for (int j = 0; j < fa2.t_list.size(); j++){
              changeState = (fa2.t_list.get(j));
              changeState.setState_1(fa1.getFinal()+1+j);
              changeState.setState_2(fa1.getFinal()+2+j);
              newNFA.t_list.push(changeState);
            }
            // push the newnfa onto the stack
            nfa.push(newNFA);
          }
          // union case
          else if (c == '|'){
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            int fa1NewLast = fa1.t_list.size();  // get the last number of the 1st nfa
            // generate the new nfa2 last number
            int fa2NewLast = fa1NewLast+fa2.t_list.size()+1;
            // used for a new final state
            newFinal = fa2NewLast+1;
            newNFA = new NFA(0,newFinal);
            // push a new start state transition
            newNFA.t_list.push(new Transition(0,1,'E'));
            // add the transitions from nfa1
            for (int j = 0; j < fa1.t_list.size();j++){
              changeState = fa1.t_list.get(j);
              changeState.setState_1(changeState.getState_1()+1);
              changeState.setState_2(changeState.getState_2()+1);
              newNFA.t_list.push(changeState);
            }
            // push a transition from nfa1 to the new final state of the
            // unionized nfa
            newNFA.t_list.push(new Transition(fa1NewLast, newFinal,'E'));
            // push a new transition from tne new start state to nfa2's start
            newNFA.t_list.push(new Transition(0,fa1NewLast+1,'E'));
            // push all of nfa2's transitions onto the stack
            for(int j =0; j < fa2.t_list.size(); j++){
              changeState = fa2.t_list.get(j);
              changeState.setState_1(changeState.getState_1()+fa1NewLast+1);
              changeState.setState_2(changeState.getState_2()+fa1NewLast+1);
              newNFA.t_list.push(changeState);
            }
            // pushes the last state of nfa2 to the new final state
            newNFA.t_list.push(new Transition(fa2NewLast,newFinal,'E'));

            nfa.push(newNFA);
          }
          // star case
          else if (c == '*'){
            // just need one nfa
            fa2 = nfa.pop();
            // generate a new starting state, also make it the final state
            newNFA = new NFA(0,0);
            // push a new transition from the old start state to the new one.
            newNFA.t_list.push(new Transition(0,1,'E'));
            // push all transitions from the nfa to the stack
            for (int j = 0; j < fa2.t_list.size();j++){
              changeState = fa2.t_list.get(j);
              changeState.setState_1(changeState.getState_1()+1);
              changeState.setState_2(changeState.getState_2()+1);
              newNFA.t_list.push(changeState);
            }
            // push a transition from the last state to the first state.
            newNFA.t_list.push(new Transition(newNFA.t_list.get(newNFA.t_list.size()-1).getState_2(),
                0,'E'));
            // push the nfa onto the stack
            nfa.push(newNFA);
          }
          // single character case.
          else {
            newNFA = new NFA(0,3);
            newNFA.t_list.push(new Transition(0,1,'E'));
            newNFA.t_list.push(new Transition(1,2,c));
            newNFA.t_list.push(new Transition(2,3,'E'));
           nfa.push(newNFA);
          }
          // catch statement in case the character is not part of the language.
      } catch(Exception ex){
        System.out.println(ex.getMessage());
        System.exit(1);
      }
    }
    // prints all transitions of the expression.
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
      System.out.println("F (q"+ display.getFinal()+", E)");
    }
    }

}
