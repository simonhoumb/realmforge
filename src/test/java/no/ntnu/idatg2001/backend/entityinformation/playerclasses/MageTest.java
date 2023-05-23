package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.WinAction;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MageTest {
  Mage mage;
  Mage mageEmpty;
  @BeforeEach
  public void setUp() {
    mage = new Mage("Test Mage");
    mageEmpty = new Mage();
  }

  @Test
  void testConstructor() {
    assertEquals("Test Mage", mage.getUnitName());
    assertEquals(100, mage.getUnitHealthMax());
    assertEquals(100, mage.getUnitHealth());
    assertEquals(0, mage.getGold());
    assertEquals(100, mage.getUnitMana());
    assertEquals(100, mage.getUnitManaMax());
    assertEquals(30, mage.getDamage());
    assertEquals(30, mage.getCriticalChance());
    assertEquals(10, mage.getArmour());
    assertTrue(mage.getUnitInventory().contains("Staff"));
    assertTrue(mage.getUnitInventory().contains("Mana Potion"));
    assertTrue(mage.getUnitInventory().contains("Mage Robe"));
    assertEquals(PlayerClass.MAGE, mage.getPlayerClass());
  }

  @Test
  void testEmptyMage(){
    assertEquals("default", mageEmpty.getUnitName());
    assertEquals(100, mageEmpty.getUnitHealth());
    assertEquals(0, mageEmpty.getGold());
    assertEquals(100, mageEmpty.getUnitMana());
    assertEquals(0, mageEmpty.getUnitManaMax());
    assertEquals(0, mageEmpty.getDamage());
    assertEquals(0, mageEmpty.getCriticalChance());
    assertEquals(0, mageEmpty.getArmour());
    assertFalse(mageEmpty.getUnitInventory().contains("Staff"));
    assertFalse(mageEmpty.getUnitInventory().contains("Mana Potion"));
    assertFalse(mageEmpty.getUnitInventory().contains("Mage Robe"));
    assertNotEquals(PlayerClass.MAGE, mageEmpty.getPlayerClass());
  }
}