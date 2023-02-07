package ntnu.no.idatg2001.entityinformation;

import java.util.ArrayList;
import java.util.List;

public class Npc extends Entity{

  private int xpGain;
  private String npcName;
  private List<String> allies;
  private List<String> enemies;

  public Npc() {
    allies = new ArrayList<>();
    enemies = new ArrayList<>();
  }

  public Npc(String entityName) {
    allies = new ArrayList<>();
    enemies = new ArrayList<>();
    this.npcName = entityName;
  }

  public List<String> getAllies() {
    return allies;
  }

  public List<String> getEnemies() {
    return enemies;
  }

  public void setAllies(List<String> allies) {
    this.allies = allies;
  }

  public void setEnemies(List<String> enemies) {
    this.enemies = enemies;
  }

  public int getXpGain() {
    return xpGain;
  }

  public void setXpGain(int xpGain) {
    this.xpGain = xpGain;
  }
  public String getNpcName() {
    return npcName;
  }
}
