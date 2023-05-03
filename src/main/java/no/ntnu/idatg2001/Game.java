package no.ntnu.idatg2001;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;
import no.ntnu.idatg2001.BackEnd.gameinformation.Link;
import no.ntnu.idatg2001.BackEnd.gameinformation.Passage;
import no.ntnu.idatg2001.BackEnd.gameinformation.Story;
import no.ntnu.idatg2001.BackEnd.goals.Goal;

@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;
  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;
  @OneToMany
  private List<Goal> goals;

  protected Game() {

  }

  public Game(Unit unit, Story story, List<Goal> goals) {
    this.unit = unit;
    this.story = story;
    this.goals = goals;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }


  public Unit getPlayer() {
    return unit;
  }

  public void setPlayer(Unit unit) {
    this.unit = unit;
  }

  public Story getStory() {
    return story;
  }

  public void setStory(Story story) {
    this.story = story;
  }

  public List<Goal> getGoals() {
    return goals;
  }

  public void setGoals(List<Goal> goals) {
    this.goals = goals;
  }

  public Passage begin() {
    return story.getOpeningPassage();
  }

  public Passage go(Link link) {
    return story.getPassages().get(link);
  }
}
