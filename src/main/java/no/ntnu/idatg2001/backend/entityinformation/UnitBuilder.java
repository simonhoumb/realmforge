package no.ntnu.idatg2001.backend.entityinformation;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitBuilder<T extends Unit, B extends UnitBuilder<T, B>> {

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

  public B withUnitHealthMax(int unitHealthMax) {
    this.unitHealthMax = unitHealthMax;
    return self();
  }

  public B withUnitHealth(int unitHealth) {
    this.unitHealth = unitHealth;
    return self();
  }

  public B withUnitName(String unitName) {
    this.unitName = unitName;
    return self();
  }

  public B withGold(int gold) {
    this.gold = gold;
    return self();
  }

  public B withUnitMana(int unitMana) {
    this.unitMana = unitMana;
    return self();
  }

  public B withPlayerClass(PlayerClass playerClass) {
    this.playerClass = playerClass;
    return self();
  }

  public B withUnitInventory(List<String> unitInventory) {
    this.unitInventory = unitInventory;
    return self();
  }

  public B addToUnitInventory(String item) {
    if (this.unitInventory == null) {
      this.unitInventory = new ArrayList<>();
    }
    this.unitInventory.add(item);
    return self();
  }

  public B withDamage(int damage) {
    return self();
  }

  public B withArmour(int armour) {
    return self();
  }

  public B withScore(int score) {
    return self();
  }

  public B withCriticalStrikeChance(int criticalStrikeChance) {
    return self();
  }

  public int getUnitHealthMax() {
    return unitHealthMax;
  }

  public int getUnitMana() {
    return unitMana;
  }

  public int getGold() {
    return gold;
  }

  public int getDamage() {
    return unitDamage;
  }

  public int getCriticalChance() {
    return unitCriticalStrikeChance;
  }

  public int getArmour() {
    return unitArmour;
  }

  public abstract T build();

  protected abstract B self();
}