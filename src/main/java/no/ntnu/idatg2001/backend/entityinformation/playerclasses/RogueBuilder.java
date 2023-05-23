package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

/**
 * The RogueBuilder class represents a builder for the Rogue class.
 */
public class RogueBuilder extends UnitBuilder<Rogue, RogueBuilder> {


  /**
   * Builds a Rogue.
   */
  @Override
  public Rogue build() {
    Rogue rogue = new Rogue(unitName);
    rogue.setUnitHealthMax(unitHealthMax);
    rogue.setUnitHealth(unitHealth);
    rogue.setGold(gold);
    rogue.setUnitMana(unitMana);
    rogue.setPlayerClass(playerClass);
    rogue.addToInventory(unitInventory);
    return rogue;
  }

  /**
   * Returns the RogueBuilder itself.
   *
   * @return the RogueBuilder itself
   */
  @Override
  protected RogueBuilder self() {
    return this;
  }
}