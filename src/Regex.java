import java.io.*;
import java.util.Stack;

public class Regex {

  public static void main(String[] args){
    // Load up the file and place expressions on the stack
    Stack<String> expressions = readRegex();
    for (int i = 0; i < expressions.size();i++) {
      System.out.println("Transitions for postfix regex " + expressions.get(i) + ":");
      createNFA(expressions.get(i));
    }
  }

  public static Stack<String> readRegex(){
    String file = "reg.txt";
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
          if (c == '&') {
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            newFinal = fa1.getFinal()+fa2.getFinal()+1;
            newNFA = new NFA(fa1.getStart(),newFinal);
            for (int j = 0; j < fa1.t_list.size(); j++){
              newNFA.t_list.push(fa1.t_list.get(j));
            }
            newNFA.t_list.push(new Transition(fa1.getFinal(),fa1.getFinal()+1,'E'));
            for (int j = 0; j < fa2.t_list.size(); j++){
              changeState = (fa2.t_list.get(j));
              changeState.setState_1(fa1.getFinal()+1+j);
              changeState.setState_2(fa1.getFinal()+2+j);
              newNFA.t_list.push(changeState);
            }
            nfa.push(newNFA);
          }
          else if (c == '|'){
            fa2 = nfa.pop();
            fa1 = nfa.pop();
            //newFinal =
            //nfa.push(newNFA);
          }
          else if (c == '*'){
            fa2 = nfa.pop();
            newNFA = new NFA(0,0);
            newNFA.t_list.push(new Transition(0,1,'E'));
            for (int j = 0; j < fa2.t_list.size(); j++){
              changeState = fa2.t_list.get(j);
              changeState.setState_1(j+1);
              changeState.setState_2(j+2);
              newNFA.t_list.push(changeState);
            }
            int finalInd = newNFA.t_list.size()-1;
            newNFA.t_list.push(new Transition(newNFA.t_list.get(finalInd).getState_2()
                ,newNFA.getStart(),'E'));
            nfa.push(newNFA);
          }
          else {
            newNFA = new NFA(0,3);
            newNFA.t_list.push(new Transition(0,1,'E'));
            newNFA.t_list.push(new Transition(1,2,c));
            newNFA.t_list.push(new Transition(2,3,'E'));
           nfa.push(newNFA);
          }

      } catch(Exception ex){
        System.out.println(ex.getMessage());
        System.exit(1);
      }
    }
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
