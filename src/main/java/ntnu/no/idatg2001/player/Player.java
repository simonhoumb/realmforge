package ntnu.no.idatg2001.player;

import java.util.List;

/**
 * Represents A Item Class.

 * @author Eskil Alstad
 * @version 2022-12-07
 */
public class Player {

  private String playerName; //Name of the Player.
  private int playerHealthPoints; //The Health points of the Player.
  private int playerScore; //The score of the player.
  private int playerGold; //The gold of the player
  private List<String> playerInventory; // The players inventory


  /**
   * Instantiates a new Player.
   *
   * @param playerName         the player name
   * @param playerHealthPoints the player health points
   * @param playerScore        the player score
   * @param playerGold         the player gold
   * @param playerInventory          the inventory
   */
  public Player(String playerName, int playerHealthPoints, int playerScore, int playerGold,
      List<String> playerInventory) {
    this.playerName = playerName;
    this.playerHealthPoints = playerHealthPoints;
    this.playerScore = playerScore;
    this.playerGold = playerGold;
    this.playerInventory = playerInventory;
  }

  /**
   * Gets player name.
   *
   * @return the player name
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Sets player name.
   *
   * @param playerName the player name
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Gets player health points.
   *
   * @return the player health points
   */
  public int getPlayerHealthPoints() {
    return playerHealthPoints;
  }

  /**
   * Sets player health points.
   *
   * @param playerHealthPoints the player health points
   */
  public void setPlayerHealthPoints(int playerHealthPoints) {
    this.playerHealthPoints = playerHealthPoints;
  }

  /**
   * Gets player score.
   *
   * @return the player score
   */
  public int getPlayerScore() {
    return playerScore;
  }

  /**
   * Sets player score.
   *
   * @param playerScore the player score
   */
  public void setPlayerScore(int playerScore) {
    this.playerScore = playerScore;
  }

  /**
   * Gets gold.
   *
   * @return the gold
   */
  public int getGold() {
    return playerGold;
  }

  /**
   * Sets gold.
   *
   * @param playerGold the player gold
   */
  public void setGold(int playerGold) {
    this.playerGold = playerGold;
  }

  /**
   * Gets inventory.
   *
   * @return the inventory
   */
  public List<String> getInventory() {
    return playerInventory;
  }

  /**
   * Sets inventory.
   *
   * @param playerInventory the player inventory
   */
  public void setInventory(List<String> playerInventory) {
    this.playerInventory = playerInventory;
  }
}
