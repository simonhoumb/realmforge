package no.ntnu.idatg2001.frontend.view.dialogs;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/newStoryDialog", locale);
    // Set the dialog title
    setTitle(resourceBundle.getString("createNewStory.windowName"));

    layoutFields();
    // Create custom buttons
    createConfirmButton();
    createCancelButton();
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(confirmButton, backButton);
    // Set the dialog content
    getDialogPane().setContent(new VBox(content, buttonBar));
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
    confirmButton = new Button(resourceBundle.getString("createNewStory.confirmButton"));
    confirmButton.setOnAction(event -> {
      String storyTitle = storyTitleField.getText();
      String passageTitle = passageTitleField.getText();
      String passageContent = passageContentArea.getText();

      // Validation: Disallow blank or empty fields
      if (!(storyTitle.isBlank() || storyTitle.isEmpty()
          && passageTitle.isBlank() || passageTitle.isEmpty()
          && passageContent.isBlank() || passageContent.isEmpty())) {
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
    backButton = new Button(resourceBundle.getString("createNewStory.backButton"));
    backButton.setOnAction(event -> {
      setResult(null);
      controller.onCloseSource(event);
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
