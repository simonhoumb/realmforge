package no.ntnu.idatg2001.frontend.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.*;


public class CreateStoryViewTemp extends AnchorPane {
  private Stage stage;
  private MainMenuView mainMenuView;
  private SettingsModel settings;
  private Scene createStoryScene;
  private TextArea textArea;
  private ListView<String> roomListView;
  private Map<String, TextArea> roomTextAreas;
  private List<String> roomList;
  private Button addButton;
  private Button saveButton;
  private Button backButton;
  private CreateStoryController controller;

  public CreateStoryViewTemp() {
    this.roomList = new ArrayList<>();
    this.roomTextAreas = new HashMap<>();
    this.setController(controller);
    createTextArea();
    createListView();
    createAddButton();
    createBackButton();
    createSaveButton();
    createLayout();
  }

  private void createTextArea() {
    textArea = new TextArea();
    textArea.setPrefWidth(400);
    textArea.setWrapText(true);
  }

  private void createListView() {
    roomListView = new ListView<>();
    roomListView.setPrefWidth(200);
    roomListView.getItems().addAll(roomList);
    roomListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (newValue != null) {
          showRoomTextArea(newValue);
        }
      }
    });
  }

  private void createAddButton() {
    addButton = new Button("Add Room");
    addButton.setOnAction(event -> addRoom());
  }

  private void createSaveButton() {
    saveButton = new Button("Save Story");
    saveButton.setOnAction(event -> {
      controller.saveStoryTest(event);
    });
  }

  private void createBackButton() {
    backButton = new Button("Back");
    backButton.setOnAction(event -> {
      try {
        controller.onBackToMainMenuButtonPressed(event);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void createLayout() {
    // Arrange controls in the layout
    VBox leftBox = new VBox(10, new Label("Story Text:"), textArea, addButton, saveButton, backButton);
    leftBox.setAlignment(Pos.TOP_LEFT);
    leftBox.setPadding(new Insets(20));
    leftBox.setStyle("-fx-background-color: #f2f2f2;");

    HBox rightBox = new HBox(roomListView);
    rightBox.setAlignment(Pos.TOP_CENTER);
    rightBox.setPadding(new Insets(20));
    rightBox.setStyle("-fx-background-color: #f2f2f2;");

    HBox contentBox = new HBox();
      contentBox.getChildren().addAll(leftBox,rightBox);
    this.getChildren().add(contentBox);
  }

  private void addRoom() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Add Room");
    dialog.setHeaderText(null);
    dialog.initOwner(this.stage);
    dialog.setContentText("Enter room name:");
    dialog.showAndWait().ifPresent(roomName -> {
      if (!roomName.isEmpty()) {
        roomList.add(roomName);
        roomListView.getItems().setAll(roomList);
        textArea.appendText("\n::" + roomName + "\n");
      }
    });
  }



  private void saveStory() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Story");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.Paths"));
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        writer.write(textArea.getText());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void showRoomTextArea(String newValue) {
    TextArea roomTextArea = roomTextAreas.get(newValue);
    if (roomTextArea == null) {
      roomTextArea = new TextArea();
      roomTextArea.setPrefWidth(400);
      roomTextArea.setWrapText(true);
      roomTextAreas.put(newValue, roomTextArea);
    }
    VBox leftBox = (VBox) ((HBox) this.createStoryScene.getRoot()).getChildren().get(0);
    leftBox.getChildren().set(1, roomTextArea);
  }

  public Map<String, TextArea> getRoomTextAreas() {
    return roomTextAreas;
  }

  public void setController(CreateStoryController createStoryController) {
    this.controller = createStoryController;
  }
}