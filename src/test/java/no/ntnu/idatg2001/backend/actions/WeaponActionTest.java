package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WeaponActionTest {

  private WeaponAction weaponAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    weaponAction = new WeaponAction("Sword");
    unit = new Ranger("test");
    unit.setWeapon("Hands");
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.WEAPON, weaponAction.getActionType());
    assertEquals("Sword", weaponAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    DamageAction action = new DamageAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    weaponAction.execute(unit);
    assertEquals("Sword", unit.getWeapon());
  }
}
