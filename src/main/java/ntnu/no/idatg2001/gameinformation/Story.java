package ntnu.no.idatg2001.gameinformation;

import java.util.HashMap;
import java.util.Map;

public class Story {
  private String title;
  private Map<Link, Passage> passages;
  private Passage openingPassage;

  public Story(String title, Passage openingPassage) {
    this.title = title;
    this.openingPassage = openingPassage;
    this.passages = new HashMap<>();
  }

  public String getTitle() {
    return title;
  }

  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public void addPassage(Passage passage) {
    getPassages().put(new Link(this.title, this.title), passage);
  }

  public Passage getPassage(Link link) {
    return getPassages().get(link);
  }

  public Map<Link, Passage> getPassages() {
    return passages;
  }


}
