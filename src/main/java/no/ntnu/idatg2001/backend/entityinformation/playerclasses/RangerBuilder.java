package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

public class RangerBuilder extends UnitBuilder<Ranger, RangerBuilder> {

  private int specificField;

  public RangerBuilder withSpecificField(int specificField) {
    this.specificField = specificField;
    return this;
  }

  @Override
  public Ranger build() {
    Ranger ranger = new Ranger(unitName);
    ranger.setUnitHealthMax(unitHealthMax);
    ranger.setUnitHealth(unitHealth);
    ranger.setGold(gold);
    ranger.setUnitMana(unitMana);
    ranger.setPlayerClass(playerClass);
    ranger.addToInventory(unitInventory);
    ranger.setDamage(specificField);
    return ranger;
  }

  @Override
  protected RangerBuilder self() {
    return this;
  }
}