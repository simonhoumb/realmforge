package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.entityinformation.Entity;

public class ScoreAction implements Action {
  int points;

  public ScoreAction(int points) {
    this.points = points;
  }

  public void execute(Entity entity) {
    entity.setEntityLevel(entity.getEntityLevel() + points);
  }
}
