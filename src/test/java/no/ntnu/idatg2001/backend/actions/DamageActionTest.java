package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DamageActionTest {

  private DamageAction damageAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    damageAction = new DamageAction(10);
    unit = new Ranger("test");
    unit.setUnitHealth(100);
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.DAMAGE, damageAction.getActionType());
    assertEquals(10, damageAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    DamageAction action = new DamageAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    damageAction.execute(unit);
    assertEquals(90, unit.getUnitHealth());
  }

  @Test
  void testExecuteWithNegativeNumber() {
    damageAction.setValue(-5);
    damageAction.execute(unit);
    assertEquals(105, unit.getUnitHealth());
  }
}
