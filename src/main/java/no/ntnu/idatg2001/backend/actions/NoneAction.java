package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class NoneAction extends Action {

  public NoneAction() {
    this.setActionType(ActionType.NONE);
  }

  public void execute(Unit unit) {
    // No action is performed in the execute method for the NoneAction
    // You can leave it empty or add any specific behavior as needed
  }
}