package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarriorTest {

  private Warrior warrior;
  private Warrior warriorEmpty;

  @BeforeEach
  void setUp() {
    warrior = new Warrior("Test Warrior");
    warriorEmpty = new Warrior();
  }

  @Test
  void testConstructor() {
    assertEquals("Test Warrior", warrior.getUnitName());
    assertEquals(200, warrior.getUnitHealthMax());
    assertEquals(200, warrior.getUnitHealth());
    assertEquals(0, warrior.getGold());
    assertEquals(50, warrior.getUnitMana());
    assertEquals(50, warrior.getUnitManaMax());
    assertEquals(20, warrior.getDamage());
    assertEquals(5, warrior.getCriticalChance());
    assertEquals(25, warrior.getArmour());
    assertTrue(warrior.getUnitInventory().contains("GreatSword"));
    assertTrue(warrior.getUnitInventory().contains("HealthPotion"));
    assertTrue(warrior.getUnitInventory().contains("Shield"));
    assertTrue(warrior.getUnitInventory().contains("BodyArmor"));
    assertEquals(PlayerClass.WARRIOR, warrior.getPlayerClass());
  }

  @Test
  void testEmptyWarrior(){
    assertEquals("default", warriorEmpty.getUnitName());
    assertEquals(100, warriorEmpty.getUnitHealth());
    assertEquals(0, warriorEmpty.getGold());
    assertEquals(100, warriorEmpty.getUnitMana());
    assertEquals(0, warriorEmpty.getUnitManaMax());
    assertEquals(0, warriorEmpty.getDamage());
    assertEquals(0, warriorEmpty.getCriticalChance());
    assertEquals(0, warriorEmpty.getArmour());
    assertFalse(warriorEmpty.getUnitInventory().contains("GreatSword"));
    assertFalse(warriorEmpty.getUnitInventory().contains("HealthPotion"));
    assertFalse(warriorEmpty.getUnitInventory().contains("Shield"));
    assertFalse(warriorEmpty.getUnitInventory().contains("BodyArmor"));
    assertNotEquals(PlayerClass.WARRIOR, warriorEmpty.getPlayerClass());
  }
}
