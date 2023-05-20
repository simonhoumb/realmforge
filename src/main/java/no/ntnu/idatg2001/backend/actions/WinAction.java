package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class WinAction extends Action {

  public WinAction() {
    this.setActionType(ActionType.WIN);
  }

  public void execute(Unit unit) {
    // Implement the logic to handle the win action here
    // For example, you can display a victory message, award bonus points, or perform other relevant actions
  }
}
