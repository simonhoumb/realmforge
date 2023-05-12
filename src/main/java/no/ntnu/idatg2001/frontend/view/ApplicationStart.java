package no.ntnu.idatg2001.frontend.view;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import no.ntnu.idatg2001.frontend.controller.Controller;
import no.ntnu.idatg2001.frontend.controller.MainMenuController;
import no.ntnu.idatg2001.dao.GameDAO;

public class ApplicationStart extends Application {
  private Controller mainMenuController;
  private View view;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    Platform.runLater(() -> {
      Screen screen = Screen.getPrimary();
      double screenWidth = screen.getBounds().getWidth();
      double screenHeight = screen.getBounds().getWidth();
      // Create the MainMenuView object
      try {
        view = new MainMenuView();
        mainMenuController = new MainMenuController((MainMenuView) view);
        view.setController(mainMenuController);

      } catch (IOException e) {
        e.printStackTrace();
      }
      // Create a new Scene with the MainMenuView as its root
      Scene mainMenuScene = new Scene((Parent) view);

      //Set up the Primary View
      Image icon = new Image(Objects.requireNonNull(getClass().getResource("/images/fantasy.png")).toExternalForm());
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
      primaryStage.setScene(mainMenuScene);
      primaryStage.setOnCloseRequest(windowEvent -> {
        GameDAO.getInstance().close();
        Platform.exit();
        System.exit(0);
      });
      primaryStage.show();
    });
  }

  public static void startApplication(String[] args) {
    launch();
  }
}