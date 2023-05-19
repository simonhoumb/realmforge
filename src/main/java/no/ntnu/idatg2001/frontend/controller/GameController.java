package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import no.ntnu.idatg2001.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.frontend.view.ExitDialog;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.LoadGameDialog;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SettingsDialog;

public class GameController extends Controller<GameView> {
  private PauseMenuDialog pauseMenuDialog;
  private SettingsDialog settingsDialog;
  private LoadGameDialog loadGameDialog;


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

  public void onLinkPressed(ActionEvent event, Link link) {
    Passage passageToGoTo = view.getCurrentGameSave().getGame().go(link);
    view.clearGameTextFlow();
    view.addToGameTextFlow(passageToGoTo);
    event.consume();
  }

  public void onSaveButtonPressed() {
    GameSave newGameSave = new GameSave(view.getCurrentGameSave().getGame(),
        view.getCurrentGameSave().getGame().getUnit().getUnitName());
    newGameSave.savePassage(view.getCurrentPassage());
    GameSaveDAO.getInstance()
        .add(newGameSave);
  }

  @Override
  public void onLoadGameButtonPressed(ActionEvent event) {
    loadGameDialog = new LoadGameDialog(this);
    configureSavedGamesTableView(event);
    populateSavedGamesTableView(event);
    loadGameDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    loadGameDialog.showAndWait();
  }

  @Override
  public void onLoadSelectedGame(ActionEvent event) {
    GameSave selectedGameSave = loadGameDialog.getSelectedGameSave();
    view.loadGameSave(selectedGameSave);
    pauseMenuDialog.close();
    onCloseSource(event);
  }

  @Override
  public void populateSavedGamesTableView(ActionEvent event) {
    event.consume();
    loadGameDialog.getSavedGamesTableView().getItems().clear();
    List<GameSave> gameSaves = GameSaveDAO.getInstance().getAll();
    Collections.reverse(gameSaves);
    loadGameDialog.getSavedGamesTableView()
        .setItems(FXCollections.observableArrayList(gameSaves));
  }

  @Override
  public void configureSavedGamesTableView(ActionEvent event) {
    event.consume();
    loadGameDialog.getNameColumn().setCellValueFactory(new PropertyValueFactory<>("storyAndLastPassage"));
    loadGameDialog.getDateTimeColumn().setCellValueFactory(new PropertyValueFactory<>("timeOfSaveFormatted"));
    loadGameDialog.getPlayerColumn().setCellValueFactory(new PropertyValueFactory<>("playerName"));
  }
}
