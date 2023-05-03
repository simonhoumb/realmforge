package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class ScoreAction extends Action {
  int points;

  public ScoreAction(int points) {
    this.points = points;
  }

  public void execute(Unit unit) {
    unit.setUnitLevel(unit.getUnitLevel() + points);
  }
}
