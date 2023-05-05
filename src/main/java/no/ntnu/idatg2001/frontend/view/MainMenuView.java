package no.ntnu.idatg2001.frontend.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.idatg2001.backend.MusicPlayer;
import no.ntnu.idatg2001.frontend.controller.MainMenuController;
import no.ntnu.idatg2001.backend.SettingsModel;


public class MainMenuView extends BorderPane {

  private static final String CSS_FILE = "/CSS/MainMenuStyleSheet.css";
  private JFXButton newGameButton;
  private JFXButton loadGameButton;
  private JFXButton settingsButton;
  private JFXButton exitGameButton;
  private ResourceBundle resourceBundle;
  private MainMenuController controller;


  public MainMenuView() throws IOException {
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    getStylesheets().add(CSS_FILE);
    MusicPlayer.getInstance().playMusic();
    menuView();
  }

  private void menuView() throws IOException {
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", SettingsModel.getInstance().getLocale());
    //Buttons for the menu
    newGameButton = new JFXButton();
    newGameButton.setId("newGameButton");
    newGameButton.setOnAction(actionEvent
        -> controller.onNewGameButtonPressed());

    loadGameButton = new JFXButton();
    loadGameButton.setId("loadGameButton");
    loadGameButton.setOnAction(actionEvent
        -> controller.onLoadGameButtonPressed());

    settingsButton = new JFXButton();
    settingsButton.setId("settingsButton");
    settingsButton.setOnAction(event -> controller.onSettingsViewButtonPressed());

    exitGameButton = new JFXButton();
    exitGameButton.setCancelButton(true);
    exitGameButton.setOnAction(event1 -> controller.onExitViewButtonPressed());

    updateMainMenu();

    //a box to hold the buttons
    HBox buttonBox = new HBox(10, newGameButton, loadGameButton, settingsButton, exitGameButton);
    buttonBox.setAlignment(Pos.CENTER);

    //a box to hold the buttons and the Title
    Image logoImage = new Image(getClass().getResource("/images/Menutwo.jpg").openStream());
    ImageView imageView = new ImageView(logoImage);
    imageView.setPreserveRatio(true);
    imageView.autosize();

    StackPane stackPane = new StackPane();
    stackPane.setBorder(getBorder());
    stackPane.getChildren().add(imageView);

    VBox contentBox = new VBox(20, stackPane, buttonBox);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.setMinSize(200, 200);
    contentBox.setPadding(new Insets(10, 0, 0, 0));
    setCenter(contentBox);
    contentBox.widthProperty().addListener(((observableValue, oldValue, newValue)
        -> imageView.setFitWidth(newValue.doubleValue() * 0.9)));
    contentBox.heightProperty().addListener(((observableValue, oldValue, newValue)
        -> imageView.setFitHeight(newValue.doubleValue() * 0.9)));
  }

  public void setController(MainMenuController menuController) {
    this.controller = menuController;
  }

  public void updateMainMenu() {
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", SettingsModel.getInstance().getLocale());
    newGameButton.setText(resourceBundle.getString("menu.newGame"));
    loadGameButton.setText(resourceBundle.getString("menu.loadGame"));
    settingsButton.setText(resourceBundle.getString("menu.settings"));
    exitGameButton.setText(resourceBundle.getString("menu.exitGame"));
  }
}