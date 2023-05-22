package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ScoreAction extends Action {

  public ScoreAction(int points) {
    this.value = points;
    this.setActionType(ActionType.SCORE);
  }

  public ScoreAction() {

  }

  public void execute(Unit unit) {
    unit.setUnitScore(unit.getUnitScore() + Integer.parseInt(value.toString()));
  }
}
