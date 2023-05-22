package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GoldActionTest {

  private GoldAction goldAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    goldAction = new GoldAction(100);
    unit = new Ranger("test");
    unit.setGold(50);
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.GOLD, goldAction.getActionType());
    assertEquals(100, goldAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    GoldAction action = new GoldAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    goldAction.execute(unit);
    assertEquals(150, unit.getGold());
  }

  @Test
  void testExecuteWithNegativeNumber() {
    goldAction.setValue(-5);
    goldAction.execute(unit);
    assertEquals(45, unit.getGold());
  }
}
