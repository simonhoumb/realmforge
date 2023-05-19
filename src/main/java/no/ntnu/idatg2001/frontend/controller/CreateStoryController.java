package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.dialogs.AddPassageDialog;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.NewStoryDialog;

public class CreateStoryController extends Controller<CreateStoryView> {
  private AddPassageDialog addPassageDialog;
  private NewStoryDialog newStoryDialog;

  public CreateStoryController(CreateStoryView view) {
    this.view = view;
    init();
  }

  private void init() {
    configureTableView();
    populateTableView();
  }

  /*
  public void saveStoryTest(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Story");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.Paths"));
    File file = fileChooser.showSaveDialog(createStoryView.getScene().getWindow());
    if (file != null) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        // Iterate through the entries of the HashMap and save the content of each text area
        for (Map.Entry<String, TextArea> entry : createStoryView.getRoomTextAreas().entrySet()) {
          String roomName = entry.getKey();
          TextArea textArea = entry.getValue();
          String text = textArea.getText();
          // Write the room name and the content of the text area to the file
          writer.write("::" + roomName + "\n");
          writer.write(text);
          writer.write("\n\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

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

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = view.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public void onNewStory() {
    // Create a new dialog that opens the new story dialog, this story will be
    // saved in the StoryDao.
    newStoryDialog = new NewStoryDialog(this);
    newStoryDialog.initOwner(view.getScene().getWindow());
    newStoryDialog.initStyle(StageStyle.UNDECORATED);
    newStoryDialog.showAndWait();
    populateTableView();
  }

  public void onLoad() {
    //TODO: Implement functionality for loading a previously saved story
  }

  private void populateTableView() {
    view.getStoryTableView().getItems().clear();
    List<Story> storylist = StoryDAO.getInstance().getAll();
    ObservableList<Story> list = FXCollections.observableArrayList(storylist);
    view.getStoryTableView().setItems(list);
  }
  public void configureTableView() {
    view.getColumnStoryName().setCellValueFactory(new PropertyValueFactory<>("title"));
    view.getColumnStoryPassageAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int passageAmount = story.getTotalAmountOfPassages();
      return new SimpleIntegerProperty(passageAmount).asObject();
    });
    view.getColumnStoryLinkAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int linkAmount = story.getTotalAmountOfPassagesLinks()  +
          story.getOpeningPassage().getLinks().size();
      return new SimpleIntegerProperty(linkAmount).asObject();
    });
  }

  public Story getSelectedItemInTableView() {
    // Get the selected item from the table view
     Story selectedStory = view.getStoryTableView().getSelectionModel().getSelectedItem();
// If no item is selected, show an error message and return
    if (selectedStory == null) {
      Alert alert = new Alert(AlertType.ERROR, "Please select a story to edit.");
      alert.initOwner(view.getScene().getWindow());
      alert.showAndWait();
      return null;
    }
    return selectedStory;
  }
}
