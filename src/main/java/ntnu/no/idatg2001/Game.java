package ntnu.no.idatg2001;

import java.util.List;
import ntnu.no.idatg2001.gameinformation.Link;
import ntnu.no.idatg2001.gameinformation.Passage;
import ntnu.no.idatg2001.gameinformation.Story;
import ntnu.no.idatg2001.goals.Goal;
import ntnu.no.idatg2001.playerinformation.Player;

public class Game {
  private Player player;
  private Story story;
  private List<Goal> goals;

  public Game(Player player, Story story, List<Goal> goals) {
    this.player = player;
    this.story = story;
    this.goals = goals;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
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
