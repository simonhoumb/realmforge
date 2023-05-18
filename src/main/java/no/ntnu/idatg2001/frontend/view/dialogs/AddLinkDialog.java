package no.ntnu.idatg2001.frontend.view.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class AddLinkDialog extends Dialog {

  private TextField linkTextField;
  private Button addButton;
  private Button cancelButton;
  private EditStoryController controller;
  private TableView<Passage> passageTableView;
  TableColumn<Passage, String> addLinkPassageColumn;

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

    gridPane.addRow(0, linkLabel);
    gridPane.addRow(1, linkTextField);
    gridPane.addRow(2, createTableView());

    HBox buttonBox = new HBox();
    buttonBox.setAlignment(javafx.geometry.Pos.CENTER);
    buttonBox.setSpacing(10);
    buttonBox.getChildren().addAll(addButton, cancelButton);
    gridPane.addRow(3, buttonBox);
    getDialogPane().setContent(gridPane);

    // Enable/disable Add button based on input validation
    addButton.setDisable(true);
    linkTextField.textProperty().addListener((observable, oldValue, newValue) ->
        addButton.setDisable(newValue.trim().isEmpty()));

    // Set result converter to return null when Add button is clicked
    setResultConverter(dialogButton -> null);
  }

  private TableView<Passage> createTableView() {
    createAddLinkPassageColumn();
    passageTableView = new TableView<>();
    passageTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    passageTableView.getColumns().add(addLinkPassageColumn);
    passageTableView.setPrefHeight(150);

    return passageTableView;
  }

  private void createAddLinkPassageColumn() {
    addLinkPassageColumn = new TableColumn<>("Passage");
  }

  private void createAddButton() {
    addButton = new Button("Add");
    addButton.setOnAction(e -> {
      controller.onAddLinkToPassageAddButton(e);
      System.out.println("Add button pressed");
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

  public TableView<Passage> getPassageTableView() {
    return passageTableView;
  }

  public TableColumn<Passage, String> getAddLinkPassageColumn() {
    return addLinkPassageColumn;
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getCancelButton() {
    return cancelButton;
  }
}
