package no.ntnu.idatg2001.backend.gameinformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.ArmorAction;
import no.ntnu.idatg2001.backend.actions.DamageAction;
import no.ntnu.idatg2001.backend.actions.GameOverAction;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import no.ntnu.idatg2001.backend.actions.HealthAction;
import no.ntnu.idatg2001.backend.actions.ItemAction;
import no.ntnu.idatg2001.backend.actions.LoseAction;
import no.ntnu.idatg2001.backend.actions.NoneAction;
import no.ntnu.idatg2001.backend.actions.PassageAction;
import no.ntnu.idatg2001.backend.actions.ScoreAction;
import no.ntnu.idatg2001.backend.actions.WeaponAction;
import no.ntnu.idatg2001.backend.actions.WinAction;
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
      if (storyTitle == null || openingPassage == null) {
        return null;
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
        addLinkToList(linkToAdd, linksToAdd);
      } else if (currentLine.startsWith("{")) {
        addActionToLink(linkToAdd, currentLine);
      } else {
        appendToPassageContent(passageContent, currentLine);
      }
    }

    passageToAdd = new Passage(passageTitle, passageContent);
    addLinksToPassage(linksToAdd, passageToAdd);

    return passageToAdd;
  }

  private void addLinkToList(Link linkToAdd, ArrayList<Link> linksToAdd) {
    if (linkToAdd != null) {
      linksToAdd.add(linkToAdd);
    }
  }

  private void addActionToLink(Link linkToAdd, String currentLine) {
    Action actionToAdd = createNewAction(findActionTypeFromLine(currentLine),
        findActionValueFromLine(currentLine));
    if (linkToAdd != null && actionToAdd != null) {
      linkToAdd.addAction(actionToAdd);
    }
  }

  private void appendToPassageContent(StringBuilder passageContent, String currentLine) {
    if (passageContent.length() > 0) {
      passageContent.append("\n");
    }
    passageContent.append(currentLine);
  }

  private void addLinksToPassage(ArrayList<Link> linksToAdd, Passage passageToAdd) {
    for (Link link : linksToAdd) {
      passageToAdd.addLink(link);
    }
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
    if (type == null) {
      return null;
    }

    switch (type) {
      case GOLD, HEALTH, DAMAGE, ARMOR, SCORE -> {
        if (checkIfValid.isInteger(actionValue)) {
          int value = Integer.parseInt(actionValue);
          return createActionByType(type, value);
        }
      }
      case WEAPON, ITEM, PASSAGE -> {
        return createActionByType(type, actionValue);
      }
      case GAME_OVER -> {
        return new GameOverAction();
      }
      case WIN -> {
        return new WinAction();
      }
      case LOSE -> {
        return new LoseAction();
      }
      case NONE -> {
        return new NoneAction();
      }
    }

    return null;
  }

  private Action createActionByType(ActionType type, int value) {
    return switch (type) {
      case GOLD -> new GoldAction(value);
      case HEALTH -> new HealthAction(value);
      case DAMAGE -> new DamageAction(value);
      case ARMOR -> new ArmorAction(value);
      case SCORE -> new ScoreAction(value);
      default -> null;
    };
  }

  private Action createActionByType(ActionType type, String value) {
    return switch (type) {
      case WEAPON -> new WeaponAction(value);
      case ITEM -> new ItemAction(value);
      case PASSAGE -> new PassageAction(value);
      default -> null;
    };
  }
}
