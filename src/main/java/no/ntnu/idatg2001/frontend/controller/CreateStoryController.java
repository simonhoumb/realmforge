package no.ntnu.idatg2001.frontend.controller;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.AddRoomDialog;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.NewStoryDialog;

public class CreateStoryController {
  private CreateStoryView createStoryView;
  private AddRoomDialog addRoomDialog;
  private NewStoryDialog newStoryDialog;

  public CreateStoryController(CreateStoryView createStoryView) {
    this.createStoryView = createStoryView;
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
    EditStoryView editStoryView = new EditStoryView();
    Scene newScene = createStoryView.getScene();
    EditStoryController editStoryController = new EditStoryController(editStoryView);
    editStoryView.setController(editStoryController);
    newScene.setRoot(editStoryView);
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = createStoryView.getScene();
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
    newStoryDialog.initOwner(createStoryView.getScene().getWindow());
    newStoryDialog.showAndWait();
    populateTableView();
  }

  public void onLoad() {
    //TODO: Implement functionality for loading a previously saved story
  }

  private void populateTableView() {
    createStoryView.getStoryTableView().getItems().clear();
    List<Story> storylist = StoryDAO.getInstance().getAll();
    ObservableList<Story> list = FXCollections.observableArrayList(storylist);
    createStoryView.getStoryTableView().setItems(list);
  }
  public void configureTableView() {
    createStoryView.getColumnStoryName().setCellValueFactory(new PropertyValueFactory<>("title"));
    createStoryView.getColumnStoryPassageAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int passageAmount = story.getTotalAmountOfPassages();
      return new SimpleIntegerProperty(passageAmount).asObject();
    });
    createStoryView.getColumnStoryLinkAmount().setCellValueFactory(cell -> {
      Story story = cell.getValue();
      int linkAmount = story.getTotalAmountOfPassagesLinks();
      return new SimpleIntegerProperty(linkAmount).asObject();
    });
  }

  public void getSelectedItemInTableView() {
    // Get the selected item from the table view
    Story selectedStory = createStoryView.getStoryTableView().getSelectionModel().getSelectedItem();

// If no item is selected, show an error message and return
    if (selectedStory == null) {
      Alert alert = new Alert(AlertType.ERROR, "Please select a story to edit.");
      alert.initOwner(createStoryView.getScene().getWindow());
      alert.showAndWait();
      return;
    }
    System.out.println(selectedStory);
  }
}
