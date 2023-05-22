package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoneActionTest {

  private NoneAction noneAction;
  private Unit unit;

  @BeforeEach
  public void setUp() {
    noneAction = new NoneAction();
    unit = new Ranger("test");
  }

  @Test
  void testConstructorWithoutValue() {
    assertEquals(ActionType.NONE , noneAction.getActionType());
    assertNull(noneAction.getValue());
  }
}
