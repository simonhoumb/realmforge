package ntnu.no.idatg2001;

import java.io.IOException;
import ntnu.no.idatg2001.gameinformation.Story;
import ntnu.no.idatg2001.gameinformation.StoryFileReader;

public class Main {

  public static void main(String[] args) {
    StoryFileReader storyReader = new StoryFileReader();
    for (Story story : storyReader.readFile("src/main/resources/story.Paths")) {
      System.out.println(story);
    }
  }
}