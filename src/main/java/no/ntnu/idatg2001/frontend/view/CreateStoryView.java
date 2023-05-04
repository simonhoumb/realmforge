package no.ntnu.idatg2001.frontend.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import java.io.IOException;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.CreateStoryController;

public class CreateStoryView extends BorderPane {

  private ButtonBar buttonBar;
  private JFXTextArea jfxTextArea;
  private JFXTreeTableView<Passage> jfxTreeTableView;
  private CreateStoryController controller;
  private JFXButton storyNameButton;
  private JFXButton newRoomButton;
  private JFXButton newLinkButton;
  private JFXButton saveButton;
  private JFXButton loadButton;
  private JFXButton backButton;


  public CreateStoryView() {
    this.setController(controller);
    // Initialize the view
    init();
  }

  private void init() {
    // Set up the buttons
    createNewStoryNameButton();
    createNewPassageButton();
    creatNewLinkButton();
    saveButton = new JFXButton("Save");
    loadButton = new JFXButton("Load");
    createBackButton();
    buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(storyNameButton, newRoomButton, newLinkButton, saveButton, loadButton, backButton);

    // Set up the text area
    jfxTextArea = new JFXTextArea();
    jfxTextArea.setPadding(new Insets(0,0,0,20));

    jfxTreeTableView = new JFXTreeTableView<>();
    // Set up the table columns
    TreeTableColumn<Passage, String> roomName = new TreeTableColumn<>("Room");
    TreeTableColumn<Passage, String> linkName = new TreeTableColumn<>("Link");
    jfxTreeTableView.getColumns().setAll(roomName,linkName);

    // Set up the layout
    setPadding(new Insets(20,20,0,20));
    setCenter(jfxTextArea);

    VBox rightVBox = new VBox();
    rightVBox.getChildren().addAll(buttonBar, jfxTreeTableView);

    HBox bottomHBox = new HBox();
    bottomHBox.setPadding(new Insets(10));
    bottomHBox.setSpacing(10);
    bottomHBox.getChildren().addAll(storyNameButton, newRoomButton, newLinkButton, saveButton, loadButton, backButton);

    setLeft(rightVBox);
    setBottom(bottomHBox);
  }
  private void createNewStoryNameButton() {
    storyNameButton = new JFXButton("Story Name");
    storyNameButton.setOnAction(event -> {
      //TODO
    });
  }

  private void createNewPassageButton() {
    newRoomButton = new JFXButton("New Room");
    newRoomButton.setOnAction(event -> {
      //TODO
    });
  }

  private void creatNewLinkButton() {
    newLinkButton = new JFXButton("New Link");
    newLinkButton.setOnAction(event -> {
      //TODO
    });
  }

  private void createSaveStoryButton() {
    saveButton = new JFXButton("Save");
    saveButton.setOnAction(event -> {
      //TODO
    });
  }

  private void createLoadStoryButton() {
    loadButton = new JFXButton("Load");
    loadButton.setOnAction(event -> {
      //TODO
    });
  }


  private void createBackButton() {
    backButton = new JFXButton("Back");
    backButton.setOnAction(event -> {
      try {
        controller.onBackToMainMenuButtonPressed(event);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public Map<String, TextArea> getRoomTextAreas() {
    return (Map<String, TextArea>) jfxTextArea;
  }

  public void setController(CreateStoryController createStoryController) {
    this.controller = createStoryController;
  // Add any additional methods, event handlers, or getters/setters as needed
  }
}

