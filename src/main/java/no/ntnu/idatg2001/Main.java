package no.ntnu.idatg2001;


import java.util.ArrayList;
import no.ntnu.idatg2001.BackEnd.entityinformation.playerclasses.Ranger;
import no.ntnu.idatg2001.BackEnd.gameinformation.Passage;
import no.ntnu.idatg2001.BackEnd.gameinformation.Story;
import no.ntnu.idatg2001.BackEnd.goals.Goal;
import no.ntnu.idatg2001.BackEnd.goals.ScoreGoal;
import no.ntnu.idatg2001.FrontEnd.View.ApplicationStart;
import no.ntnu.idatg2001.dao.GameDAO;


public class Main {

  public static void main(String[] args) {
    /*
        StoryFileReader storyReader = new StoryFileReader();
    for (Story story : storyReader.readFile("src/main/resources/story.Paths")) {
      System.out.println(story);
    }
     */
    ArrayList<Goal> goals = new ArrayList<>();
    goals.add(new ScoreGoal(1000));
    GameDAO.getInstance().add(new Game(
        new Ranger("Legolas"),
        new Story("myStory", new Passage("openingPassage", "this is the opening passage")),
        goals));

    ApplicationStart.startApplication(args);
  }
}