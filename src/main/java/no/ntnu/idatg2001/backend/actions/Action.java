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
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ActionType getActionType() {
    return actionType;
  }


  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public abstract void execute(Unit unit);

  @Override
  public String toString() {
    return "Action{" +
        "id=" + id +
        ", actionType=" + actionType +
        ", value=" + value +
        '}';
  }
}
