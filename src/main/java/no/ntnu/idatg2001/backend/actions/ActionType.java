package no.ntnu.idatg2001.backend.actions;

public enum ActionType {
  GOLD ("Gold"),
  HEALTH ("Health"),
  DAMAGE ("Damage"),
  ARMOR ("Armor"),
  SCORE ("Score"),
  WEAPON ("Weapon"),
  ITEM ("Item"),
  PASSAGE  ("Passage"),
  GAME_OVER ("Game Over"),
  WIN ("Win"),
  LOSE ("Lose"),
  NONE ("None");
  private String actionTypeLabel;

  ActionType(String label) {
    this.actionTypeLabel = label;
  }

  public String getLabel() {
    return actionTypeLabel;
  }

  public static ActionType valueOfLabel(String label) {
    for (ActionType type : values()) {
      if (type.getLabel().equalsIgnoreCase(label)) {
        return type;
      }
    }
    return null;
  }
}
