package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RogueTest {

  private Rogue rogue;
  private Rogue rogueEmpty;

  @BeforeEach
  void setUp() {
    rogue = new Rogue("Test Rogue");
    rogueEmpty = new Rogue();
  }

  @Test
  void testConstructor() {
    assertEquals("Test Rogue", rogue.getUnitName());
    assertEquals(75, rogue.getUnitHealthMax());
    assertEquals(75, rogue.getUnitHealth());
    assertEquals(0, rogue.getGold());
    assertEquals(75, rogue.getUnitMana());
    assertEquals(75, rogue.getUnitManaMax());
    assertEquals(35, rogue.getDamage());
    assertEquals(20, rogue.getCriticalChance());
    assertEquals(10, rogue.getArmour());
    assertTrue(rogue.getUnitInventory().contains("Dagger"));
    assertTrue(rogue.getUnitInventory().contains("HealthPotion"));
    assertTrue(rogue.getUnitInventory().contains("PoisonBottle"));
    assertTrue(rogue.getUnitInventory().contains("Cloak"));
    assertEquals(PlayerClass.ROGUE, rogue.getPlayerClass());
  }

  @Test
  void testEmptyRogue(){
    assertEquals("default", rogueEmpty.getUnitName());
    assertEquals(100, rogueEmpty.getUnitHealth());
    assertEquals(0, rogueEmpty.getGold());
    assertEquals(100, rogueEmpty.getUnitMana());
    assertEquals(0, rogueEmpty.getUnitManaMax());
    assertEquals(0, rogueEmpty.getDamage());
    assertEquals(0, rogueEmpty.getCriticalChance());
    assertEquals(0, rogueEmpty.getArmour());
    assertTrue(rogueEmpty.getUnitInventory().isEmpty());
    assertFalse(rogueEmpty.getUnitInventory().contains("Staff"));
    assertFalse(rogueEmpty.getUnitInventory().contains("Mana Potion"));
    assertFalse(rogueEmpty.getUnitInventory().contains("Mage Robe"));
    assertNotEquals(PlayerClass.ROGUE, rogueEmpty.getPlayerClass());
  }
}

