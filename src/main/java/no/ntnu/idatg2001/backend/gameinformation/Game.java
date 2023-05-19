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
import jakarta.persistence.Table;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.goals.Goal;

@Entity
@Table(name = "game")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "unit_id")
  private Unit unit;
  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "game_id")
  private List<Goal> goals;

  public Game() {

  }

  /**
   * Constructor for Game.
   *
   * @param unit  The unit that is playing the game.
   * @param story The story that the unit is playing.
   * @param goals The goals that the unit has to complete.
   */
  public Game(Unit unit, Story story, List<Goal> goals) {
    this.unit = unit;
    this.story = story;
    this.goals = goals;
  }

  /**
   * getId returns the id of the game.
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * setId sets the id of the game.
   * @param id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * getUnit returns the unit that is playing the game.
   * @return unit
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * setUnit sets the unit that is playing the game.
   * @param unit
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  /**
   * getStory returns the story that the unit is playing.
   * @return story
   */
  public Story getStory() {
    return story;
  }

  /**
   * setStory sets the story that the unit is playing.
   * @param story
   */
  public void setStory(Story story) {
    this.story = story;
  }

  /**
   * getGoals returns the goals that the unit has to complete.
   * @return goals
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * setGoals sets the goals that the unit has to complete.
   * @param goals
   */
  public void setGoals(List<Goal> goals) {
    this.goals = goals;
  }

  /**
   * gets the opening passage of the story.
   * @return opening passage
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * goes to the passage you get with link.
   * @return story.getPassages().get(link)
   */
  public Passage go(Link link) {
    if (story.getOpeningPassage().getTitle().equals(link.getReference())) {
      return story.getOpeningPassage();
    } else {
      return story.getPassages().get(link);
    }
  }
}
