package cs3500.hw04;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Tester class for StrictCoinGameModel
 */
public class StrictCoinGameModelTest extends TestCase {

  StrictCoinGameModel g1;
  StrictCoinGameModel g2;
  StrictCoinGameModel g3;
  StrictCoinGameModel g4;

  @org.junit.Before
  public void setUp() throws Exception {
    g1 = new StrictCoinGameModel("--O-O--OO", 3);
    g2 = new StrictCoinGameModel("-O-O--O", 4);
    g3 = new StrictCoinGameModel("--O-OO");
    g4 = new StrictCoinGameModel("--OO", 2);

  }

  @org.junit.After
  public void tearDown() throws Exception {
    g1 = null;
    g2 = null;
    g3 = null;
    g4 = null;
  }

  @Test
  public void testGetPlayerTurn() throws Exception {
    g1.move(2, 5);
    g1.move(0, 0);
    assertEquals(g1.getPlayerTurn(), 2);
    g1.move(1, 1);
    assertEquals(g1.getPlayerTurn(), 0);
    g3.move(1, 3);
    assertEquals(g3.getPlayerTurn(), 0);
    g3.move(0, 1);
    assertEquals(g3.getPlayerTurn(), 0);
  }

  @Test
  public void testGetWinner() throws Exception {
    g4.move(0, 0);
    g4.move(1, 1);
    assertTrue(g4.isGameOver());
    assertEquals(g4.getWinner(), 2);
    g2.move(0, 0);
    g2.move(1, 1);
    g2.move(2, 2);
    assertEquals(g2.getWinner(), 3);
  }

  @Test (expected = IllegalStateException.class)
  public void testWinnerFail() throws Exception {
    g1.move(0, 1);
    g1.getWinner();
  }

  @Test
  public void testAddPlayer() throws Exception {
    g4.addPlayer(1);
    g4.addPlayer(2);
    assertEquals(g4.getPlayerNumber(0), 3);
    assertEquals(g4.getPlayerNumber(1), 4);
    g2.addPlayer(3);
    assertEquals(g2.numPlayers(), 5);
    g3.addPlayer(1);
    g3.addPlayer(1);
    g3.move(0, 0);
    g3.addPlayer(4);
    assertEquals(g3.getPlayerNumber(3), 4);
    assertEquals(g3.getPlayerNumber(0), 3);
    assertEquals(g3.getPlayerNumber(1), 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddFail() throws Exception {
    g2.addPlayer(-5);
  }

  @Test
  public void testNumPlayers() throws Exception {
    assertEquals(g1.numPlayers(), 3);
    assertEquals(g3.numPlayers(), 1);
    g4.addPlayer(6);
    g4.addPlayer(6);
    assertEquals(g4.numPlayers(), 4);
    g4.addPlayer(6);
    g4.addPlayer(6);
    assertEquals(g4.numPlayers(), 6);
  }

  @Test
  public void testGetPlayerNumber() throws Exception {
    assertEquals(g2.getPlayerNumber(2), 3);
    assertEquals(g4.getPlayerNumber(0), 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetFail() throws Exception{
    g3.getPlayerNumber(-5);
  }

}