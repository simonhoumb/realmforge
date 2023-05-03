package no.ntnu.idatg2001.BackEnd.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

@Entity
public class ScoreAction extends Action {
  int points;

  public ScoreAction(int points) {
    this.points = points;
  }

  public ScoreAction() {

  }

  public void execute(Unit unit) {
    unit.setUnitLevel(unit.getUnitLevel() + points);
  }
}
