package no.ntnu.idatg2001.frontend.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.Pair;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class EditPassageDialog extends Dialog<Pair<String, String>> {

  private final Passage passage;
  private final TextField roomNameTextField;
  private final TextArea roomContentTextArea;
  private Button saveButton;
  private Button cancelButton;
  private EditStoryController controller;

  /**
   * The constructor of the class.
   *
   * @param passage    the passage to be edited.
   * @param controller the controller of the class.
   */
  public EditPassageDialog(Passage passage, EditStoryController controller) {
    this.passage = passage;
    this.controller = controller;
    setTitle("Edit Passage");
    setHeaderText("Edit Room Name and Content");

    // Set the button types.
    createSaveButton();
    createCancelButton();
    getDialogPane().getChildren().addAll(saveButton, cancelButton);

    // Create the room name and content labels and text fields.
    Label roomNameLabel = new Label("Room Name:");
    Label roomContentLabel = new Label("Room Content:");
    roomNameTextField = new TextField(passage.getTitle());
    roomContentTextArea = new TextArea(passage.getContent());

    // Create a grid pane to hold the labels and text fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    grid.add(roomNameLabel, 0, 0);
    grid.add(roomNameTextField, 1, 0);
    grid.add(roomContentLabel, 0, 1);
    grid.add(roomContentTextArea, 1, 1);
    grid.add(saveButton, 2, 0);
    grid.add(cancelButton, 2, 1);
    grid.setAlignment(Pos.CENTER);

    getDialogPane().setContent(grid);

    // Request focus on the room name field by default.
    Platform.runLater(() -> roomNameTextField.requestFocus());

    // Convert the result to a room name and content pair when the Save button is clicked.
    setResultConverter(dialogButton -> {
      if (dialogButton.equals(saveButton)) {
        return new Pair<>(roomNameTextField.getText(), roomContentTextArea.getText());
      }
      return null;
    });
  }

  private void createSaveButton() {
    saveButton = new Button("Save");
    saveButton.setPrefSize(100, 50);
    saveButton.setOnAction(event -> {
      if (roomContentTextArea.getText().isEmpty() || roomContentTextArea.getText().isBlank()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(getDialogPane().getScene().getWindow());
        alert.setContentText("The Content Can't be Empty!");
        alert.showAndWait();
      } else {
        controller.onEditPassageSaveButtonPressed(event);
        controller.onCloseSource(event);
      }
    });
  }

  private void createCancelButton() {
    cancelButton = new Button("Cancel");
    cancelButton.setPrefSize(100, 50);
    cancelButton.setOnAction(event -> controller.onCloseSource(event));
}

  private String fixTextForTitle() {
    String title = roomNameTextField.getText();
    return title.replaceAll("\\s+", " ").trim();
  }

  private String fixTextForContent() {
    String content = roomContentTextArea.getText();
    return content.replaceAll("\\s+", " ").trim();
  }

  public String getRoomNameTextField() {
    return fixTextForTitle();
  }

  public String getRoomContentTextArea() {
    return fixTextForContent();
  }
}
