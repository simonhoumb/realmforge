package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The GameOverAction class represents an action that ends the game.
 */
@Entity
public class GameOverAction extends Action {

  /**
   * Creates a GameOverAction.
   */
  public GameOverAction() {
    this.setActionType(ActionType.GAME_OVER);
  }

  public void execute(Unit unit) {
    // Implement the logic to handle the game over action here
    // For example, you can end the game, display a game over
    // message, or perform other relevant actions
  }
}