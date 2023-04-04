package ntnu.no.idatg2001.FrontEnd.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ApplicationStart extends Application {

  private static final double SCREEN_WIDTH = 800;
  private static final double SCREEN_HEIGHT = 700;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Platform.runLater(() -> {
      Screen screen = Screen.getPrimary();
      double screenWidth = screen.getBounds().getWidth();
      double screenHeight = screen.getBounds().getWidth();
      MainMenuView menuView = null;
      try {
        menuView = new MainMenuView(screenHeight, screenWidth);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      Scene scene = new Scene(menuView, screenHeight, screenWidth);
      Image icon = new Image(getClass().getResource("/images/fantasy.png").toExternalForm());
      primaryStage.setScene(scene);

      primaryStage.setX((screenWidth - primaryStage.getWidth()) / 2);
      primaryStage.setY((screenHeight - primaryStage.getHeight()) / 2);

      primaryStage.setWidth(screenWidth * 0.8);
      primaryStage.setHeight(screenHeight * 0.8);
      primaryStage.setFullScreen(true);
      primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Ctrl+Alt+F"));
      primaryStage.setFullScreenExitHint("");
      primaryStage.getIcons().add(icon);
      primaryStage.setResizable(true);
      primaryStage.setMinHeight(200);
      primaryStage.setMinWidth(200);
      primaryStage.setTitle("StoryGameWIP");
      primaryStage.show();
    });
  }

  public static void startApplication(String[] args) {
    launch();
  }
}