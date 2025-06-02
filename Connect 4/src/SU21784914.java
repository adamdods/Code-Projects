import javax.swing.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.awt.Color;

public class SU21784914 {
    //Board size
    static int X = 6; // Height
    static int Y = 7; // Width
    //This variable can be used to turn your debugging output on and off. Preferably use
    static boolean DEBUG = true;
    static int[][] grid = new int[X][Y];
    static int[][] gridDiscTypes = new int[X][Y];
    static int[] p1powers;
    static int[] p2powers;

    public static void main(String[] args) throws InterruptedException {
        //The 6-by-7 grid that represents the gameboard, it is initially empty
        //Shows which player's turn it is, true for player 1 and false for player 2
        //Shows the number of special tokens a player has left
        p1powers = new int[]{2, 2, 2};
        p2powers = new int[]{2, 2, 2};

        grid = new int[X][Y];

        // Asks for mode Gui(G) or Terminal(T)
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Select mode: G (GUI) / T (Terminal): ");
        String inputMode = inputScanner.nextLine().trim().toUpperCase();

        //Sets whether the game is in terminal mode or GUI mode
        if (inputMode.equals("T")) {
            commandLineRun();
        } else if (inputMode.equals("G")) {
            // Draws the initial GUI
            drawGUI();
            // Starts listening for user input
            actionListener();
        } else {
            StdOut.println("Usage:\nArgument 1: G/T\nG - Launches GUI MODE\nT - Launches Command Line mode");
        }

    }

    // Checks if option input was valid and returns the input if it was valid, otherwise returns -1 signaling error
    public static int getOption() {
        String inpStr = StdIn.readLine();
        if (inpStr.length() != 1) {
            StdOut.println("Please choose a valid option");
            StdOut.println();
            Display();
            StdOut.println();
            return -1;
        }
        char inpChar = inpStr.charAt(0);
        if (!Character.isDigit(inpChar)) {
            StdOut.println("Please choose a valid option");
            StdOut.println();
            Display();
            StdOut.println();
            return -1;
        }
        int inpInt = Integer.parseInt(inpStr);
        if (inpInt < 0 || inpInt > 6) {
            StdOut.println("Please choose a valid option");
            StdOut.println();
            Display();
            StdOut.println();
            return -1;
        }
        return inpInt;
    }

    // Checks if move input was valid and returns the input if it was valid, otherwise returns -1 signaling error
    public static int getMove() {
        String inpStr = StdIn.readLine();
        if (inpStr.length() != 1) {
            StdOut.println("Illegal move, please input legal move:");
            return -1;
        }
        char inpChar = inpStr.charAt(0);
        if (!Character.isDigit(inpChar)) {
            StdOut.println("Illegal move, please input legal move:");
            return -1;
        }
        int inpInt = Integer.parseInt(inpStr);
        if (inpInt < 0 || inpInt > 6) {
            StdOut.println("Illegal move, please input legal move:");
            return -1;
        }
        return inpInt;
    }

    /**
     * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows column and row numbers at the top.
     *
     * @param grid The current board state
     */
    public static void Display() {
        StdOut.print("  ");
        for (int i = 0; i < Y; i++) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        for (int i = 0; i < X; i++) {
            StdOut.print(i + " ");
            for (int j = 0; j < Y; j++) {
                switch (grid[i][j]) {
                    case 0:
                        StdOut.print("* ");
                        break;
                    case 1:
                        StdOut.print("R ");
                        break;
                    case 2:
                        StdOut.print("Y ");
                        break;
                }
            }
            StdOut.println();
        }
    }

    /**
     * Exits the current game state
     */
    public static void Exit() {
        System.exit(0);
    }

    /**
     * Plays a normal disc in the specified position (i) in the colour of the player given. Returns the modified grid or
     * prints out an error message if the column is full.
     *
     * @param i       Column that the disc is going to be played in
     * @param grid    The current board state
     * @param player1 The current player
     * @return grid     The modified board state
     */
    public static int[][] Play(int i, boolean player1) {
        for (int j = X - 1; j >= 0; j--) {
            if (grid[j][i] == 0) {
                setPlayerToken(j, i, player1);
                break;
            }
        }
        return grid;
    }

    public static void setPlayerToken(int j, int i, boolean player1) {
        if (player1) {
            grid[j][i] = 1;
        } else {
            grid[j][i] = 2;
        }
    }

    /**
     * Checks whether a player has 4 tokens in a row or if the game is a draw.
     *
     * @param grid The current board state in a 2D array of integers
     */
    public static int Check_Win() {
        boolean player1wins = false;
        boolean player2wins = false;
        boolean boardFull = true;
        //check down
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Y; j++) {
                if (grid[i][j] == 1) {
                    int count = 0;
                    for (int k = i; k < i + 4; k++) {
                        if (grid[k][j] == 1) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player1wins = true;
                    }
                }
                if (grid[i][j] == 2) {
                    int count = 0;
                    for (int k = i; k < i + 4; k++) {
                        if (grid[k][j] == 2) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player2wins = true;
                    }
                }
            }
        }
        //right
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 1) {
                    int count = 0;
                    for (int k = j; k < j + 4; k++) {
                        if (grid[i][k] == 1) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player1wins = true;
                    }
                }
                if (grid[i][j] == 2) {
                    int count = 0;
                    for (int k = j; k < j + 4; k++) {
                        if (grid[i][k] == 2) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player2wins = true;
                    }
                }
            }
        }
        //south east
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 1) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        if (grid[i + k][j + k] == 1) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player1wins = true;
                    }
                }
                if (grid[i][j] == 2) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        if (grid[i + k][j + k] == 2) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player2wins = true;
                    }
                }
            }
        }
        //north east
        for (int i = 3; i < X; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 1) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        if (grid[i - k][j + k] == 1) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player1wins = true;
                    }
                }
                if (grid[i][j] == 2) {
                    int count = 0;
                    for (int k = 0; k < 4; k++) {
                        if (grid[i - k][j + k] == 2) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    if (count == 4) {
                        player2wins = true;
                    }
                }
            }
        }
        // Checks if the board is full
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (grid[i][j] == 0) {
                    boardFull = false;
                    break;
                }
            }
        }
        /*
            0 - Draw
            1 - P1 wins
            2 - P2 wins
           -1 - No winner
         */
        // Draw
        if (player1wins && player2wins) {
            return 0;
        } else if (player1wins) {
            return 1;
        } else if (player2wins) {
            return 2;
            // Draw
        } else if (boardFull) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Plays a bomb disc that blows up the surrounding tokens and drops down tokens above it. Should not affect the
     * board state if there's no space in the column. In that case, print the error message: "Column is full."
     *
     * @param i       Column that the disc is going to be played in
     * @param grid    The current board state
     * @param player1 The current player
     * @return grid     The modified board state
     */
    public static int[][] Bomb(int i, boolean player1) {
        // Checks for the first empty position in the column and return
        for (int j = X - 1; j >= 0; j--) {
            // If the position is empty play the bomb and end the
            if (grid[j][i] == 0) {
                dropBomb(i, j, player1);
                return grid;
            }
        }
        return grid;
    }

    // Drops the bomb in selected column and destroys all tokens surrounding the dropped location
    public static void dropBomb(int i, int j, boolean player1) {
        boolean bottom = false;
        boolean top = false;
        boolean left = false;
        boolean right = false;

        // Checks if the  position where the bomb is dropped are on any borders of the board
        if (j == 0) {
            top = true;
        }
        if (j == X - 1) {
            bottom = true;
            gridDiscTypes[j][i] = 2;
        } else {
            gridDiscTypes[j + 1][i] = 2;
        }
        if (i == 0) {
            left = true;
        }
        if (i == Y - 1) {
            right = true;
        }

        // If the bomb is not dropped on the left border column, destroy tokens left to it
        if (!left) {
            grid[j][i - 1] = 0;
            gridDiscTypes[j][i - 1] = 0;
            // If the bomb is not dropped at the top of the board, destroy tokens above it
            if (!top) {
                grid[j - 1][i - 1] = 0;
                gridDiscTypes[j - 1][i - 1] = 0;
            }
            // If the bomb is not dropped at the bottom of the board, destroy tokens below it
            if (!bottom) {
                grid[j + 1][i - 1] = 0;
                gridDiscTypes[j + 1][i - 1] = 0;
                // Drops discs 3 space above destroyed disc
                dropDiscs(i - 1, j + 1, 3);
            } else {
                // Drops discs 2 spaces above destroyed disc
                dropDiscs(i - 1, j, 2);
            }
        }

        // If the bomb is not dropped on the right border column, destroy tokens to the right of it
        if (!right) {
            grid[j][i + 1] = 0;
            gridDiscTypes[j][i + 1] = 0;
            // If the bomb is not dropped at the top of the board, destroy tokens above it
            if (!top) {
                grid[j - 1][i + 1] = 0;
                gridDiscTypes[j - 1][i + 1] = 0;
            }
            // If the bomb is not dropped at the bottom of the board, destroy tokens below it
            if (!bottom) {
                grid[j + 1][i + 1] = 0;
                gridDiscTypes[j + 1][i + 1] = 0;
                // Drops discs 3 space above destroyed disc
                dropDiscs(i + 1, j + 1, 3);
            } else {
                // Drops discs 2 spaces above destroyed disc
                dropDiscs(i + 1, j, 2);
            }
        }

        /*
          If the bomb is dropped on the bottom border, do no destroys token underneath outside border range and the bomb
          token should not drop a level
         */
        if (!bottom) {
            grid[j][i] = 0;
            gridDiscTypes[j][i] = 0;
            setPlayerToken(j + 1, i, player1);
        } else {
            setPlayerToken(j, i, player1);
        }
    }

    // Drops discs above destroyed discs
    public static void dropDiscs(int i, int j, int dropLength) {
        for (; j >= dropLength; j--) {
            grid[j][i] = grid[j - dropLength][i];
            gridDiscTypes[j][i] = gridDiscTypes[j - dropLength][i];
            grid[j - dropLength][i] = 0;
            gridDiscTypes[j - dropLength][i] = 0;
        }
    }

    /**
     * Plays a teleporter disc that moves the targeted disc 3 columns to the right. If this is outside of the board
     * boundaries, it should wrap back around to the left side. If the column where the targeted disc lands is full,
     * destroy that disc. If the column where the teleporter disc falls is full, play as normal, with the teleporter
     * disc replacing the top disc.
     *
     * @param i       Column that the disc is going to be played in
     * @param grid    The current board state
     * @param player1 The current player
     * @return grid     The modified board state
     */
    public static int[][] Teleport(int i, boolean player1) {
        // If the column is full, teleport the top token and place your token at the top position
        if (grid[0][i] != 0) {
            teleportDisc(i, 0, player1);
            gridDiscTypes[0][i] = 3;
            return grid;
        }
        // If the bottom position is open dont play anything
        if (grid[X - 1][i] == 0) {
            return grid;
        }
        // Look for the first open position in the column, then teleport the token below that postion and place your token
        // at rhe old position position
        for (int j = X - 1; j >= 0; j--) {
            if (grid[j][i] == 0) {
                teleportDisc(i, j + 1, player1);
                gridDiscTypes[j + 1][i] = 3;
                return grid;
            }
        }
        return grid;
    }

    // Teleports the disc 3 tiles right
    public static void teleportDisc(int i, int j, boolean player1) {
        // Checks if the column the disc is teleported to is full if it is print an error, else play a token in column
        // as normal
    	// Warps the token around if it falls outside of the border using modulus
        if (grid[0][(i + 3) % Y] != 0) {
            StdOut.println("Column is full.");
        } else {
            if (grid[j][i] == 1) {
                setTeleportedDiscTypes((i + 3) % Y, gridDiscTypes[j][i]);
                Play((i + 3) % Y, true);
            }
            if (grid[j][i] == 2) {
                setTeleportedDiscTypes((i + 3) % Y, gridDiscTypes[j][i]);
                Play((i + 3) % Y, false);
            }
        }

        // Change the old token to the player token
        setPlayerToken(j, i, player1);
    }

    // Sets the teleported disc type to the original disc type value
    public static void setTeleportedDiscTypes(int i, int discType) {
        for (int k = X - 1; k >= 0; k--) {
            if (grid[k][i] == 0) {
                gridDiscTypes[k][i] = discType;
                break;
            }
        }
    }

    /**
     * Plays the colour changer disc that changes the affected disc's colour to the opposite colour
     *
     * @param i       Column that the disc is going to be played in
     * @param grid    The current board state
     * @param player1 The current player
     * @return grid     The modified board state
     */
    public static int[][] Colour_Changer(int i, boolean player1) {
        // Checks if the column is full, if it is change the top token to the player token
        if (grid[0][i] != 0) {
            grid[0][i] = (grid[0][i]) % 2 + 1;
            gridDiscTypes[0][i] = 4;
            return grid;
        }
        // Checks if the botton position in the column is empty, if it is, play player token as normal
        if (grid[X - 1][i] == 0) {
            setPlayerToken(X - 1, i, player1);
            gridDiscTypes[X - 1][i] = 4;
            return grid;
        }

        for (int j = X - 1; j >= 0; j--) {
            if (grid[j][i] == 0) {
                grid[j + 1][i] = (grid[j + 1][i]) % 2 + 1;
                gridDiscTypes[j + 1][i] = 4;
                break;
            }
        }
        return grid;
    }

    /**
     * Reads in a board from a text file.
     *
     * @param name The name of the given file
     * @return
     */
    //Reads in a game state from a text file, assumes the file is a txt file
    public static int[][] Test(String name) {
        int[][] grid = new int[6][7];
        try {
            File file = new File(name + ".txt");
            Scanner sc = new Scanner(file);

            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grid;
    }

    /**
     * Saves the current game board to a text file.
     *
     * @param name The name of the given file
     * @param grid The current game board
     * @return
     */
    // Used for testing
    public static int[][] Save(String name) {
        try {
            FileWriter fileWriter = new FileWriter(name + ".txt");
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    fileWriter.write(Integer.toString(grid[i][j]) + " ");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return grid;
    }

    /**
     * Debugging tool, preferably use this since we can then turn off your debugging output if you forgot to remove it.
     * Only prints out if the DEBUG variable is set to true.
     *
     * @param line The String you would like to print out.
     */
    public static void Debug(String line) {
        if (DEBUG)
            System.out.println(line);
    }

    public static void newGame() {
        grid = new int[X][Y];
        gridDiscTypes = new int[X][Y];
        p1powers = new int[]{2, 2, 2};
        p2powers = new int[]{2, 2, 2};
        redrawBoard(true);
    }

    public static void commandLineRun() {
        boolean gameOver = false;

        StdOut.println("****************************************************************************");
        StdOut.println("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *" +
                "\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *" +
                "\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *" +
                "\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *" +
                "\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *" +
                "\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *" +
                "\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
        StdOut.println("*                                                                          *");
        StdOut.println("****************************************************************************");

        //Array for current player's number of power storage
        int[] curppowers;

        while (true) {
            boolean player1;
            // For loop used to alternate the player
            for (int p = 1; true; p++) {
                player1 = (p % 2) == 1;

                int input;
                // Blocking variable used to check if the users turn is done and can switch to next player turn
                boolean blocking = true;
                do {
                    do {
                        if (player1) {
                            StdOut.println("Player 1's turn (You are Red):");
                            curppowers = p1powers;
                        } else {
                            StdOut.println("Player 2's turn (You are Yellow):");
                            curppowers = p2powers;
                        }
                        StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb (" + curppowers[0] + " left) \n 3. Play Teleportation (" + curppowers[1] + " left) \n 4. Play Colour Changer (" + curppowers[2] + " left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
                        input = getOption();
                    } while (input == -1);


                    switch (input) {
                        case 0:
                            Exit();
                            break;

                        case 1:
                            StdOut.println("Choose a column to play in:");
                            // Checks if given move is a valid move
                            do {
                                input = getMove();
                            } while (input == -1);
                            if (grid[0][input] != 0) {
                                StdOut.println("Column is full.");
                            } else {
                                Play(input, player1);
                                blocking = false;
                            }
                            break;

                        case 2:
                            StdOut.println("Choose a column to play in:");

                            // Checks if given move is a valid move
                            do {
                                input = getMove();
                            } while (input == -1);
                            if (player1) {
                                if (p1powers[0] > 0) {
                                    p1powers[0]--;
                                } else {
                                    StdOut.println("You have no bomb power discs left");
                                    break;
                                }
                            } else {
                                if (p2powers[0] > 0) {
                                    p2powers[0]--;
                                } else {
                                    StdOut.println("You have no bomb power discs left");
                                    break;
                                }
                            }

                            blocking = false;

                            if (grid[0][input] != 0) {
                                StdOut.println("Column is full.");
                            } else {
                                Bomb(input, player1);
                            }
                            break;

                        case 3:
                            StdOut.println("Choose a column to play in:");
                            do {
                                input = getMove();
                            } while (input == -1);

                            if (player1) {
                                if (p1powers[1] > 0) {
                                    p1powers[1]--;
                                } else {
                                    StdOut.println("You have no teleporter power discs left");
                                    break;
                                }
                            } else {
                                if (p2powers[1] > 0) {
                                    p2powers[1]--;
                                } else {
                                    StdOut.println("You have no teleporter power discs left");
                                    break;
                                }
                            }

                            Teleport(input, player1);
                            blocking = false;

                            break;

                        case 4:
                            StdOut.println("Choose a column to play in:");
                            do {
                                input = getMove();
                            } while (input == -1);

                            if (player1) {
                                if (p1powers[2] > 0) {
                                    p1powers[2]--;
                                } else {
                                    StdOut.println("You have no colour changer power discs left");
                                    break;
                                }
                            } else {
                                if (p2powers[2] > 0) {
                                    p2powers[2]--;
                                } else {
                                    StdOut.println("You have no colour changer power discs left");
                                    break;
                                }
                            }

                            Colour_Changer(input, player1);
                            blocking = false;

                            break;

                        //This part will be used during testing, please DO NOT change it. This will result in loss of marks
                        case 5:
                            Display();
                            //Displays the current gameboard again, doesn't count as a move, so the player stays the same
                            player1 = !player1;
                            blocking = false;
                            break;

                        //This part will be used during testing, please DO NOT change it. This will result in loss of marks
                        case 6:
                            grid = Test(StdIn.readString());
                            //Reads in a test file and loads the gameboard from it.
                            player1 = !player1;
                            blocking = false;
                            break;
                        //This part will be used during testing, please DO NOT change it. This will result in loss of marks
                        case 7:
                            Save(StdIn.readString());
                            player1 = !player1;
                            blocking = false;
                            break;

                        default:
                            // Invalid choice was made, print out an error message but do not switch player turn
                            // Handled in getOption() function
                            break;
                    }
                    //Displays the grid after a new move was played
                    StdOut.println();
                    Display();
                    StdOut.println();
                } while (blocking);


                int win = Check_Win();
                switch (win) {
                    case 0:
                        StdOut.println("Draw!");
                        gameOver = true;
                        break;
                    case 1:
                        StdOut.println("Player 1 wins!");
                        gameOver = true;
                        break;
                    case 2:
                        StdOut.println("Player 2 wins!");
                        gameOver = true;
                        break;
                }
                if (gameOver) {
                    StdOut.println("Do you want to play again? \n 1. Yes \n 2. No");
                    //assumes valid input
                    input = Integer.parseInt(StdIn.readLine());
                    if (input == 1) {
                        gameOver = false;
                        newGame();
                        break;
                    } else {
                        Exit();
                    }
                }
            }
        }
    }

    public static void drawGUI() {
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);
        StdDraw.setPenRadius(0.005);

        drawStatusBar(true);
        
        // Draws Quit button
        StdDraw.rectangle(75, 145, 65, 45);
        StdDraw.text(75, 145, "Quit");

        // Draws the board grid
        for (int i = 150 + (114 / 2); i < 950; i = i + 114) {
            for (int j = 100 + (133 / 2); j < 900; j = j + 133) {
                StdDraw.rectangle(i, j, (114 / 2), (133 / 2));
            }
        }
    }

    public static void actionListener() throws InterruptedException {
        boolean player1 = true;
        while (true) {
            player1 = listenForKeyInput(player1);
            player1 = listenForMouseInput(player1);
        }
    }

    public static void redrawBoard(boolean player1) {

        drawStatusBar(player1);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                int x = 150 + (114 / 2) + 114 * i;
                int y = 900 - ((133 / 2) + 133 * j);
                switch (grid[j][i]) {
                    case 0:
                        StdDraw.setPenColor(Color.WHITE);
                        StdDraw.filledRectangle(x, y, (114 / 2), (133 / 2));
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.rectangle(x, y, (114 / 2), (133 / 2));
                        break;
                    case 1:
                        StdDraw.setPenColor(Color.RED);
                        StdDraw.filledCircle(x, y, (110 / 2));
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.circle(x, y, (110 / 2));
                        drawDiscLabel(j, i, x, y);
                        break;
                    case 2:
                        StdDraw.setPenColor(Color.YELLOW);
                        StdDraw.filledCircle(x, y, (110 / 2));
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.circle(x, y, (110 / 2));
                        drawDiscLabel(j, i, x, y);
                        break;
                }
            }
        }
    }

    public static void drawStatusBar(boolean player1) {
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledRectangle(75, 855, 65, 45);
        StdDraw.filledRectangle(75, 750, 65, 45);
        StdDraw.filledRectangle(75, 645, 65, 45);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.filledRectangle(75, 540, 65, 45);
        StdDraw.filledRectangle(75, 435, 65, 45);
        StdDraw.filledRectangle(75, 330, 65, 45);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.filledRectangle(500, 950, 200, 40);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(75, 855, 65, 45);
        StdDraw.text(75, 855, "P1 - B: " + p1powers[0]);
        StdDraw.rectangle(75, 750, 65, 45);
        StdDraw.text(75, 750, "P1 - T: " + p1powers[1]);
        StdDraw.rectangle(75, 645, 65, 45);
        StdDraw.text(75, 645, "P1 - C: " + p1powers[2]);
        StdDraw.rectangle(75, 540, 65, 45);
        StdDraw.text(75, 540, "P2 - B: " + p2powers[0]);
        StdDraw.rectangle(75, 435, 65, 45);
        StdDraw.text(75, 435, "P2 - T: " + p2powers[1]);
        StdDraw.rectangle(75, 330, 65, 45);
        StdDraw.text(75, 330, "P2 - C: " + p2powers[2]);
        StdDraw.rectangle(500, 950, 200, 40);

        if (player1) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(500, 950, "RED player turn");
        } else {
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(500, 950, "YELLOW player turn");
        }

        StdDraw.setPenColor(Color.BLACK);
    }

    public static void drawDiscLabel(int j, int i, double x, double y) {
        StdDraw.setPenColor(Color.BLACK);
        switch (gridDiscTypes[j][i]) {
            case 2:
                StdDraw.text(x, y, "B");
                break;
            case 3:
                StdDraw.text(x, y, "T");
                break;
            case 4:
                StdDraw.text(x, y, "C");
                break;
        }
    }

    public static boolean playMoveGUI(int col, boolean player1) {
        if (StdDraw.isKeyPressed(KeyEvent.VK_B) && grid[0][col] == 0) {
            if (player1) {
                if (p1powers[0] > 0) {
                    p1powers[0]--;
                    Bomb(col, player1);
                    player1 = !player1;
                }
            } else {
                if (p2powers[0] > 0) {
                    p2powers[0]--;
                    Bomb((col), player1);
                    player1 = !player1;
                }
            }
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_T)) {
            if (player1) {
                if (p1powers[1] > 0) {
                    p1powers[1]--;
                    Teleport(col, player1);
                    player1 = !player1;
                }
            } else {
                if (p2powers[1] > 0) {
                    p2powers[1]--;
                    Teleport(col, player1);
                    player1 = !player1;
                }
            }
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_C)) {
            if (player1) {
                if (p1powers[2] > 0) {
                    p1powers[2]--;
                    Colour_Changer(col, player1);
                    player1 = !player1;
                }
            } else {
                if (p2powers[2] > 0) {
                    p2powers[2]--;
                    Colour_Changer(col, player1);
                    player1 = !player1;
                }
            }
        } else if (grid[0][col] == 0) {
            Play(col, player1);
            player1 = !player1;
        }
        return player1;
    }

    public static boolean endMoveGUI(boolean player1) {
        redrawBoard(player1);
        switch (Check_Win()) {
            case 0:
                JOptionPane.showMessageDialog(null, "Draw!");
                newGame();
                player1 = true;
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Player 1 wins!");
                newGame();
                player1 = true;
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Player 2 wins!");
                newGame();
                player1 = true;
                break;
        }
        return player1;
    }

    public static boolean listenForMouseInput(boolean player1) throws InterruptedException {
        if (StdDraw.isMousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();

            // Listens if quit button was clicked
            if (x >= (75 - 65) && x <= (75 + 65) && y >= (145 - 45) && y <= (145 + 45)) {
                Exit();
            }

            // Used to fix Java's casting and rounding down from negative double to int problem
            int col;
            if ((x - 150) / 114 > 0) {
                col = (int) (x - 150) / 114;
            } else {
                col = -1;
            }

            // Listens if board input was made
            if (col >= 0 && col <= 6 && y >= 100 && y <= 900) {
                player1 = playMoveGUI(col, player1);
            }
            player1 = endMoveGUI(player1);
        }
        return player1;
    }

    public static boolean listenForKeyInput(boolean player1) {
        if (StdDraw.isKeyPressed(KeyEvent.VK_0)) {
            player1 = playMoveGUI(0, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
            player1 = playMoveGUI(1, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
            player1 = playMoveGUI(2, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
            player1 = playMoveGUI(3, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
            player1 = playMoveGUI(4, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
            player1 = playMoveGUI(5, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_6)) {
            player1 = playMoveGUI(6, player1);
            player1 = endMoveGUI(player1);
        } else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
            Exit();
        }
        return player1;
    }

}
