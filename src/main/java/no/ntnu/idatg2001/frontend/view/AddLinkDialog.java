package no.ntnu.idatg2001.frontend.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class AddLinkDialog extends Dialog {

  private TextField linkTextField;
  private TextField referenceTextField;
  private Button addButton;
  private Button cancelButton;
  private EditStoryController controller;

  public AddLinkDialog(EditStoryController controller) {
    this.controller = controller;
    createAddButton();
    createCancelButton();
    setTitle("Add Link");
    setHeaderText("Enter the Link Details");

    // Set up the dialog content
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label linkLabel = new Label("Link Text:");
    linkTextField = new TextField();

    Label referenceLabel = new Label("Reference Passage:");
    referenceTextField = new TextField();

    gridPane.addRow(0, linkLabel, linkTextField);
    gridPane.addRow(1, referenceLabel, referenceTextField);

    HBox buttonBox = new HBox();
    buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
    buttonBox.setSpacing(10);
    buttonBox.getChildren().addAll(addButton, cancelButton);
    gridPane.addRow(2, buttonBox);
    getDialogPane().setContent(gridPane);

    // Enable/disable Add button based on input validation
    addButton.setDisable(true);
    linkTextField.textProperty().addListener((observable, oldValue, newValue) ->
        addButton.setDisable(newValue.trim().isEmpty()));

    // Set result converter to return null when Add button is clicked
    setResultConverter(dialogButton -> null);
  }

  private void createAddButton() {
    addButton = new Button("Add");
    addButton.setOnAction(e -> {
      controller.onAddLinkToPassageAddButton();
      controller.onCloseSource(e);
    });
  }

  private void createCancelButton() {
    cancelButton = new Button("Cancel");
    cancelButton.setOnAction(event -> {
      controller.onCloseSource(event);
    });
  }

  public TextField getLinkTextField() {
    return linkTextField;
  }

  public TextField getReferenceTextField() {
    return referenceTextField;
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getCancelButton() {
    return cancelButton;
  }
}
