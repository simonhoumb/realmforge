package ntnu.no.idatg2001.entityinformation;

import java.util.ArrayList;
import java.util.List;
import ntnu.no.idatg2001.utility.CheckIfValid;

/**
 * Represents A Item Class.

 * @author Eskil Alstad & Simon Husås Houmb
 * @version 2022-12-07
 */
public abstract class Entity {

  //ALL
  private String entityName; //Name of the Entity.
  private PlayerClass playerClass; //The Class of the Player
  private int entityHealthMax; //The max Health points of the Entity.
  private int entityHealth; //The Health of the Entity.
  private int entityLevel; //The Level of the Entity.
  private int entityMana;
  private int entityManaMax;
  //Stats
  private int strength; //Strength stat of the Entity.
  private int intelligence; //Intelligence stat of the Entity.
  private int dexterity; //Dexterity stat of the Entity.
  private int luck; //Luck stat of the Entity.
  private int gold; //Gold for the Entity.
  private double damage = 30; //Damage the Entity starts with.
  private double criticalChance = 0.0; //Entity CriticalChance.
  private int armour; //Armor stat to the Entity.
  private String weapon = "hands"; //Weapon Entity Starts With.
  private List<String> entityInventory; // The entity's inventory
  private final CheckIfValid checkIfValid = new CheckIfValid();

  protected Entity() {
    this(100, 100, "default", 0, 100);
  }

  /**
   * Instantiates a new Entity.
   *
   * @param entityHealthMax the entity health max
   * @param entityHealth    the entity health
   * @param entityName      the entity name
   * @param gold            the gold
   */
  protected Entity(int entityHealthMax, int entityHealth, String entityName, int gold,
      int entityMana) {
    this.entityHealthMax = entityHealthMax;
    this.entityHealth = entityHealth;
    this.entityName = entityName;
    this.entityMana = entityMana;
    this.entityInventory = new ArrayList<>();
  }

  /**
   * Gets entity health.
   *
   * @return the entity health
   */
  public int getEntityHealth() {
    return this.entityHealth;
  }

  /**
   * Sets entity health. if Health is bigger than max health, health
   * is set to maxHealth.
   *
   * @param entityHealth the entity health
   */
  public void setEntityHealth(int entityHealth) {
    if (entityHealth > this.entityHealthMax) {
      this.entityHealth = this.entityHealthMax;
    } else if (entityHealth < 0) {
      this.entityHealth = 0;
    } else {
      this.entityHealth = entityHealth;
    }
  }

  public int getEntityManaMax() {
    return entityManaMax;
  }

  public void setEntityManaMax(int entityManaMax) {
    this.entityManaMax = entityManaMax;
    if (entityMana > entityManaMax) {
      entityMana = entityManaMax;
    }
  }

  /**
   * Gets entity mana.
   *
   * @return the entity mana
   */
  public int getEntityMana() {
    return entityMana;
  }

  /**
   * Sets entity mana if mana is bigger than mana, its set back
   * to max mana.
   *
   * @param entityMana the entity mana
   */
  public void setEntityMana(int entityMana) {
    if (entityMana > this.entityManaMax) {
      this.entityMana = this.entityManaMax;
    } else if (entityMana < 0) {
      this.entityHealth = 0;
    } else {
      this.entityMana = entityMana;
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
   * Gets crit chance.
   *
   * @return the crit chance
   */
  public double getCriticalChance() {
    return criticalChance;
  }

  /**
   * Sets crit chance.
   *
   * @param criticalChance the crit chance
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
   * Gets entity health max.
   *
   * @return the entity health max
   */
  public int getEntityHealthMax() {
    return entityHealthMax;
  }

  /**
   * Sets entity health max.
   *
   * @param entityHealthMax the entity health max
   */
  public void setEntityHealthMax(int entityHealthMax) {
    this.entityHealthMax = entityHealthMax;
    if (entityHealth > entityHealthMax) {
      entityHealth = entityHealthMax;
    }
  }

  /**
   * Gets entity name.
   *
   * @return the entity name
   */
  public String getEntityName() {
    return entityName;
  }

  /**
   * Sets entity name.
   *
   * @param entityName the entity name
   */
  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  /**
   * Gets entity level.
   *
   * @return the entity level
   */
  public int getEntityLevel() {
    return entityLevel;
  }

  /**
   * Sets entity level.
   *
   * @param entityLevel the entity level
   */
  public void setEntityLevel(int entityLevel) {
    this.entityLevel = entityLevel;
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
   * Adds an item to the entity's inventory.
   *
   * @param item the item to be added to the inventory
   */
  public void addToInventory(String item) {
    entityInventory.add(item);
  }

  /**
   * Adds multiple items to the entity's inventory.
   *
   * @param itemsToAdd the items to be added to the inventory
   */
  public void addToInventory(List<String> itemsToAdd) {
    entityInventory.addAll(itemsToAdd);
  }

  /**
   * Removes an item from the entity's inventory.
   *
   * @param item the item to be removed from the inventory
   * @throws IllegalArgumentException ("Item not in entity's inventory.") if the item is not in the
   *     entity's inventory.
   */
  public void removeFromInventory(String item) throws IllegalArgumentException {
    if (!entityInventory.contains(item)) {
      throw new IllegalArgumentException("Item not in entity's inventory.");
    } else {
      entityInventory.remove(item);
    }
  }

  /**
   * Returns the entity's inventory.
   *
   * @return the inventory to be returned as a List.
   */
  public List<String> getEntityInventory() {
    return entityInventory;
  }


  @Override
  public String toString() {
    return "Entity{"
        + "entityName= '" + entityName + '\''
        + ", entityHealthPoints= " + entityHealth
        + ", entityMana= " + entityMana
        + ", entityScore= " + entityLevel
        + ", entityGold= " + gold
        + ", playerClass= " + playerClass
        + ", entityInventory= " + entityInventory
        + ", entity Stats §§ "
        + " STR " + strength + ", "
        + " INT " + intelligence + ", "
        + " DEX " + dexterity + ", "
        + " LUCK " + luck + ", "
        + " §§ "
        + '}';
  }
}


