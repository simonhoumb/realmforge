package no.ntnu.idatg2001.backend.gameinformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import no.ntnu.idatg2001.backend.actions.Action;

@Entity
public class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  private String text;
  private String reference;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "link_id")
  private List<Action> actions;

  /**
   * Constructor for Link.
   *
   * @param text      The text that is displayed to the user.
   * @param reference The reference to the passage that the link leads to.
   */
  public Link(String text, String reference) {
    setText(text);
    setReference(reference);
    actions = new ArrayList<>(); //vet ikke om denne skal være her
  }

  public Link() {}

  /**
   * getId returns the id of the link.
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * setId sets the id of the link.
   * @param id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * getText returns the text of the link.
   * @return text in link.
   */
  public String getText() {
    return text;
  }

  /**
   * setText sets the text of the link.
   * @param text
   * @return text in link.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * getReference returns the reference of the link.
   * @return link reference.
   */
  public String getReference() {
    return reference;
  }

  /**
   * setReference sets the reference of the link.
   * @param reference
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * addAction adds an action to the link.
   * @param action
   * @return added action.
   */
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

  public boolean removeAction(Action action) {
    boolean actionRemoved = false;
    try {
      if(action == null) {
        throw new IllegalArgumentException();
      } else {
        getActions().remove(action);
      }
    } catch (Exception exception) {
      actionRemoved = true;
    }
    return actionRemoved;
  }


  /**
   * getActions returns the actions of the link.
   * @return actions in link.
   */
  public List<Action> getActions() {
    return actions;
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
    return Objects.equals(reference, link.reference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }

  /**
   * toString returns the Link as a string.
   * @return the Link as a String
   */
  @Override
  public String toString() {
    return "Link{"
        + "text='" + text + '\''
        + ", reference='" + reference + '\''
        + ", actions=" + actions
        + '}';
  }

}
