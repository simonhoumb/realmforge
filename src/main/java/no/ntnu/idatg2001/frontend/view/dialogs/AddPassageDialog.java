package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.util.Pair;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class AddPassageDialog extends Dialog<Pair<String, String>> {

  private final TextField roomNameTextField;
  private final TextArea roomContentTextArea;
  private Label titleLabel;
  private Button addButton;
  private Button cancelButton;
  private EditStoryController controller;
  ResourceBundle resourceBundle;

  /**
   * The constructor of the class.
   *
   * @param controller the controller of the class.
   */
  public AddPassageDialog(EditStoryController controller) {
    this.controller = controller;
    resourceBundle = ResourceBundle.getBundle("languages/addPassageDialog"
        , SettingsModel.getInstance().getLocale());
    // Set the button types.
    createCustomTitleHeader();
    createAddButton();
    createCancelButton();
    getDialogPane().getChildren().addAll(addButton,cancelButton);
    getDialogPane().getStyleClass().add("new-story-dialog");
    getDialogPane().getStylesheets().add(("css/newStoryDialog.css"));
    // Create the room name and content labels and text fields.
    Label roomNameLabel = new Label(resourceBundle.getString("label.roomName"));
    Label roomContentLabel = new Label(resourceBundle.getString("label.roomContent"));
    roomNameTextField = new TextField();
    roomContentTextArea = new TextArea();
    roomContentTextArea.setWrapText(true);

    // Create a grid pane to hold the labels and text fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 100, 10, 10));
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

  private void createCustomTitleHeader() {
    titleLabel = new Label(resourceBundle.getString("dialog.title"));
    titleLabel.getStyleClass().add("dialog-title"); // Apply CSS style class to the title label

    // Create close button
    JFXButton closeButton = new JFXButton("X");
    closeButton.setFocusTraversable(false);
    closeButton.getStyleClass().add("dialog-close-button");
    closeButton.setOnAction(event -> controller.onCloseSource(event)); // Close the dialog when the close button is clicked

    // Set up the layout
    HBox headerBox = new HBox();
    headerBox.setAlignment(Pos.CENTER_LEFT);
    headerBox.setPadding(new Insets(0, 0, 0, 0));
    headerBox.getChildren().add(titleLabel);

    StackPane headerPane = new StackPane();
    headerPane.setPadding(new Insets(2, 2, 0, 0));
    headerPane.getChildren().addAll(headerBox, closeButton);
    StackPane.setAlignment(closeButton, Pos.CENTER_RIGHT);

    getDialogPane().setHeader(headerPane);
    titleLabel.setPadding(new Insets(10, 0, 0, 10));
  }

  private void createAddButton() {
    addButton = new Button(resourceBundle.getString("button.add"));
    addButton.setPrefSize(100,50);
    addButton.setOnAction(event -> {
      if (roomContentTextArea.getText().isEmpty() || roomContentTextArea.getText().isBlank()) {
        AlertHelper.showWarningAlert(getDialogPane().getScene().getWindow(), resourceBundle.getString("alert.contentEmpty"),
            resourceBundle.getString("alert.enterContent"));
      } else {
       controller.onAddPassageAddButtonPressed();
       controller.onCloseSource(event);
       }
    });
  }

  private void createCancelButton() {
    cancelButton = new Button(resourceBundle.getString("button.cancel"));
    cancelButton.setPrefSize(100,50);
    cancelButton.setOnAction(event -> {
        controller.setPassageBeingEdited(false);
        controller.onCloseSource(event);
    });
  }

  public void setRoomNameTextField(String roomName) {
    roomNameTextField.setText(roomName);
  }

  public void setRoomContentTextArea(String roomContent) {
    roomContentTextArea.setText(roomContent);
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
