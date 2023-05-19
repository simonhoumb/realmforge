package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class AddLinkDialog extends Dialog {

  private TextField linkTextField;
  private Label titleLabel;
  private Button addButton;
  private Button cancelButton;
  private EditStoryController controller;
  private TableView<Passage> passageTableView;
  TableColumn<Passage, String> addLinkPassageColumn;
  ResourceBundle resourceBundle;

  public AddLinkDialog(EditStoryController controller) {
    this.controller = controller;
    resourceBundle = ResourceBundle.getBundle("languages/addLinkDialog"
        , SettingsModel.getInstance().getLocale());
    getDialogPane().getStylesheets().add(("css/addLinkDialog.css"));
    createCustomTitleHeader();
    createAddButton();
    createCancelButton();

    // Set up the dialog content
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label linkLabel = new Label(resourceBundle.getString("label.linkText"));
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


  private TableView<Passage> createTableView() {
    createAddLinkPassageColumn();
    passageTableView = new TableView<>();
    passageTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    passageTableView.getColumns().add(addLinkPassageColumn);
    passageTableView.setPrefHeight(150);

    return passageTableView;
  }

  private void createAddLinkPassageColumn() {
    addLinkPassageColumn = new TableColumn<>(resourceBundle.getString("column.name"));
  }

  private void createAddButton() {
    addButton = new Button(resourceBundle.getString("button.add"));
    addButton.setOnAction(e -> {
      controller.onAddLinkToPassageAddButton(e);
    });
  }

  private void createCancelButton() {
    cancelButton = new Button(resourceBundle.getString("button.cancel"));
    cancelButton.setOnAction(event -> {
      controller.setPassageBeingEdited(false);
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

  public void setLinkTextField(String linkTextField) {
    this.linkTextField.setText(linkTextField);
  }

  public Button getAddButton() {
    return addButton;
  }

  public Button getCancelButton() {
    return cancelButton;
  }
}
