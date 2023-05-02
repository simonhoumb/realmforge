package no.ntnu.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import no.ntnu.idatg2001.FrontEnd.Controller.SettingsController;
import no.ntnu.idatg2001.FrontEnd.Model.SettingsModel;


public class MainMenuView extends BorderPane {

  private static final String cssFile = "/CSS/MainMenuStyleSheet.css";
  private JFXButton newGameButton;
  private JFXButton loadGameButton;
  private JFXButton settingsButton;
  private JFXButton exitGameButton;
  private MediaPlayer mediaPlayer;
  private SettingsController settingsController;
  private SettingsDialog settingsDialog;
  private NewGameDialog newGameDialog;
  private SettingsModel settings = new SettingsModel();
  private ResourceBundle resourceBundle;
  private LoadGameDialog loadGameDialog;



  public MainMenuView(double width, double height) throws IOException {
    setPrefSize(width, height);
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    getStylesheets().add(cssFile);
    menuView();
    playMusic();
  }

  public MainMenuView menuView() throws IOException {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    //Buttons for the menu

    newGameButton = new JFXButton(resourceBundle.getString("menu.newGame"));
    newGameButton.setId("newGameButton");
    newGameButton.setOnAction(actionEvent -> {
      newGameView();
      newGameDialog.initOwner(getScene().getWindow());
      Optional<ButtonType> result = newGameDialog.showAndWait();
      if (result.isPresent()) {
        ButtonType choice = result.get();
      }

    });
    loadGameButton = new JFXButton(resourceBundle.getString("menu.loadGame"));
    loadGameButton.setId("loadGameButton");

    loadGameButton.setOnAction(actionEvent -> {
      loadGameView();
      loadGameDialog.initOwner(getScene().getWindow());
      loadGameDialog.showAndWait();
    } );

    settingsButton = new JFXButton(resourceBundle.getString("menu.settings"));
    settingsButton.setId("settingsButton");

    settingsButton.setOnAction(event -> {
      settingsView();
      settingsDialog.initOwner(getScene().getWindow());
      settingsController.showAndWait();
      updateButtonLabels();
    });

    exitGameButton = new JFXButton(resourceBundle.getString("menu.exitGame"));
    exitGameButton.setOnAction(event1 -> {
      ExitDialogView exitDialogView = new ExitDialogView(settings);
      exitDialogView.initOwner(getScene().getWindow());
      exitDialogView.showAndWait();
    });

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
    contentBox.widthProperty().addListener(((observableValue, oldValue, newValue) -> {
      imageView.setFitWidth(newValue.doubleValue() * 0.9);
    }));
    contentBox.heightProperty().addListener(((observableValue, oldValue, newValue) -> {
      imageView.setFitHeight(newValue.doubleValue() * 0.9);
    }));
    //return contentBox;
    return this;
  }

  private void playMusic() {
    String musicFile = "/music/One-Bard-Band.mp3";
    Media music = new Media(getClass().getResource(musicFile).toExternalForm());
    mediaPlayer = new MediaPlayer(music);
    mediaPlayer.setVolume(settings.getVolumeSliderValue() / 100);
    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
    if (!settings.isMuted()) {
      mediaPlayer.play();
    }
  }

  private void settingsView() {
    settingsDialog = new SettingsDialog(settings);
    try {
      settingsController = new SettingsController(settings, settingsDialog, mediaPlayer);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  private void newGameView() {
    newGameDialog = new NewGameDialog(settings, getScene(), this);
  }
  private void loadGameView() {
    loadGameDialog = new LoadGameDialog(settings);
  }

  public void updateButtonLabels() {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    newGameButton.setText(resourceBundle.getString("menu.newGame"));
    loadGameButton.setText(resourceBundle.getString("menu.loadGame"));
    settingsButton.setText(resourceBundle.getString("menu.settings"));
    exitGameButton.setText(resourceBundle.getString("menu.exitGame"));
  }

  public JFXButton getExitGameButton() {
    return exitGameButton;
  }

  public JFXButton getNewGameButton() {
    return newGameButton;
  }

  public JFXButton getLoadGameButton() {
    return loadGameButton;
  }

  public JFXButton getSettingsButton() {
    return settingsButton;
  }
  public MainMenuView getMainScene() throws IOException {
    System.out.println("I got there");
    return menuView();
  }
}