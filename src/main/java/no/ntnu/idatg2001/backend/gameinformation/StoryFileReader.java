package no.ntnu.idatg2001.backend.gameinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
          Passage passageToAdd;
          String passageTitle = currentLine.replace(":", "");
          String passageContent = reader.readLine();
          passageToAdd = new Passage(passageTitle, passageContent);
          while ((currentLine = reader.readLine()) != null && !currentLine.trim().isEmpty()) {
            Link linkToAdd = new Link(findLinkText(currentLine), findLinkReference(currentLine));
            passageToAdd.addLink(linkToAdd);
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

  //Kilde: https://stackoverflow.com/questions/16383898/find-words-in-string-surrounded-by-and
  private String findLinkText(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkText = "";
    for (String s; (s = scanner.findWithinHorizon("(?<=\\[).*?(?=\\])", 0)) != null;) {
      linkText = s;
    }
    scanner.close();
    return linkText;
  }

  //Kilde: https://stackoverflow.com/questions/16383898/find-words-in-string-surrounded-by-and
  private String findLinkReference(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkReference = "";
    for (String s; (s = scanner.findWithinHorizon("(?<=\\().*?(?=\\))", 0)) != null;) {
      linkReference = s;
    }
    scanner.close();
    return linkReference;
  }
}
