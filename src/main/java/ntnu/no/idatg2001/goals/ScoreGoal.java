package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.Player;

public class ScoreGoal implements Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
  }

  public boolean isFulfilled(Player player) {
    return player.getScore() >= minimumPoints;
  }
}
