
public class PathFinder {
	static int visit2size =0;
    static int visitsize =0;
	public static int reach = 0;
    
	public static String findunknownpathmode1(String[][] a, int r, int c, int mazeRowSize, int mazeColSize) {

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
        for (int row = 0; row < mazeRowSize; row = row+2) {
          for (int col = 0; col < mazeColSize; col = col+2) {
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
        	positionUp = Integer.parseInt(a[mazeValueRow-1][mazeValueCol]);
        } catch (ArrayIndexOutOfBoundsException e) {
        } try {
        	positionRight = Integer.parseInt(a[mazeValueRow][mazeValueCol+1]);
        } catch (ArrayIndexOutOfBoundsException e) {
        } try {
        	positionDown = Integer.parseInt(a[mazeValueRow+1][mazeValueCol]);
        } catch (ArrayIndexOutOfBoundsException e) {
        } try { 
        	positionLeft = Integer.parseInt(a[mazeValueRow][mazeValueCol-1]);
        } catch (ArrayIndexOutOfBoundsException e) {
        	
        }
       
        try {
        	if ((positionUp == 1) && (mazeValueRowFINAL == mazeValueRow-2) && (mazeValueColFINAL == mazeValueCol)) {
                return " a"; 
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        } try {
        	if (positionRight == 1 && mazeValueRowFINAL == mazeValueRow && mazeValueColFINAL == mazeValueCol+2) {
                return " b";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        	
        } try {
        	if (positionDown == 1 && mazeValueRowFINAL == mazeValueRow+2 && mazeValueColFINAL == mazeValueCol) {
                return " c";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        	
        } try {
        	if (positionLeft == 1 && mazeValueRowFINAL == mazeValueRow && mazeValueColFINAL == mazeValueCol-2) {
                return " d";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        	
        }
       /**
        * Checks if initial position can only move one way
        */
        if (positionUp + positionRight + positionDown + positionLeft == 1) {
            if(positionUp == 1) { return "-a"; };
            if(positionRight == 1) { return "-b"; };
            if(positionDown == 1) { return "-c"; };
            if(positionLeft == 1) { return "-d"; };
        }
       
   
        
        
    String value = null;
    if (value == null) {

    	int[][] beenTo = new int[mazeRowSize][mazeColSize];
    	int[][] beento1 = new int[mazeRowSize][mazeColSize];
    	
    	System.out.println("HH");
		int distanceMin = findMissingPath(a, beenTo, mazeValueRow, mazeValueCol, mazeValueRowFINAL, mazeValueColFINAL,
				Integer.MAX_VALUE, 0, mazeRowSize, mazeColSize, beento1);
		System.out.println("HH");
		
		int up;
		int right;
		int down;
		int left;
		try {
			up = beento1[mazeValueRow-1][mazeValueCol];
		} catch (ArrayIndexOutOfBoundsException e) {
			up = 0;
		}
		try {
			right = beento1[mazeValueRow][mazeValueCol+1];
		} catch (ArrayIndexOutOfBoundsException e) {
			right = 0;
		}
		try {
			down = beento1[mazeValueRow+1][mazeValueCol];
		} catch (ArrayIndexOutOfBoundsException e) {
			down = 0;
		}
		try {
			left = beento1[mazeValueRow][mazeValueCol-1];
		} catch (ArrayIndexOutOfBoundsException e) {
			left = 0;
		}
		if (up == 1) {
			value = "-a";
		}
		if (right == 1) {
			value = "-b";
		}
		if (down == 1) {
			value = "-c";
		}
		if (left == 1) {
			value = "-d";
		}
    }
    return value;
    }

    
	
	
	public static int findMissingPath(String array[][],int beento[][], int r, int c, int rFinal, int cFinal, int distanceMin, int distance, int mazeRowSize, int mazeColSize, int[][] beento1) {
    	
    	// if destination is found, update min_dist
    			if (r == rFinal && c == cFinal)
    			{
    				reach++;
    				
    				if (reach>1) {
    					for (int row = 0; row < mazeRowSize; r++) {
    						for (int col = 0; col < mazeColSize; col++) {
    							if (beento[row][col] == 1) {
    								visit2size++;
    							}
    							if (beento1[row][col] == 1) {
    								visitsize++;
    							}
    						}
    					} 
    					if (visitsize == 0 && visit2size != 0) {
    						for (int row = 0; row < mazeRowSize; row++) {
    							for (int col = 0; col < mazeColSize; col++) {
    								beento1[row][col] = beento[row][col];
    							}
    						}
    					} else if (visit2size < visitsize && visit2size != 0) {
    						for (int row = 0; row < mazeRowSize; row++) {
    							for (int col = 0; col < mazeColSize; col++) {
    								beento1[row][col] = beento[row][col];
    							}
    						}
    					}
    				} else {
    					for (int row = 0; row < mazeRowSize; row++) {
    						for (int col = 0; col < mazeColSize; col++) {
    							beento1[row][col] = beento[row][col];
    						}
    					}
    				}
    				
    				return Integer.min(distance, distanceMin);
    			}

    			// set (i, j) cell as visited
    			beento[r][c] = 1;

    			
    			// go to bottom cell
    			if (isValid(r + 1, c, mazeRowSize, mazeColSize) && isSafe(array, beento, r + 1, c)) {
    				distanceMin = findMissingPath(array, beento, r + 1, c, rFinal, cFinal,
    						distanceMin, distance + 1, mazeRowSize, mazeColSize, beento1);
    			}

    			// go to right cell
    			if (isValid(r, c + 1, mazeRowSize, mazeColSize) && isSafe(array, beento, r, c + 1)) {
    				distanceMin = findMissingPath(array, beento, r, c + 1, rFinal, cFinal,
    											distanceMin, distance + 1, mazeRowSize, mazeColSize, beento1);
    			}

    			// go to top cell
    			if (isValid(r - 1, c, mazeRowSize, mazeColSize) && isSafe(array, beento, r - 1, c)) {
    				distanceMin = findMissingPath(array, beento, r - 1, c, rFinal, cFinal,
    											distanceMin, distance + 1, mazeRowSize, mazeColSize, beento1);
    			}

    			// go to left cell
    			if (isValid(r, c - 1, mazeRowSize, mazeColSize) && isSafe(array, beento, r, c - 1)) {
    				distanceMin = findMissingPath(array, beento, r, c - 1, rFinal, cFinal,
    											distanceMin, distance + 1, mazeRowSize, mazeColSize, beento1);
    			}

    			
    			// Backtrack - Remove (i, j) from visited matrix
    			beento[r][c] = 0;

    			return distanceMin;
    }
    
 // Check if it is possible to go to (x, y) from current position. The
 	// function returns false if the cell has value 0 or already visited
 	private static boolean isSafe(String array[][], int beento[][], int rFinal, int cFinal)
 	{
 		return !(array[rFinal][cFinal].equals("0") || beento[rFinal][cFinal] != 0);
 	}

 	// if not a valid position, return false
 	private static boolean isValid(int rFinal, int cFinal, int mazeRowSize, int mazeColSize)
 	{
 		return (rFinal < mazeRowSize && cFinal < mazeColSize && rFinal >= 0 && cFinal >= 0);
 	}
}
