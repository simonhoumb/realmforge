package no.ntnu.idatg2001.BackEnd.gameinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import no.ntnu.idatg2001.BackEnd.actions.Action;

public class Link {
  private String text;
  private String reference;
  private List<Action> actions;

  public Link(String text, String reference) {
    this.text = text;
    this.reference = reference;
    actions = new ArrayList<>(); //vet ikke om denne skal være her
  }

  public String getText() {
    return text;
  }

  public String getReference() {
    return reference;
  }

  public void addAction(Action action) {
    actions.add(action);
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
