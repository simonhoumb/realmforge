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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Entity
public class Story {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  private String title;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "story_id")
  private Map<Link, Passage> passages;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "opening_passage_id")
  private Passage openingPassage;

  public void setOpeningPassage(Passage openingPassage) {
    this.openingPassage = openingPassage;
  }

  public Story(String title, Passage openingPassage) {
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
  }

  public Story() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
      getPassages().put(new Link(passage.getTitle(), passage.getTitle()), passage);
      return true;
    }
  }

  public void removePassage(Link link) {
    for (Entry<Link, Passage> entry : passages.entrySet()) {
      if (entry.getValue().getLinks().size() == 1
      && entry.getValue().getLinks().contains(link)) {
        passages.remove(link);
      }
    }
  }

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

  @Override
  public String toString() {
    return "Story{"
        + "title='" + title + '\''
        + ", openingPassage=" + openingPassage
        + ", passages=" + passages +
        + '}';
  }
}
