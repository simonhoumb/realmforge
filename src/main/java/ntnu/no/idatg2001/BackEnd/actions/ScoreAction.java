package ntnu.no.idatg2001.BackEnd.actions;

import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;

public class ScoreAction implements Action {
  int points;

  public ScoreAction(int points) {
    this.points = points;
  }

  public void execute(Entity entity) {
    entity.setEntityLevel(entity.getEntityLevel() + points);
  }
}
