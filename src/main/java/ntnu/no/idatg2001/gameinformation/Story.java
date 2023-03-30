package ntnu.no.idatg2001.gameinformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

  public boolean addPassage(Passage passage) {
    if (getPassages().containsValue(passage)) {
      return false;
    } else {
      getPassages().put(new Link(this.title, this.title), passage);
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


}
