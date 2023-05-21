package no.ntnu.idatg2001.backend.goals;

public enum GoalType {
  GOLD_GOAL ("Gold"),
  HEALTH_GOAL ("Health"),
  INVENTORY_GOAL ("Inventory"),
  SCORE_GOAL ("Score");

  private String goalTypeLabel;

  GoalType(String label) {
    this.goalTypeLabel = label;
  }

  public String getLabel() {
    return goalTypeLabel;
  }

  public static GoalType valueOfLabel(String label) {
    for (GoalType type : values()) {
      if (type.getLabel().equalsIgnoreCase(label)) {
        return type;
      }
    }
    return null;
  }
}
