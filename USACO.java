import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class USACO{
  public static void main(String[] args) {
    System.out.println(bronze(args[0]));
  }
  public static int bronze(String filename){
    try {
      File f = new File(filename);
      bSolve b;
      b = new bSolve(f);
      return b.solve();
    }
    catch (FileNotFoundException f) {

    }
    return -1;
  }


  public static class bSolve {

    int[][] field;
    ArrayList<Integer> firstLine;
    ArrayList<Integer> fieldElevation = new ArrayList<Integer>();
    ArrayList<String> trampleInstruct = new ArrayList<String>();

    public bSolve(File f) throws FileNotFoundException {
      Scanner s = new Scanner(f);
      String first = s.nextLine();
      firstLine = convertStringToValues(first);
      field = new int[firstLine.get(0)][firstLine.get(1)]; //field to given size

      for (int x = 0; x < firstLine.get(0);x++) {
        String row = s.nextLine();
        //System.out.println(row);
        ArrayList<Integer> g = convertStringToValues(row);
        //System.out.println(g);
        for (int i = 0; i < g.size(); i++) {
          fieldElevation.add(g.get(i));
        }
        //fieldElevation.addAll(g); idk why addall doesn't work
      }

      int index = 0;
      for (int r = 0; r < field.length; r++) {
        for (int c = 0; c < field[r].length; c++) {
          field[r][c] = fieldElevation.get(index);
          index++;
        }
      }

      for (int x = 0; x < firstLine.get(3); x++) {
        trampleInstruct.add(s.nextLine());
      }

    }

    public int solve() {

      for (int x = 0; x < trampleInstruct.size(); x++) {
        ArrayList<Integer> instruct = convertStringToValues(trampleInstruct.get(x));
        for (int inch = 0; inch < instruct.get(2); inch++) {
          stampDown(instruct.get(0),instruct.get(1));
        }
      }
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

    private static ArrayList<Integer> convertStringToValues(String soap) {
      int starter = 0;
      ArrayList<Integer> help = new ArrayList<Integer>();

      for (int x = 0; x < soap.length(); x++) {
        if (soap.charAt(x) == ' ') {
          String value = soap.substring(starter,x);
          help.add(new Integer(value));
          starter = x+1;
        }
      }

      for (int x = soap.length()-1; x > -1; x--){
        if (soap.charAt(x) == ' '){
          String value = soap.substring(x+1);
          help.add(new Integer(value));
          x = -450;
        }
      }
      return help;
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
