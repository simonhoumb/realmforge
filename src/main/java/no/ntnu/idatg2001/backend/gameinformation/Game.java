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
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.goals.Goal;

/**
 * The Game class represents a game.
 */
@Entity
@Table(name = "game")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "unit_id")
  private Unit unit;
  @ManyToOne
  private Story story;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "game_id")
  private List<Goal> goals;

  /**
   * Constructor for Game.
   */
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
   *
   * @return id
   */
  public Long getId() {
    return id;
  }

  /**
   * setId sets the id of the game.
   *
   * @param id the id of the game.
   */
  public void setId(Long id) {
    this.id = id;
  }

  public Unit getUnit() {
    return unit;
  }

  /**
   * setUnit sets the unit that is playing the game.
   *
   * @param unit the unit that is playing the game.
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
  }


  /**
   * getStory returns the story that the unit is playing.
   *
   * @return story the story that the unit is playing.
   */
  public Story getStory() {
    return story;
  }

  /**
   * setStory sets the story that the unit is playing.
   *
   * @param story the story that the unit is playing.
   */
  public void setStory(Story story) {
    this.story = story;
  }

  /**
   * getGoals returns the goals that the unit has to complete.
   *
   * @return goals
   */
  public List<Goal> getGoals() {
    return goals;
  }

  /**
   * setGoals sets the goals that the unit has to complete.
   *
   * @param goals the goals that the unit has to complete.
   */
  public void setGoals(List<Goal> goals) {
    this.goals = goals;
  }

  /**
   * gets the opening passage of the story.
   *
   * @return opening passage
   */
  public Passage begin() {
    return story.getOpeningPassage();
  }

  /**
   * goes to the passage you get with link.
   *
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
