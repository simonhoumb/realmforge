package no.ntnu.idatg2001.BackEnd.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

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
