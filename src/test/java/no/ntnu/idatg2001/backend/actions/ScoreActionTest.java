package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScoreActionTest {

  private ScoreAction scoreAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    //Keep in mind the maxHealth of a unit as the execute method will not exceed this value.
    scoreAction = new ScoreAction(50);
    unit = new Ranger("test");
    unit.setUnitScore(25);
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.SCORE, scoreAction.getActionType());
    assertEquals(50, scoreAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    ScoreAction action = new ScoreAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }

  @Test
  void testExecute() {
    scoreAction.execute(unit);
    assertEquals(75, unit.getUnitScore());
  }

  @Test
  void testExecuteWithNegativeNumber() {
    scoreAction.setValue(-5);
    scoreAction.execute(unit);
    assertEquals(20, unit.getUnitScore());
  }
}
