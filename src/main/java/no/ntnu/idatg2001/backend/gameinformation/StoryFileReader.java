package no.ntnu.idatg2001.backend.gameinformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import no.ntnu.idatg2001.backend.actions.HealthAction;
import no.ntnu.idatg2001.backend.actions.InventoryAction;
import no.ntnu.idatg2001.backend.actions.ScoreAction;
import no.ntnu.idatg2001.backend.utility.CheckIfValid;

public class StoryFileReader{

  private final CheckIfValid checkIfValid = new CheckIfValid();

  private String currentLine;

  public Story readFile(String fileName) {
    Story story = null;
    String storyTitle = null;
    Passage openingPassage = null;
    ArrayList<Passage> passagesToAdd = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNext()) {
        currentLine = scanner.nextLine();
        if (storyTitle == null) {
          storyTitle = currentLine.trim();
        }
        if (currentLine.startsWith("::")) {
          if (openingPassage == null) {
            openingPassage = createPassage(scanner);
          } else {
            passagesToAdd.add(createPassage(scanner));
          }
        }
      }
      story = new Story(storyTitle, openingPassage);
      for (Passage passage : passagesToAdd) {
        story.addPassage(passage);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return story;
  }

  private Passage createPassage(Scanner scanner) {
    String passageTitle = currentLine.replace(":", "");
    Passage passageToAdd;
    StringBuilder passageContent = new StringBuilder();
    ArrayList<Link> linksToAdd = new ArrayList<>();
    Link linkToAdd = null;
    while (scanner.hasNext() && !(currentLine = scanner.nextLine()).isBlank()) {
      if (currentLine.startsWith("[")) {
        linkToAdd = createNewLink(findLinkTextFromLine(currentLine),
            findLinkReferenceFromLine(currentLine));
        if (linkToAdd != null) {
          linksToAdd.add(linkToAdd);
        }
      } else if (currentLine.startsWith("{")) {
        Action actionToAdd = createNewAction(findActionTypeFromLine(currentLine),
            findActionValueFromLine(currentLine));
        if (linkToAdd != null && actionToAdd != null) {
          linkToAdd.addAction(actionToAdd);
        }
      } else {
        if (!passageContent.isEmpty()) {
          passageContent.append("\n");
        }
        passageContent.append(currentLine);
      }
    }
    passageToAdd = new Passage(passageTitle, passageContent);
    for (Link link : linksToAdd) {
      passageToAdd.addLink(link);
    }
    return passageToAdd;
  }

  //Kilde: https://stackoverflow.com/questions/16383898/find-words-in-string-surrounded-by-and
  private String findLinkTextFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkText = "";
    for (String s; (s = scanner.findInLine("(?<=\\[).*?(?=\\])")) != null;) {
      linkText = s;
    }
    scanner.close();
    return linkText.trim();
  }


  //Kilde: https://stackoverflow.com/questions/16383898/find-words-in-string-surrounded-by-and
  private String findLinkReferenceFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkReference = "";
    for (String s; (s = scanner.findInLine("(?<=\\().*?(?=\\))")) != null;) {
      linkReference = s;
    }
    scanner.close();
    return linkReference.trim();
  }

  private String findActionTypeFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String actionType = "";
    for (String s; (s = scanner.findInLine("(?<=\\{).*?(?=\\})")) != null;) {
      actionType = s;
    }
    scanner.close();
    return actionType.trim();
  }

  private String findActionValueFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String actionValue = "";
    for (String s; (s = scanner.findInLine("(?<=<).*?(?=>)")) != null;) {
      actionValue = s;
    }
    scanner.close();
    return actionValue.trim();
  }

  private Link createNewLink(String linkText, String linkReference) {
    if (linkText.isBlank() || linkReference.isBlank()) {
      return null;
    } else {
      return new Link(linkText, linkReference);
    }
  }

  private Action createNewAction(String actionType, String actionValue) {
    ActionType type = ActionType.valueOfLabel(actionType);
    if (type == ActionType.HEALTH && checkIfValid.isInteger(actionValue)) {
      return new HealthAction(Integer.parseInt(actionValue));
    } else if (type == ActionType.GOLD && checkIfValid.isInteger(actionValue)) {
      return new GoldAction(Integer.parseInt(actionValue));
    } else if (type == ActionType.ITEM) { //TODO: legg til check for item om trengs
      return new InventoryAction(actionValue);
    } else if (type == ActionType.SCORE && checkIfValid.isInteger(actionValue)) {
      return new ScoreAction(Integer.parseInt(actionValue));
    }
    return null;
  }
}
