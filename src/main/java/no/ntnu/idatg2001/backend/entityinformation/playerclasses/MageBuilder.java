package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

/**
 * The MageBuilder class represents a builder for the Mage class.
 */
public class MageBuilder extends UnitBuilder<Mage, MageBuilder> {

  /**
   * Creates a MageBuilder with the default unit name of "Mage".
   */
  protected Mage createUnit() {
    Mage mage = new Mage(unitName);
    mage.setUnitHealthMax(unitHealthMax);
    mage.setUnitHealth(unitHealth);
    mage.setGold(gold);
    mage.setUnitMana(unitMana);
    mage.setPlayerClass(playerClass);
    mage.addToInventory(unitInventory);
    return mage;
  }

  /**
   * Builds the Mage.
   */
  @Override
  public Mage build() {
    return createUnit();
  }

  /**
   * Returns the MageBuilder itself.
   *
   * @return the MageBuilder itself
   */
  @Override
  protected MageBuilder self() {
    return this;
  }
}