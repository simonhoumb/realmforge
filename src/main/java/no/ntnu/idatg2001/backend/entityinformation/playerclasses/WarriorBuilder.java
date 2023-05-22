package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

public class WarriorBuilder extends UnitBuilder<Warrior, WarriorBuilder> {

  private int specificField;

  public WarriorBuilder withSpecificField(int specificField) {
    this.specificField = specificField;
    return this;
  }

  @Override
  public Warrior build() {
    Warrior warrior = new Warrior(unitName);
    warrior.setUnitHealthMax(unitHealthMax);
    warrior.setUnitHealth(unitHealth);
    warrior.setGold(gold);
    warrior.setUnitMana(unitMana);
    warrior.setPlayerClass(playerClass);
    warrior.addToInventory(unitInventory);
    warrior.setArmour(specificField);
    return warrior;
  }

  @Override
  protected WarriorBuilder self() {
    return this;
  }
}