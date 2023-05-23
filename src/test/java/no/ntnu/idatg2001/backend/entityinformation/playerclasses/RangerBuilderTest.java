package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangerBuilderTest {

  private static Ranger unit;
  private int unitHealthMax = 420;
  private int unitHealth = 500;
  private String unitName = "TestUnit";
  private int gold = 1337;
  private int unitMana = 42;
  private PlayerClass playerClass = PlayerClass.RANGER;
  private int unitScore = 0;
  private List<String> unitInventory = Arrays.asList();
  private List<String> testInventory = Arrays.asList("Stick", "Hat");

  @BeforeEach
  void setUpEach() {
    unit = new RangerBuilder().withUnitHealthMax(unitHealthMax)
        .withUnitHealth(unitHealth)
        .withUnitName(unitName)
        .withGold(gold)
        .withUnitMana(unitMana)
        .withPlayerClass(playerClass)
        .withUnitScore(unitScore)
        .withUnitInventory(unitInventory)
        .build();
    unit.clearInventory();
    unit.setUnitHealth(10);
    unit.addToInventory("Stick");
    unit.addToInventory("Hat");
  }

  @Test
  void testUnitBuilder() {
    // Verify the unit properties
    Assertions.assertEquals(unitHealthMax, unit.getUnitHealthMax());
    Assertions.assertNotSame(unitHealth, unit.getUnitHealth());
    Assertions.assertEquals(unitName, unit.getUnitName());
    Assertions.assertEquals(gold, unit.getGold());
    Assertions.assertEquals(unitMana, unit.getUnitMana());
    Assertions.assertEquals(playerClass, unit.getPlayerClass());
    Assertions.assertEquals(unitScore, unit.getUnitScore());
    Assertions.assertEquals(testInventory, unit.getUnitInventory());
  }
}