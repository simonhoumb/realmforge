package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.Player;

public class GoldGoal {
  private int minimumGold;

  public GoldGoal(int goldMinimum) {
    this.minimumGold = goldMinimum;
  }

  public boolean isFulfilled(Player player) {
    return player.getGold() >= minimumGold;
  }
}
