package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
import javafx.util.Duration;
import ntnu.no.idatg2001.FrontEnd.Controller.MainMenuController;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsController;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;


public class MainMenuView extends BorderPane {

  private static final String cssFile = "/CSS/MainMenuStyleSheet.css";
  private JFXButton newGameButton;
  private JFXButton loadGameButton;
  private JFXButton settingsButton;
  private JFXButton exitGameButton;
  private MediaPlayer mediaPlayer;
  private SettingsController settingsController;
  private SettingsView settingsView;
  private SettingsModel settings = new SettingsModel();
  private ExitDialog exitDialog;
  private ResourceBundle resourceBundle;



  public MainMenuView(double width, double height) throws IOException {
    setPrefSize(width, height);
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    getStylesheets().add(cssFile);
    menuView();
    settingsView();
    playMusic();
  }

    public VBox menuView() throws IOException {
      Locale locale = new Locale(settings.getLocale().toString());
      resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
      //Buttons for the menu
      newGameButton = new JFXButton(resourceBundle.getString("menu.newGame"));
      newGameButton.setId("newGameButton");

      loadGameButton = new JFXButton(resourceBundle.getString("menu.loadGame"));
      loadGameButton.setId("loadGameButton");

      settingsButton = new JFXButton(resourceBundle.getString("menu.settings"));
      settingsButton.setId("settingsButton");

      settingsButton.setOnAction(event -> {
        settingsView();
        settingsView.initOwner(getScene().getWindow());
        settingsController.showAndWait();
        updateButtonLabels();
      });

      exitGameButton = new JFXButton(resourceBundle.getString("menu.exitGame"));
      exitGameButton.setId("exitButton");

      exitGameButton.setOnAction(event1 -> {
        settingsView();
        exitDialog.initOwner(getScene().getWindow());
        exitDialog.showAndWait();
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
      Image logoImage = new Image(getClass().getResource("/images/Menutwo.jpg").openStream());
      ImageView imageView = new ImageView(logoImage);
      imageView.setPreserveRatio(true);
      imageView.autosize();


      StackPane stackPane = new StackPane();
      stackPane.getChildren().add(imageView);

      VBox contentBox = new VBox(20, stackPane, buttonBox);
      contentBox.setAlignment(Pos.CENTER);
      contentBox.setPadding(new Insets(10, 0, 0, 0));
      setCenter(contentBox);
      contentBox.widthProperty().addListener(((observableValue, oldValue, newValue) -> {
        imageView.setFitWidth(newValue.doubleValue() *0.8);
      } ));
      contentBox.heightProperty().addListener(((observableValue, oldValue, newValue) -> {
        imageView.setFitHeight(newValue.doubleValue() *0.8);
      } ));
      System.out.println(imageView.getFitHeight());
      System.out.println(imageView.getFitWidth());
      return contentBox;
    }

  private void playMusic() {
    String musicFile = "/music/One-Bard-Band.mp3";
    Media music = new Media(getClass().getResource(musicFile).toExternalForm());
    mediaPlayer = new MediaPlayer(music);
    mediaPlayer.setVolume(settings.getVolumeSliderValue() / 100);
    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
    if (!settings.isMuted()){
      mediaPlayer.play();
    }
  }
  private void settingsView() {
    settingsView = new SettingsView(settings);
    exitDialog = new ExitDialog();
    try {
      settingsController = new SettingsController(settings, settingsView, mediaPlayer,exitDialog);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  public void updateButtonLabels() {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    newGameButton.setText(resourceBundle.getString("menu.newGame"));
    loadGameButton.setText(resourceBundle.getString("menu.loadGame"));
    settingsButton.setText(resourceBundle.getString("menu.settings"));
    exitGameButton.setText(resourceBundle.getString("menu.exitGame"));
  }
}