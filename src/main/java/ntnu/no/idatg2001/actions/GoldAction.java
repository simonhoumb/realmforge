package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.Player;

public class GoldAction implements Action {
  int gold;

  public GoldAction(int gold) {
    this.gold = gold;
  }

  public void execute(Player player) {
    player.setGold(player.getGold() + gold);
  }
}
