package no.ntnu.idatg2001.backend.goals;

/**
 * The GoalType enum represents the different types of goals.
 */
public enum GoalType {
  GOLD_GOAL ("Gold"),
  HEALTH_GOAL ("Health"),
  INVENTORY_GOAL ("Inventory"),
  SCORE_GOAL ("Score");

  private final String goalTypeLabel;

  /**
   * Constructor for GoalType.
   *
   * @param label The label of the goal type.
   */
  GoalType(String label) {
    this.goalTypeLabel = label;
  }

  /**
   * getLabel returns the label of the goal type.
   *
   * @return goalTypeLabel
   */
  public String getLabel() {
    return goalTypeLabel;
  }

  /**
   * valueOfLabel returns the GoalType with the given label.
   *
   * @param label The label of the GoalType.
   * @return GoalType
   */
  public static GoalType valueOfLabel(String label) {
    for (GoalType type : values()) {
      if (type.getLabel().equalsIgnoreCase(label)) {
        return type;
      }
    }
    return null;
  }
}
