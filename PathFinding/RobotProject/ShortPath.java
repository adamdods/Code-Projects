class ShortPath
{

	static int rowSize;
	static int colSize;
	private static int beentolast[][];
    private static int beentolastnew[][];

	public static String pathmode1(String[][] a, int mazeValueRow, int mazeValueCol, int mazeValueRowFINAL, int mazeValueColFINAL, int mazeRowSize, int mazeColSize)
	{
		String array[][] = a;

	    int beentolast2size =0;
	    int beentolastsize =0;
		rowSize = mazeRowSize;
		colSize = mazeColSize;
		// construct a arrayrix to keep track of beento cells
		int[][] beento = new int[rowSize][colSize];
		beentolast = new int[rowSize][colSize];
		beentolastnew = new int[rowSize][colSize];
		int size = 0;
		int distanceMin = findShortestPath(array, beento, mazeValueRow, mazeValueCol, mazeValueRowFINAL, mazeValueColFINAL,
										Integer.MAX_VALUE, 0);

		int up;
		int right;
		int down;
		int left;
		try {
			up = beentolast[mazeValueRow-1][mazeValueCol];
		} catch (ArrayIndexOutOfBoundsException e) {
			up = 0;
		}
		try {
			right = beentolast[mazeValueRow][mazeValueCol+1];
		} catch (ArrayIndexOutOfBoundsException e) {
			right = 0;
		}
		try {
			down = beentolast[mazeValueRow+1][mazeValueCol];
		} catch (ArrayIndexOutOfBoundsException e) {
			down = 0;
		}
		try {
			left = beentolast[mazeValueRow][mazeValueCol-1];
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
	

	// Find Shortest Possible Route in a arrayrix array from source cell (0, 0)
	// to destination cell (x, y)

	// 'distanceMin' stores length of longest path from source to destination
	// found so far and 'dist' maintains length of path from source cell to
	// the current cell (i, j)

	public static int findShortestPath(String array[][], int beento[][],
					 int i, int j, int x, int y, int distanceMin, int distance)
	{
		

		// if destination is found, update distanceMin
		if (i == x && j == y)
		{
			int beentolastsize = 0;
			int beentolast2size = 0;

			for(int r = 0; r < rowSize; r++) {
				 for (int c = 0; c < colSize; c++) {

					 beentolast2size = beentolast2size + beento[r][c];
					 beentolastsize = beentolastsize + beentolast[r][c];
				 }
			}
			if (beentolastsize == 0) {
				for(int r = 0; r < rowSize; r++) {
					 for (int c = 0; c < colSize; c++) {
						 beentolast[r][c] = beento[r][c];
						 beentolastsize = beentolastsize + beentolast[r][c];
					 }
				}
			}
			if (beentolastsize > beentolast2size) {
				for(int r = 0; r < rowSize; r++) {
					 for (int c = 0; c < colSize; c++) {
						beentolast[r][c] = beento[r][c];
					 }
				}
			}
			return Integer.min(distance, distanceMin);
		}

		// set (i, j) cell as beento
		beento[i][j] = 1;

		// go to bottom cell
		if (positionGood(i + 1, j) && positionPossible(array, beento, i + 1, j)) {
			distanceMin = findShortestPath(array, beento, i + 1, j, x, y,
										distanceMin, distance + 1);
		}

		// go to right cell
		if (positionGood(i, j + 1) && positionPossible(array, beento, i, j + 1)) {
			distanceMin = findShortestPath(array, beento, i, j + 1, x, y,
										distanceMin, distance + 1);
		}

		// go to top cell
		if (positionGood(i - 1, j) && positionPossible(array, beento, i - 1, j)) {
			distanceMin = findShortestPath(array, beento, i - 1, j, x, y,
										distanceMin, distance + 1);
		}

		// go to left cell
		if (positionGood(i, j - 1) && positionPossible(array, beento, i, j - 1)) {
			distanceMin = findShortestPath(array, beento, i, j - 1, x, y,
										distanceMin, distance + 1);
		}

		
		// Backtrack - Remove (i, j) from beento arrayrix
		beento[i][j] = 0;

		return distanceMin;
	}
	// Check if it is possible to go to (x, y) from current position. The
		// function returns false if the cell has value 0 or already beento
		private static boolean positionPossible(String array[][], int beento[][], int x, int y)
		{
			return !(array[x][y].equals("0") || beento[x][y] != 0);
		}

		// if not a valid position, return false
		private static boolean positionGood(int x, int y)
		{
			return (x < rowSize && y < colSize && x >= 0 && y >= 0);
		}


}