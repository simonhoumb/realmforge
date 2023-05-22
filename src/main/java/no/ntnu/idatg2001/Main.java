package no.ntnu.idatg2001;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.gameinformation.StoryFileReader;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.ApplicationStart;

public class Main {

  public static void main(String[] args) throws IOException {

    /* Bruk dette for å importere fra fil, (bruk update, ikke add!)
    StoryFileReader storyReader = new StoryFileReader();
    Story story;
    story = storyReader.readFile("src/main/resources/stories/Haunted House.Paths");
    StoryDAO.getInstance().update(story);
     */

    ApplicationStart.startApplication(args);
  }
}