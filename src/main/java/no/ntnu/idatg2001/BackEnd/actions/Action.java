package no.ntnu.idatg2001.BackEnd.actions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Action {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public abstract void execute(Unit unit);
}
