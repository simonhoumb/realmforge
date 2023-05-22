package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArmorActionTest {

  private ArmorAction armorAction;
  private Unit unit;

  @BeforeEach
  void setUp() {
    armorAction = new ArmorAction(5);
    unit = new Ranger("test");
    unit.setArmour(10);
  }

  @AfterEach
  void tearDown() {
    unit = null;
  }

  @Test
  void testConstructor() {
    assertEquals(ActionType.ARMOR, armorAction.getActionType());
    assertEquals(5, armorAction.getValue());
  }

  @Test
  void testExecuteWithPositiveNumber() {
    armorAction.execute(unit);
    assertEquals(15, unit.getArmour());
  }

  @Test
  void testExecuteWithNegativeNumber() {
    armorAction.setValue(-5);
    armorAction.execute(unit);
    assertEquals(5, unit.getArmour());
  }
}
