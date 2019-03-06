import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class USACO{

  public static int bronze(String filename){

    return 1;
  }

  private int bronzeH(File filename, int[] start) {
    return 1;
  }


  public class bronzeSolve{

    int[][] field;
    ArrayList<int> firstLine;
    public bronzeSolve(File f) {
      Scanner s = new Scanner(f);
      String first = s.nextLine();
      for (int x = 0; x < first.length; x++) {
        //put firstline into firstline
      }
      //make an array to dimensions given
      //fill array
    }

    private void stampDown(int row, int col, int inches) {
      ArrayList<int> fieldAffect;  //holds the coordinates of all the plots to be affected
      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          if (row + x < field.length && col + y < field[row].length) {
            fieldAffect.add(row+x);
            fieldAffect.add(col+y);
          }
        }
      }
      
    }
  }
}
