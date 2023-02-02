package ntnu.no.idatg2001;

import java.util.List;

public class Player {
  private String name;
  private int health;
  private int score;
  private int gold;
  private List<String> inventory;

  public Player(String name, int health, int score, int gold) {
    this.name = name;
    this.health = health;
    this.score = score;
    this.gold = gold;
  }

  public String getName() {
    return name;
  }

  public int getHealth() {
    return health;
  }

  public int getScore() {
    return score;
  }

  public int getGold() {
    return gold;
  }

  public List<String> getInventory() {
    return inventory;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public void addHealth(int health) {
    this.health = this.health + health;
  }

  public void addScore(int score) {
    this.score = this.score + score;
  }

  public void addGold(int gold) {
    this.gold = this.gold + gold;
  }

  public void addToInventory(String item) {
    inventory.add(item);
  }
}
