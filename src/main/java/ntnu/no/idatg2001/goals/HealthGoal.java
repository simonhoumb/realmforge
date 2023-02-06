package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.playerinformation.Player;

public class HealthGoal implements Goal  {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public boolean isFulfilled(Player player) {
    return player.getPlayerHealthPoints() >= minimumHealth;
  }
}
