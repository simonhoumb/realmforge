package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

/**
 * The Ranger class represents a Ranger.
 */
public class RangerBuilder extends UnitBuilder<Ranger, RangerBuilder> {

  /**
   * Builds a Ranger.
   */
  @Override
  public Ranger build() {
    Ranger ranger = new Ranger(unitName);
    ranger.setUnitHealthMax(unitHealthMax);
    ranger.setUnitHealth(unitHealth);
    ranger.setGold(gold);
    ranger.setUnitMana(unitMana);
    ranger.setPlayerClass(playerClass);
    ranger.addToInventory(unitInventory);
    return ranger;
  }

  /**
   * Returns the RangerBuilder itself.
   *
   * @return the RangerBuilder itself
   */
  @Override
  protected RangerBuilder self() {
    return this;
  }
}