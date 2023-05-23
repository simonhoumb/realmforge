package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The GoldAction class represents an action that increases the gold of a unit.
 */
@Entity
public class GoldAction extends Action {

  /**
   * Creates an GoldAction with the specified gold value.
   *
   * @param gold the gold value
   */
  public GoldAction(int gold) {
    this.value = gold;
    this.setActionType(ActionType.GOLD);
  }

  /**
   * Creates an GoldAction with the default gold value of 1.
   */
  public GoldAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    unit.setGold(unit.getGold() + Integer.parseInt(value.toString()));
  }
}
