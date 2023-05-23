package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The NoneAction class represents an action that does nothing.
 */
@Entity
public class NoneAction extends Action {

  /**
   * Creates a NoneAction.
   */
  public NoneAction() {
    this.setActionType(ActionType.NONE);
  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    // No action is performed in the execute method for the NoneAction
    // You can leave it empty or add any specific behavior as needed
  }
}