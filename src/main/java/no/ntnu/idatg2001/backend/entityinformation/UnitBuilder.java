package no.ntnu.idatg2001.backend.entityinformation;

import java.util.ArrayList;
import java.util.List;

/**
 * The UnitBuilder class represents a builder for the Unit class.
 *
 * @param <T> the type of Unit
 * @param <B> the type of UnitBuilder
 */
public abstract class UnitBuilder<T extends Unit, B extends UnitBuilder<T, B>> {


  private Long id;

  protected int unitHealthMax;
  protected int unitHealth;
  protected String unitName;
  protected int gold;
  protected int unitMana;
  protected PlayerClass playerClass;
  protected int unitDamage;
  protected int unitArmour;
  protected int unitScore;
  protected int unitCriticalStrikeChance;
  protected List<String> unitInventory;


  /**
   * Builds a Unit.
   */
  public Long getId() {
    return id;
  }

  /**
   * Builds a Unit.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Builds a Unit.
   */
  public B withUnitHealthMax(int unitHealthMax) {
    this.unitHealthMax = unitHealthMax;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withUnitHealth(int unitHealth) {
    this.unitHealth = unitHealth;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withUnitName(String unitName) {
    this.unitName = unitName;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withGold(int gold) {
    this.gold = gold;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withUnitMana(int unitMana) {
    this.unitMana = unitMana;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withPlayerClass(PlayerClass playerClass) {
    this.playerClass = playerClass;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withUnitInventory(List<String> unitInventory) {
    this.unitInventory = unitInventory;
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  public B withUnitScore(int unitScore) {
    this.unitScore = unitScore;
    return self();
  }

  /**
   * adds an item to the unit inventory.
   *
   * @param item the item to add
   * @return the UnitBuilder
   */
  public B addToUnitInventory(String item) {
    if (this.unitInventory == null) {
      this.unitInventory = new ArrayList<>();
    }
    this.unitInventory.add(item);
    return self();
  }

  /**
   * Returns the UnitBuilder itself.
   *
   * @param unitDamage the damage to add
   * @return the UnitBuilder itself
   */
  public B withDamage(int unitDamage) {
    this.unitDamage = unitDamage;
    return self();
  }

  /**
   * Adds armour to the unit.
   *
   * @param unitArmour the armour to add
   * @return the UnitBuilder
   */
  public B withArmour(int unitArmour) {
    this.unitArmour = unitArmour;
    return self();
  }

  /**
   * Adds a score to the unit.
   *
   * @param unitScore the score to add
   * @return the UnitBuilder
   */
  public B withScore(int unitScore) {
    this.unitCriticalStrikeChance = unitScore;
    return self();
  }

  /**
   * Adds a critical strike chance to the unit.
   *
   * @param criticalStrikeChance the critical strike chance to add
   * @return the UnitBuilder
   */
  public B withCriticalStrikeChance(int criticalStrikeChance) {
    this.unitCriticalStrikeChance = criticalStrikeChance;
    return self();
  }

  /**
   * Gets unit Max Health.
   *
   * @return the unit Max Health
   */
  public int getUnitHealthMax() {
    return unitHealthMax;
  }

  /**
   * Gets unit Mana.
   *
   * @return the unit Mana
   */
  public int getUnitMana() {
    return unitMana;
  }

  /**
   * Gets unit Gold.
   *
   * @return the unit Gold
   */
  public int getGold() {
    return gold;
  }

  /**
   * Gets unit Damage.
   *
   * @return the unit Damage
   */
  public int getDamage() {
    return unitDamage;
  }

  /**
   * Gets unit criticalChance.
   *
   * @return the unit criticalChance
   */
  public int getCriticalChance() {
    return unitCriticalStrikeChance;
  }

  /**
   * Gets unit armour.
   *
   * @return the unit armour
   */
  public int getArmour() {
    return unitArmour;
  }

  /**
   * Builds the Unit.
   *
   * @return the Unit
   */
  public abstract T build();

  /**
   * Returns the UnitBuilder itself.
   *
   * @return the UnitBuilder itself
   */
  protected abstract B self();
}