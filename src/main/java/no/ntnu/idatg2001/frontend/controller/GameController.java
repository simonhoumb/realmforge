package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.view.ExitDialog;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SettingsDialog;

public class GameController extends Controller<GameView> {
  private PauseMenuDialog pauseMenuDialog;
  private SettingsDialog settingsDialog;

  public GameController(GameView gameView) {
    this.view = gameView;
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    event.consume();
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = view.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  public void onEscapeButtonPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      pauseMenuDialog = new PauseMenuDialog(this);
      pauseMenuDialog.initOwner(view.getScene().getWindow());
      pauseMenuDialog.showAndWait();
    }
  }

  public void onSettingsViewButtonPressed() {
    settingsDialog = new SettingsDialog(this);
    System.out.println(pauseMenuDialog.getDialogPane().getScene().getWindow());
    settingsDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    settingsDialog.showAndWait();
  }

  @Override
  public void onSettingSaveButtonPressed(ActionEvent event) {
    // Update the model with new settings data
    SettingsModel.getInstance().setLanguageSelection(settingsDialog.getLanguageSelection());
    SettingsModel.getInstance().setVolumeSliderValue(settingsDialog.getVolumeSliderValue());
    SettingsModel.getInstance().setMuted(settingsDialog.isMuteCheckBoxSelected());

    // Save the settings data to a file.
    SettingsModel.getInstance().saveSettings();

    pauseMenuDialog.update();
    view.update();

    // Close the settings dialog.
    onCloseSource(event);
  }

  public void onExitViewButtonPressed(ActionEvent event) {
    event.consume();
    ExitDialog exitDialog = new ExitDialog(this);
    exitDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    exitDialog.showAndWait();
  }
}
