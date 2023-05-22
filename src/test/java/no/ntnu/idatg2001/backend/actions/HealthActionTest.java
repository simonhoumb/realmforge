package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HealthActionTest {

  private HealthAction healthAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    //Keep in mind the maxHealth of a unit as the execute method will not exceed this value.
    healthAction = new HealthAction(50);
    unit = new Ranger("test");
    unit.setUnitHealth(25);
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.HEALTH, healthAction.getActionType());
    assertEquals(50, healthAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    HealthAction action = new HealthAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    healthAction.execute(unit);
    assertEquals(75, unit.getUnitHealth());
  }

  @Test
  void testExecuteWithNegativeNumber() {
    healthAction.setValue(-5);
    healthAction.execute(unit);
    assertEquals(20, unit.getUnitHealth());
  }
}
