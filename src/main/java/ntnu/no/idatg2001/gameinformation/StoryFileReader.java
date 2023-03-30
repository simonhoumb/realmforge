package ntnu.no.idatg2001.gameinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StoryFileReader{
  public List<Story> readFile(String fileName) {
    List<Story> stories = new ArrayList<>();
    Story story;
    String storyTitle = "";
    List<Passage> passagesToAdd = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
      String currentLine;
      while ((currentLine = reader.readLine()) != null) {
        if (storyTitle.equals("")) {
          storyTitle = currentLine.trim();
        }
        if (currentLine.startsWith("::")) {
          String passageTitle = currentLine.replace(":", "");
          String passageContent = reader.readLine();
          passagesToAdd.add(new Passage(passageTitle, passageContent));
        }
      }
        story = new Story(storyTitle, passagesToAdd.get(0));
      for (Passage passage : passagesToAdd) {
        story.addPassage(passage);
        stories.add(story);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stories;
  }
}
