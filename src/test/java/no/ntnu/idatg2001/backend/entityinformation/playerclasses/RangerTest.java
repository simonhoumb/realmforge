package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangerTest {

  private Ranger ranger;

  @BeforeEach
  void setUp() {
    ranger = new Ranger("Test Ranger");
  }

  @Test
  void testConstructor() {
    assertEquals("Test Ranger", ranger.getUnitName());
    assertEquals(150, ranger.getUnitHealthMax());
    assertEquals(150, ranger.getUnitHealth());
    assertEquals(0, ranger.getGold());
    assertEquals(50, ranger.getUnitMana());
    assertEquals(50, ranger.getUnitManaMax());
    assertEquals(25, ranger.getDamage());
    assertEquals(20, ranger.getCriticalChance());
    assertEquals(15, ranger.getArmour());
    assertTrue(ranger.getUnitInventory().contains("Bow"));
    assertTrue(ranger.getUnitInventory().contains("HealthPotion"));
    assertTrue(ranger.getUnitInventory().contains("Trap"));
    assertTrue(ranger.getUnitInventory().contains("Boots"));
    assertEquals(PlayerClass.RANGER, ranger.getPlayerClass());
  }
}
