package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;

public class GameController {
  private GameView gameView;

  public GameController(GameView gameView) {
    this.gameView = gameView;
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = gameView.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

}
