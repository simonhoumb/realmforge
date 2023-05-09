package no.ntnu.idatg2001.frontend.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.Pair;
import no.ntnu.idatg2001.frontend.controller.CreateStoryController;

public class AddRoomDialog extends Dialog<Pair<String, String>> {

  private final TextField roomNameTextField;
  private final TextArea roomContentTextArea;
  private Button addButton;
  private Button cancelButton;
  private CreateStoryController controller;

  public AddRoomDialog(CreateStoryController controller) {
    this.controller = controller;
    setTitle("Add Room");
    setHeaderText("Enter Room Name and Content");

    // Set the button types.
    createAddButton();
    createCancelButton();
    getDialogPane().getChildren().addAll(addButton,cancelButton);

    // Create the room name and content labels and text fields.
    Label roomNameLabel = new Label("Room Name:");
    Label roomContentLabel = new Label("Room Content:");
    roomNameTextField = new TextField();
    roomContentTextArea = new TextArea();

    // Create a grid pane to hold the labels and text fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    grid.add(roomNameLabel, 0, 0);
    grid.add(roomNameTextField, 1, 0);
    grid.add(roomContentLabel, 0, 1);
    grid.add(roomContentTextArea, 1, 1);
    grid.add(addButton,2,0);
    grid.add(cancelButton,2,1);
    grid.setAlignment(Pos.CENTER);

    // Enable/disable the Add button depending on whether the room name is empty.
    addButton.setDisable(true);
    roomNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      addButton.setDisable(newValue.trim().isEmpty());
    });

    getDialogPane().setContent(grid);

    // Request focus on the room name field by default.
    Platform.runLater(() -> roomNameTextField.requestFocus());

    // Convert the result to a room name and content pair when the Add button is clicked.
    setResultConverter(dialogButton -> {
      if (dialogButton.equals(addButton)) {
        return new Pair<>(roomNameTextField.getText(), roomContentTextArea.getText());
      }
      return null;
    });
  }

  private void createAddButton() {
    addButton = new Button("Add");
    addButton.setPrefSize(100,50);
    addButton.setOnAction(event -> {
      if (roomContentTextArea.getText().isEmpty() || roomContentTextArea.getText().isBlank()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(getDialogPane().getScene().getWindow());
        alert.setContentText("The Content Can't be Empty!");
        alert.showAndWait();
      } else {
       controller.onCloseSource(event);
      }
    });
  }

  private void createCancelButton() {
    cancelButton = new Button("Cancel");
    cancelButton.setPrefSize(100,50);
    cancelButton.setOnAction(event ->
        controller.onCloseSource(event));
  }

  private String fixTextForContent() {
    String content = roomContentTextArea.getText();
    return content.replaceAll("\\s+", " ").trim();
  }

  public TextField getRoomNameTextField() {
    return roomNameTextField;
  }

  public TextArea getRoomContentTextArea() {
    return roomContentTextArea;
  }
}
