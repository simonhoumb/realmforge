package no.ntnu.idatg2001.frontend.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.gameinformation.StoryFileReader;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.dao.PassageDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.NewStoryDialog;

/**
 * controller class for the CreateStoryView.
 *
 *@author Eskil Alstad og Simon Husås Houmb.
 *@version 1.0
 */
public class CreateStoryController extends Controller<CreateStoryView> {

  /**
   * Constructor for the CreateStoryController.
   *
   * @param view The CreateStoryView associated with this controller.
   */
  public CreateStoryController(CreateStoryView view) {
    this.view = view;
    init();
  }

  /**
   * Initializes the CreateStoryController.
   */
  private void init() {
    configureTableView();
    populateTableView();
  }

  /**
   * Handles the edit button event.
   */
  public void onEditButton() {
    if (getSelectedItemInTableView() != null) {
      EditStoryView editStoryView = new EditStoryView();
      Scene newScene = view.getScene();
      EditStoryController editStoryController = new EditStoryController(editStoryView);
      editStoryView.setController(editStoryController);
      editStoryController.setSelectedStory(getSelectedItemInTableView());
      newScene.setRoot(editStoryView);
    }
  }

  /**
   * Handles the back to main menu button pressed event.
   *
   * @throws IOException if an I/O error occurs.
   */
  public void onBackToMainMenuButtonPressed() throws IOException {
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = view.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  /**
   * Handles the new story event.
   */
  public void onNewStory() {
    NewStoryDialog newStoryDialog = new NewStoryDialog(this);
    newStoryDialog.initOwner(view.getScene().getWindow());
    newStoryDialog.initStyle(StageStyle.UNDECORATED);
    newStoryDialog.showAndWait();
    populateTableView();
  }

  /**
   * Handles the import button pressed event.
   */
  public void onImportButtonPressed() {
    StoryFileReader storyReader = new StoryFileReader();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Story");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paths Files", "*.Paths"));
    File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
    if (selectedFile != null) {
      try {
        Story story = storyReader.readFile(selectedFile.getPath());
        if (story == null) {
          throw new IllegalArgumentException();
        }
        StoryDAO.getInstance().update(story);
        populateTableView();
        List<Link> brokenLinks = story.getBrokenLinks();
        if (!brokenLinks.isEmpty()) {
          StringBuilder brokenLinksStringBuilder = new StringBuilder();
          brokenLinksStringBuilder.append("The following passages have broken links:");
          brokenLinks.forEach(link -> brokenLinksStringBuilder.append("\n")
              .append(link.getReference()));
          AlertHelper.showWarningAlert(view.getScene().getWindow(), "Broken links",
              brokenLinksStringBuilder.toString());
        }
      } catch (IllegalArgumentException illegalArgumentException) {
        AlertHelper.showErrorAlert(view.getScene().getWindow(), "Error loading file",
            "The file you tried to load is not a valid story file. Make sure format is correct.");
      }
    }
  }


  public void onDeleteButtonPressed() {
    Story story = getSelectedItemInTableView();
    if (story != null) {
      if (GameSaveDAO.getInstance().getAll().stream()
          .map(GameSave::getGame)
          .map(Game::getStory)
          .map(Story::getId)
          .anyMatch(storyId -> storyId.equals(story.getId()))) {
        AlertHelper.showErrorAlert(view.getScene().getWindow(), "Error deleting story",
            "The story you tried to delete is currently in use by a game save. "
                + "Please delete the game save first.");
      } else {
        story.setOpeningPassage(null);
        StoryDAO.getInstance().update(story);
        StoryDAO.getInstance().remove(story);
        populateTableView();
      }
    }
  }


  /**
   * Populates the table view with stories.
   */
  private void populateTableView() {
    view.getStoryTableView().getItems().clear();
    List<Story> storylist = StoryDAO.getInstance().getAll().stream().toList();
    ObservableList<Story> list = FXCollections.observableArrayList(storylist);
    if (!list.isEmpty()) {
      view.getStoryTableView().setItems(list);
    } else {
      //NO STORIES);
    }
  }

  /**
   * Configures the table view.
   */
  public void configureTableView() {
    view.getColumnStoryName().setCellValueFactory(new PropertyValueFactory<>("title"));
    view.getColumnStoryPassageAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int passageAmount = story.getTotalAmountOfPassages();
      return new SimpleIntegerProperty(passageAmount).asObject();
    });
    view.getColumnStoryLinkAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int linkAmount = story.getTotalAmountOfLinks();
          //+ story.getOpeningPassage().getLinks().size();
      return new SimpleIntegerProperty(linkAmount).asObject();
    });
  }

  /**
   * Retrieves the selected item in the table view.
   *
   * @return The selected story.
   */
  public Story getSelectedItemInTableView() {
    return view.getStoryTableView().getSelectionModel().getSelectedItem();
  }
}