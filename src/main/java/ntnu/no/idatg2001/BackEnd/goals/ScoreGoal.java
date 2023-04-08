package ntnu.no.idatg2001.BackEnd.goals;

import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;

public class ScoreGoal implements Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getEntityLevel() >= minimumPoints;
  }
}
