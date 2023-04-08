package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;

public class CreateStoryView extends Parent {
  private Stage stage;
  private ResourceBundle resourceBundle;
  private SettingsModel settings = new SettingsModel();
  private MainMenuView mainMenuView;

  public CreateStoryView(Stage parentStage, MainMenuView mainMenuView) throws IOException {
    this.mainMenuView = mainMenuView;
    create(parentStage);
  }

  public void create(Stage parentStage) {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    //Stage creatStoryStage = new Stage();
    //creatStoryStage.setTitle(resourceBundle.getString("createStoryTitle"));

    JFXTextArea storyTextArea = new JFXTextArea();
    storyTextArea.setWrapText(true);
    storyTextArea.setMaxWidth(Double.MAX_VALUE);
    storyTextArea.setMaxHeight(Double.MAX_VALUE);

    Button addSectionButton = new Button(resourceBundle.getString("addSectionButton"));
    addSectionButton.setOnAction(event -> {
      storyTextArea.appendText("\n\n::New section\n");
    });

    Button backButton = new Button("Test");
    backButton.setOnAction(event -> {
      try {
        mainMenuView = new MainMenuView(200, 200);
        Scene mainMenuScene = new Scene(mainMenuView, 600, 400);
        parentStage.setScene(mainMenuView.getMainScene());
        parentStage.setFullScreen(true);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    Button saveButton = new Button(resourceBundle.getString("saveButton"));
    saveButton.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle(resourceBundle.getString("saveDialogTitle"));
      //fileChooser.getExtensionFilters().add(
          //new ExtensionFilter());
      File file = fileChooser.showSaveDialog(parentStage);
      if (file != null) {
        try (PrintWriter writer = new PrintWriter(file)) {
          writer.print(storyTextArea.getText());
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    });

    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(addSectionButton,saveButton,backButton);
    VBox layout = new VBox(storyTextArea,buttonBox);
    layout.setFillWidth(true);
    layout.setMaxWidth(Double.MAX_VALUE);
    VBox.setVgrow(storyTextArea, Priority.ALWAYS);

    Scene createStoryScene = new Scene(layout,600 ,400);
    parentStage.setScene(createStoryScene);
    parentStage.setFullScreen(true);
    parentStage.show();
    getScene().setRoot(layout);
    //mainMenuView.getMainScene().setRoot(layout);
  }
}
