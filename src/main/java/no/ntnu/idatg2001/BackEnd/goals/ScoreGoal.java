package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class ScoreGoal extends Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitLevel() >= minimumPoints;
  }
}
