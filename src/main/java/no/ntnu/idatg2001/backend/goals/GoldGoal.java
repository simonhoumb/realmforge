package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class GoldGoal extends Goal  {
  private int minimumGold;

  public GoldGoal(int goldMinimum) {
    this.minimumGold = goldMinimum;
  }

  public GoldGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getGold() >= minimumGold;
  }
}
