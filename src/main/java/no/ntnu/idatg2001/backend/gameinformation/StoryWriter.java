package no.ntnu.idatg2001.backend.gameinformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

public class StoryWriter {
  public static void writeStoryToFile(Story selectedStory, File file) {
    try (PrintWriter writer = new PrintWriter(file )) {
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

      // Write the remaining passages and their links
      for (Passage passage : selectedStory.getPassages().values()) {
        writer.println("::" + passage.getTitle());
        writer.println(passage.getContent());

        // Write the passage links
        writeLinks(writer, passage.getLinks());

        writer.println();
      }

      // Check for broken links
      List<Link> brokenLinks = selectedStory.getBrokenLinks();
      if (!brokenLinks.isEmpty()) {
        writer.println("Broken Links:");
        for (Link link : brokenLinks) {
          writer.println(link.getText());
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error writing the story to file: " + e.getMessage());
    }
  }

  private static void writeLinks(PrintWriter writer, List<Link> links) {
    for (Link link : links) {
      writer.println("[" + link.getText() + "](" + link.getReference() + ")");
      List<Action> actions = link.getActions();
      if (!actions.isEmpty()) {
        writer.println(getActionString(actions));
      }
    }
  }

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


