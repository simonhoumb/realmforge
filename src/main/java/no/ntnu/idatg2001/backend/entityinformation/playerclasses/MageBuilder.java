package no.ntnu.idatg2001.backend.entityinformation.playerclasses;// Import statements...

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Mage;

public class MageBuilder extends UnitBuilder<Mage, MageBuilder> {

  private int specificField;

  public MageBuilder withSpecificField(int specificField) {
    this.specificField = specificField;
    return self();
  }

  protected Mage createUnit() {
    Mage mage = new Mage(unitName);
    mage.setUnitHealthMax(unitHealthMax);
    mage.setUnitHealth(unitHealth);
    mage.setGold(gold);
    mage.setUnitMana(unitMana);
    mage.setPlayerClass(playerClass);
    mage.addToInventory(unitInventory);
    mage.setDamage(specificField);
    return mage;
  }

  @Override
  public Mage build() {
    return createUnit();
  }

  @Override
  protected MageBuilder self() {
    return this;
  }
}