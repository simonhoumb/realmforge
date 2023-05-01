package no.ntnu.idatg2001;

import java.util.List;
import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;
import no.ntnu.idatg2001.BackEnd.gameinformation.Link;
import no.ntnu.idatg2001.BackEnd.gameinformation.Passage;
import no.ntnu.idatg2001.BackEnd.gameinformation.Story;
import no.ntnu.idatg2001.BackEnd.goals.Goal;


public class Game {
  private Entity entity;
  private Story story;
  private List<Goal> goals;

  public Game(Entity entity, Story story, List<Goal> goals) {
    this.entity = entity;
    this.story = story;
    this.goals = goals;
  }

  public Entity getPlayer() {
    return entity;
  }

  public void setPlayer(Entity entity) {
    this.entity = entity;
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
