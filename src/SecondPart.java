import java.util.Scanner;

public class SecondPart {

  // ------------------------------ GLOBAL VARIABLES ------------------------------

  // A player location
  public static int aPlayerXLocation;
  public static int aPlayerYLocation;

  // B player location
  public static int bPlayerXLocation;
  public static int bPlayerYLocation;

  // Top left corner of WINNERS CARPET location
  public static int xLocCarpet;
  public static int yLocCarpet;

  // WINNERS CARPET size
  public static int carpetSize;

  // ------------------------------------------------------------------------------

  // ------------------------- GLOBAL CONSTANTS VARIABLES -------------------------

  // Assumption: The board game is square in size
  public static final int BOARD_SIZE = 10;
  public static final int SAFETY_WALL_SIZE = 1;

  // SYMBOLS
  public static final char SAFETY_WALL_SYMBOL = '#';
  public static final char WINNERS_CARPET_SYMBOL = '*';
  public static final char EMPTY_CELL_SYMBOL = ' ';

  // Note: I would normally define the player symbols as characters (chars).
  // In order to stick to the signatures of the functions I'll define them as a string.
  public static final String PLAYER_A_SYMBOL = "A";
  public static final String PLAYER_B_SYMBOL = "B";

  // ------------------------------------------------------------------------------

  public static void main(String[] args) {

    // Set up initial game values and display the starting game board
    initializeValues();
    printBoard();

    // Calculate the minimum moves required for each player to reach the WINNERS CARPET
    int aMinWinningMoves = calcWinningMoves(aPlayerXLocation, aPlayerYLocation);
    int bMinWinningMoves = calcWinningMoves(bPlayerXLocation, bPlayerYLocation);

    // Determine which player has a better chance of reaching the carpet first.
    // Note: The first player to play has the advantage of going first and will win
    //       if both players require the same number of moves.
    //       I'll assume there is no need to consider this fact based on
    //       the assignment requirements.
    if (aMinWinningMoves < bMinWinningMoves) {
      System.out.println("A player has the better chance");
    } else if (aMinWinningMoves > bMinWinningMoves) {
      System.out.println("B player has the better chance");
    } else {
      System.out.println("Chances are equal for both players");
    }
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: The function receives the player's current coordinates. ---
  // --- Exit statement: The function returns the minimum number of moves required
  //                     for the player to win the game. ---
  private static int calcWinningMoves(int xLocPlayer, int yLocPlayer) {

    // Optimal coordinates on the WINNERS CARPET.
    // (The coordinates of the nearest point on the WINNERS CARPET to the player's current position)
    int optimalX = xLocPlayer;
    int optimalY = yLocPlayer;

    // Determine the optimal x-coordinate on the winner's carpet.
    if (xLocPlayer < xLocCarpet) {
      optimalX = xLocCarpet;
    } else if (xLocPlayer >= xLocCarpet + carpetSize) {
      optimalX = xLocCarpet + carpetSize - 1;
    }

    // Determine the optimal y-coordinate on the winner's carpet.
    if (yLocPlayer < yLocCarpet) {
      optimalY = yLocCarpet;
    } else if (yLocPlayer > yLocCarpet + carpetSize) {
      optimalY = yLocCarpet + carpetSize - 1;
    }

    // Calculate the Manhattan distance between the optimal point on the carpet and the current
    // position.
    return Math.abs(optimalX - xLocPlayer) + Math.abs(optimalY - yLocPlayer);
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function doesn't get any parameters. ---
  // --- Exit statement: This function gets initial data for the players'
  //     starting positions, the top-left location of the WINNERS CARPET,
  //     and the carpet's size.
  //     These values are then stored in global variables for game setup. ---
  private static void initializeValues() {
    // Scanner object for capturing user input for initial positions and game settings
    final Scanner playerInput = new Scanner(System.in);

    // Collect initial position data for players and WINNERS CARPET from user input
    System.out.println("WELCOME\nenter A player X location");
    aPlayerXLocation = playerInput.nextInt();
    System.out.println("enter A player Y location");
    aPlayerYLocation = playerInput.nextInt();
    System.out.println("enter B player X location");
    bPlayerXLocation = playerInput.nextInt();
    System.out.println("enter B player y location");
    bPlayerYLocation = playerInput.nextInt();
    System.out.println("enter WINNERS CARPET top left location");
    xLocCarpet = playerInput.nextInt();
    yLocCarpet = playerInput.nextInt();
    System.out.println("enter WINNERS CARPET size");
    carpetSize = playerInput.nextInt();
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function doesn't get any parameters ---
  // --- Exit statement: This function displays the current state of the game board ---
  private static void printBoard() {

    // Iterate through each row on the board, including safety walls
    for (int yIndex = 0; yIndex < BOARD_SIZE + 2 * SAFETY_WALL_SIZE; yIndex++) {
      // Iterate through each column in the current row
      for (int xIndex = 0; xIndex < BOARD_SIZE + 2 * SAFETY_WALL_SIZE; xIndex++) {
        // SAFETY WALL
        if (xIndex == 0
            || yIndex == 0
            || xIndex == BOARD_SIZE + 2 * SAFETY_WALL_SIZE - 1
            || yIndex == BOARD_SIZE + 2 * SAFETY_WALL_SIZE - 1) {
          System.out.print(SAFETY_WALL_SYMBOL);
        }

        // PLAYER A
        else if (xIndex == aPlayerXLocation && yIndex == aPlayerYLocation) {
          System.out.print(PLAYER_A_SYMBOL);
        }

        // PLAYER B
        else if (xIndex == bPlayerXLocation && yIndex == bPlayerYLocation) {
          System.out.print(PLAYER_B_SYMBOL);
        }

        // WINNERS CARPET
        else if (isOnWinnersCarpet(xIndex, yIndex)) {
          System.out.print(WINNERS_CARPET_SYMBOL);
        }

        // EMPTY CELL
        else {
          System.out.print(EMPTY_CELL_SYMBOL);
        }
      }

      // Move to the next row after printing all columns in the current row
      System.out.println();
    }
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function takes coordinates (xIndex, yIndex) as parameters. ---
  // --- Exit statement: Returns true if the given coordinates are within the
  //     boundaries of the WINNERS CARPET, otherwise false. ---
  public static boolean isOnWinnersCarpet(int xIndex, int yIndex) {

    return (xIndex >= xLocCarpet && xIndex < xLocCarpet + carpetSize)
        && (yIndex >= yLocCarpet && yIndex < yLocCarpet + carpetSize);
  }

  // ------------------------------------------------------------------------------
}
