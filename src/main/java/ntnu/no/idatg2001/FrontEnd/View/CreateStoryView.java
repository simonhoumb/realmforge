package ntnu.no.idatg2001.FrontEnd.View;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;

public class CreateStoryView extends Parent {
  private Stage stage;
  private MainMenuView mainMenuView;
  private SettingsModel settings;
  private Scene createStoryScene;

  public CreateStoryView(Stage parentStage, SettingsModel settings, MainMenuView mainMenuView) {
    this.stage = parentStage;
    this.mainMenuView = mainMenuView;
    this.settings = settings;
    Button backButton = createBackButton();

    VBox layout = new VBox();
    layout.setSpacing(10);
    layout.setPadding(new Insets(10));

    Label titleLabel = new Label("Title:");
    TextField titleField = new TextField();
    HBox titleBox = new HBox(titleLabel, titleField);
    titleBox.setSpacing(10);
    layout.getChildren().add(titleBox);

    Label storyLabel = new Label("Story:");
    TextArea storyArea = new TextArea();
    storyArea.setPrefRowCount(5);
    HBox storyBox = new HBox(storyLabel, storyArea);
    storyBox.setSpacing(10);
    layout.getChildren().add(storyBox);

    Label choicesLabel = new Label("Player Choices:");
    TextArea choicesArea = new TextArea();
    choicesArea.setPrefRowCount(5);
    HBox choicesBox = new HBox(choicesLabel, choicesArea);
    choicesBox.setSpacing(10);
    layout.getChildren().add(choicesBox);

    Button createButton = new Button("Create");
    createButton.setOnAction(event -> {
      String title = titleField.getText();
      String story = storyArea.getText();
      String choices = choicesArea.getText();

      String fileContent = title + "\n\n" + story + "\n\n" + choices;

      // save file

      // go back to main menu
      try {
        stage.setScene(this.mainMenuView.getMainScene().getScene());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      stage.setFullScreen(true);
    });
    layout.getChildren().addAll(createButton,backButton);

    createStoryScene = new Scene(layout, 600, 800);
    createStoryScene.setRoot(layout);
    parentStage.setScene(createStoryScene);
    parentStage.show();
  }
  private Button createBackButton() {
    Button backButton = new Button("Back");
    backButton.setOnAction(event -> {
      if (mainMenuView != null) { // null check
        try {
          stage.setScene(this.mainMenuView.getMainScene().getScene());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        stage.setFullScreen(true);
      }
    });
    return backButton;
  }
}