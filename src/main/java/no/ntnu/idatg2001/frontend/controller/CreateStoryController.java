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
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.gameinformation.StoryFileReader;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
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
      } catch (IllegalArgumentException illegalArgumentException) {
        AlertHelper.showErrorAlert(view.getScene().getWindow(), "Error loading file",
            "The file you tried to load is not a valid story file. Make sure format is correct.");
      }
    }
  }

  /**
   * Populates the table view with stories.
   */
  private void populateTableView() {
    view.getStoryTableView().getItems().clear();
    List<Story> storylist = StoryDAO.getInstance().getAll();
    ObservableList<Story> list = FXCollections.observableArrayList(storylist);
    view.getStoryTableView().setItems(list);
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
      int linkAmount = story.getTotalAmountOfPassagesLinks()
          + story.getOpeningPassage().getLinks().size();
      return new SimpleIntegerProperty(linkAmount).asObject();
    });
  }

  /**
   * Retrieves the selected item in the table view.
   *
   * @return The selected story.
   */
  public Story getSelectedItemInTableView() {
    Story selectedStory = view.getStoryTableView().getSelectionModel().getSelectedItem();
    if (selectedStory == null) {
      AlertHelper.showErrorAlert(view.getScene().getWindow(),
          view.getResourceBundle().getString("error"),
          view.getResourceBundle().getString("error_select_story"));
      return null;
    }
    return selectedStory;
  }
}