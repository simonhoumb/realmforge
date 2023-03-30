package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.entityinformation.Entity;

public class GoldAction implements Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
  }

  public void execute(Entity entity) {
    entity.setGold(entity.getGold() + gold);
  }
}
