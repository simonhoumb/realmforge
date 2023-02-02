package ntnu.no.idatg2001;

import ntnu.no.idatg2001.playerinformation.Player;
import ntnu.no.idatg2001.playerinformation.PlayerBuilder;

public class Main {

  public static void main(String[] args) {
    Player player = new PlayerBuilder()
        .setPlayerName("Jonas")
        .setPlayerGold(500)
        .setPlayerHealthPoints(150)
        .setPlayerScore(0)
        .getPlayer();
    player.addToInventory("Health Potion");
    player.addToInventory("Sword");
    player.addToInventory("Shield");

    System.out.println(player);
  }
}