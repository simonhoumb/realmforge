package ntnu.no.idatg2001;

import java.io.IOException;
import ntnu.no.idatg2001.gameinformation.StoryFileReader;

public class Main {

  public static void main(String[] args) throws IOException {
    StoryFileReader storyReader = new StoryFileReader();
    storyReader.readFile("src/main/resources/story.Paths");
  }
}