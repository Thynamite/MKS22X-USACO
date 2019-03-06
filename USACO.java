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


  public class bronzeSolve {

    int[][] field;
    ArrayList<Integer> firstLine;
    public bronzeSolve(File f) throws FileNotFoundException {
      Scanner s = new Scanner(f);
      String first = s.nextLine();
      for (int x = 0; x < first.length(); x++) {
        //put firstline into firstline
      }
      //make an array to dimensions given
      //fill array
    }

    private void stampDown(int row, int col, int inches) {
      ArrayList<Integer> fieldAffect = new ArrayList<Integer>();  //holds the coordinates of all the plots to be affected
      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          if (row + x < field.length && col + y < field[row].length) {
            fieldAffect.add(row+x);
            fieldAffect.add(col+y);
          }
        }
      }

    }

    private void lower(int row, int col) {
      field[row][col]--;
    }

    private int[] findHighest(ArrayList<Integer> help) {
      int[] me = new int[2];
      int r = help.get(0);
      int c = help.get(1);
      for (int x = 2; x < help.size(); x+=2) {
        if (field[help.get(x)][help.get(x+1)] > field[r][c]){
          r = help.get(x);
          c = help.get(x+1);
        }
      }
      me[0] = r;
      me[1] = c;
      return me;
    }
  }
}
