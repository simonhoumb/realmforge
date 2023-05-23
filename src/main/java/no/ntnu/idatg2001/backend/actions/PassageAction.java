package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The PassageAction class represents an action that navigates a unit to a new location.
 */
@Entity
public class PassageAction extends Action {

  /**
   * Creates an PassageAction with the specified passage.
   *
   * @param passage the passage
   */
  public PassageAction(String passage) {
    this.value = passage;
    this.setActionType(ActionType.PASSAGE);
  }

  /**
   * Creates an PassageAction with the default passage value of 1.
   */
  public PassageAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
      // Implement the logic to handle passage navigation here
      // For example, you can update the unit's current location based on the passage value
  }
}