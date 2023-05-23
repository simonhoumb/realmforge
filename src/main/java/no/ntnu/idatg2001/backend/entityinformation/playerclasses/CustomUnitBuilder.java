package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

/**
 * The CustomUnitBuilder class represents a builder for the CustomUnit class.
 */
public class CustomUnitBuilder extends UnitBuilder<CustomUnit, CustomUnitBuilder> {

  /**
   * Creates a CustomUnitBuilder with the default unit name of "CustomUnit".
   */
  protected CustomUnit createUnit() {
    CustomUnit customUnit = new CustomUnit(unitName);
    // Set other properties as needed
    customUnit.setUnitHealthMax(unitHealthMax);
    customUnit.setUnitHealth(unitHealth);
    customUnit.setGold(gold);
    customUnit.setUnitManaMax(unitMana);
    customUnit.setUnitMana(unitMana);
    customUnit.setPlayerClass(playerClass);
    customUnit.setDamage(unitDamage);
    customUnit.setArmour(unitArmour);
    customUnit.setCriticalChance(unitCriticalStrikeChance);
    return customUnit;
  }

  /**
   * Builds the CustomUnit.
   */
  @Override
  public CustomUnit build() {
    return createUnit();
  }

  /**
   * Returns the CustomUnitBuilder itself.
   *
   * @return the CustomUnitBuilder itself
   */
  @Override
  protected CustomUnitBuilder self() {
    return this;
  }
}