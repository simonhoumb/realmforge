package ntnu.no.idatg2001;

import java.io.IOException;
import ntnu.no.idatg2001.gameinformation.Link;
import ntnu.no.idatg2001.gameinformation.Passage;
import ntnu.no.idatg2001.gameinformation.Story;
import ntnu.no.idatg2001.gameinformation.StoryFileReader;

public class Main {

  public static void main(String[] args) {
    /*
        StoryFileReader storyReader = new StoryFileReader();
    for (Story story : storyReader.readFile("src/main/resources/story.Paths")) {
      System.out.println(story);
      System.out.println("----");
      for (Passage passage : story.getPassages().values()) {
        System.out.println(passage);
        System.out.println("----");
        for (Link link : passage.getLinks()) {
          System.out.println(link);
          System.out.println("----");
        }
      }
    }
     */

    ApplicationStart applicationStart = new ApplicationStart();
    applicationStart.startApplication(args);
  }
}