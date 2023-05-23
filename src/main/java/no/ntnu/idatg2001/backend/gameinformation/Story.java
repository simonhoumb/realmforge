package no.ntnu.idatg2001.backend.gameinformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatg2001.backend.actions.Action;

/**
 * The Passage class represents a passage in a story.
 */
@Entity
@Table(name = "story")
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "story_id")
  private Map<Link, Passage> passages;
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "opening_passage_id")
  private Passage openingPassage;


  private String title;

  /**
   * Constructor for Story.
   *
   * @param title   The title of the Story.
   * @param openingPassage The content of the passage.
   */
  public Story(String title, Passage openingPassage) {
    this.title = title;
    this.passages = new HashMap<>();
    this.openingPassage = openingPassage;
    this.addPassage(this.openingPassage);
  }

  /**
   * Constructor for Story.
   */
  public Story() {}

  /**
   * getPassage id.
   *
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the passage.
   *
   * @param id The id of the passage.
   */
  public void setId(Long id) {
    this.id = id;
  }

  public Story setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getTitle() {
    return title;
  }


  public void setOpeningPassage(Passage openingPassage) {
    this.openingPassage = openingPassage;
  }

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  /**
   * addPassage adds a passage to the story.
   *
   * @param passage The passage to add.
   * @return true if the passage was added, false if it already exists.
   */
  public boolean addPassage(Passage passage) {
    if (getPassages().containsValue(passage)) {
      return false;
    } else {
      getPassages().put(new Link(passage.getTitle(), passage.getTitle()), passage);
      return true;
    }
  }

  /**
   * Removes a passage from the story.
   *
   * @param link The link to the passage to remove.
   */
  public void removePassage(Link link) {
    Passage passageToRemove = getPassage(link);
    boolean hasOtherReferences = passages.values().stream()
        .filter(passage -> !passage.equals(passageToRemove))
        .flatMap(passage -> passage.getLinks().stream())
        .anyMatch(l -> l.getReference().equals(passageToRemove.getTitle()));
    if (!hasOtherReferences) {
      if (getPassage(link).equals(getOpeningPassage())) {
        setOpeningPassage(null);
      }
      passages.remove(link);
    } else {
      throw new IllegalStateException("Passage has other links referencing it.");
    }
  }


  /**
   * Removes passage and all links that reference it.
   *
   * @param passage The passage to remove.
   */
  public void removePassageAndConnectedLinks(Passage passage) {
    List<Link> linksToRemove = passages.values().stream()
        .flatMap(p -> p.getLinks().stream())
        .filter(l -> l.getReference().equals(passage.getTitle()))
        .toList();
    for (Link link : linksToRemove) {
      passages.remove(link);
    }
    // Remove the passage from the passages map
    if (passage.equals(getOpeningPassage())) {
      setOpeningPassage(null);
    }
    passages.values().remove(passage);
  }

  /**
   * This method checks if the story contains any broken links.
   *
   * @return  if the story contains broken links.
   */
  public List<Link> getBrokenLinks() {
    ArrayList<Link> brokenLinks = new ArrayList<>();
    this.passages.values().stream()
        .flatMap(passage -> passage.getLinks().stream())
        .forEach(link -> {
          if (!passages.containsKey(link)) {
            brokenLinks.add(link);
          }
        });
    return brokenLinks;
  }

  public Passage getPassage(Link link) {
    return getPassages().get(link);
  }

  public Map<Link, Passage> getPassages() {
    return passages;
  }


  /**
   * This method counts the amount of passages in Story.
   *
   * @return the amount of passages in the story as an int.
   */
  public int getTotalAmountOfPassages() {
    List<Passage> passageList = new ArrayList<>();
    for (Passage storyPassage : passages.values()) {
      if (!passageList.contains(storyPassage)) {
        passageList.add(storyPassage);
      }
    }
    if (getOpeningPassage() != null
        && !getPassages().containsValue(getOpeningPassage())) {
      passageList.add(getOpeningPassage());
    }
    return passageList.size();
  }

  /**
   * This method counts the amount of links in passages in Story.
   *
   * @return the amount of links in passages in the story as an int.
   */
  public int getTotalAmountOfLinks() {
    List<String> linkTextList = new ArrayList<>();

    for (Passage passage : passages.values()) {
      passage.getLinks().forEach(link -> {
        if (!linkTextList.contains(link.getText())) {
          linkTextList.add(link.getText());
        }
      });
    }
    return linkTextList.size();
  }

  /**
   * This method toStrings the Passage.
   *
   * @return the passage as a string.
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("Story{"
        + "Title=%s,"
        + "OpeningPassage=%s", title, openingPassage));
    for (Passage passage : passages.values()) {
      stringBuilder.append(String.format("%nPassage{%nTitle=%s", passage.getTitle()));
      for (Link link : passage.getLinks()) {
        stringBuilder.append(String.format("%nLink{%nText=%s%nReference=%s%n",
            link.getText(), link.getReference()));
        for (Action action : link.getActions()) {
          stringBuilder.append(String.format("%nAction{%nText=%s%nType=%s}}%n",
              action.getActionType(), action.getValue()));
        }
      }
    }
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}
