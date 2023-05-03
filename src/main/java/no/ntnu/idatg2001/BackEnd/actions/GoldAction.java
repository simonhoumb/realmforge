package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class GoldAction extends Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
  }

  public void execute(Unit unit) {
    unit.setGold(unit.getGold() + gold);
  }
}
