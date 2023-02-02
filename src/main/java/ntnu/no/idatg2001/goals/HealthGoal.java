package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.Player;

public class HealthGoal {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public boolean isFulfilled(Player player) {
    return player.getHealth() >= minimumHealth;
  }
}
