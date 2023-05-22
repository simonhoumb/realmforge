package no.ntnu.idatg2001.backend.actions;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
 class ActionTest {

  private Action action;

  @BeforeEach
  public void setUp() {
    action = new Action() {
      @Override
      public void execute(Unit unit) {
      }
    };
  }

  @Test
  void testGetAndSetId() {
    Long id = 1L;
    assertNull(action.getId());
    action.setId(id);
    assertEquals(id, action.getId());
  }

  @Test
  void testGetAndSetActionType() {
    ActionType actionType = ActionType.NONE;
    assertNull(action.getActionType());
    action.setActionType(actionType);
    assertEquals(actionType, action.getActionType());
  }

  @Test
  void testGetAndSetValue() {
    Object value = "Test value";
    assertNull(action.getValue());
    action.setValue(value);
    assertEquals(value, action.getValue());
  }
}
