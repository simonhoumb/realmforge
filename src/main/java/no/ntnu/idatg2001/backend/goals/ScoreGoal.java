package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ScoreGoal extends Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
  }

  public ScoreGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitLevel() >= minimumPoints;
  }
}
