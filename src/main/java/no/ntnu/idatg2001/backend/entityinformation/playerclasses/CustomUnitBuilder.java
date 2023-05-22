package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.CustomUnit;

public class CustomUnitBuilder extends UnitBuilder<CustomUnit, CustomUnitBuilder> {

  private int customField;

  public CustomUnitBuilder withCustomField(int customField) {
    this.customField = customField;
    return self();
  }

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

  @Override
  public CustomUnit build() {
    return createUnit();
  }

  @Override
  protected CustomUnitBuilder self() {
    return this;
  }
}