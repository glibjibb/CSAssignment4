package cs3500.hw04;

import java.util.ArrayList;

/**
 * An implementation of the CoinGameModel using the strict ruleset. Allows for multiple players
 * with any turn order.
 */
public final class StrictCoinGameModel implements CoinGameModel {
  protected int coinCount;
  protected ArrayList<Integer> currentBoard;
  protected ArrayList<Integer> players;
  protected int currentTurn;

  /**
   * coinCount keeps track of the number of coins on the board, for convenience.
   * currentBoard is an ArrayList (don't know size of board given) of integers, representing
   *   coins as their individual coinIndexes, and represents blank space as -1.
   * players is an ArrayList (players can be added any time) of integers in sequential order,
   *  representing their player numbers.
   * currentTurn will keep track of which player's turn it is
   *
   * INVARIANT: coinCount is >= 0 and <= currentBoard size
   * INVARIANT: currentBoard is filled with -1's and coin indexes, or empty
   * INVARIANT: players is filled with the player numbers, 1 based,
   *    in the order that they move
   * INVARIANT: currentTurn is >= 0 and < players size
   *
   */

  /**
   * StrictCoinGameModel(String board, int playerCount);
   * Constructs a board as an ArrayList of integers representing where
   * the coins are, either with their coinIndex or a -1 for blanks,
   * and fills an array of players with each's player number from 1
   * to playerCount. Initializes currentTurn to 1.
   *
   * @param board the board being passed in
   * @param playerCount the number of players, initially
   * @throws IllegalArgumentException if the board has invalid characters
   * @throws IllegalArgumentException if number of players is <= 0
   *
   * StrictCoinGameModel(String board);
   * Constructs a board as an ArrayList of integers representing coins with
   * coinIndex and blanks with -1. Sets the array of players to contain only 1.
   * Initialize turn to 0.
   *
   * @param board the board being passed in
   * @throws IllegalArgumentException if the board has invalid characters
   */
  protected StrictCoinGameModel(String board, int playerCount) {
    this.currentBoard = new ArrayList<Integer>();
    this.players = new ArrayList<Integer>();
    this.coinCount = 0;
    this.currentTurn = 0;
    for(int i = 0; i < board.length(); i++) {
      char curr = board.charAt(i);
      if(board.charAt(i) == 'O') {
        this.currentBoard.add(coinCount);
        this.coinCount++;
      }
      else if(board.charAt(i) == '-'){ this.currentBoard.add(-1); }
      else throw new IllegalArgumentException("Board has an invalid character.");
    }
    for(int i = 0; i < playerCount; i++) {
      this.players.add(i+1);
    }

  }
  protected StrictCoinGameModel(String board) {
    this.currentBoard = new ArrayList<Integer>();
    this.players = new ArrayList<Integer>();
    this.players.add(1);
    this.coinCount = 0;
    this.currentTurn = 0;
    for(int i = 0; i < board.length(); i++) {
      char curr = board.charAt(i);
      if (board.charAt(i) == 'O') {
        this.currentBoard.add(coinCount);
        this.coinCount++;
      } else if (board.charAt(i) == '-') {
        this.currentBoard.add(-1);
      } else throw new IllegalArgumentException("Board has an invalid character.");
    }
  }

  @Override
  public int boardSize() {
    return this.currentBoard.size();
  }

  @Override
  public int coinCount() {
    return this.coinCount;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    if(0 <= coinIndex && coinIndex < this.coinCount)
      return this.currentBoard.indexOf(coinIndex);
    else throw new IllegalArgumentException("No coin with the requested index.");
  }

  @Override
  public boolean isGameOver() {
    if (this.currentBoard.indexOf(-1) < this.coinCount && this.currentBoard.indexOf(-1) >= 0)
      return false;
    else return true;
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    if(this.isGameOver())
      throw new IllegalMoveException("Game is over.");
    else if (this.currentBoard.get(newPosition) != -1)
      throw new IllegalMoveException("There is a coin there.");
    else {
      int old = this.currentBoard.indexOf(coinIndex);
      for(int i = newPosition; i < old; i++) {
        if (this.currentBoard.get(i) != -1)
          throw new IllegalMoveException("There is a coin in the way.");
      }
      this.currentBoard.set(newPosition, coinIndex);
      this.currentBoard.set(old, -1);
      if(this.isGameOver()) {
        System.out.println("Player " + this.getWinner() + " wins!");
      }
      else {
        this.currentTurn++;
        if (this.currentTurn >= this.numPlayers())
          this.currentTurn = 0;
      }
    }
  }

  @Override
  public int getPlayerTurn() {
    return this.currentTurn;
  }

  @Override
  public int getWinner() {
    return this.players.get(this.getPlayerTurn());
  }

  @Override
  public void addPlayer(int turn) {
    if(turn <= 0)
      throw new IllegalArgumentException("Turn order cannot be <= 0.");
    else if(turn > this.numPlayers())
      this.players.add(this.numPlayers()+1);
    else
      this.players.add(turn-1, this.numPlayers()+1);
  }

  @Override
  public int getPlayerNumber(int index) {
    if(index < 0 || index >= this.numPlayers())
      throw new IllegalArgumentException("Index invalid.");
    return this.players.get(index);
  }

  @Override
  public int numPlayers() {
    return this.players.size();
  }
}
