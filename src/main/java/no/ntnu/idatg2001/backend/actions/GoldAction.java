package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class GoldAction extends Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
  }

  public GoldAction() {

  }

  public void execute(Unit unit) {
    unit.setGold(unit.getGold() + gold);
  }
}
