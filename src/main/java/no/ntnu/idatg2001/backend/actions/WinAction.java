package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The WinAction class represents an action that wins the game.
 */
@Entity
public class WinAction extends Action {

  /**
   * Creates a WinAction.
   */
  public WinAction() {
    this.setActionType(ActionType.WIN);
  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    // Implement the logic to handle the win action here
    // For example, you can display a victory message, award bonus points, or perform other relevant actions
  }
}
