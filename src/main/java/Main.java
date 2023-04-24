import brown.Brown;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        try{
            Brown brown = new Brown(args[0]);
            brown.start(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
            System.out.println("PLAYER A");
            System.out.println("Number of occurrences: "+brown.numberOfStrategy("A"));
            System.out.println("Frequency of strategies: "+brown.frequencyOfStrategy("A"));
            System.out.println("Value of the game: " + Arrays.toString(brown.gameValue("A")));

            System.out.println("PLAYER B");
            System.out.println("Number of occurrences: "+brown.numberOfStrategy("B"));
            System.out.println("Frequency of strategies: "+brown.frequencyOfStrategy("B"));
            System.out.println("Value of the game: " + Arrays.toString(brown.gameValue("B")));

        } catch (IOException e){
            System.out.println("Missing or incorrect path to file. Path should be first argument");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Missing or incorrect arguments. Try giving the number of iterations first then the first strategy");
        } catch (IndexOutOfBoundsException e){
            System.out.println("Strategy don't exist.");
        } catch( Exception e){
            System.out.println("Unknown error.");
            e.printStackTrace();
        }



    }
}
