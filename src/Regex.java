import java.io.*;
import java.util.Stack;

public class Regex {

  public static void main(String[] args){
    Stack<String> expressions = readRegex();
    Stack<NFA> nfa = new Stack<NFA>();
    NFA fa1;
    NFA fa2;
    char c;
    for (int i = 0; i < expressions.size(); i++){
      for(int j = 0; j < expressions.get(i).length(); j++){
        c = expressions.get(i).charAt(j);
        if(c == '\n'){
          break;
        }
        else {
          if (c == '&') {
            fa1 = nfa.pop();
            fa2 = nfa.pop();
            nfa.push()
          }
        }
      }
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

}
