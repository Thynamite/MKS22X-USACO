import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class USACO{

  public static int bronze(String filename){
    try {
      File f = new File(filename);
      bSolve b;
      b = new bSolve(f);

    }
    catch (FileNotFoundException f) {

    }

    return 1;
  }


  public static class bSolve {

    int[][] field;
    ArrayList<Integer> firstLine;
    ArrayList<Integer> fieldElevation;
    ArrayList<String> trampleInstruct;
    public bSolve(File f) throws FileNotFoundException {
      Scanner s = new Scanner(f);
      String first = s.nextLine();
      int start = 0;
      for (int x = 0; x < first.length(); x++) {
        if (first.charAt(x) == ' ') {
          String value = first.substring(start,x);
          firstLine.add(new Integer(value));
          start = x+1;
        }
      }
      field = new int[firstLine.get(0)][firstLine.get(1)]; //field to given size

      //get the first value
      for (int x = 0; x < firstLine.get(0);x++) {  //i repeated this, cause im dumb
        String row = s.nextLine();
        int starter = 0;
        for (int y = 0; y < row.length(); y++) {
          if (row.charAt(y) == ' '){
            String value = row.substring(starter,y);
            fieldElevation.add(new Integer(value));
            start = y+1;
          }
        }
      }
      //fill array
      //next line the instructions into trampleInstruct
    }

    public int solve() {

      //run the other lines of instructions
      int sum = 0;
      for (int x = 0; x < field.length; x++) {
        for (int y = 0; y < field[x].length; y++) {
          if (field[x][y] != -1) {
            sum += field[x][y];
          }
        }
      }
      sum*= 72 * 72;
      return sum;
    }
    private void stampDown(int row, int col) {
      ArrayList<Integer> fieldAffect = new ArrayList<Integer>();  //holds the coordinates of all the plots to be affected
      ArrayList<Integer> fieldToAffect = new ArrayList<Integer>(); //holds the equal elevation plot to be affected
      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          if (row + x < field.length && col + y < field[row].length) {
            fieldAffect.add(row+x);
            fieldAffect.add(col+y);
          }
        }
      }
      int[] high = findHighest(fieldAffect);

      for (int x = 0; x < fieldAffect.size(); x+= 2) {

        if (field[fieldAffect.get(x)][fieldAffect.get(x+1)] == field[high[0]][high[1]]){
          fieldToAffect.add(fieldAffect.get(x));
          fieldToAffect.add(fieldAffect.get(x+1));
        }
      }
      for (int x = 0; x < fieldToAffect.size(); x+=2) {
        lower(x,x+1);
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

    private void convertToDepth(int elevation){
      //change array to be like this, in case of needing to see this step
      //not sure what equal elevation of water would entail, water or land?
      for (int x = 0; x < field.length; x++) {
        for (int y = 0; y < field[x].length; y++) {
          if (field[x][y] >= elevation) {
            field[x][y] = -1;
          }
          else {
            field[x][y] = elevation - field[x][y];
          }
        }
      }
    }
  }
}
