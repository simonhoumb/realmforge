  package no.ntnu.idatg2001.backend.gameinformation;

  import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
  import jakarta.persistence.Column;
  import jakarta.persistence.Entity;
  import jakarta.persistence.GeneratedValue;
  import jakarta.persistence.GenerationType;
  import jakarta.persistence.Id;
  import jakarta.persistence.JoinColumn;
  import jakarta.persistence.OneToMany;
  import jakarta.persistence.Table;
  import java.util.ArrayList;
  import java.util.List;
  import java.util.Objects;

  @Entity
  @Table
  public class Passage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String content;
    @OneToMany
    @JoinColumn(name = "passage_id")
    private List<Link> links;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Constructor for Passage.
     *
     * @param title   The title of the passage.
     * @param content The content of the passage.
     */
    public Passage(String title, String content) {
      this.title = setTitle(title);
      this.content = setContent(content);
      this.links = new ArrayList<>();
    }

    public Passage() {}

    /**
     * getTitle returns the title of the passage.
     *
     * @return title
     */
    public String getTitle() {
      return title;
    }

    /**
     * setTitle sets the title of the passage.
     *
     * @param title
     * @return the title of the passage.
     */
    public String setTitle(String title) {
      return this.title = title;
    }

    /**
     * getContent returns the content of the passage.
     * @return content in passage.
     */
    public String getContent() {
      return content;
    }

    public String setContent(String content) {
      return this.content = content;
    }

    /**
     * addLink to a passage.
     * @param link
     * @return  the link linkAdded.
     * @throws IllegalArgumentException
     */
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

    /**
     * removeLink removes a link from a passage.
     * @param link
     * @return true if link is removed, false if not.
     */
    public boolean removeLink(Link link) {
      boolean linkRemoved = false;
      try {
        if(link == null) {
          throw new IllegalArgumentException();
        } else {
          getLinks().remove(link);
        }
      } catch (Exception exception) {
        linkRemoved = true;
      }
      return linkRemoved;
    }


    /**
     * getLinks returns the links in a passage.
     * @return links
     */
    public List<Link> getLinks() {
      return links;
    }

    /**
     * hasLinks checks if a passage has links.
     * @return true if passage has links, false if not.
     */
    public boolean hasLinks() {
      return (!this.links.isEmpty());
    }

    /**
     * checks if a passage has a link that is equals to the link parameter.
     * @param o the object to check if is equal.
     * @return true if passage has link, false if not.
     */
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

    /**
     * hashCode returns the hashcode of the passage.
     * @return hashcode
     */
    @Override
    public int hashCode() {
      return Objects.hash(title, content, links);
    }

    /**
     * toString returns a string representation of the passage.
     * @return string representation of passage.
     */
    @Override
    public String toString() {
      return "Passage{"
          + "title='" + title + '\''
          + ", content='" + content + '\''
          + ", links=" + links
          + '}';
    }
  }
