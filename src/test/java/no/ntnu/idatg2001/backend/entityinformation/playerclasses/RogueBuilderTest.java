package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RogueBuilderTest {
  private static Rogue unit;
  private int unitHealthMax = 100;
  private int unitHealth = 80;
  private String unitName = "TestUnit";
  private int gold = 500;
  private int unitMana = 50;
  private PlayerClass playerClass = PlayerClass.WARRIOR;
  private int unitScore = 0;
  private List<String> unitInventory = Arrays.asList();
  private List<String> testInventory = Arrays.asList("Purse");

  @BeforeEach
  void setUpEach() {
    unit = new RogueBuilder().withUnitHealthMax(unitHealthMax)
        .withUnitHealth(unitHealth)
        .withUnitName(unitName)
        .withGold(gold)
        .withUnitMana(unitMana)
        .withPlayerClass(playerClass)
        .withUnitScore(unitScore)
        .withUnitInventory(unitInventory)
        .build();
    unit.clearInventory();
    unit.addToInventory("Purse");
    unit.setPlayerClass(PlayerClass.ROGUE);
  }

  @Test
  void testUnitBuilder() {
    // Verify the unit properties
    Assertions.assertEquals(unitHealthMax, unit.getUnitHealthMax());
    Assertions.assertEquals(unitHealth, unit.getUnitHealth());
    Assertions.assertEquals(unitName, unit.getUnitName());
    Assertions.assertEquals(gold, unit.getGold());
    Assertions.assertEquals(unitMana, unit.getUnitMana());
    Assertions.assertNotSame(playerClass, unit.getPlayerClass());
    Assertions.assertEquals(unitScore, unit.getUnitScore());
    Assertions.assertEquals(testInventory, unit.getUnitInventory());
  }
}