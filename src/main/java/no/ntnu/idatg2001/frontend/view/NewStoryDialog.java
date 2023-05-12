package no.ntnu.idatg2001.frontend.view;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.controller.Controller;
import no.ntnu.idatg2001.frontend.controller.CreateStoryController;

public class NewStoryDialog extends Dialog<Story> implements View {

  private CreateStoryController controller;
  private TextField storyTitleField;
  private TextField passageTitleField;
  private TextArea passageContentArea;
  private VBox content;
  private ResourceBundle resourceBundle;

  public NewStoryDialog(CreateStoryController controller) {
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/newStoryDialog", locale);
    // Set the dialog title
    setTitle(resourceBundle.getString("createNewStory.windowName"));

    // Set the button types
    ButtonType createConfirmType = new ButtonType(resourceBundle.getString("createNewStory.confirmButton"), ButtonBar.ButtonData.OK_DONE);
    ButtonType createBackButtonType = new ButtonType(resourceBundle.getString("createNewStory.backButton"),
        ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().addAll(createConfirmType, createBackButtonType);

    // Create the content for the dialog
    content = new VBox();
    content.setAlignment(Pos.CENTER);
    content.setSpacing(10);
    content.setPadding(new Insets(10, 10, 10, 10));

    // Create the story title field
    Label storyTitleLabel = new Label(resourceBundle.getString("createNewStory.labelTitle"));
    storyTitleField = new TextField();
    content.getChildren().addAll(storyTitleLabel, storyTitleField);

    // Create the passage title field
    Label passageTitleLabel = new Label(resourceBundle.getString("createNewStory.labelOpeningPassage"));
    passageTitleField = new TextField();
    content.getChildren().addAll(passageTitleLabel, passageTitleField);

    // Create the passage content area
    Label passageContentLabel = new Label(resourceBundle.getString("createNewStory.labelPassageContent"));
    passageContentArea = new TextArea();
    passageContentArea.setWrapText(true);
    passageContentArea.setPrefRowCount(4);
    content.getChildren().addAll(passageContentLabel, passageContentArea);

    // Set the dialog content
    getDialogPane().setContent(content);

    // Enable or disable the create button depending on the input
    Node createButton = getDialogPane().lookupButton(createConfirmType);
    createButton.disableProperty().bind(storyTitleField.textProperty().isEmpty()
        .or(passageTitleField.textProperty().isEmpty())
        .or(passageContentArea.textProperty().isEmpty()));

    // Convert the result to a Story object when the create button is clicked
    setResultConverter(dialogButton -> {
      if (dialogButton == createConfirmType) {
        String storyTitle = storyTitleField.getText();
        String passageTitle = passageTitleField.getText();
        String passageContent = passageContentArea.getText();

        Passage openingPassage = new Passage(passageTitle, passageContent);
        Story newStory = new Story(storyTitle, openingPassage);
        StoryDAO.getInstance().add(newStory);
        return newStory;
      }
      return null;
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

  @Override
  public void setController(Controller controller) {
    this.controller = (CreateStoryController) controller;
  }
}

