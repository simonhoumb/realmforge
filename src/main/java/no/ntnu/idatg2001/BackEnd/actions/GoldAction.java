package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public class GoldAction implements Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
  }

  public void execute(Entity entity) {
    entity.setGold(entity.getGold() + gold);
  }
}
