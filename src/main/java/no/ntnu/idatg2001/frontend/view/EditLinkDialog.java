package no.ntnu.idatg2001.frontend.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class EditLinkDialog extends Dialog<Void> {

  private TextField linkTextField;
  private Button saveButton;
  private Button cancelButton;
  private EditStoryController controller;
  private Link selectedLink;

  public EditLinkDialog(Link selectedLink,EditStoryController controller) {
    this.selectedLink = selectedLink;
    this.controller = controller;
    createSaveButton();
    createCancelButton();
    setTitle("Edit Link");
    setHeaderText("Enter the Link Details");

    // Set up the dialog content
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label linkLabel = new Label("Link Text:");
    linkTextField = new TextField(selectedLink.getText());

    gridPane.addRow(0, linkLabel);
    gridPane.addRow(1, linkTextField);

    gridPane.addRow(2, createButtonBox());

    getDialogPane().setContent(gridPane);

    // Enable/disable Save button based on input validation
    saveButton.setDisable(true);
    linkTextField.textProperty().addListener((observable, oldValue, newValue) ->
        saveButton.setDisable(newValue.trim().isEmpty()));

    // Set result converter to return null when Save button is clicked
    setResultConverter(dialogButton -> null);
  }

  private GridPane createButtonBox() {
    GridPane buttonBox = new GridPane();
    buttonBox.setHgap(10);

    buttonBox.addRow(0, saveButton, cancelButton);
    return buttonBox;
  }

  private void createSaveButton() {
    saveButton = new Button("Save");
    saveButton.setOnAction(event -> {
      if (linkTextField.getText().isEmpty() || linkTextField.getText().isBlank()) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(getDialogPane().getScene().getWindow());
        alert.setContentText("The Content Can't be Empty!");
        alert.showAndWait();
      } else {
        controller.onEditLinkSaveButtonPressed(event);
        controller.onCloseSource(event);
      }
    });
  }

  private void createCancelButton() {
    cancelButton = new Button("Cancel");
    cancelButton.setOnAction(event -> {
      controller.onCloseSource(event);
    });
  }

  public String getLinkText() {
    return linkTextField.getText();
  }
}

