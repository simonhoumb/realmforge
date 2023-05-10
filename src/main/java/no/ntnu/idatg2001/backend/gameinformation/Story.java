package no.ntnu.idatg2001.backend.gameinformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "opening_passage_id")
  private Passage openingPassage;
  private String title;

  public void setOpeningPassage(Passage openingPassage) {
    this.openingPassage = openingPassage;
  }

  public Story(String title, Passage openingPassage) {
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
  }
  public Story(String title) {
    this.title = title;
  }

  public Story() {}

  public Long getId() {
    return id;
  }

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

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public boolean addPassage(Passage passage) {
    if (getPassages().containsValue(passage)) {
      return false;
    } else {
      getPassages().put(new Link(this.title, this.title), passage);
      return true;
    }
  }

  //TODO må sjekke om detter rett.
  public void removePassage(Link link) {
    for (Entry<Link, Passage> entry : passages.entrySet()) {
      if (entry.getValue().getLinks().size() == 1
      && entry.getValue().getLinks().contains(link)) {
        passages.remove(link);
      }
    }
  }

  //TODO må sjekke om detter rett.
  public List<Link> getBrokenLinks() {
    ArrayList<Link> brokenLinks = new ArrayList<>();
    for (Entry<Link, Passage> entry : passages.entrySet()) {
      if (!entry.getKey().getReference().equals(entry.getValue().getTitle())) {
        brokenLinks.add(entry.getKey());
      }
    }
    return brokenLinks;
  }

  public Passage getPassage(Link link) {
    return getPassages().get(link);
  }

  public Map<Link, Passage> getPassages() {
    return passages;
  }

  /**
   * This method returns a string representation of the Story object.
   * @return a string representation of the Story object.
   */
  @Override
  public String toString() {
    return "Story{"
        + "title='" + title + '\''
        + ", openingPassage=" + openingPassage
        + ", passages=" + passages +
        + '}';
  }

  /**
   * This method counts the amount of passages in Story.
   * @return the amount of passages in the story as a int.
   */
  public int getTotalAmountOfPassages() {
    List<Passage> passageList = new ArrayList<>();
    for (Passage storyPassage: passages.values()) {
      if (!passageList.contains(storyPassage)) {
        passageList.add(storyPassage);
      }
    }
    if (getOpeningPassage() != null) {
      passageList.add(getOpeningPassage());
    }
    return passageList.size();
  }

  /**
   * This method counts the amount of links in passages in Story.
   * @return the amount of links in passages in the story as an int.
   */
  public int getTotalAmountOfPassagesLinks() {
    List<Link> linkList = new ArrayList<>();

    for (Passage passage : passages.values()) {
      passage.getLinks().forEach(link -> {
        if (!linkList.contains(link)) {
          linkList.add(link);
        }
      });
    }
    return linkList.size();
  }
}
