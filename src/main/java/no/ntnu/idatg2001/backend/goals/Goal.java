package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The Goal class represents a goal in a game.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Transient
   protected Object goalValue;

  private GoalType goalType;

  /**
   * setId sets the id of the goal.
   *
   * @param id The id to set.
   * @return this
   */
  public Goal setId(Long id) {
    this.id = id;
    return this;
  }

  /**
   * getId returns the id of the goal.
   *
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * isFulfilled checks if the goal is fulfilled.
   *
   * @param unit The unit that is playing the game.
   * @return true if the goal is fulfilled, false if not.
   */
  public abstract boolean isFulfilled(Unit unit);

  /**
   * setGoalValue sets the goal value.
   *
   * @param value The value to set.
   */
  public abstract void setGoalValue(Object value);

  /**
   * getGoalValue returns the goal value.
   *
   * @return goalValue
   */
  public Object getGoalValue() {
    return goalValue;
  }

  /**
   * getGoalType returns the goal type.
   *
   * @return goalType
   */
  public GoalType getGoalType() {
    return goalType;
  }

  /**
   * setGoalType sets the goal type.
   *
   * @param goalType The goal type to set.
   */
  protected void setGoalType(GoalType goalType) {
    this.goalType = goalType;
  }
}
