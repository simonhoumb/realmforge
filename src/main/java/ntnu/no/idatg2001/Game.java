package ntnu.no.idatg2001;

import java.util.List;
import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.gameinformation.Link;
import ntnu.no.idatg2001.gameinformation.Passage;
import ntnu.no.idatg2001.gameinformation.Story;
import ntnu.no.idatg2001.goals.Goal;


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
