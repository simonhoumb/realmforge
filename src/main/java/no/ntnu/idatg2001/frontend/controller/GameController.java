package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.backend.goals.GoldGoal;
import no.ntnu.idatg2001.backend.goals.HealthGoal;
import no.ntnu.idatg2001.backend.goals.InventoryGoal;
import no.ntnu.idatg2001.backend.goals.ScoreGoal;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.frontend.view.dialogs.ExitDialog;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.dialogs.GoalsDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.LoadGameDialog;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SaveGameDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SettingsDialog;

public class GameController extends Controller<GameView> {

  private PauseMenuDialog pauseMenuDialog;
  private SettingsDialog settingsDialog;
  private LoadGameDialog loadGameDialog;
  private SaveGameDialog saveGameDialog;
  private GoalsDialog goalsDialog;
  private GameSave currentGameSave;
  private Passage currentPassage;


  public GameController(GameView gameView, GameSave currentGameSave) {
    this.view = gameView;
    loadGameSave(currentGameSave);
  }


  public void loadGameSave(GameSave gameSaveToLoad) {
    this.currentGameSave = gameSaveToLoad;
    if (currentGameSave.getLastSavedPassage() == null) {
      this.currentPassage = currentGameSave.getGame().begin();
    } else {
      this.currentPassage = currentGameSave.getLastSavedPassage();
    }
    view.getPlayerNameLabel().setText(currentGameSave.getGame().getUnit().getUnitName());
    view.addLinksToButtons(currentPassage);
  }

  public void updateStats() {
    view.getHealthBar().setProgress((double) currentGameSave.getGame().getUnit().getUnitHealth()
        / currentGameSave.getGame().getUnit().getUnitHealthMax());
    view.getHealthLabel().setText(String.format("%d/%d", currentGameSave.getGame().getUnit().getUnitHealth(),
        currentGameSave.getGame().getUnit().getUnitHealthMax()));
    view.getManaBar().setProgress((double) currentGameSave.getGame().getUnit().getUnitMana()
        / currentGameSave.getGame().getUnit().getUnitManaMax());
    view.getManaLabel().setText(String.format("%d/%d", currentGameSave.getGame().getUnit().getUnitMana(),
        currentGameSave.getGame().getUnit().getUnitManaMax()));
    view.getGoldAmountLabel().setText(String.valueOf(currentGameSave.getGame().getUnit().getGold()));
    view.getScoreAmountLabel().setText(String.valueOf(currentGameSave.getGame().getUnit().getUnitScore()));
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

  public void onMenuButtonPressed(ActionEvent event) {
    pauseMenuDialog = new PauseMenuDialog(this);
    pauseMenuDialog.initOwner(view.getScene().getWindow());
    pauseMenuDialog.showAndWait();
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

    // Close the settings dialog.
    onCloseSource(event);
  }

  public void onExitViewButtonPressed(ActionEvent event) {
    event.consume();
    ExitDialog exitDialog = new ExitDialog(this);
    exitDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    exitDialog.showAndWait();
  }

  public void onDialogButtonPressed(ActionEvent event) {
    goalsDialog = new GoalsDialog(this);
    goalsDialog.initOwner(view.getScene().getWindow());
    goalsDialog.showAndWait();
  }

  public void onLinkPressed(ActionEvent event, Link link) {
    currentPassage = getCurrentGameSave().getGame().go(link);
    link.getActions().stream()
        .filter(action -> !action.getActionType().equals(ActionType.NONE))
        .forEach(action -> action.execute(getCurrentGameSave().getGame().getUnit()));
    view.addLinksToButtons(currentPassage);
    populatePlayerInventoryListView();
    event.consume();
  }

  public void onEndGameButtonPressed() {

  }

  public void onRestartGameButtonPressed() {
    restartGame();
  }

  // Method to check if all goals are completed
  public boolean areAllGoalsCompleted() {
    for (Goal goal : getCurrentGameSave().getGame().getGoals()) {
      if (!goal.isFulfilled(getCurrentGameSave().getGame().getUnit())) {
        return false;
      }
    }
    return true;
  }

  public void onSaveButtonPressed(ActionEvent event) {
    saveGameDialog = new SaveGameDialog(this);
    saveGameDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    configureSavedGameTableViewForSavedGame();
    populateSavedGameTableViewForSavedGames();
    saveGameDialog.showAndWait();
  }

  public void onSaveSelectedGame(ActionEvent event) {
    GameSave selectedItem = (saveGameDialog.getSelectedGameSave());
    GameSave newGameSave = new GameSave(getCurrentGameSave().getGame(),
        getCurrentGameSave().getGame().getUnit().getUnitName());
    newGameSave.savePassage(getCurrentPassage());
    if (selectedItem != null) {
      System.out.println(currentPassage.getTitle());
      selectedItem.setGame(newGameSave.getGame());
      selectedItem.setLastSavedPassage(newGameSave.getLastSavedPassage());
      selectedItem.setTimeOfSave(LocalDateTime.now());
      GameSaveDAO.getInstance().update(selectedItem);
    } else {
      GameSaveDAO.getInstance().add(newGameSave);
    }

    onCloseSource(event);
  }

  @Override
  public void onLoadGameButtonPressed(ActionEvent event) {
    loadGameDialog = new LoadGameDialog(this);
    loadGameDialog.setDeleteButtonDisabledProperty(false);
    configureSavedGamesTableView(event);
    populateSavedGamesTableView(event);
    loadGameDialog.initOwner(pauseMenuDialog.getDialogPane().getScene().getWindow());
    loadGameDialog.showAndWait();
  }

  @Override
  public void onLoadSelectedGame(ActionEvent event) {
    GameSave selectedGameSave = loadGameDialog.getSelectedGameSave();
    loadGameSave(selectedGameSave);
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

  public void populatePlayerInventoryListView() {
    view.getPlayerInventoryListView().getItems().clear();
    view.getPlayerInventoryListView().setItems(FXCollections.observableArrayList(
        getCurrentGameSave().getGame().getUnit().getUnitInventory()));
  }


  private void populateSavedGameTableViewForSavedGames() {
    saveGameDialog.getSavedGamesTableView().getItems().clear();
    List<GameSave> gameSaves = GameSaveDAO.getInstance().getAll();
    Collections.reverse(gameSaves);
    saveGameDialog.getSavedGamesTableView()
        .setItems(FXCollections.observableArrayList(gameSaves));
  }

  @Override
  public void configureSavedGamesTableView(ActionEvent event) {
    event.consume();
    loadGameDialog.getNameColumn()
        .setCellValueFactory(new PropertyValueFactory<>("storyAndLastPassage"));
    loadGameDialog.getDateTimeColumn()
        .setCellValueFactory(new PropertyValueFactory<>("timeOfSaveFormatted"));
    loadGameDialog.getPlayerColumn().setCellValueFactory(new PropertyValueFactory<>("playerName"));
  }


  public void configureSavedGameTableViewForSavedGame() {
    saveGameDialog.getNameColumn()
        .setCellValueFactory(new PropertyValueFactory<>("storyAndLastPassage"));
    saveGameDialog.getDateTimeColumn()
        .setCellValueFactory(new PropertyValueFactory<>("timeOfSaveFormatted"));
    saveGameDialog.getPlayerColumn().setCellValueFactory(new PropertyValueFactory<>("playerName"));
  }

  public Object getCurrentAmount(Goal goal) {
    if (goal instanceof HealthGoal) {
      return getCurrentGameSave().getGame().getUnit().getUnitHealth();
    } else if (goal instanceof ScoreGoal) {
      return getCurrentGameSave().getGame().getUnit().getUnitScore();
    } else if (goal instanceof GoldGoal){
      return getCurrentGameSave().getGame().getUnit().getGold();
    } else if (goal instanceof InventoryGoal) {
      if (getCurrentGameSave().getGame().getUnit().getUnitInventory().equals(goal.getGoalValue())) {
        return getCurrentGameSave().getGame().getUnit().getUnitInventory().equals(goal.getGoalValue());
      } else {
        return goalsDialog.getResourceBundle().getString("dialog.no")+ ": " + goal.getGoalValue() + " " +goalsDialog.getResourceBundle().getString("dialog.inInventory");
      }
    }
    return "";
  }

  public boolean isGoalReached(Goal goal) {
    if (goal instanceof HealthGoal) {
      // Replace this with your logic to check if the health goal is reached
      int currentHealth = getCurrentGameSave().getGame().getUnit().getUnitHealth();
      int goalValue = Integer.parseInt(goal.getGoalValue().toString());
      return currentHealth >= goalValue;
    } else if (goal instanceof ScoreGoal) {
      // Replace this with your logic to check if the score goal is reached
      int currentScore = getCurrentGameSave().getGame().getUnit().getUnitScore();
      int goalValue = Integer.parseInt(goal.getGoalValue().toString());
      return currentScore >= goalValue;
    } else if (goal instanceof GoldGoal) {
      int currentGold = getCurrentGameSave().getGame().getUnit().getGold();
      int goalValue = Integer.parseInt(goal.getGoalValue().toString());
      return currentGold >= goalValue;
    } else if (goal instanceof InventoryGoal) {
      List<String> currentInventory = getCurrentGameSave().getGame().getUnit().getUnitInventory();
      String inventoryGoal = goal.getGoalValue().toString();
      return currentInventory.contains(inventoryGoal);
    }
    return false;
  }

  public GameSave getCurrentGameSave() {
    return this.currentGameSave;
  }

  public Passage getCurrentPassage() {
    return this.currentPassage;
  }

  public void restartGame() {

  }

  public void setCurrentPassage (Passage passage){
    this.currentPassage = passage;
  }
}


