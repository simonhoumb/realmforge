package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Convert
   protected Object goalValue;

  private GoalType goalType;

  Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public abstract boolean isFulfilled(Unit unit);

  public abstract void setGoalValue(Object value);

  public Object getGoalValue() {
    return goalValue;
  }

  public GoalType getGoalType() {
    return goalType;
  }

  protected void setGoalType(GoalType goalType) {
    this.goalType = goalType;
  }
}
