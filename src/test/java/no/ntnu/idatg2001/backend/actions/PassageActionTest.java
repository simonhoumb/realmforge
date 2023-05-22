package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PassageActionTest {

  private PassageAction passageAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    passageAction = new PassageAction("testPassage");
    unit = new Ranger("test");
  }

  @Test
  void testConstructorWithValue() {
    assertEquals(ActionType.PASSAGE , passageAction.getActionType());
    assertEquals("testPassage", passageAction.getValue());
  }

  @Test
  void testConstructorWithoutValue() {
    PassageAction action = new PassageAction();
    assertNull(action.getActionType());
    assertNull(action.getValue());
  }
}
