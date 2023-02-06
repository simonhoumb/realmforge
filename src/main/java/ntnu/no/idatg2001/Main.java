package ntnu.no.idatg2001;

import ntnu.no.idatg2001.playerinformation.Mage;
import ntnu.no.idatg2001.playerinformation.Player;
import ntnu.no.idatg2001.playerinformation.PlayerBuilder;
import ntnu.no.idatg2001.playerinformation.PlayerClass;

public class Main {

  public static void main(String[] args) {
    Player player = new PlayerBuilder()
        .setPlayerName("Jonas")
        .setPlayerGold(500)
        .setPlayerHealthPoints(-10)
        .setPlayerScore(0)
        .setPlayerClass(PlayerClass.Mage)
        .getPlayer();
    player.addToInventory("Health Potion");
    player.addToInventory("Sword");
    player.addToInventory("Shield");

    System.out.println(player);

    System.out.println(player.getPlayerClass());
    player.setPlayerClass(PlayerClass.valueOfClassNumber(3));
    System.out.println(player.getPlayerClass());

   Mage mage = new Mage(20);
    System.out.println(mage);
  }
}