package ntnu.no.idatg2001.FrontEnd.View;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ntnu.no.idatg2001.FrontEnd.Controller.NewGameController;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;

public class NewGameDialog extends Dialog<ButtonType> {

  private Dialog<ButtonType> newGameDialog;
  private NewGameController newGameController;
  private SettingsModel settings = new SettingsModel();
  private Button newStoryButton;
  private Button createStoryButton;
  private Button backToMainMenuButton;
  private ResourceBundle resourceBundle;
  private Label newGameLable;


  public NewGameDialog(ResourceBundle bundle) {
    newGameDialog = new Dialog<>();
    getDialogPane().setContent(layout());
    initStyle(StageStyle.TRANSPARENT);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add("css/newGameDialog.css");
  }

  private GridPane layout() {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);

    newGameLable = new Label();
    newGameLable.setText(resourceBundle.getString("newGameHeaderText"));
    newGameLable.setWrapText(true);
    newGameLable.setMaxWidth(Double.MAX_VALUE);

    GridPane layout = new GridPane();
    layout.setHgap(10);
    layout.setVgap(10);
    layout.setMaxWidth(Double.MAX_VALUE);
    layout.setMaxHeight(Double.MAX_VALUE);
    layout.setGridLinesVisible(false);
    layout.setMaxWidth(Double.MAX_VALUE);

    VBox vBox = new VBox();

    createPlayNewStoryButton(resourceBundle);
    createCreateStoryButton(resourceBundle);
    createBackToMainMenuButton(resourceBundle);
    vBox.fillWidthProperty();
    vBox.getContentBias();
    vBox.getChildren().addAll(newStoryButton,createStoryButton);

    newStoryButton.setPrefSize(150,30);
    backToMainMenuButton.setPrefSize(100,30);

    layout.add(newGameLable,1,0);
    layout.add(vBox,0,1);
    layout.add(backToMainMenuButton,2,2);
    return layout;
  }

  public Button createPlayNewStoryButton(ResourceBundle bundle) {
    newStoryButton = new Button(bundle.getString("playNewStoryButton"));
    ButtonType playNewStoryType = new ButtonType(bundle.getString("playNewStoryButton"),
        ButtonData.OK_DONE);
    newStoryButton.setUserData(playNewStoryType);
    newStoryButton.setPrefWidth(Region.USE_COMPUTED_SIZE);
    newStoryButton.setMaxWidth(Double.MAX_VALUE);
    return newStoryButton;
  }

  public Button createCreateStoryButton(ResourceBundle bundle) {
    createStoryButton = new Button(bundle.getString("createStoryButton"));
    ButtonType createStoryType = new ButtonType(bundle.getString("createStoryButton"),
        ButtonData.OK_DONE);
    createStoryButton.setUserData(createStoryType);
    createStoryButton.setAlignment(Pos.CENTER);
    createStoryButton.setWrapText(true);
    createStoryButton.setMaxWidth(Double.MAX_VALUE);

    return createStoryButton;
  }
  public Button createBackToMainMenuButton(ResourceBundle bundle) {
    backToMainMenuButton = new Button(bundle.getString("backToMainMenuButton"));
    ButtonType backToMainMenuType = new ButtonType(bundle.getString("backToMainMenuButton"),
        ButtonData.CANCEL_CLOSE);
    backToMainMenuButton.setUserData(backToMainMenuType);
    backToMainMenuButton.setAlignment(Pos.CENTER);
    backToMainMenuButton.setWrapText(true);

    backToMainMenuButton.setOnAction(event -> {
      Node source = (Node) event.getSource();
      Stage stage = (Stage) source.getScene().getWindow();
      stage.close();
    });
    return backToMainMenuButton;
  }

  public Button getPlayNewStory() {
    return newStoryButton;
  }

  public Button getCreateStory() {
    return createStoryButton;
  }

  public Button getBackToMainMenu() {
    return backToMainMenuButton;
  }
}
