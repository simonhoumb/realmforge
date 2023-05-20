package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class LoseAction extends Action {

  public LoseAction() {
    this.setActionType(ActionType.LOSE);
  }

  public void execute(Unit unit) {
    // Implement the logic to handle the lose action here
    // For example, you can display a game over message, deduct points, or perform other relevant actions
  }
}