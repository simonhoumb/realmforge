package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameOverActionTest {

  @Test
  void testConstructor() {
    GameOverAction gameOverAction = new GameOverAction();
    assertEquals(ActionType.GAME_OVER, gameOverAction.getActionType());
  }

  @Test
  void testExecute() {
    GameOverAction gameOverAction = new GameOverAction();
    Unit unit = new Ranger("test");
    // Execute the game over action
    gameOverAction.execute(unit);
    // Add assertions based on your specific game over logic
    // For example, check if the game has ended or display a game over message
  }
}
