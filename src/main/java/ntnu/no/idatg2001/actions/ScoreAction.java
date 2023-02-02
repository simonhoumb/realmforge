package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.playerinformation.Player;

public class ScoreAction implements Action {
  int points;

  public ScoreAction(int points) {
    this.points = points;
  }

  public void execute(Player player) {
    player.setPlayerScore(player.getPlayerScore() + points);
  }
}
