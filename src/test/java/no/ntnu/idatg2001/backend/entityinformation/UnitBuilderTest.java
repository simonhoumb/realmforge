package no.ntnu.idatg2001.backend.entityinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.CustomUnit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.CustomUnitBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnitBuilderTest {

  private static CustomUnit unit;
  private int unitHealthMax = 100;
  private int unitHealth = 80;
  private String unitName = "TestUnit";
  private int gold = 500;
  private int unitMana = 50;
  private PlayerClass playerClass = PlayerClass.WARRIOR;
  private int unitScore = 0;
  private List<String> unitInventory = Arrays.asList();
  private List<String> testInventory = Arrays.asList("Item1", "Item2", "Item3");

  @BeforeEach
  void setUpEach() {
    unit = new CustomUnitBuilder().withUnitHealthMax(unitHealthMax)
        .withUnitHealth(unitHealth)
        .withUnitName(unitName)
        .withGold(gold)
        .withUnitMana(unitMana)
        .withPlayerClass(playerClass)
        .withUnitScore(unitScore)
        .withUnitInventory(unitInventory)
        .build();
  }

  @Test
  void testWithUnitHealthMax() {
    int newUnitHealthMax = 150;
    unit.setUnitHealthMax(newUnitHealthMax);
    Assertions.assertEquals(newUnitHealthMax, unit.getUnitHealthMax());
  }

  @Test
  void testWithUnitHealth() {
    int newUnitHealth = 60;
    unit.setUnitHealth(newUnitHealth);
    Assertions.assertEquals(newUnitHealth, unit.getUnitHealth());
  }

  @Test
  void testWithUnitName() {
    String newUnitName = "NewTestUnit";
    unit.setUnitName(newUnitName);
    Assertions.assertEquals(newUnitName, unit.getUnitName());
  }

  @Test
  void testWithGold() {
    int newGold = 1000;
    unit.setGold(newGold);
    Assertions.assertEquals(newGold, unit.getGold());
  }

  @Test
  void testWithUnitMana() {
    int newUnitMana = 100;
    unit.setUnitMana(newUnitMana); // Mana cant be over Max Mana
    Assertions.assertNotSame(newUnitMana, unit.getUnitMana());
  }

  @Test
  void testWithPlayerClass() {
    PlayerClass newPlayerClass = PlayerClass.MAGE;
    unit.setPlayerClass(newPlayerClass);
    Assertions.assertEquals(newPlayerClass, unit.getPlayerClass());
  }

  @Test
  void testWithUnitInventory() {
    unit.addToInventory(testInventory);
    Assertions.assertEquals(testInventory, unit.getUnitInventory());
  }

  @Test
  void testWithUnitScore() {
    int newUnitScore = 100;
    unit.setUnitScore(newUnitScore);
    Assertions.assertEquals(newUnitScore, unit.getUnitScore());
  }

  @Test
  void testAddToUnitInventory() {
    String newItem = "New Item";
    unit.addToInventory(newItem);
    List<String> expectedInventory = new ArrayList<>(unitInventory);
    expectedInventory.add(newItem);
    Assertions.assertEquals(expectedInventory, unit.getUnitInventory());
  }

  @Test
  void testBuild() {
    // Verify the unit properties after build
    Assertions.assertEquals(unitHealthMax, unit.getUnitHealthMax());
    Assertions.assertEquals(unitHealth, unit.getUnitHealth());
    Assertions.assertEquals(unitName, unit.getUnitName());
    Assertions.assertEquals(gold, unit.getGold());
    Assertions.assertEquals(unitMana, unit.getUnitMana());
    Assertions.assertEquals(playerClass, unit.getPlayerClass());
    Assertions.assertEquals(unitScore, unit.getUnitScore());
    Assertions.assertEquals(unitInventory, unit.getUnitInventory());
  }

  @Test
  void getId() {
  }

  @Test
  void setId() {
  }

  @Test
  void withUnitHealthMax() {
  }

  @Test
  void withUnitHealth() {
  }

  @Test
  void withUnitName() {
  }

  @Test
  void withGold() {
  }

  @Test
  void withUnitMana() {
  }

  @Test
  void withPlayerClass() {
  }

  @Test
  void withUnitInventory() {
  }

  @Test
  void withUnitScore() {
  }

  @Test
  void addToUnitInventory() {
  }

  @Test
  void withDamage() {
  }

  @Test
  void withArmour() {
  }

  @Test
  void withScore() {
  }

  @Test
  void withCriticalStrikeChance() {
  }

  @Test
  void getUnitHealthMax() {
  }

  @Test
  void getUnitMana() {
  }

  @Test
  void getGold() {
  }

  @Test
  void getDamage() {
  }

  @Test
  void getCriticalChance() {
  }

  @Test
  void getArmour() {
  }

  @Test
  void build() {
  }

  @Test
  void self() {
  }

  private static class TestUnit extends Unit {

      private String entityName;


    public TestUnit(String entityName) {
      this.entityName = entityName;


    }

    private static class TestUnitBuilder extends UnitBuilder<TestUnit, TestUnitBuilder> {

      @Override
      public TestUnit build() {
        return new TestUnit("this");
      }

      @Override
      protected TestUnitBuilder self() {
        return this;
      }
    }
  }
  @Test
  void testUnitBuilder() {
    TestUnit testUnit = new TestUnit.TestUnitBuilder()
        .withUnitName("TestUnit")
        .withUnitHealthMax(unitHealthMax)
        .withUnitHealth(unitHealth)
        .withUnitName(unitName)
        .withGold(500)
        .withUnitMana(unitMana)
        .withPlayerClass(playerClass)
        .withUnitScore(unitScore)
        .withUnitInventory(unitInventory)
        .build();

    testUnit.setUnitHealth(80);
    testUnit.setUnitName("TestUnit");
    testUnit.setGold(500);
    testUnit.setUnitManaMax(50);
    testUnit.setPlayerClass(PlayerClass.WARRIOR);

    // Verify the unit properties
    Assertions.assertEquals(unitHealthMax, testUnit.getUnitHealthMax());
    Assertions.assertEquals(unitHealth, testUnit.getUnitHealth());
    Assertions.assertEquals(unitName, testUnit.getUnitName());
    Assertions.assertEquals(gold, testUnit.getGold());
    Assertions.assertEquals(unitMana, testUnit.getUnitMana());
    Assertions.assertEquals(playerClass, testUnit.getPlayerClass());
    Assertions.assertEquals(unitScore, testUnit.getUnitScore());
    Assertions.assertEquals(unitInventory, testUnit.getUnitInventory());


  }
}
