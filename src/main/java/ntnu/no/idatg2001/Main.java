package ntnu.no.idatg2001;

import ntnu.no.idatg2001.player.Player;
import ntnu.no.idatg2001.player.PlayerBuilder;

public class Main {

  public static void main(String[] args) {
    Player player = new PlayerBuilder().setName("Jonas").getPlayer();
  }
}