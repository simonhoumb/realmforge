package no.ntnu.idatg2001.backend.entityinformation;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents A unit in the game.

 * @author Eskil Alstad and Simon Husaas Houmb
 * @version 2022-12-07
 */
@Entity
public abstract class Unit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  //ALL
  private String unitName; //Name of the Unit.
  private PlayerClass playerClass; //The Class of the Player
  private int unitHealthMax; //The max Health points of the Unit.
  private int unitHealth; //The Health of the Unit.
  private int unitLevel; //The Level of the Unit.
  private int unitMana; //The Mana of the unit.
  private int unitManaMax; //The max Mana points of the unit.

  //Stats
  private int strength; //Strength stat of the Unit.
  private int intelligence; //Intelligence stat of the Unit.
  private int dexterity; //Dexterity stat of the Unit.
  private int luck; //Luck stat of the Unit.
  private int gold; //Gold for the Unit.
  private double damage = 30; //Damage the Unit starts with.
  private double criticalChance = 0.0; //Unit CriticalChance.
  private int armour; //Armor stat to the Unit.
  private String weapon = "hands"; //Weapon Unit Starts With.
  @ElementCollection
  private List<String> unitInventory; // The unit's inventory


  protected Unit() {
    this(100, 100, "default", 0, 100);
  }

  /**
   * Instantiates a new Unit.
   *
   * @param unitHealthMax the unit health max
   * @param unitHealth    the unit health
   * @param unitName      the unit name
   * @param gold            the gold
   */
  protected Unit(int unitHealthMax, int unitHealth, String unitName, int gold,
      int unitMana) {
    this.unitHealthMax = unitHealthMax;
    this.unitHealth = unitHealth;
    this.unitName = unitName;
    this.unitMana = unitMana;
    this.unitInventory = new ArrayList<>();
  }

  /**
   * Gets unit name.
   *
   * @return the unit name
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets unit name.
   *
   * @param id the unit name
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets unit health.
   *
   * @return the unit health
   */
  public int getUnitHealth() {
    return this.unitHealth;
  }

  /**
   * Sets unit health. if Health is bigger than max health, health
   * is set to maxHealth.
   *
   * @param unitHealth the unit health
   */
  public void setUnitHealth(int unitHealth) {
    if (unitHealth > this.unitHealthMax) {
      this.unitHealth = this.unitHealthMax;
    } else if (unitHealth < 0) {
      this.unitHealth = 0;
    } else {
      this.unitHealth = unitHealth;
    }
  }

  /**
   * Gets unit health max.
   *
   * @return the unit health max
   */
  public int getUnitManaMax() {
    return unitManaMax;
  }

  /**
   * Sets unit health max.
   *
   * @param unitManaMax the unit health max
   */
  public void setUnitManaMax(int unitManaMax) {
    this.unitManaMax = unitManaMax;
    if (unitMana > unitManaMax) {
      unitMana = unitManaMax;
    }
  }

  /**
   * Gets unit mana.
   *
   * @return the unit mana
   */
  public int getUnitMana() {
    return unitMana;
  }

  /**
   * Sets unit mana if mana is bigger than mana, its set back
   * to max mana.
   *
   * @param unitMana the unit mana
   */
  public void setUnitMana(int unitMana) {
    if (unitMana > this.unitManaMax) {
      this.unitMana = this.unitManaMax;
    } else if (unitMana < 0) {
      this.unitHealth = 0;
    } else {
      this.unitMana = unitMana;
    }
  }

  /**
   * Gets gold.
   *
   * @return the gold
   */
  public int getGold() {
    return gold;
  }

  /**
   * Sets gold.
   *
   * @param gold the gold
   */
  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * Gets damage.
   *
   * @return the damage
   */
  public double getDamage() {
    return damage;
  }

  /**
   * Sets damage.
   *
   * @param damage the damage
   */
  public void setDamage(double damage) {
    this.damage = damage;
  }

  /**
   * Gets critical chance.
   *
   * @return the critical chance
   */
  public double getCriticalChance() {
    return criticalChance;
  }

  /**
   * Sets critical chance.
   *
   * @param criticalChance the critical chance
   */
  public void setCriticalChance(double criticalChance) {
    this.criticalChance = criticalChance;
  }

  /**
   * Gets armour.
   *
   * @return the armour
   */
  public int getArmour() {
    return armour;
  }

  /**
   * Sets armour.
   *
   * @param armour the armour
   */
  public void setArmour(int armour) {
    this.armour = armour;
  }

  /**
   * Gets unit health max.
   *
   * @return the unit health max
   */
  public int getUnitHealthMax() {
    return unitHealthMax;
  }

  /**
   * Sets unit health max.
   *
   * @param unitHealthMax the unit health max
   */
  public void setUnitHealthMax(int unitHealthMax) {
    this.unitHealthMax = unitHealthMax;
    if (unitHealth > unitHealthMax) {
      unitHealth = unitHealthMax;
    }
  }

  /**
   * Gets unit name.
   *
   * @return the unit name
   */
  public String getUnitName() {
    return unitName;
  }

  /**
   * Sets unit name.
   *
   * @param unitName the unit name
   */
  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  /**
   * Gets unit level.
   *
   * @return the unit level
   */
  public int getUnitLevel() {
    return unitLevel;
  }

  /**
   * Sets unit level.
   *
   * @param unitLevel the unit level
   */
  public void setUnitLevel(int unitLevel) {
    this.unitLevel = unitLevel;
  }

  /**
   * Gets strength.
   *
   * @return the strength
   */
  public int getStrength() {
    return strength;
  }

  /**
   * Sets strength.
   *
   * @param strength the strength
   */
  public void setStrength(int strength) {
    this.strength = strength;
  }

  /**
   * Gets intelligence.
   *
   * @return the intelligence
   */
  public int getIntelligence() {
    return intelligence;
  }

  /**
   * Sets intelligence.
   *
   * @param intelligence the intelligence
   */
  public void setIntelligence(int intelligence) {
    this.intelligence = intelligence;
  }

  /**
   * Gets dexterity.
   *
   * @return the dexterity
   */
  public int getDexterity() {
    return dexterity;
  }

  /**
   * Sets dexterity.
   *
   * @param dexterity the dexterity
   */
  public void setDexterity(int dexterity) {
    this.dexterity = dexterity;
  }

  /**
   * Gets luck.
   *
   * @return the luck
   */
  public int getLuck() {
    return luck;
  }

  /**
   * Sets luck.
   *
   * @param luck the luck
   */
  public void setLuck(int luck) {
    this.luck = luck;
  }

  /**
   * Gets weapon.
   *
   * @return the weapon
   */
  public String getWeapon() {
    return weapon;
  }

  /**
   * Gets player class.
   *
   * @return the player class
   */
  public PlayerClass getPlayerClass() {
    return playerClass;
  }

  /**
   * Sets player class.
   *
   * @param playerClass the player class
   */
  public void setPlayerClass(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }

  /**
   * Adds an item to the unit's inventory.
   *
   * @param item the item to be added to the inventory
   */
  public void addToInventory(String item) {
    unitInventory.add(item);
  }

  /**
   * Adds multiple items to the unit's inventory.
   *
   * @param itemsToAdd the items to be added to the inventory
   */
  public void addToInventory(List<String> itemsToAdd) {
    unitInventory.addAll(itemsToAdd);
  }

  /**
   * Removes an item from the unit's inventory.
   *
   * @param item the item to be removed from the inventory
   * @throws IllegalArgumentException ("Item not in unit's inventory.") if the item is not in the
   *     unit's inventory.
   */
  public void removeFromInventory(String item) throws IllegalArgumentException {
    if (!unitInventory.contains(item)) {
      throw new IllegalArgumentException("Item not in unit's inventory.");
    } else {
      unitInventory.remove(item);
    }
  }

  /**
   * Returns the unit's inventory.
   *
   * @return the inventory to be returned as a List.
   */
  public List<String> getUnitInventory() {
    return unitInventory;
  }


  @Override
  public String toString() {
    return "Unit{"
        + "unitName= '" + unitName + '\''
        + ", unitHealthPoints= " + unitHealth
        + ", unitMana= " + unitMana
        + ", unitScore= " + unitLevel
        + ", unitGold= " + gold
        + ", playerClass= " + playerClass
        + ", unitInventory= " + unitInventory
        + ", unit Stats §§ "
        + " STR " + strength + ", "
        + " INT " + intelligence + ", "
        + " DEX " + dexterity + ", "
        + " LUCK " + luck + ", "
        + " §§ "
        + '}';
  }
}


