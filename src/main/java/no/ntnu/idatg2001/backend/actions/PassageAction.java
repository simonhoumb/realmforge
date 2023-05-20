package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class PassageAction extends Action {

  public PassageAction(String passage) {
    this.value = passage;
    this.setActionType(ActionType.PASSAGE);
  }

  public PassageAction() {

  }

  public void execute(Unit unit) {
    if (value instanceof String) {
      String passage = (String) value;
      // Implement the logic to handle passage navigation here
      // For example, you can update the unit's current location based on the passage value
    }
  }
}