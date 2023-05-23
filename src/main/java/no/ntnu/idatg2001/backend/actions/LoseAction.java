package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The LoseAction class represents an action that ends the game.
 */
@Entity
public class LoseAction extends Action {

  /**
   * Creates a LoseAction.
   */
  public LoseAction() {
    this.setActionType(ActionType.LOSE);
  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    // Implement the logic to handle the lose action here
    // For example, you can display a game over message, deduct points, or perform other relevant actions
  }
}