package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

public class RogueBuilder extends UnitBuilder<Rogue, RogueBuilder> {

  private int specificField;

  public RogueBuilder withSpecificField(int specificField) {
    this.specificField = specificField;
    return this;
  }

  @Override
  public Rogue build() {
    Rogue rogue = new Rogue(unitName);
    rogue.setUnitHealthMax(unitHealthMax);
    rogue.setUnitHealth(unitHealth);
    rogue.setGold(gold);
    rogue.setUnitMana(unitMana);
    rogue.setPlayerClass(playerClass);
    rogue.addToInventory(unitInventory);
    rogue.setDamage(specificField);
    return rogue;
  }

  @Override
  protected RogueBuilder self() {
    return this;
  }
}