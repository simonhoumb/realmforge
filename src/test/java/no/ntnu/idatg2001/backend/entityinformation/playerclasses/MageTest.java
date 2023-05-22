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
  @BeforeEach
  public void setUp() {
    mage = new Mage("Test Mage");
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

}