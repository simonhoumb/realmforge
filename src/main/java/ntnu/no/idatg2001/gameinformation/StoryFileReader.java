package ntnu.no.idatg2001.gameinformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class StoryFileReader {
  public StoryFileReader() {

  }
  //Bare å slette dette når du har immplimentert den andre FileReaderen om den fungerer
  //som den skal.
  public ArrayList<Story> storyArrayList(String filename) throws IOException {
    ArrayList<Story> story = new ArrayList<>();
    BufferedReader reader = Files.newBufferedReader(Path.of(filename));
    String line;
    while ((line = reader.readLine()) != null ) {
      System.out.println(line);
    }

    return storyArrayList(filename);
  }

}
