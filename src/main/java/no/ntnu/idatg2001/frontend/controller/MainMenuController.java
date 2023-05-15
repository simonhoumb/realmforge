package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
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
import no.ntnu.idatg2001.frontend.view.StartNewGameView;

public class MainMenuController extends Controller<MainMenuView> {
  private SettingsDialog settingsDialog;

  public MainMenuController(MainMenuView view) {
    this.view = view;
  }

  public void onStartGameButtonPressed(ActionEvent event) {
    StartNewGameController startNewGameController;
    StartNewGameView startNewGameView;
    startNewGameView = new StartNewGameView();
    startNewGameController = new StartNewGameController(startNewGameView);
    startNewGameView.setController(startNewGameController);
    Scene newScene = view.getScene();
    onCloseSource(event);
    newScene.setRoot(startNewGameView);

    /* Starter selve spillet, skal flyttes
    GameController gameController;
    GameView gameView;
    gameView = new GameView();
    gameController = new GameController(gameView);
    gameView.setController(gameController);
    Scene newScene = view.getScene();
    onCloseSource(event);
    newScene.setRoot(gameView);
     */
  }

  public void onNewGameButtonPressed() {
    NewGameDialog newGameDialog = new NewGameDialog(this);
    newGameDialog.initOwner(view.getScene().getWindow());
    newGameDialog.showAndWait();
  }

  public void onCreateStoryButtonPressed(ActionEvent event) throws IOException {
    CreateStoryView createStoryView = new CreateStoryView();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    Scene newScene = view.getScene();
    onCloseSource(event);
    newScene.setRoot(createStoryView);
  }

  public void onLoadGameButtonPressed() {
    LoadGameDialog loadGameDialog = new LoadGameDialog(this);
    loadGameDialog.initOwner(view.getScene().getWindow());
    loadGameDialog.showAndWait();
  }

  public void onSettingsViewButtonPressed() {
    settingsDialog = new SettingsDialog(this);
    settingsDialog.initOwner(view.getScene().getWindow());
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

    //updates Main Menu.
    view.updateMainMenu();

    // Close the settings dialog.
    onCloseSource(event);
  }

  //TODO fix so all onSomething methods takes an event as a parameter and consume if not used.
  public void onExitViewButtonPressed(ActionEvent event) {
    event.consume();
    ExitDialog exitDialog = new ExitDialog(this);
    exitDialog.initOwner(view.getScene().getWindow());
    exitDialog.showAndWait();
  }
}
