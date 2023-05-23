package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import javafx.scene.layout.Pane;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The Action class represents an action that can be executed on a unit.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  private ActionType actionType;

  @Convert
  protected Object value;

  /**
   * Gets the id of the action.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the action.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the action type.
   *
   * @return the action type
   */
  public ActionType getActionType() {
    return actionType;
  }

  /**
   * Sets the action type.
   *
   * @param actionType the action type
   */
  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }

  /**
   * Gets the value of the action.
   *
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the value of the action.
   *
   * @param value the value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public abstract void execute(Unit unit);

  /**
   * Gets the pane of the action.
   *
   * @return the pane
   */
  @Override
  public String toString() {
    return "Action{" +
        "id=" + id +
        ", actionType=" + actionType +
        ", value=" + value +
        '}';
  }
}
