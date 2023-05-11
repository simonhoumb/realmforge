package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;

public class GameController {
  private GameView gameView;
  private PauseMenuDialog pauseMenuDialog;
  private SettingsDialog settingsDialog;

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

  public void onEscapeButtonPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      pauseMenuDialog = new PauseMenuDialog(this);
      pauseMenuDialog.initOwner(gameView.getScene().getWindow());
      pauseMenuDialog.showAndWait();
    }
  }

  /**
   * Se ChatGPT historie for hjelp, (kanskje?)
  public void onSettingsViewButtonPressed() {
    settingsDialog = new SettingsDialog(this);
    settingsDialog.initOwner(gameView.getScene().getWindow());
    settingsDialog.showAndWait();
  }
   */

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public void onExitApplication(ActionEvent event) {
    GameDAO.getInstance().close();
    Platform.exit();
    System.exit(0);
  }

}
