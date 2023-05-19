package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.controller.Controller;
import no.ntnu.idatg2001.frontend.controller.CreateStoryController;

public class NewStoryDialog extends Dialog<Story> {

  private CreateStoryController controller;
  private Label titleLabel;
  private Label storyTitleLabel;
  private Label passageTitleLabel;
  private Label passageContentLabel;
  private TextField storyTitleField;
  private Button confirmButton;
  private Button backButton;
  private TextField passageTitleField;
  private TextArea passageContentArea;
  private VBox content;
  private ResourceBundle resourceBundle;

  public NewStoryDialog(CreateStoryController controller) {
    this.controller = controller;
    resourceBundle = ResourceBundle.getBundle("languages/newStoryDialog", SettingsModel.getInstance()
        .getLocale());
    getDialogPane().getStyleClass().add("new-story-dialog");
    getDialogPane().getStylesheets().add(("css/newStoryDialog.css"));
    // Set the dialog title
    createConfirmButton();
    createCustomTitleHeader();
    layoutFields();
    createCancelButton();
    createCloseDialogButton();
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(confirmButton, backButton);
    // Set the dialog content
    getDialogPane().setContent(new VBox(content, buttonBar));
  }

  private void createCustomTitleHeader() {
    titleLabel = new Label(resourceBundle.getString("createNewStory.windowName"));
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

  private void layoutFields() {
    content = new VBox();
    content.setAlignment(Pos.CENTER);
    content.setSpacing(10);
    content.setPadding(new Insets(10, 10, 10, 10));
    // Create the story title field
    storyTitleLabel = new Label(resourceBundle.getString("createNewStory.labelTitle"));
    storyTitleField = createTextField();
    // Create the passage title field
    passageTitleLabel = new Label(resourceBundle.getString("createNewStory.labelOpeningPassage"));
    passageTitleField = createTextField();
    // Create the passage content area
    passageContentLabel = new Label(resourceBundle.getString("createNewStory.labelPassageContent"));
    passageContentArea = createTextArea();

    content.getChildren().addAll(storyTitleLabel, storyTitleField, passageTitleLabel,
        passageTitleField, passageContentLabel, passageContentArea);
  }

  private TextField createTextField() {
    TextField textField = new TextField();
    textField.setPromptText(resourceBundle.getString("createNewStory.labelTitle"));
    return textField;
  }

  private TextArea createTextArea() {
    TextArea textArea = new TextArea();
    textArea.setWrapText(true);
    textArea.setPrefRowCount(4);
    textArea.setPromptText(resourceBundle.getString("createNewStory.labelOpeningPassage"));
    return textArea;
  }

  private void createConfirmButton() {
    confirmButton = new JFXButton(resourceBundle.getString("createNewStory.confirmButton"));
    confirmButton.setOnAction(event -> {
      String storyTitle = storyTitleField.getText();
      String passageTitle = passageTitleField.getText();
      String passageContent = passageContentArea.getText();

      // Validation: Disallow blank or empty fields
        if (!storyTitle.isEmpty() && !storyTitle.isBlank()
          && !passageTitle.isEmpty() && !passageTitle.isBlank()
          && !passageContent.isEmpty() && !passageContent.isBlank()) {
        Passage openingPassage = new Passage(passageTitle, new StringBuilder(passageContent));
        Story newStory = new Story(storyTitle, openingPassage);
        StoryDAO.getInstance().add(newStory);
        setResult(newStory);
      } else {
      AlertHelper.showWarningAlert(getDialogPane().getScene().getWindow(), "Warning",
          "Please fill out all fields and they cant be empty or blank");
      }
    });
  }

  private void createCancelButton() {
    backButton = new JFXButton(resourceBundle.getString("createNewStory.backButton"));
    backButton.setCancelButton(true);
    backButton.setOnAction(event -> {
      setResult(null);
      controller.onCloseSource(event);
    });
  }

  private void createCloseDialogButton() {
    getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
      setResult(null);
      close();
    });
  }

  public TextField getStoryTitleField() {
    return storyTitleField;
  }

  public TextField getPassageTitleField() {
    return passageTitleField;
  }

  public TextArea getPassageContentArea() {
    return passageContentArea;
  }

  public void setController(CreateStoryController controller) {
    this.controller = controller;
  }
}
