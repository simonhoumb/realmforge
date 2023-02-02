package ntnu.no.idatg2001;

import java.util.Collection;
import java.util.Map;

public class Story {
  private String title;
  private Map<Link, Passage> passages;
  private Passage openPassage;

  public Story(String title, Passage openPassage) {
    this.title = title;
    this.openPassage = openPassage;
  }

  public String getTitle() {
    return title;
  }

  public Passage getOpenPassage() {
    return openPassage;
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
