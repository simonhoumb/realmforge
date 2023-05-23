package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

/**
 * The RogueBuilder class represents a builder for the Warrior class.
 */
public class WarriorBuilder extends UnitBuilder<Warrior, WarriorBuilder> {

  /**
   * Builds a Warrior.
   */
  @Override
  public Warrior build() {
    Warrior warrior = new Warrior(unitName);
    warrior.setUnitHealthMax(unitHealthMax);
    warrior.setUnitHealth(unitHealth);
    warrior.setGold(gold);
    warrior.setUnitMana(unitMana);
    warrior.setPlayerClass(playerClass);
    warrior.addToInventory(unitInventory);
    return warrior;
  }

  /**
   * Returns the WarriorBuilder itself.
   *
   * @return the WarriorBuilder itself
   */
  @Override
  protected WarriorBuilder self() {
    return this;
  }
}