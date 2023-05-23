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

/**
 * The StoryFileReader class reads a story file and creates a Story object.
 */
public class StoryFileReader {

  private final CheckIfValid checkIfValid = new CheckIfValid();

  private String currentLine;

  /**
   * Constructor for StoryFileReader.
   */
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

  /**
   * Create a new Passage from the file.
   *
   * @param scanner The scanner to read the file.
   * @return a new Passage.
   */
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

  /**
   * Create a new Link.
   *
   * @param linkToAdd The link to add.
   * @param linksToAdd The list of links to add to.
   */
  private void addLinkToList(Link linkToAdd, ArrayList<Link> linksToAdd) {
    if (linkToAdd != null) {
      linksToAdd.add(linkToAdd);
    }
  }

  /**
   * Create a new Link.
   *
   * @param linkToAdd The link to add.
   * @param currentLine The current line to read.
   */
  private void addActionToLink(Link linkToAdd, String currentLine) {
    Action actionToAdd = createNewAction(findActionTypeFromLine(currentLine),
        findActionValueFromLine(currentLine));
    if (linkToAdd != null && actionToAdd != null) {
      linkToAdd.addAction(actionToAdd);
    }
  }

  /**
   * Append the current line to the passage content.
   *
   * @param passageContent The passage content to append to.
   * @param currentLine The current line to append.
   */
  private void appendToPassageContent(StringBuilder passageContent, String currentLine) {
    if (passageContent.length() > 0) {
      passageContent.append("\n");
    }
    passageContent.append(currentLine);
  }

  /**
   * Adds Links to the Passage.
   *
   * @param linksToAdd The links to add.
   * @param passageToAdd The passage to add the links to.
   */
  private void addLinksToPassage(ArrayList<Link> linksToAdd, Passage passageToAdd) {
    for (Link link : linksToAdd) {
      passageToAdd.addLink(link);
    }
  }


  /**
   * Finds link text from the current line.
   *
   * @param currentLine The current line to read.
   * @return the link text.
   */
  private String findLinkTextFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkText = "";
    for (String s; (s = scanner.findInLine("(?<=\\[).*?(?=\\])")) != null;) {
      linkText = s;
    }
    scanner.close();
    return linkText.trim();
  }


  /**
   * Finds link reference from the current line.
   *
   * @param currentLine The current line to read.
   * @return the link reference.
   */
  private String findLinkReferenceFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String linkReference = "";
    for (String s; (s = scanner.findInLine("(?<=\\().*?(?=\\))")) != null;) {
      linkReference = s;
    }
    scanner.close();
    return linkReference.trim();
  }

  /**
   * Finds action type from the current line.
   *
   * @param currentLine The current line to read.
   * @return the action type.
   */
  private String findActionTypeFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String actionType = "";
    for (String s; (s = scanner.findInLine("(?<=\\{).*?(?=\\})")) != null;) {
      actionType = s;
    }
    scanner.close();
    return actionType.trim();
  }

  /**
   * Finds action value from the current line.
   *
   * @param currentLine The current line to read.
   * @return the action value.
   */
  private String findActionValueFromLine(String currentLine) {
    Scanner scanner = new Scanner(currentLine);
    String actionValue = "";
    for (String s; (s = scanner.findInLine("(?<=<).*?(?=>)")) != null;) {
      actionValue = s;
    }
    scanner.close();
    return actionValue.trim();
  }

  /**
   * Create a new Link.
   *
   * @param linkText The link text.
   * @param linkReference The link reference.
   * @return a new Link.
   */
  private Link createNewLink(String linkText, String linkReference) {
    if (linkText.isBlank() || linkReference.isBlank()) {
      return null;
    } else {
      return new Link(linkText, linkReference);
    }
  }

  /**
   * Create a new Action.
   *
   * @param actionType The action type.
   * @param actionValue The action value.
   * @return a new Action.
   */
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
      default -> {
        return null;
      }
    }

    return null;
  }

  /**
   * Create a new Action.
   *
   * @param type The action type.
   * @param value The action value.
   * @return a new Action.
   */
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

  /**
   * Create a new Action.
   *
   * @param type The action type.
   * @param value The action value.
   * @return a new Action.
   */
  private Action createActionByType(ActionType type, String value) {
    return switch (type) {
      case WEAPON -> new WeaponAction(value);
      case ITEM -> new ItemAction(value);
      case PASSAGE -> new PassageAction(value);
      default -> null;
    };
  }
}
