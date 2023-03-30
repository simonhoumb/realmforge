package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.entityinformation.Entity;

public class GoldGoal implements Goal  {
  private int minimumGold;

  public GoldGoal(int goldMinimum) {
    this.minimumGold = goldMinimum;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getGold() >= minimumGold;
  }
}
