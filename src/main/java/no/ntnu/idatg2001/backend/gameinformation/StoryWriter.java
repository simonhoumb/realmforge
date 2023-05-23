package no.ntnu.idatg2001.backend.gameinformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import no.ntnu.idatg2001.backend.actions.Action;

/**
 * The StoryWriter class writes a story to a file.
 */
public class StoryWriter {

  /**
   * Constructor for StoryWriter.
   */
  private StoryWriter() {

  }

  /**
   * This method writes a story to a file.
   *
   * @param selectedStory the story to write.
   * @param file the file to write to.
   */
  public static void writeStoryToFile(Story selectedStory, File file) {
    try (PrintWriter writer = new PrintWriter(file)) {
      // Write the story title
      writer.println(selectedStory.getTitle());
      writer.println();

      // Write the opening passage
      Passage openingPassage = selectedStory.getOpeningPassage();
      writer.println("::" + openingPassage.getTitle());
      writer.println(openingPassage.getContent());

      // Write the opening passage links
      writeLinks(writer, openingPassage.getLinks());

      writer.println();

      selectedStory.getPassages().values().stream().filter(p -> !p.equals(openingPassage))
          .forEach(p -> {
            writer.println("::" + p.getTitle());
            writer.println(p.getContent());

            // Write the passage links
            writeLinks(writer, p.getLinks());

            writer.println();
          });

      // Check for broken links
      List<Link> brokenLinks = selectedStory.getBrokenLinks();
      if (!brokenLinks.isEmpty()) {
        writer.println("Broken Links:");
        for (Link link : brokenLinks) {
          writer.println(link.getText());
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method writes a Links to a file.
   *
   * @param writer the unit to write to file.
   * @param links the links to write.
   */
  private static void writeLinks(PrintWriter writer, List<Link> links) {
    for (Link link : links) {
      writer.println("[" + link.getText() + "](" + link.getReference() + ")");
      List<Action> actions = link.getActions();
      if (!actions.isEmpty()) {
        writer.println(getActionString(actions));
      }
    }
  }

  /**
   * This meth to get Actions and write them to file.
   *
   * @param actionTypes the action types to write.
   * @return the action string.
   */
  private static String getActionString(List<Action> actionTypes) {
    if (actionTypes == null || actionTypes.isEmpty()) {
      return "";
    } else {
      StringBuilder sb = new StringBuilder();
      for (Action actionType : actionTypes) {
        sb.append("{").append(actionType.getActionType()).append("}").append("<")
            .append(actionType.getValue()).append(">\n");
      }
      return sb.toString().trim();
    }
  }
}


