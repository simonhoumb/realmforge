package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WinActionTest {

  private WinAction winAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    winAction = new WinAction();
    unit = new Ranger("test");
  }

  @Test
  void testConstructorWithoutValue() {
    assertEquals(ActionType.WIN , winAction.getActionType());
    assertNull(winAction.getValue());
  }
}
