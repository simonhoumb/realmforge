package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.playerinformation.Player;

public class HealthAction implements Action {
  int health;

  public HealthAction(int health) {
    this.health = health;
  }

  public void execute(Player player) {
    player.setPlayerHealthPoints(player.getPlayerHealthPoints() + health);
  }
}
