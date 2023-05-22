package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemActionTest {

  private ItemAction itemAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    itemAction = new ItemAction("Sword");
    unit = new Ranger("test");
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.ITEM, itemAction.getActionType());
    assertEquals("Sword", itemAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    ItemAction action = new ItemAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    itemAction.execute(unit);
    assertTrue(unit.getUnitInventory().contains("Sword"));
    assertEquals(ActionType.NONE, itemAction.getActionType());
  }
}
