package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The ScoreAction class represents an action that increases the score of a unit.
 */
@Entity
public class ScoreAction extends Action {

  /**
   * Creates an ScoreAction with the specified score value.
   *
   * @param points the score value
   */
  public ScoreAction(int points) {
    this.value = points;
    this.setActionType(ActionType.SCORE);
  }

  /**
   * Creates an ScoreAction with the default score value of 1.
   */
  public ScoreAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    unit.setUnitScore(unit.getUnitScore() + Integer.parseInt(value.toString()));
  }
}
