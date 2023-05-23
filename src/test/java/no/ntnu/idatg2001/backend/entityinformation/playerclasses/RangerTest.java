package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangerTest {

  private Ranger ranger;
  private Ranger rangerEmpty;

  @BeforeEach
  void setUp() {
    ranger = new Ranger("Test Ranger");
    rangerEmpty = new Ranger();

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

  @Test
  void testEmptyMage(){
    assertEquals("default", rangerEmpty.getUnitName());
    assertEquals(100, rangerEmpty.getUnitHealth());
    assertEquals(0, rangerEmpty.getGold());
    assertEquals(100, rangerEmpty.getUnitMana());
    assertEquals(0, rangerEmpty.getUnitManaMax());
    assertEquals(0, rangerEmpty.getDamage());
    assertEquals(0, rangerEmpty.getCriticalChance());
    assertEquals(0, rangerEmpty.getArmour());
    assertFalse(rangerEmpty.getUnitInventory().contains("Staff"));
    assertFalse(rangerEmpty.getUnitInventory().contains("Mana Potion"));
    assertFalse(rangerEmpty.getUnitInventory().contains("Mage Robe"));
    assertNotEquals(PlayerClass.RANGER, rangerEmpty.getPlayerClass());
  }
}
