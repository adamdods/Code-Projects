import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Central class File
 * 
 *
 */
public class Robot {
    
    public static int count = 0;
    /**
     * Main Class
     * @param args Takes in the two command
     * line arguments.
     */
    public static void main(String[] args) {
      
      //Takes in commandLine argument for value of the mode.
      int mode = Integer.parseInt(args[0]);
      //Takes in file.
      File file = new File(args[1]); 
      File file2 = null;
      if (args.length == 3) {
          file2 = new File(args[2]);
      }
      /**According to what mode we are in, will determine what type of 
      * file was read-in and thus different cases will sort file 
      * information differently.
      * Each case corresponds to the equivalent mode.
      */
      switch (mode) {
        //MODE 0: Read in DFA input file.
        case 0:
          mode0(file);
          break;
        //MODE 1: Read in Maze file.
        case 1:
          mode1(file);
        case 2:
            if (args.length == 3) {
                mode2(file, file2);
            }
          break;
        case 3:
            if (args.length == 3) {
                mode3(file, file2);
            }
          break;
        case 4:
          break;
        default:
        }
    }
    
    /**
     * Method to find unknown paths.
     * @param a String array from mode 0 called simp.
     * @param r Current row that the mode 0 call is at.
     * @param c Current column that the mode 0 call is at. 
     * @return The value that should be placed in the final array "output".
     */
    public static String findunknownpaths(String[][] a, int r, int c) {
        /**
         * This for loop checks how many places the initial state 
         * goes to for array creation.
         * 
         */
        
        String row = String.valueOf(r);
        String col = String.valueOf(c);
        int increment = 0;
        for (int z = 0; z < a.length; z++) {
            if (a[z][0].equals(row)) {
               increment++;
            }
        }
        /**
         * This for loop finds where the initial state goes and puts 
         * those values into array "whereDoesItGo".
         * "toSendValue" keeps the paths of how initial gets to next.
         */
        int num = 0;
        String[] whereDoesItGo = new String[increment];
        String[] toSendValue = new String[increment];
        for (int z = 0; z < a.length; z++) {
            if (a[z][0].equals(row)) {
                whereDoesItGo[num] = a[z][2];
                toSendValue[num] = a[z][1];
                num++;
            }
        }

       /**
         * This will initialize return variable and 
         * checks the places that the initial state can 
         * go to see if any of them go to the final state.
         */
        String d = null;
        int increment2 = 0;
        for (int z = 0; z < whereDoesItGo.length; z++) {
            for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereDoesItGo[z])) {
                    increment2++;
                    if (a[x][2].equals(col)) {
                        d = "-" + toSendValue[z];
                    }
                }
            }
        }
        if (d == null) {
        int num2 = 0;
        String[] whereGo = new String[increment2 + increment2];
        String[] sendValue2 = new String[increment2 + increment2];
        for (int z = 0; z < whereDoesItGo.length; z++) {
            for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereDoesItGo[z])) {
                    whereGo[num2] = a[x][0];
                    whereGo[num2 + 1] = a[x][2];
                    sendValue2[num2 + 1] = a[x][0];
                    sendValue2[num2] = a[x][1];
                    num2 += 2;
                }
            }
        }
        int p = 0;
        for (int z = 1; z < whereGo.length; z += 2) {
            for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereGo[z])) {
                    if (a[x][2].equals(col)) {
                        p = z - 1; 
                    }
                }
            }
        }
        for (int z = 0; z < whereDoesItGo.length; z++) {
            if (whereGo[p] == whereDoesItGo[z]) {
                d = "-" + toSendValue[z];
            }
        }
        }
        if (d != null) {
        return d;
        } else {
            return "  ";
        }
    }
    
    /**
     * Mode 0 method.
     * @param file Input file from command line argument args[1].
     */
    public static void mode0(File file) {
      //Initialize Scanner.
      Scanner scan = null;
      try {
        scan = new Scanner(file);
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        //Takes in first line info.
        String[] firstLine = scan.nextLine().split(" ");
        int numOfStates = Integer.parseInt(firstLine[0]);
        int numOfEdges = Integer.parseInt(firstLine[1]);
        int numOfTransitions = Integer.parseInt(firstLine[2]);
        /*Creates 2D array and fills it by creating another 
         * normal array where for each new line and
         * puts those arrays values into 2D array.
         *
         * >>>Number of rows is equal is amount of 
         * transitions and nextLines(). 
         * >>>Number of columns always 3 as there is never
         *  more then 3 char values on a single line.
         */
        String[][] transFunc = new String[numOfTransitions][3];
        for (int r = 0; r < transFunc.length; r++) {
        String[] stringArray = scan.nextLine().split(" ");
          for (int c = 0; c < 3; c++) {
            transFunc[r][c] = stringArray[c];
          }
        }
        
        /**
         * Create new array "simp" to simplify inserting values 
         * into final output array.
         * Simplify by changing all Uppercase to corresponding number.
         */
        String[][] simp = new String[numOfTransitions][3];
        for (int r = 0; r < numOfTransitions; r++) {
            for (int c = 0; c < 3; c++) {
                if (c == 1) {
                    simp[r][c] = transFunc[r][c];
                } else if (transFunc[r][c].equals("A")) {
                    simp[r][c] = "0";
                } else if (transFunc[r][c].equals("B")) {
                    simp[r][c] = "1";
                } else if (transFunc[r][c].equals("C")) {
                    simp[r][c] = "2";
                } else if (transFunc[r][c].equals("D")) {
                    simp[r][c] = "3";
                } else if (transFunc[r][c].equals("E")) {
                    simp[r][c] = "4";
                } else if (transFunc[r][c].equals("F")) {
                    simp[r][c] = "5";
                } else if (transFunc[r][c].equals("G")) {
                    simp[r][c] = "6";
                } else if (transFunc[r][c].equals("H")) {
                    simp[r][c] = "7";
                } else if (transFunc[r][c].equals("I")) {
                    simp[r][c] = "8";
                } else if (transFunc[r][c].equals("J")) {
                    simp[r][c] = "9";
                } else if (transFunc[r][c].equals("K")) {
                    simp[r][c] = "10";
                } else if (transFunc[r][c].equals("L")) {
                    simp[r][c] = "11";
                } else if (transFunc[r][c].equals("M")) {
                    simp[r][c] = "12";
                } else if (transFunc[r][c].equals("N")) {
                    simp[r][c] = "13";
                } else if (transFunc[r][c].equals("O")) {
                    simp[r][c] = "14";
                } else if (transFunc[r][c].equals("P")) {
                    simp[r][c] = "15";
                } else if (transFunc[r][c].equals("Q")) {
                    simp[r][c] = "16";
                } else if (transFunc[r][c].equals("R")) {
                    simp[r][c] = "17";
                } else if (transFunc[r][c].equals("S")) {
                    simp[r][c] = "18";
                } else if (transFunc[r][c].equals("T")) {
                    simp[r][c] = "19";
                } else if (transFunc[r][c].equals("U")) {
                    simp[r][c] = "20";
                } else if (transFunc[r][c].equals("V")) {
                    simp[r][c] = "21";
                } else if (transFunc[r][c].equals("W")) {
                    simp[r][c] = "22";
                } else if (transFunc[r][c].equals("X")) {
                    simp[r][c] = "23";
                } else if (transFunc[r][c].equals("Y")) {
                    simp[r][c] = "24";
                } else if (transFunc[r][c].equals("Z")) {
                    simp[r][c] = "25";
                }
            }
        }
        /**
         * Use array "simp" to fill in values to the final array "output".
         */
        String[][] output = new String[numOfStates][numOfStates];
        for (int r = 0; r < numOfTransitions; r++) {
            int a = Integer.parseInt(simp[r][0]);
            int b = Integer.parseInt(simp[r][2]);
            output[a][b] = " " + simp[r][1];
        }
        for (int r = 0; r < numOfStates; r++) {
            for (int c = 0; c < numOfStates; c++) {
                if (r == c) {
                    output[r][c] = "  ";
                } else if (output[r][c] == null) {
                    output[r][c] = "  ";
                } 
                if (output[r][c].contentEquals("  ") && (r != c)) {
                    
                    output[r][c] = findunknownpaths(simp, r, c);
                }
            }
        } 
        /**
         * Output for mode 0 in table form only.
         */
        for (int r = 0; r < numOfStates; r++) {
            for (int c = 0; c < numOfStates; c++) {
                if (c == 0) {
                    System.out.print(output[r][c]);
                } else {
                    System.out.print(" " + output[r][c]);
                }
            }
            System.out.println();
        }
        return;
    }
    
   
    static int visit2size = 0;
    static int visitsize = 0;
    public static int reach = 0;
    
    /**
     * Finds unknown path left mode1.
     * @param a Simplified array.
     * @param r intitial Position.
     * @param c Final Position.
     * @param mazeRowSize RowSize parameter.
     * @param mazeColSize ColSize parameter.
     * @return String value of first step in shortest path.
     */
    public static String findunknownpathmode1(String[][] a, int r, int c,
            int mazeRowSize, int mazeColSize) {
        /*
         * Finds corresponding position of the r & c in the array.
         */
        String stringR = String.valueOf(r);
        String stringC = String.valueOf(c);
        //Intial postion in array a;
        int mazeValueRow = 0;
        int mazeValueCol = 0;
        //Final postion in array a;
        int mazeValueRowFINAL = 0;
        int mazeValueColFINAL = 0;
        for (int row = 0; row < mazeRowSize; row = row + 2) {
          for (int col = 0; col < mazeColSize; col = col + 2) {
              if (a[row][col].equals(stringR)) {
                 mazeValueRow = row;
                 mazeValueCol = col;
              }
              if (a[row][col].contentEquals(stringC)) {
                 mazeValueRowFINAL = row;
                 mazeValueColFINAL = col;
              }
          }
        }
        /**
         * Takes the values in array 'a' and converts to int.
         * First checks whether intial position is next to final position.
         * Then checks whether the position can only go in one of the for 
         * directions, if so returns that direction value.
         */
        int positionUp = 0;
        int positionRight = 0;
        int positionDown = 0;
        int positionLeft = 0;
    
       
        try {
            positionUp = Integer.parseInt(a[mazeValueRow - 1][mazeValueCol]);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            positionRight = Integer.parseInt(a[mazeValueRow][mazeValueCol + 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
        } 
        try {
            positionDown = Integer.parseInt(a[mazeValueRow + 1][mazeValueCol]);
        } catch (ArrayIndexOutOfBoundsException e) {
        } 
        try { 
            positionLeft = Integer.parseInt(a[mazeValueRow][mazeValueCol - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
       
        try {
            if ((positionUp == 1) && (mazeValueRowFINAL == mazeValueRow - 2) 
                    && (mazeValueColFINAL == mazeValueCol)) {
                return " a"; 
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            if (positionRight == 1 && mazeValueRowFINAL == mazeValueRow 
                    && mazeValueColFINAL == mazeValueCol + 2) {
                return " b";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        try {
            if (positionDown == 1 && mazeValueRowFINAL == mazeValueRow + 2 
                    && mazeValueColFINAL == mazeValueCol) {
                return " c";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        try {
            if (positionLeft == 1 && mazeValueRowFINAL == mazeValueRow 
                    && mazeValueColFINAL == mazeValueCol - 2) {
                return " d";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
       /**
        * Checks if initial position can only move one way
        */
        if (positionUp + positionRight + positionDown + positionLeft == 1) {
            if (positionUp == 1) { 
                return "-a"; 
            }
            if (positionRight == 1) { 
                return "-b"; 
            }
            if (positionDown == 1) { 
                return "-c"; 
            }
            if (positionLeft == 1) { 
                return "-d"; 
            }
        }
        
        /**
         * For unfound null values.
         */
        String[][] b = new String[mazeRowSize][mazeColSize];
        for (int x = 0; x < mazeRowSize; x++) {
            for (int z = 0; z < mazeColSize; z++) {
                b[x][z] = a[x][z];
                if (Integer.parseInt(b[x][z]) > 0) {
                    b[x][z] = "1"; 
                }
            }
        }
        b[0][0] = "1";
        String value = null;
        
        count++;
        
        if (value == null) {
            value = pathmode1(b, mazeValueRow, mazeValueCol, mazeValueRowFINAL, 
                    mazeValueColFINAL, mazeRowSize, mazeColSize);
        }
        return value;
    }
    
    static int rowSize;
    static int colSize;
    private static int[][] beentolast;
    private static int[][] beentolastnew;
    /**
     * Finds Path for Mode 1
     * @param a Array.
     * @param mazeValueRow initial row
     * @param mazeValueCol initial col
     * @param mazeValueRowFINAL final Row
     * @param mazeValueColFINAL final col
     * @param mazeRowSize row size param
     * @param mazeColSize col size param
     * @return string value of path step.
     */
    public static String pathmode1(String[][] a, int mazeValueRow,
            int mazeValueCol, int mazeValueRowFINAL, int mazeValueColFINAL,
            int mazeRowSize, int mazeColSize) {
        String[][] array = a;

        int beentolast2size = 0;
        int beentolastsize = 0;
        rowSize = mazeRowSize;
        colSize = mazeColSize;
        
        /*
         * These arrays will hold values to where we have been and 
         * the shortest path.
         */
        int[][] beento = new int[rowSize][colSize];
        beentolast = new int[rowSize][colSize];
        beentolastnew = new int[rowSize][colSize];
        int size = 0;
        int[] finalPositions = new int[2];
        finalPositions[0] = mazeValueRowFINAL;
        finalPositions[1] = mazeValueColFINAL;
        
        int distanceMin = findShortestPath(array, beento, mazeValueRow,
            mazeValueCol, finalPositions,
            Integer.MAX_VALUE, 0);

        int up;
        int right;
        int down;
        int left;
        try {
            up = beentolast[mazeValueRow - 1][mazeValueCol];
        } catch (ArrayIndexOutOfBoundsException e) {
            up = 0;
        }
        try {
            right = beentolast[mazeValueRow][mazeValueCol + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            right = 0;
        }
        try {
            down = beentolast[mazeValueRow + 1][mazeValueCol];
        } catch (ArrayIndexOutOfBoundsException e) {
            down = 0;
        }
        try {
            left = beentolast[mazeValueRow][mazeValueCol - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            left = 0;
        }
        if (up == 1) {

            return "-a";
        }
        if (right == 1) {

            return "-b";
        }
        if (down == 1) {

            return "-c";
        }
        if (left == 1) {

            return "-d";
        }
        return "--";
    }
    
    /**
     * Uses recursion to find shortest path between intial state and 
     * final state.
     * During the recursive compute different paths will be found,
     *  chooses shortest
     * path by summing values in beento arrays, makes smallest equal to
     * beentolast array.
     * @param array This is the simp array modified to only have 0 & 1.
     * @param beento Array that keeps track of where in the compute 
     * we have beento.
     * @param mazeValueRow Initial row position.
     * @param mazeValueCol Initial col position.
     * @param finalPositions array holding mazeValueRowFinal
     * in finalPositions[0] and mazeValueColFinal in 
     * finalPositions[1].
     * @param distanceMin Long Distance
     * @param distance short distance
     * @return Shortest distance.
     */
    public static int findShortestPath(String[][] array, int[][] beento,
                    int mazeValueRow, int mazeValueCol, 
                    int[] finalPositions, int distanceMin, 
                    int distance) {
            

            /*
             * If we reach final position update.
             */
            if (mazeValueRow == finalPositions[0] 
                    && mazeValueCol == finalPositions[1]) {
                int beentolastsize = 0;
                int beentolast2size = 0;

            for (int r = 0; r < rowSize; r++) {
              for (int c = 0; c < colSize; c++) {
                beentolast2size = beentolast2size + beento[r][c];
                beentolastsize = beentolastsize + beentolast[r][c];
                     }
                }
            if (beentolastsize == 0) {
              for (int r = 0; r < rowSize; r++) {
                for (int c = 0; c < colSize; c++) {
                  beentolast[r][c] = beento[r][c];
                    beentolastsize = beentolastsize 
                      + beentolast[r][c];
                         }
                    }
                }
                if (beentolastsize > beentolast2size) {
                  for (int r = 0; r < rowSize; r++) {
                    for (int c = 0; c < colSize; c++) {
                    beentolast[r][c] = beento[r][c];
                    }
                  }
                }
                return Integer.min(distance, distanceMin);
            }

            /*
             * Make equal to one, showing beento that place.
             */
            beento[mazeValueRow][mazeValueCol] = 1;

            /*
             * Down.
             */
            if (positionGood(mazeValueRow + 1, mazeValueCol) 
            && positionPossible(array, beento, mazeValueRow + 1,
            mazeValueCol)) {
            distanceMin = findShortestPath(array, beento, 
            mazeValueRow + 1, mazeValueCol, finalPositions, 
            distanceMin, distance + 1);
            }

            /**
             * Right.
             */
            if (positionGood(mazeValueRow, mazeValueCol + 1) 
                && positionPossible(array, beento, mazeValueRow,
                mazeValueCol + 1)) {
                distanceMin = findShortestPath(array, beento,
                mazeValueRow, mazeValueCol + 1,
                finalPositions,
                distanceMin, distance + 1);
            }

            /**
             * UP.
             */
        if (positionGood(mazeValueRow - 1, mazeValueCol) 
        && positionPossible(array, beento, mazeValueRow - 1, 
                            mazeValueCol)) {
            distanceMin = findShortestPath(array, 
                    beento, mazeValueRow - 1, 
            mazeValueCol, finalPositions,
            distanceMin, distance + 1);
            }

            /**
             * Left.
             */
        if (positionGood(mazeValueRow, mazeValueCol - 1) 
            && positionPossible(array, beento, mazeValueRow, 
            mazeValueCol - 1)) {
        distanceMin = findShortestPath(array, beento, mazeValueRow, 
        mazeValueCol - 1, finalPositions,
        distanceMin, distance + 1);
            }
            beento[mazeValueRow][mazeValueCol] = 0;

            return distanceMin;
        }
        /**
         * Checks if in can move there(not '0' or been there).
         * @param array Simp array modified to 1 and 0.
         * @param beento Where we have been.
         * @param mazeValueRow Row param.
         * @param mazeValueCol Col param.
         * @return True is can move.
         */
        private static boolean positionPossible(String[][] array, 
            int[][] beento, int mazeValueRow, 
            int mazeValueCol) {
        return !(array[mazeValueRow][mazeValueCol].equals("0") 
            || beento[mazeValueRow][mazeValueCol] != 0);
            }

            /**
             * True if good, false otherwise.
             * @param mazeValueRow Row param.
             * @param mazeValueCol Col param.
             * @return True if can move there.
             */
            private static boolean positionGood(int mazeValueRow, 
                    int mazeValueCol) {
            return (mazeValueRow < rowSize && mazeValueRow >= 0 
                   && mazeValueCol < colSize && mazeValueCol >= 0);
            }

             
  

    /**
     * Mode 1 method.
     * @param file Input file from command line args[1].
     */
    public static void mode1(File file) {
      //Initialize Scanner.
      Scanner scan = null;
      try {
        scan = new Scanner(file);
      } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        /**
         * Takes in firstLine numbers corresponding to number 
         * of rows and columns.
         * numOfRows/numofColumns used to create maze simp.
         * numRow/numCol used for outputMode1 array.
         */
        String[] firstLine1 = scan.nextLine().split(" ");
        int numRow = Integer.parseInt(firstLine1[0]);
        int numCol = Integer.parseInt(firstLine1[1]);
        int numOfRows = (Integer.parseInt(firstLine1[0]) 
            + (Integer.parseInt(firstLine1[0]) + 1));
        int numOfColumns = (Integer.parseInt(firstLine1[1]) 
            + (Integer.parseInt(firstLine1[1]) + 1));
        //Creates maze array with firstLine parameters.
        String[][] maze = new String[numOfRows][numOfColumns];
        String line1 = null;
        
        //For loops with conditional if statments to load scanned textual 
        //maze into maze array.
        for (int row = 0; row < numOfRows; row++) {
          line1 = scan.nextLine();
          int a = 1;
          int b = 4;
          int c = 0;
          int d = 1; 
          
          for (int col = 0; col < numOfColumns; col++) {
            if (col % 2 != 0) {
              maze[row][col] = line1.substring(a, b);
              a += 4;
              b += 4;
            } else if (col % 2 == 0) {
                maze[row][col] = line1.substring(c, d);
                c += 4;
                d += 4;
                }
            }
          }
        
        /*Now that the maze file has been identically scanned into array 
        * called maze, now use that array to create simplified new 
        * array for processing.
        */
        int numRowSimplified = ((Integer.parseInt(firstLine1[0]) * 2) - 1);
        int numColSimplified = ((Integer.parseInt(firstLine1[1]) * 2) - 1);
        String[][] mazeValue = new String[numRowSimplified][numColSimplified];

        /*Place corresponding values from maze array to mazeValue array.
         * # is open space.
         * + is intersection of walls.
         * 0 is path between # is closed.
         * 1 is path between # open.
        */
        int place = 0;
        for (int row1 = 0; row1 < numRowSimplified; row1++) {
          for (int col1 = 0; col1 < numColSimplified; col1++) {
            if (row1 % 2 != 0 && maze[row1 + 1][col1 + 1].equals("   ")) {
              mazeValue[row1][col1] = "1";
              } else if (maze[row1 + 1][col1 + 1].equals("   ")) {
                   mazeValue[row1][col1] = String.valueOf(place);

                  place++;  
                  } else if (maze[row1 + 1][col1 + 1].equals("|")) {
                      mazeValue[row1][col1] = "0";
                      } else if (maze[row1 + 1][col1 + 1].equals(" ")) {
                          mazeValue[row1][col1] = "1";
                          } else if (maze[row1 + 1][col1 + 1].equals("---")) {
                              mazeValue[row1][col1] = "0";
                              } else if 
                                 (maze[row1 + 1][col1 + 1].equals("+")) {
                                  mazeValue[row1][col1] = "0";
                                  }
            }
          }
           
          /**
           * Now that we have simplified input need to start computing output.
           * To do this we need to create the 2D outputMode1 array.
           * outputMode1 will have numOfRows*numOfColumns as both row and col.
           */

        String[][] outputMode1 = new String[numRow * numCol][numRow * numCol];
        for (int r = 0; r < (numRow * numCol); r++) {
          for (int c = 0; c < (numRow * numCol); c++) {
            if (r == c) {
                outputMode1[r][c] = "  ";  
            } else if (outputMode1[r][c] == null) {
                outputMode1[r][c] = findunknownpathmode1(mazeValue, r, c, 
                        numRowSimplified, numColSimplified);
            }
          }
        }
        
        /**
         * Printing outputMode1
         */
        for (int r = 0; r < numRow * numCol; r++) {
          for (int c = 0; c < numRow * numCol; c++) {
            if (c == 0) {
              System.out.print(outputMode1[r][c]);
                } else {
                    System.out.print(" " + outputMode1[r][c]);
                }
            }
            System.out.println();
        }
        
         
         return;
    }
    /**
     * Mode 2 start.
     * @param file File of targets.
     * @param file2 File of DFA.
     */
    public static void mode2(File file, File file2) {
        //Initialize scanner and input for arg[1] - target file.
        Scanner scan = null;
        try {
            scan = new Scanner(file);
          } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
        String initialState = scan.nextLine();
        int numOfTargets = Integer.parseInt(scan.nextLine());
        String[] targets = new String[numOfTargets];

        for (int r = 0; r < numOfTargets; r++) {
            targets[r] = scan.nextLine();
        }
        scan.close();
        
        //Initialize scanner and input for arg[2] - dfa file.
        Scanner scanDFA = null;
        try {
            scanDFA = new Scanner(file2);
          } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
      
        //Takes in first line info.
        String[] firstLine = scanDFA.nextLine().split(" ");
        int numOfStates = Integer.parseInt(firstLine[0]);
        int numOfEdges = Integer.parseInt(firstLine[1]);
        int numOfTransitions = Integer.parseInt(firstLine[2]);
        
        /*Creates 2D array and fills it by creating another 
         * normal array where for each new line and
         * puts those arrays values into 2D array.
         *
         * >>>Number of rows is equal is amount of 
         * transitions and nextLines(). 
         * >>>Number of columns always 3 as there is never
         *  more then 3 char values on a single line.
         */
        String[][] transFunc = new String[numOfTransitions][3];
        for (int r = 0; r < transFunc.length; r++) {
        String[] stringArray = scanDFA.nextLine().split(" ");
          for (int c = 0; c < 3; c++) {
            transFunc[r][c] = stringArray[c];
          }
        }
       
        
        String[] outputMode2 = findmode2paths(transFunc, initialState, 
                numOfTargets, targets);
        
        
        
        /**
         * Output mode 2.
         */
        for (int z = 0; z < outputMode2.length; z++) {
            if (outputMode2[z] != null) {
                System.out.println(outputMode2[z]);
            } else {
                break;
            }
        }
        
    }
    /**
     * Finds paths for Mode 2
     * @param a array
     * @param initialState Initail state
     * @param numOfTargets # targets
     * @param targets The targets
     * @return String array of output values.
     */
    public static String[] findmode2paths(String[][] a, 
        String initialState, int numOfTargets, 
        String[] targets) {
        String[] targetNew = new String[numOfTargets + 1];
        targetNew[0] = initialState;
        for (int r = 1; r < targetNew.length; r++) {
            targetNew[r] = targets[r - 1];
        }        
        String[] saveHowValues = new String[100];
        int number = 0; int targetNum = 0; int done = 0;
        while (done != 1) {
        //Counts how many places current Target can go to.
        int increment = 0;
        for (int z = 0; z < a.length; z++) {
            if (a[z][0].equals(targetNew[targetNum])) {
               increment++;
            }
        }
        int found = 0; int num = 0;
        String[] whereGo = new String[increment];
        String[] howGo = new String[increment];
        for (int z = 0; z < a.length; z++) {
            if (a[z][0].equals(targetNew[targetNum])) {
                whereGo[num] = a[z][2];
                howGo[num] = a[z][1];
                if (whereGo[num].equals(targetNew[targetNum + 1])) {
                    saveHowValues[number] = howGo[num];
                    number++; found = 1;
                }
                num++;
            }
        }
        if (found != 1) {
            int found1 = 0; int increment1 = 0;
            for (int z = 0; z < whereGo.length; z++) {
              for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereGo[z])) {
                    increment1++;
                  if (a[x][2].equals(targetNew[targetNum + 1])) {
                    saveHowValues[number] = howGo[z];
                    number++;
                    saveHowValues[number] = a[x][1];
                    number++; found1 = 1;
                  }
                }
              }
            }
            if (found1 != 1) {
                int found2 = 0; int num1 = 0;
                String[] whereGo1 = new String[increment1 + increment1];
                String[] howGo1 = new String[increment1 + increment1];
            for (int z = 0; z < whereGo.length; z++) {
              for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereGo[z])) {
                  whereGo1[num1] = a[x][0]; //from
                  whereGo1[num1 + 1] = a[x][2]; //To
                  howGo1[num1] = a[x][1]; //how goes from->TO.
                  howGo1[num1 + 1] = a[x][0]; //place 'from'comefrom
                  num1 += 2;
                }
              }
            }
            for (int z = 1; z < whereGo1.length; z += 2) {
              for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereGo1[z])) {
                  if (a[x][2].equals(targetNew[targetNum + 1])) {
                    String placeHolder = howGo1[z];
                    String howGoValue = null;
                    for (int r = 0; r < whereGo.length; r++) {
                      if (placeHolder.equals(whereGo[r])) {
                         howGoValue = howGo[r];
                      }
                    }
                    saveHowValues[number] = howGoValue;
                    number++;
                    saveHowValues[number] = howGo1[z - 1];
                    number++;
                    saveHowValues[number] = a[x][1];
                    number++; found2 = 1;
                  }
                }
              }
            }
            if (found2 != 1) {                    
                int found3 = 0; int increment2 = 0;
                for (int z = 1; z < whereGo1.length; z += 2) {
                  for (int x = 0; x < a.length; x++) {
                    if (a[x][0].equals(whereGo1[z])) {
                        increment2++;
                    }
                  }
                }
                int num2 = 0;
                String[] whereGo2 = new String[increment2 + increment2];
                String[] howGo2 = new String[increment2 + increment2];
                for (int z = 1; z < whereGo1.length; z += 2) {
                  for (int x = 0; x < a.length; x++) {
                    if (a[x][0].equals(whereGo1[z])) {
                      whereGo2[num2] = a[x][0]; //from
                      whereGo2[num2 + 1] = a[x][2]; //To
                      howGo2[num2] = a[x][1]; //how goes from->TO.
                      howGo2[num2 + 1] = a[x][0]; //place 'from' 
                      //come from. eg: initial target.
                      num2 += 2;
                    }
                  }
                }
            for (int z = 1; z < whereGo2.length; z += 2) {
              for (int x = 0; x < a.length; x++) {
                if (a[x][0].equals(whereGo2[z])) {
                  if (a[x][2].equals(targetNew[targetNum + 1])) {
                    String placeHolder1 = howGo2[z];
                    String howGoValue1 = null;
                    for (int r = 1; r < whereGo1.length; r += 2) {
                      if (placeHolder1.equals(whereGo1[r])) {
                        String place = whereGo1[r - 1];
                        for (int c = 0; c < whereGo.length; c++) {
                          if (whereGo1[r - 1].equals(whereGo[c])) {
                            howGoValue1 = howGo[c];
                          }
                        }
                      }
                    }
                            saveHowValues[number] = howGoValue1;
                            number++;
                            String placeHolder = howGo2[z];
                            String howGoValue = null;
                        for (int r = 1; r < whereGo1.length; r += 2) {
                          if (placeHolder.equals(whereGo1[r])) {
                             howGoValue = howGo1[r - 1];
                          }
                        }
                            saveHowValues[number] = howGoValue;
                            number++;
                            saveHowValues[number] = howGo2[z - 1];
                            number++;
                            saveHowValues[number] = a[x][1];
                            number++; found3 = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (targetNum == targetNew.length - 2) {
            done = 1;
        }
        targetNum++;
        }
        return saveHowValues;
    }
    
    /**
     * Mode 3 STARTS.
     * @param file target file
     * @param file2 maze File
     */
    public static void mode3(File file, File file2) {
        //Initialize scanner and input for arg[1] - target file.
        Scanner scan = null;
        try {
            scan = new Scanner(file);
          } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
        String initialState = scan.nextLine();
        int numOfTargets = Integer.parseInt(scan.nextLine());
        String[] targets = new String[numOfTargets + numOfTargets + 2];
        
        targets[0] = initialState.substring(0, 1);
        targets[1] = initialState.substring(1, 2);
        for (int r = 2; r < targets.length; r += 2) {
            String str = scan.nextLine();
            targets[r] = str.substring(0, 1);
            targets[r + 1] = str.substring(1, 2);
        }
        scan.close();
        
        //Initialize scanner and input for arg[2] - dfa file.
        Scanner scanMaze = null;
        try {
            scanMaze = new Scanner(file2);
          } catch (FileNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
      
        /**
         * Takes in firstLine numbers corresponding to number 
         * of rows and columns.
         * numOfRows/numofColumns used to create maze simp.
         * numRow/numCol used for outputMode1 array.
         */
        String[] firstLine1 = scanMaze.nextLine().split(" ");
        int numRow = Integer.parseInt(firstLine1[0]);
        int numCol = Integer.parseInt(firstLine1[1]);
        int numOfRows = (Integer.parseInt(firstLine1[0]) 
            + (Integer.parseInt(firstLine1[0]) + 1));
        int numOfColumns = (Integer.parseInt(firstLine1[1]) 
            + (Integer.parseInt(firstLine1[1]) + 1));
        //Creates maze array with firstLine parameters.
        String[][] maze = new String[numOfRows][numOfColumns];
        String line1 = null;
        
        //For loops with conditional if statments to load scanned textual 
        //maze into maze array.
        for (int row = 0; row < numOfRows; row++) {
          line1 = scanMaze.nextLine();
          int a = 1;
          int b = 4;
          int c = 0;
          int d = 1; 
          for (int col = 0; col < numOfColumns; col++) {
            if (col % 2 != 0) {
              maze[row][col] = line1.substring(a, b);
              a += 4;
              b += 4;
            } else if (col % 2 == 0) {
                maze[row][col] = line1.substring(c, d);
                c += 4;
                d += 4;
                }
            }
          }
        /*Now that the maze file has been identically scanned into array 
        * called maze, now use that array to create simplified new 
        * array for processing.
        */
        int numRowSimplified = ((Integer.parseInt(firstLine1[0]) * 2) - 1);
        int numColSimplified = ((Integer.parseInt(firstLine1[1]) * 2) - 1);
        String[][] mazeValue = new String[numRowSimplified][numColSimplified];
        /*Place corresponding values from maze array to mazeValue array.
         * # is open space.
         * + is intersection of walls.
         * 0 is path between # is closed.
         * 1 is path between # open.
        */
        int place = 0;
        for (int row1 = 0; row1 < numRowSimplified; row1++) {
          for (int col1 = 0; col1 < numColSimplified; col1++) {
            if (row1 % 2 != 0 && maze[row1 + 1][col1 + 1].equals("   ")) {
              mazeValue[row1][col1] = "1";
              } else if (maze[row1 + 1][col1 + 1].equals("   ")) {
                   mazeValue[row1][col1] = String.valueOf(place);
                  place++;  
                  } else if (maze[row1 + 1][col1 + 1].equals("|")) {
                      mazeValue[row1][col1] = "0";
                      } else if (maze[row1 + 1][col1 + 1].equals(" ")) {
                          mazeValue[row1][col1] = "1";
                          } else if (maze[row1 + 1][col1 + 1].equals("---")) {
                              mazeValue[row1][col1] = "0";
                              } else if 
                                 (maze[row1 + 1][col1 + 1].equals("+")) {
                                  mazeValue[row1][col1] = "0";
                                  }
            }
          }
          mazeValue[0][0] = "1"; int done = 0; int number = 0;
          int[] size = new int[2]; size[0] = numRowSimplified; 
          size[1] = numColSimplified;
          while (done != 1) {
          int[][] path = pathmode3(mazeValue, targets[number], 
                  targets[number + 1],
                  targets[number + 2], targets[number + 3], size);
          int[] targetValue = new int[4];
          targetValue[0] = convertstringtoint(targets[number]);
          targetValue[1] = convertstringtoint(targets[number + 1]);
          finalcompoutput(path, targetValue);
          if (number == targets.length - 4) {
                done = 1;
            }
          number += 2; 
          }
        }
    /**
     * Takes shortest Path found and uses it to compute output.
     * @param path Shortest Path Matrix.
     * @param targetValue Values of initial position.
     */
    public static void finalcompoutput(int[][] path, int[] targetValue) {
        int up = 0; int right = 0; int down = 0; int left = 0;
        try {
            up = path[targetValue[0] - 1][targetValue[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        try {
            right = path[targetValue[0]][targetValue[1] + 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        try {
            down = path[targetValue[0] + 1][targetValue[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        try {
            left = path[targetValue[0]][targetValue[1] - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        int upC = 0;
        int rightC = 0;
        int downC = 0;
        int leftC = 0;
        int row = targetValue[0]; int col = targetValue[1];
        while (up == 1 || right == 1 || down == 1 || left == 1) {
            if (up == 1) {
                System.out.println("up");
                path[row - 1][col] = 2;
                row = row - 2;
                try {
                      up = path[row - 1][col]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      up = 0;
                  }
                  try {
                      right = path[row][col + 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      right = 0;
                  }
                  try {
                      down = path[row + 1][col];
                  } catch (ArrayIndexOutOfBoundsException e) {
                      down = 0;
                  }
                  try {
                      left = path[row][col - 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      left = 0;
                  }
            } else if (down == 1) {
                System.out.println("down");
                path[row + 1][col] = 2;
                row = row + 2;
                try {
                      up = path[row - 1][col]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      up = 0;
                  }
                  try {
                      right = path[row][col + 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      right = 0;
                  }
                  try {
                      down = path[row + 1][col];
                  } catch (ArrayIndexOutOfBoundsException e) {
                      down = 0;
                  }
                  try {
                      left = path[row][col - 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      left = 0;
                  }
            } else if (right == 1) {
                System.out.println("right");
                path[row][col + 1] = 2;
                col = col + 2;
                try {
                      up = path[row - 1][col]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      up = 0;
                  }
                  try {
                      right = path[row][col + 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      right = 0;
                  }
                  try {
                      down = path[row + 1][col];
                  } catch (ArrayIndexOutOfBoundsException e) {
                      down = 0;
                  }
                  try {
                      left = path[row][col - 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      left = 0;
                  }
            } else if (left == 1) {
                System.out.println("left");
                path[row][col - 1] = 2;
                col = col - 2;
                try {
                      up = path[row - 1][col]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      up = 0;
                  }
                  try {
                      right = path[row][col + 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      right = 0;
                  }
                  try {
                      down = path[row + 1][col];
                  } catch (ArrayIndexOutOfBoundsException e) {
                      down = 0;
                  }
                  try {
                      left = path[row][col - 1]; 
                  } catch (ArrayIndexOutOfBoundsException e) {
                      left = 0;
                  }
            }
        }
    }
    /**
     * This function converts string position to int.
     * @param a string value o position.
     * @return int value of position.
     */
    public static int convertstringtoint(String a) {
            int targetsNew = 0;
            if (a.equals("A")) {
                targetsNew = 0;
            }
            if (a.equals("B")) {
                targetsNew = 2;
            }
            if (a.equals("C")) {
                targetsNew = 4;
            }
            if (a.equals("D")) {
                targetsNew = 6;
            }
            if (a.equals("E")) {
                targetsNew = 8;
            }
            if (a.equals("F")) {
                targetsNew = 10;
            }
            if (a.equals("G")) {
                targetsNew = 12;
            }
            if (a.equals("H")) {
                targetsNew = 14;
            }
            if (a.equals("I")) {
                targetsNew = 16;
            }
            if (a.equals("J")) {
                targetsNew = 18;
            }
            if (a.equals("K")) {
                targetsNew = 20;
            }
            if (a.equals("L")) {
                targetsNew = 22;
            }
            if (a.equals("M")) {
                targetsNew = 24;
            }
            if (a.equals("N")) {
                targetsNew = 26;
            }
            if (a.equals("O")) {
                targetsNew = 28;
            }
            if (a.equals("P")) {
                targetsNew = 30;
            }
            if (a.equals("Q")) {
                targetsNew = 32;
            }
            if (a.equals("R")) {
                targetsNew = 34;
            }
            if (a.equals("S")) {
                targetsNew = 36;
            }
            if (a.equals("T")) {
                targetsNew = 38;
            }
            if (a.equals("U")) {
                targetsNew = 40;
            }
            if (a.equals("V")) {
                targetsNew = 42;
            }
            if (a.equals("W")) {
                targetsNew = 44;
            }
            if (a.equals("X")) {
                targetsNew = 46;
            }
            if (a.equals("Y")) {
                targetsNew = 48;
            }
            if (a.equals("Z")) {
                targetsNew = 50;
            }
        return targetsNew;
    }
        

    /**
     * Finds Shortest path matrix and shortest path.
     * @param a Array info.
     * @param mazeRowValue Start position.
     * @param mazeColValue start position
     * @param mazeRowValueFinal End position.
     * @param mazeColValueFinal End position
     * @param size1 Array of row and col size.
     * @return shortest path matrix.
     */
    public static int[][] pathmode3(String[][] a, String mazeRowValue,
            String mazeColValue, String mazeRowValueFinal, 
            String mazeColValueFinal,
            int[] size1) {
        String[][] array = a;
        String[] value = new String[4];
        value[0] = mazeRowValue; value[1] = mazeColValue;
        value[2] = mazeRowValueFinal; value[3] = mazeColValueFinal;
        int[] targetsNew = new int[4];

        for (int z = 0; z < targetsNew.length; z++) {
            if (value[z].equals("A")) {
                targetsNew[z] = 0;
            }
            if (value[z].equals("B")) {
                targetsNew[z] = 2;
            }
            if (value[z].equals("C")) {
                targetsNew[z] = 4;
            }
            if (value[z].equals("D")) {
                targetsNew[z] = 6;
            }
            if (value[z].equals("E")) {
                targetsNew[z] = 8;
            }
            if (value[z].equals("F")) {
                targetsNew[z] = 10;
            }
            if (value[z].equals("G")) {
                targetsNew[z] = 12;
            }
            if (value[z].equals("H")) {
                targetsNew[z] = 14;
            }
            if (value[z].equals("I")) {
                targetsNew[z] = 16;
            }
            if (value[z].equals("J")) {
                targetsNew[z] = 18;
            }
            if (value[z].equals("K")) {
                targetsNew[z] = 20;
            }
            if (value[z].equals("L")) {
                targetsNew[z] = 22;
            }
            if (value[z].equals("M")) {
                targetsNew[z] = 24;
            }
            if (value[z].equals("N")) {
                targetsNew[z] = 26;
            }
            if (value[z].equals("O")) {
                targetsNew[z] = 28;
            }
            if (value[z].equals("P")) {
                targetsNew[z] = 30;
            }
            if (value[z].equals("Q")) {
                targetsNew[z] = 32;
            }
            if (value[z].equals("R")) {
                targetsNew[z] = 34;
            }
            if (value[z].equals("S")) {
                targetsNew[z] = 36;
            }
            if (value[z].equals("T")) {
                targetsNew[z] = 38;
            }
            if (value[z].equals("U")) {
                targetsNew[z] = 40;
            }
            if (value[z].equals("V")) {
                targetsNew[z] = 42;
            }
            if (value[z].equals("W")) {
                targetsNew[z] = 44;
            }
            if (value[z].equals("X")) {
                targetsNew[z] = 46;
            }
            if (value[z].equals("Y")) {
                targetsNew[z] = 48;
            }
            if (value[z].equals("Z")) {
                targetsNew[z] = 50;
            }
        }
        int beentolast2size = 0;
        int beentolastsize = 0;
        rowSize = size1[0];
        colSize = size1[1];
        
        /*
         * These arrays will hold values to where we have been and 
         * the shortest path.
         */
        int[][] beento = new int[rowSize][colSize];
        beentolast = new int[rowSize][colSize];
        beentolastnew = new int[rowSize][colSize];
        int size = 0;
        int[] finalPositions = new int[2];
        finalPositions[0] = targetsNew[2];
        finalPositions[1] = targetsNew[3];
        
        int distanceMin = findShortestPath(array, beento, targetsNew[0],
            targetsNew[1], finalPositions,
            Integer.MAX_VALUE, 0);

        return  beentolast;
    }
}

