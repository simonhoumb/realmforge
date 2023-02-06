package ntnu.no.idatg2001.playerinformation;

import java.util.ArrayList;
import java.util.List;
import ntnu.no.idatg2001.utility.CheckIfValid;

/**
 * Represents A Item Class.

 * @author Eskil Alstad & Simon Husås Houmb
 * @version 2022-12-07
 */
public class Player {

  private String playerName; //Name of the Player.
  private PlayerClass playerClass;
  private int playerHealthPoints; //The Health points of the Player.
  private int playerScore; //The score of the player.
  private int playerGold; //The gold of the player
  private List<String> playerInventory; // The players inventory

  private CheckIfValid checkIfValid;

  /**
   * Instantiates a new Player.
   *
   * @param playerName         the player name
   * @param playerHealthPoints the player health points
   * @param playerScore        the player score
   * @param playerGold         the player gold
   * @param playerClass        the player Class
   */
  public Player(String playerName, int playerHealthPoints, int playerScore, int playerGold,
      PlayerClass playerClass) {
    this.playerName = playerName;
    this.playerHealthPoints = playerHealthPoints;
    this.playerScore = playerScore;
    this.playerGold = playerGold;
    this.playerClass = playerClass;
    this.playerInventory = new ArrayList<>();
    checkIfValid = new CheckIfValid();
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
    if (checkIfValid.checkIfNumberIsNotLowerThanZero(playerHealthPoints)) {
      this.playerHealthPoints = playerHealthPoints;
    } else {
      this.playerHealthPoints = 0;
    }
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
   * Gets player gold.
   *
   * @return the gold
   */
  public int getPlayerGold() {
    return playerGold;
  }

  /**
   * Sets player gold.
   *
   * @param playerGold the player gold
   */
  public void setPlayerGold(int playerGold) {
    this.playerGold = playerGold;
  }

  /**
   * Gets the player inventory.
   *
   * @return the inventory
   */
  public List<String> getPlayerInventory() {
    return playerInventory;
  }

  /**
   * Sets the player inventory.
   *
   * @param playerInventory the player inventory
   */
  public void setPlayerInventory(List<String> playerInventory) {
    this.playerInventory = playerInventory;
  }

  /**
   * Gets player class.
   *
   * @return the player class
   */
  public PlayerClass getPlayerClass() {
    return playerClass;
  }

  /**
   * Sets player class.
   *
   * @param playerClass the player class
   * @return the player class
   */
  public void setPlayerClass(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }

  /**
   * Adds an item to the player inventory.
   *
   * @param item the item to be added to the inventory
   */
  public void addToInventory(String item) {
    playerInventory.add(item);
  }

  /**
   * Adds multiple items to the player inventory.
   *
   * @param itemsToAdd the items to be added to the inventory
   */
  public void addToInventory(List<String> itemsToAdd) {
    playerInventory.addAll(itemsToAdd);
  }

  /**
   * Adds an item to the player inventory.
   *
   * @param item the item to be added to the inventory
   */
  public boolean removeFromInventory(String item) {
    boolean itemRemoved = true;
    try {
      playerInventory.remove(item);
    } catch (Exception exception) {
      itemRemoved = false;
    }
    return itemRemoved;
  }

  @Override
  public String toString() {
    return "Player{"
        + "playerName= '" + playerName + '\''
        + ", playerHealthPoints= " + playerHealthPoints
        + ", playerScore= " + playerScore
        + ", playerGold= " + playerGold
        + ", playerClass= " + playerClass
        + ", playerInventory= " + playerInventory
        + '}';
  }
}


