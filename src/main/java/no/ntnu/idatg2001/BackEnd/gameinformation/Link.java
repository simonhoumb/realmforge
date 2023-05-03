package no.ntnu.idatg2001.BackEnd.gameinformation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import no.ntnu.idatg2001.BackEnd.actions.Action;

@Entity
public class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  private String text;
  private String reference;
  @OneToMany
  private List<Action> actions;

  public Link(String text, String reference) {
    this.text = text;
    this.reference = reference;
    actions = new ArrayList<>(); //vet ikke om denne skal være her
  }

  public Link() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getText() {
    return text;
  }

  public String getReference() {
    return reference;
  }

  public Boolean addAction(Action action) {
    boolean actionAdded = true;
    try {
      if(action == null) {
        throw new IllegalArgumentException();
      } else {
        getActions().add(action);
      }
    } catch (Exception exception) {
      actionAdded = false;
    }
    return actionAdded;
  }

  public List<Action> getActions() {
    return actions;
  }

  @Override
  public String toString() {
    return "Link{"
        + "text='" + text + '\''
        + ", reference='" + reference + '\''
        + ", actions=" + actions
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return text.equals(link.text) && reference.equals(link.reference) && actions.equals(
        link.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, reference, actions);
  }
}
