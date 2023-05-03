package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class GoldGoal extends Goal  {
  private int minimumGold;

  public GoldGoal(int goldMinimum) {
    this.minimumGold = goldMinimum;
  }

  public boolean isFulfilled(Unit unit) {
    return unit.getGold() >= minimumGold;
  }
}
