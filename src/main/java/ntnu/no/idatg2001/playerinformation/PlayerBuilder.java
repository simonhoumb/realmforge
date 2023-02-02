package ntnu.no.idatg2001.playerinformation;

import java.util.List;
import ntnu.no.idatg2001.utilities.CheckIfValid;

/**
 * Represents A Item Class.

 * @author Eskil Alstad & Simon Husås Houmb
 * @version 2022-12-07
 */
public class PlayerBuilder {

  private String playerName; //Name of the Player.
  private int playerHealthPoints; //The Health points of the Player.
  private int playerScore; //The score of the player.
  private int playerGold; //The gold of the player
  private List<String> playerInventory; // The players inventory
  private final CheckIfValid checkIfValid = new CheckIfValid();

  /**
   * Sets name.
   *
   * @param playerName the player name
   * @return the name
   */
  public PlayerBuilder setName(String playerName) {
    this.playerName = playerName;
    return this;
  }

  /**
   * Sets health points and checks if player health is not lower than Zero.
   *
   * @param playerHealthPoints the player health points
   * @return the health points
   */
  public PlayerBuilder setHealthPoints(int playerHealthPoints) {
    if (checkIfValid.checkIfNumberIsNotLowerThanZero(playerHealthPoints)) {
      this.playerHealthPoints = playerHealthPoints;
      return this;
    }
    return setHealthPoints(0);
  }

  /**
   * Sets player score.
   *
   * @param playerScore the player score
   * @return the player score
   */
  public PlayerBuilder setPlayerScore(int playerScore) {
    this.playerScore = playerScore;
    return this;
  }

  /**
   * Sets player gold.
   *
   * @param playerGold the player gold
   * @return the player gold
   */
  public PlayerBuilder setPlayerGold(int playerGold) {
    this.playerGold = playerGold;
    return this;
  }

  /**
   * Sets player inventory.
   *
   * @param inventory the inventory
   * @return the player inventory
   */
  public PlayerBuilder setPlayerInventory(List<String> inventory) {
    this.playerInventory = playerInventory;
    return this;
  }

  /**
   * Gets the finished player from builder.
   *
   * @return the player
   */
  public Player getPlayer() {
    return new Player(playerName, playerHealthPoints, playerScore, playerGold, playerInventory);
  }
}
