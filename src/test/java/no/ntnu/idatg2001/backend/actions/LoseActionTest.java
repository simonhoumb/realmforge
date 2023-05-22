package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoseActionTest {

  private LoseAction loseAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    loseAction = new LoseAction();
    unit = new Ranger("test");
  }

  @Test
  void testConstructorWithoutValue() {
    assertEquals(ActionType.LOSE , loseAction.getActionType());
    assertNull(loseAction.getValue());
  }
}
