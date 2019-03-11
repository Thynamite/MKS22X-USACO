import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class USACO{
  /* testing purposes
  public static void main(String[] args) {
    System.out.println(bronze(args[0]));
  }
  */
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

    int[][] field;  //will hold all the elevations
    ArrayList<Integer> firstLine;  //holds the integers that determine the setup
    ArrayList<Integer> fieldElevation = new ArrayList<Integer>();   //array that reads the strings that hold the elevation to be placed into field
    ArrayList<String> trampleInstruct = new ArrayList<String>();   //holds the strings of the trample instructions

    public bSolve(File f) throws FileNotFoundException {   //constructor that fills the instances
      Scanner s = new Scanner(f);
      String first = s.nextLine();

      //fills the arraylist with the ints
      firstLine = convertStringToValues(first);
      field = new int[firstLine.get(0)][firstLine.get(1)]; //field to given size

      //fills fieldelevation array
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

      //fills the field int[][]
      int index = 0;
      for (int r = 0; r < field.length; r++) {
        for (int c = 0; c < field[r].length; c++) {
          field[r][c] = fieldElevation.get(index);
          index++;
        }
      }

      //fills the array list with the trample instructions, number of is 3rd index
      for (int x = 0; x < firstLine.get(3); x++) {
        trampleInstruct.add(s.nextLine());
      }

    }

    public int solve() {
      //System.out.println(yes());

      //trample the land through all the instructions, and each one is run for how many inches are done
      for (int x = 0; x < trampleInstruct.size(); x++) {
        ArrayList<Integer> instruct = convertStringToValues(trampleInstruct.get(x));
        for (int inch = 0; inch < instruct.get(2); inch++) {
          stampDown(instruct.get(0)-1,instruct.get(1)-1); //my array field is by index, so 0,0 is 1,1
        }
        //System.out.println(yes());
      }

      //turns the land to water, -1 if above water, not sure what equal elvation entails, presumably nothing
      convertToDepth(firstLine.get(2));
      //System.out.println(yes());

      //finds the total area of area underwater
      int sum = 0;
      for (int x = 0; x < field.length; x++) {
        for (int y = 0; y < field[x].length; y++) {
          if (field[x][y] != -1) {
            sum += field[x][y];
          }
        }
      }
      sum*= 72 * 72; //just for volume sake
      return sum;
    }

    //stamps down the 3x3 area, but checks if that zone goes off the field to avoid outofbounds exception
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
      //System.out.println(fieldAffect);
      int[] high = findHighest(fieldAffect);  //coordiantes of the peak
      //System.out.println(high[0] + " " + high[1]);
      //System.out.println(field[high[0]][high[1]]);
      //System.out.println(field[fieldAffect.get(2)][fieldAffect.get(3)]);

      for (int x = 0; x < fieldAffect.size(); x+= 2) {

        if (field[fieldAffect.get(x)][fieldAffect.get(x+1)] == field[high[0]][high[1]]){
          fieldToAffect.add(fieldAffect.get(x));
          fieldToAffect.add(fieldAffect.get(x+1));
        }
      }
      //System.out.println(fieldToAffect);
      for (int x = 0; x < fieldToAffect.size(); x+=2) {
        lower(fieldToAffect.get(x),fieldToAffect.get(x+1));
      }
    }
    //helper method to lower the field elevation at coordinates by 1
    private void lower(int row, int col) {
      field[row][col]--;
    }
    //finds the highest coordinate elevation in the given array (which is the target 3x3 area)
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
    //read string to arraylist of ints
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
      //change array to be in depth instead of elevation, in case of needing to see this step
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
    //just a tostring that is for testing only
    private String yes() {
      String fieldy = "";
      for (int r = 0; r < field.length; r++) {
        for (int c = 0; c < field[r].length; c++){
          fieldy += field[r][c] + " ";
        }
        fieldy += '\n';
      }
      return fieldy;
    }
  }


  //patten is maybe odd moves cannot move to start (or even distance away), even move patten is unkwown
  public static int silver(String filename){
    try {
    File f = new File(filename);
    Ssilver d;
    d = new Ssilver(f);
    return d.solve();
  }
  catch (FileNotFoundException f) {

  }
  return -1;
  }

  public static class Ssilver{

    int[][] field;
    ArrayList<Integer> firstLine;
    ArrayList<Character> fieldTerrain = new ArrayList<Character>();
    ArrayList<Integer> coordinatesStartEnd;
    int distance;

    public Ssilver(File instruct) throws FileNotFoundException {
      Scanner ss = new Scanner(instruct);
      String fLine = ss.nextLine();
      firstLine = convertStringToValues(fLine);

      field = new int[firstLine.get(0)][firstLine.get(1)];

      for (int x = 0; x < firstLine.get(0);x++) {
        String row = ss.nextLine();;
        for (int i = 0; i < row.length(); i++) {
          fieldTerrain.add(row.charAt(i));
        }
      }

      int index = 0;
      for (int r = 0; r < field.length; r++) {
        for (int c = 0; c < field[r].length; c++) {
          field[r][c] = fieldTerrain.get(index);
          index++;
        }
      }

      coordinatesStartEnd = convertStringToValues(ss.nextLine());

      distance = Math.abs(coordinatesStartEnd.get(0) - coordinatesStartEnd.get(2)) + Math.abs(coordinatesStartEnd.get(1) - coordinatesStartEnd.get(3));

    }

    public int solve() {
      if ((distance % 2 == 1 && firstLine.get(2) % 2 == 0) || (distance % 2 == 0 && firstLine.get(2) % 2 == 1)) { //only thing dervied from pattern
        return 0;
      }
      return -1; 
    }

    //read string to arraylist of ints
    public ArrayList<Integer> convertStringToValues(String soap) {
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
  }
}
