package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.ExitDialog;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.LoadGameDialog;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.NewGameDialog;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;
import no.ntnu.idatg2001.dao.GameDAO;

public class MainMenuController extends Controller<MainMenuController> {
  private MainMenuView menuView;
  private SettingsDialog settingsDialog;

  public MainMenuController(MainMenuView menuView) throws IOException {
    this.menuView =  menuView;
  }

  public void onStartGameButtonPressed(ActionEvent event) {
    GameController gameController;
    GameView gameView;
    gameView = new GameView();
    gameController = new GameController(gameView);
    gameView.setController(gameController);
    Scene newScene = menuView.getScene();
    onCloseSource(event);
    newScene.setRoot(gameView);
  }

  public void onNewGameButtonPressed() {
    NewGameDialog newGameDialog = new NewGameDialog(this);
    newGameDialog.initOwner(menuView.getScene().getWindow());
    newGameDialog.showAndWait();
  }

  public void onCreateStoryButtonPressed(ActionEvent event) throws IOException {
    CreateStoryView createStoryView = new CreateStoryView();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    Scene newScene = menuView.getScene();
    onCloseSource(event);
    newScene.setRoot(createStoryView);
  }

  public void onLoadGameButtonPressed() {
    LoadGameDialog loadGameDialog = new LoadGameDialog(this);
    loadGameDialog.initOwner(menuView.getScene().getWindow());
    loadGameDialog.showAndWait();
  }

  public void onSettingsViewButtonPressed() {
    settingsDialog = new SettingsDialog(this);
    settingsDialog.initOwner(menuView.getScene().getWindow());
    settingsDialog.showAndWait();
  }

  public void onSettingSaveButtonPressed(ActionEvent event) {
    // Update the model with new settings data
    SettingsModel.getInstance().setLanguageSelection(settingsDialog.getLanguageSelection());
    SettingsModel.getInstance().setVolumeSliderValue(settingsDialog.getVolumeSliderValue());
    SettingsModel.getInstance().setMuted(settingsDialog.isMuteCheckBoxSelected());

    // Save the settings data to a file.
    SettingsModel.getInstance().saveSettings();

    //updates Main Menu.
    menuView.updateMainMenu();

    // Close the settings dialog.
    onCloseSource(event);
  }

  public void onExitViewButtonPressed() {
    ExitDialog exitDialog = new ExitDialog(this);
    exitDialog.initOwner(menuView.getScene().getWindow());
    exitDialog.showAndWait();
  }
}
