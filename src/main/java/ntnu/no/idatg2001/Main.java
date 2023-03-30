package ntnu.no.idatg2001;

import java.io.IOException;
import java.util.logging.FileHandler;
import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Ranger;
import ntnu.no.idatg2001.gameinformation.StoryFileReader;

public class Main {

  public static void main(String[] args) throws IOException {;
    StoryFileReader storyFileReader = new StoryFileReader();
    storyFileReader.storyArrayList("/test.Paths");
  }
}