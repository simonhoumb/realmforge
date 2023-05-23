package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MageBuilderTest {

  private static Mage unit;
  private int unitHealthMax = 200;
  private int unitHealth = 20;
  private String unitName = "TestUnit";
  private int gold = 1000;
  private int unitMana = 50;
  private PlayerClass playerClass = PlayerClass.MAGE;
  private int unitScore = 100;
  private List<String> unitInventory = Arrays.asList();
  private List<String> testInventory = Arrays.asList("Item1", "Item2");

  @BeforeEach
  void setUpEach() {
    unit = new MageBuilder().withUnitHealthMax(unitHealthMax)
        .withUnitHealth(unitHealth)
        .withUnitName(unitName)
        .withGold(gold)
        .withUnitMana(unitMana)
        .withPlayerClass(playerClass)
        .withUnitScore(unitScore) //This will always be 0. so you need to
                                  // set a value on unitScore after.
        .withUnitInventory(unitInventory)
        .build();

    unit.clearInventory();
    unit.addToInventory("Item1");
    unit.addToInventory("Item2");
    unit.setUnitScore(100);
  }

  @Test
  void testUnitBuilder() {
    // Verify the unit properties
    Assertions.assertEquals(unitHealthMax, unit.getUnitHealthMax());
    Assertions.assertEquals(unitHealth, unit.getUnitHealth());
    Assertions.assertEquals(unitName, unit.getUnitName());
    Assertions.assertEquals(gold, unit.getGold());
    Assertions.assertEquals(unitMana, unit.getUnitMana());
    Assertions.assertEquals(playerClass, unit.getPlayerClass());
    Assertions.assertEquals(unitScore, unit.getUnitScore());
    Assertions.assertEquals(testInventory, unit.getUnitInventory());
  }

}