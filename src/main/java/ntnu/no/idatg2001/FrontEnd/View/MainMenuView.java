package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class MainMenuView extends BorderPane {

  private static final String cssFile = "/CSS/MainMenuStyleSheet.css";
  private JFXButton newGameButton;
  private JFXButton loadGameButton;
  private JFXButton settingsButton;
  private JFXButton exitGameButton;
  private MediaPlayer mediaPlayer;


  public MainMenuView(double width, double height) throws IOException {
    setPrefSize(width,height);
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    getStylesheets().add(cssFile);

    Image logoImage = new Image(getClass().getResource("/images/Menutwo.jpg").openStream());
    ImageView logoImageView = new ImageView(logoImage);
    logoImageView.setPreserveRatio(true);
    logoImageView.setFitWidth(width);
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

    settingsButton = new JFXButton("Settings");
    settingsButton.setId("settingsButton");

    exitGameButton = new JFXButton("Exit Game");
    exitGameButton.setId("exitButton");

    exitGameButton.setOnAction(event -> {
      ExitDialog dialog = new ExitDialog();
      dialog.initOwner(getScene().getWindow());
      dialog.showAndWait();
    });

      //Add button action

      //a box to hold the buttons
      HBox buttonBox = new HBox(10, newGameButton, loadGameButton, settingsButton, exitGameButton);
      buttonBox.setAlignment(Pos.CENTER);

      //a box to hold the buttons and the Title
      VBox contentBox = new VBox(20, logoImageView, buttonBox);
      contentBox.setAlignment(Pos.CENTER);
      contentBox.setPadding(new Insets(50, 0, 0, 0));
      setCenter(contentBox);

    }
  }