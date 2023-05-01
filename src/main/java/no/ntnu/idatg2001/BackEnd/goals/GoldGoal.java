package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public class GoldGoal implements Goal  {
  private int minimumGold;

  public GoldGoal(int goldMinimum) {
    this.minimumGold = goldMinimum;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getGold() >= minimumGold;
  }
}
