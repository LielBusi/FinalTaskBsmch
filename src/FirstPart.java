import java.util.Scanner;

public class FirstPart {

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

  // Movement directions to improve readability and replace direct numeric values in the code.
  public static final int MOVE_UP = 1;
  public static final int MOVE_DOWN = 2;
  public static final int MOVE_RIGHT = 3;
  public static final int MOVE_LEFT = 4;

  // Messages displayed to players for game feedback and guidance
  public static final String OUT_OF_BOUNDS_ERROR = "You can't go outside the board";
  public static final String DIRECTIONS_MENU =
      MOVE_UP + "-up, " + MOVE_DOWN + "-down, " + MOVE_RIGHT + "-right, " + MOVE_LEFT + "-left";
  public static final String BAD_INPUT_ERROR =
      "Invalid option. Please enter a number between 1 and 4";

  // Code indicating an invalid move attempt, used in game logic validation
  public static final int INVALID_MOVE = -1;

  // ------------------------------------------------------------------------------

  public static void main(String[] args) {

    // Set up initial game values and display the starting game board
    initializeValues();
    printBoard();

    // Flag to indicate when the game should end.
    boolean didGameEnd = false;

    // Main game loop: runs until the flag is set to true, indicating a win
    while (!didGameEnd) {
      // Player A's turn to make a move
      makeTurn(PLAYER_A_SYMBOL);

      // Check if Player A's move results in a win and update the flag
      didGameEnd = didPlayerWin(aPlayerXLocation, aPlayerYLocation);
      printBoard();

      if (didGameEnd) {
        System.out.println("Player " + PLAYER_A_SYMBOL + " won this round!");
      } else {
        // Player B's turn to make a move (only if Player A didn’t win)
        makeTurn(PLAYER_B_SYMBOL);

        // Check if Player B's move results in a win and update the flag
        didGameEnd = didPlayerWin(bPlayerXLocation, bPlayerYLocation);
        printBoard();
        if (didGameEnd) {
          System.out.println("Player " + PLAYER_B_SYMBOL + " won this round!");
        }
      }
    }
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

  // --- Enter statement: Receives the symbol of the player whose turn is being managed. ---
  // --- Exit statement: Manages a single turn for the specified player. ---
  private static void makeTurn(String player) {
    // Retrieve the player’s chosen movement direction
    int playerDirection = getMoveDirection(player);

    // If the selected move is within bounds, execute it.
    // Otherwise, display an out-of-bounds error message.
    // Note: An invalid move will result in skipping this player's turn.
    if (playerDirection != INVALID_MOVE) {
      movePlayer(player, playerDirection);
    } else {
      System.out.println(OUT_OF_BOUNDS_ERROR);
    }
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: Receives the symbol of a player. ---
  // --- Exit statement: Returns the player's chosen movement direction,
  //     or INVALID_MOVE if the chosen direction results in an out-of-bounds movement. ---
  private static int getMoveDirection(String player) {
    // Display the current player's turn and available movement directions
    System.out.println("player " + player + "'s move");
    System.out.println(DIRECTIONS_MENU);

    // Create a scanner to capture the player's input for movement direction
    final Scanner playerChoice = new Scanner(System.in);
    int playerDirectionChoice = playerChoice.nextInt();

    // Validate the chosen direction against board boundaries.
    // If it's invalid, mark it as such
    if (player.equals(PLAYER_A_SYMBOL)) {
      if (isMovingTowardsBorder(aPlayerXLocation, aPlayerYLocation, playerDirectionChoice)) {
        playerDirectionChoice = INVALID_MOVE;
      }
    } else {
      if (isMovingTowardsBorder(bPlayerXLocation, bPlayerYLocation, playerDirectionChoice)) {
        playerDirectionChoice = INVALID_MOVE;
      }
    }

    return playerDirectionChoice;
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function gets the player's current (x, y) coordinates
  //                      and their intended movement direction. ---
  // --- Exit statement: Returns true if the player is attempting to move outside
  //                     the map boundaries. Otherwise, returns false. ---
  private static boolean isMovingTowardsBorder(
      int playerXLocation, int playerYLocation, int playerMovement) {

    // Check for boundary conditions where the player would move out of bounds.
    // The precedence of AND (&&) over OR (||) eliminates the need for parentheses.

    // Note: AND (&&) has higher precedence than OR (||), so parentheses () are not needed.
    return playerXLocation == SAFETY_WALL_SIZE && playerMovement == MOVE_LEFT
        || playerXLocation == BOARD_SIZE && playerMovement == MOVE_RIGHT
        || playerYLocation == SAFETY_WALL_SIZE && playerMovement == MOVE_UP
        || playerYLocation == BOARD_SIZE && playerMovement == MOVE_DOWN;
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function gets the current player's symbol
  //            and the intended movement direction. ---
  // --- Exit statement: This function moves the player to the new coordinates
  //                     based on the movement direction.
  //                     It prints an error message if the movement direction is invalid. ---
  private static void movePlayer(String player, int playerMovement) {
    // Initialize changes in the player's coordinates
    int changeInY = 0;
    int changeInX = 0;

    // Determine the changes in coordinates based on the movement direction
    switch (playerMovement) {
      case MOVE_UP:
        changeInY = -1;
        break;
      case MOVE_DOWN:
        changeInY = 1;
        break;
      case MOVE_RIGHT:
        changeInX = 1;
        break;
      case MOVE_LEFT:
        changeInX = -1;
        break;
      default:
        // Handle invalid movement direction
        System.out.println(BAD_INPUT_ERROR);
        return;
    }

    // Update the coordinates of the respective player
    if (player.equals(PLAYER_A_SYMBOL)) {
      aPlayerXLocation += changeInX;
      aPlayerYLocation += changeInY;
    } else {
      bPlayerXLocation += changeInX;
      bPlayerYLocation += changeInY;
    }
  }

  // ------------------------------------------------------------------------------

  // --- Enter statement: This function gets the player's current coordinates (x, y). ---
  // --- Exit statement: Returns true if the player is on the WINNERS CARPET,
  //                     indicating a win. Otherwise, returns false.
  private static boolean didPlayerWin(int playerXLoc, int playerYLoc) {
    return isOnWinnersCarpet(playerXLoc, playerYLoc);
  }

  // ------------------------------------------------------------------------------
}
