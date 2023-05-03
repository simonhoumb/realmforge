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

@Entity
public class Passage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  private String title;
  private String content;
  @OneToMany
  private List<Link> links;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Passage(String title, String content) {
    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  public Passage() {}

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public boolean addLink(Link link) throws IllegalArgumentException{
    boolean linkAdded = true;
    try {
      if(link == null) {
        throw new IllegalArgumentException();
      } else {
        getLinks().add(link);
      }
    } catch (Exception exception) {
      linkAdded = false;
    }
    return linkAdded;
  }

  public List<Link> getLinks() {
    return links;
  }

  public boolean hasLinks() {
    return (!this.links.isEmpty());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Passage passage = (Passage) o;
    return title.equals(passage.title) && content.equals(passage.content) && links.equals(
        passage.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, links);
  }

  @Override
  public String toString() {
    return "Passage{"
        + "title='" + title + '\''
        + ", content='" + content + '\''
        + ", links=" + links
        + '}';
  }
}
