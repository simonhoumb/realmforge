package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ntnu.no.idatg2001.FrontEnd.Controller.MainMenuController;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsController;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModule;


public class MainMenuView extends BorderPane {

  private static final String cssFile = "/CSS/MainMenuStyleSheet.css";
  private JFXButton newGameButton;
  private JFXButton loadGameButton;
  private JFXButton settingsButton;
  private JFXButton exitGameButton;
  private MediaPlayer mediaPlayer;
  private StackPane settingsPane;
  private SettingsController settings;
  private SettingsView settingsView;


  public MainMenuView(double width, double height) throws IOException {
    setPrefSize(width, height);
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    getStylesheets().add(cssFile);
    MainMenuController menuController = new MainMenuController();

    Image logoImage = new Image(getClass().getResource("/images/Menutwo.jpg").openStream());
    ImageView logoImageView = new ImageView(logoImage);
    logoImageView.setPreserveRatio(true);
    logoImageView.preserveRatioProperty().set(true);
    setTop(logoImageView);

    String musicFile = "/music/One-Bard-Band.mp3";
    Media music = new Media(getClass().getResource(musicFile).toExternalForm());
    mediaPlayer = new MediaPlayer(music);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);

    //Buttons for the menu
    newGameButton = new JFXButton("New Game");
    newGameButton.setId("newGameButton");

    loadGameButton = new JFXButton("Load Game");
    loadGameButton.setId("loadGameButton");

    settingsButton = new JFXButton("settings");
    settingsButton.setId("settingsButton");

    settingsButton.setOnAction(event -> {
      settingsView = new SettingsView();
      settingsView.initOwner(getScene().getWindow());
      settingsView.showAndWait();
    });

      exitGameButton = new JFXButton("Exit Game");
      exitGameButton.setId("exitButton");

      exitGameButton.setOnAction(event1 -> {
        ExitDialog dialog = new ExitDialog();
        dialog.initOwner(getScene().getWindow());
        dialog.showAndWait();
      });

      addEventFilter(KeyEvent.KEY_PRESSED, event1 -> {
        if (event1.getCode() == KeyCode.ESCAPE) {
          event1.consume(); // consume the event to prevent it from propagating
          ExitDialog dialog = new ExitDialog();
          dialog.initOwner(getScene().getWindow());
          dialog.showAndWait();
        }
      });

      //Add button action

      //a box to hold the buttons
      HBox buttonBox = new HBox(10, newGameButton, loadGameButton, settingsButton, exitGameButton);
      buttonBox.setAlignment(Pos.CENTER);

      //a box to hold the buttons and the Title
      VBox contentBox = new VBox(40, logoImageView, buttonBox);
      contentBox.setAlignment(Pos.CENTER);
      contentBox.setPadding(new Insets(10, 0, 0, 0));
      setCenter(contentBox);
  }
}