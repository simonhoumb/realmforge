package ntnu.no.idatg2001.gameinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoryFileReader{
  public List<Story> readFile(String fileName) {
    Scanner scanner;
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
          Passage passageToAdd;
          String passageTitle = currentLine.replace(":", "");
          String passageContent = reader.readLine();
          String linkText = "";
          String linkReference = "";
          passageToAdd = new Passage(passageTitle, passageContent);
          while ((currentLine = reader.readLine()) != null && !currentLine.trim().isEmpty()) {
            //Kilde: https://stackoverflow.com/questions/16383898/find-words-in-string-surrounded-by-and
            scanner = new Scanner(currentLine);
            for (String s; (s = scanner.findWithinHorizon("(?<=\\[).*?(?=\\])", 0)) != null;) {
               linkText = s;
            }
            for (String s; (s = scanner.findWithinHorizon("(?<=\\().*?(?=\\))", 0)) != null;) {
              linkReference = s;
            }
            Link linkToAdd = new Link(linkText, linkReference);
            passageToAdd.addLink(linkToAdd);
            scanner.close();
          }
          passagesToAdd.add(passageToAdd);
        }
      }
        story = new Story(storyTitle, passagesToAdd.get(0));
      for (Passage passage : passagesToAdd) {
        story.addPassage(passage);
      }
      stories.add(story);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stories;
  }
}
