package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.ExitDialog;
import no.ntnu.idatg2001.frontend.view.LoadGameDialog;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.NewGameDialog;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;
import no.ntnu.idatg2001.dao.GameDAO;

public class MainMenuController {
  private MainMenuView menuView;
  private NewGameDialog newGameDialog;
  private LoadGameDialog loadGameDialog;
  private SettingsDialog settingsDialog;
  private CreateStoryView createStoryView;
  private CreateStoryController createStoryController;

  public MainMenuController(MainMenuView menuView) throws IOException {
    this.menuView =  menuView;
  }

  public void onNewGameButtonPressed() {
    newGameDialog = new NewGameDialog(this);
    newGameDialog.initOwner(menuView.getScene().getWindow());
    newGameDialog.showAndWait();
  }

  public void onCreateStoryButtonPressed(ActionEvent event) throws IOException {
    createStoryView = new CreateStoryView();
    createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    Scene newScene = menuView.getScene();
    onCloseSource(event);
    newScene.setRoot(createStoryView);
  }

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public void onLoadGameButtonPressed() {
    loadGameDialog = new LoadGameDialog(this);
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

  public void onExitApplication(ActionEvent event) {
    GameDAO.getInstance().close();
    Platform.exit();
    System.exit(0);

  }
}
