package ntnu.no.idatg2001;

import ntnu.no.idatg2001.playerinformation.Player;
import ntnu.no.idatg2001.playerinformation.PlayerBuilder;

public class Main {

  public static void main(String[] args) {
    Player player = new PlayerBuilder()
        .setPlayerName("Jonas")
        .setPlayerGold(500)
        .setPlayerHealthPoints(-10)
        .setPlayerScore(0)
        .setPlayerClass("Mage")
        .getPlayer();
    player.addToInventory("Health Potion");
    player.addToInventory("Sword");
    player.addToInventory("Shield");

    System.out.println(player);
  }
}