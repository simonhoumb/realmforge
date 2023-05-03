package no.ntnu.idatg2001.BackEnd.goals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

@Entity
public abstract class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public abstract boolean isFulfilled(Unit unit);
}
