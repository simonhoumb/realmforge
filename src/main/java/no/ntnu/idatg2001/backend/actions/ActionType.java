package no.ntnu.idatg2001.backend.actions;

/**
 * Enum for the different types of actions that can be performed.
 */
public enum ActionType {
  GOLD("Gold"),
  HEALTH("Health"),
  DAMAGE("Damage"),
  ARMOR("Armor"),
  SCORE("Score"),
  WEAPON("Weapon"),
  ITEM("Item"),
  PASSAGE("Passage"),
  GAME_OVER("Game Over"),
  WIN("Win"),
  LOSE("Lose"),
  NONE("None");
  private final String actionTypeLabel;

  /**
   * Constructor for the ActionType enum.
   *
   * @param label the label of the ActionType
   */
  ActionType(String label) {
    this.actionTypeLabel = label;
  }

  /**
   * Returns the label of the ActionType.
   *
   * @return the label of the ActionType
   */
  public String getLabel() {
    return actionTypeLabel;
  }

  /**
   * Returns the ActionType that matches the given label.
   *
   * @param label the label to match
   * @return the ActionType that matches the given label
   */
  public static ActionType valueOfLabel(String label) {
    for (ActionType type : values()) {
      if (type.getLabel().equalsIgnoreCase(label)) {
        return type;
      }
    }
    return null;
  }
}
