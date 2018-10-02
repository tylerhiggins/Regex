import java.io.*;

public class Regex {

  public static void main(String[] args){
    String file = "regexpressions.txt";
    String regex = null;

    try {
      FileReader reader = new FileReader(file);
      BufferedReader buffRead = new BufferedReader(reader);
      while((regex = buffRead.readLine()) != null){
        System.out.println(regex);
      }
      reader.close();
    }
    catch (FileNotFoundException fex){
      System.out.println("ERROR: Could not find file '" + file +"'.");
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }
    // Stack<NFA> nfa_stack = new Stack<NFA>();
  }

}
