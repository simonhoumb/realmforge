package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;

public class GameController extends Controller<GameController> {
  private GameView gameView;
  PauseMenuDialog pauseMenuDialog;

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

  public void onSettingsViewButtonPressed() {
    SettingsDialog settingsDialog = new SettingsDialog(this);
    settingsDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    settingsDialog.showAndWait();
  }

  public void onSettingSaveButtonPressed(ActionEvent event) {
    // Update the model with new settings data
    SettingsModel.getInstance().setLanguageSelection(settingsDialog.getLanguageSelection());
    SettingsModel.getInstance().setVolumeSliderValue(settingsDialog.getVolumeSliderValue());
    SettingsModel.getInstance().setMuted(settingsDialog.isMuteCheckBoxSelected());

    // Save the settings data to a file.
    SettingsModel.getInstance().saveSettings();

    pauseMenuDialog.update();
    gameView.update();

    // Close the settings dialog.
    onCloseSource(event);
  }
}
