package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class DamageAction extends Action {

  public DamageAction(int damage) {
    this.value = damage;
    this.setActionType(ActionType.DAMAGE);
  }

  public DamageAction() {
    this.setActionType(ActionType.DAMAGE);
  }

  public void execute(Unit unit) {
    int damage = Integer.parseInt(value.toString());
    int modifiedDamage = calculateModifiedDamage(damage, unit);
    unit.setUnitHealth(unit.getUnitHealth() - modifiedDamage);
  }

  private int calculateModifiedDamage(int damage, Unit unit) {
    // You can add any additional logic to modify the damage based on unit attributes or game rules
    // For example, you can consider armor, luck, or any other factors affecting damage calculation
    return damage;
  }
}