package no.ntnu.idatg2001.backend.actions;


import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The ItemAction class represents an action that adds an item to a unit's inventory.
 */
@Entity
public class ItemAction extends Action {

  /**
   * Creates an ItemAction with the specified item.
   *
   * @param item the item
   */
  public ItemAction(String item) {
    this.value = item;
    this.setActionType(ActionType.ITEM);
  }

  /**
   * Creates an ItemAction with the default item value of 1.
   */
  public ItemAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    unit.addToInventory((String) value);
    this.setActionType(ActionType.NONE);
  }
}
