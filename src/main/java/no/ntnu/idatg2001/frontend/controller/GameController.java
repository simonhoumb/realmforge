package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.CustomUnitBuilder;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.backend.goals.GoldGoal;
import no.ntnu.idatg2001.backend.goals.HealthGoal;
import no.ntnu.idatg2001.backend.goals.InventoryGoal;
import no.ntnu.idatg2001.backend.goals.ScoreGoal;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.dao.UnitDAO;
import no.ntnu.idatg2001.frontend.view.EndGameView;
import no.ntnu.idatg2001.frontend.view.dialogs.ExitDialog;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.dialogs.GoalsDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.LoadGameDialog;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.dialogs.PauseMenuDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SaveGameDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.SettingsDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.YouDiedDialog;

public class GameController extends Controller<GameView> {

  private PauseMenuDialog pauseMenuDialog;
  private SettingsDialog settingsDialog;
  private LoadGameDialog loadGameDialog;
  private SaveGameDialog saveGameDialog;
  private YouDiedDialog youDiedDialog;
  private GoalsDialog goalsDialog;
  private GameSave currentGameSave;
  private Passage currentPassage;
  private GameView view;


  public GameController(GameView gameView, GameSave currentGameSave) {
    this.view = gameView;
    loadGameSave(currentGameSave);
  }


  public void loadGameSave(GameSave gameSaveToLoad) {
    this.currentGameSave = gameSaveToLoad;
    // Reset the current passage and update the view
    if (currentGameSave.getLastSavedPassage() == null) {
      this.currentPassage = currentGameSave.getGame().begin();
    } else {
      this.currentPassage = currentGameSave.getLastSavedPassage();
    }

    view.getPlayerNameLabel().setText(currentGameSave.getGame().getUnit().getUnitName());
    updatePlayerStats(); // Update the player's stats
    view.addLinksToButtons(currentPassage);
  }

  public void updatePlayerStats() {
    Unit playerUnit = getCurrentGameSave().getGame().getUnit();
    // Update the view with the player's stats
    view.getHealthBar()
        .setProgress((double) playerUnit.getUnitHealth() / playerUnit.getUnitHealthMax());
    view.getHealthLabel()
        .setText(String.format("%d/%d", playerUnit.getUnitHealth(), playerUnit.getUnitHealthMax()));
    view.getManaBar().setProgress((double) playerUnit.getUnitMana() / playerUnit.getUnitManaMax());
    view.getManaLabel()
        .setText(String.format("%d/%d", playerUnit.getUnitMana(), playerUnit.getUnitManaMax()));
    view.getGoldAmountLabel().setText(String.valueOf(playerUnit.getGold()));
    view.getScoreAmountLabel().setText(String.valueOf(playerUnit.getUnitScore()));
    System.out.println(playerUnit);
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
    view.addLinksToButtons(currentPassage);
    link.getActions().forEach(action -> action.execute(getCurrentGameSave().getGame().getUnit()));
    if (getCurrentGameSave().getGame().getUnit().getUnitHealth() <= 0) {
      onDeath();
    }
    populatePlayerInventoryListView();
    updatePlayerStats();
    event.consume();
  }


  public void onEndGameButtonPressed() {
    EndGameView endGameView = new EndGameView();
    Scene newScene = view.getScene();
    EndViewController endViewController = new EndViewController(endGameView);
    endGameView.setController(endViewController);
    newScene.setRoot(endGameView);
  }

  public void onDeath() {
    youDiedDialog = new YouDiedDialog();
    youDiedDialog.setController(this);
    youDiedDialog.initOwner(view.getScene().getWindow());
    youDiedDialog.initStyle(StageStyle.UNDECORATED);
    youDiedDialog.showAndWait();
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
    GameSave selectedItem = saveGameDialog.getSelectedGameSave();

    // Create a new unit instance for the game save
    Unit savedUnit = new CustomUnitBuilder()
        .withUnitHealth(getCurrentGameSave().getGame().getUnit().getUnitHealth())
        .withUnitHealthMax(getCurrentGameSave().getGame().getUnit().getUnitHealthMax())
        .withUnitMana(getCurrentGameSave().getGame().getUnit().getUnitMana())
        .withGold(getCurrentGameSave().getGame().getUnit().getGold())
        .withUnitScore(getCurrentGameSave().getGame().getUnit().getUnitScore())
        .withUnitName(getCurrentGameSave().getGame().getUnit().getUnitName())
        .withUnitInventory(new ArrayList<>(getCurrentGameSave().getGame().getUnit().getUnitInventory()))
        .build();

    GameSave newGameSave = new GameSave(savedUnit, getCurrentGameSave().getStory(), getCurrentGameSave().getGoals(),
        getCurrentGameSave().getPlayerName());
    newGameSave.savePassage(getCurrentPassage());

    if (selectedItem != null) {
      // Overwrite the entire unit of the selected game
      selectedItem.getGame().setUnit(savedUnit);
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
    updatePlayerStats();
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
    if (currentPassage == null) {
      currentPassage = currentGameSave.getGame().getStory().getOpeningPassage();
    } else {
    return this.currentPassage;
    }
    return currentPassage;
  }



  public void restartGame() {
      // Reset unit to default based on its type
      currentGameSave.getGame().getUnit().setGold(0);
      currentGameSave.getGame().getUnit().setUnitScore(0);
      currentGameSave.getGame().getUnit().setUnitHealth(currentGameSave.getGame().getUnit().getUnitHealthMax());
      currentGameSave.getGame().getUnit().setUnitMana(currentGameSave.getGame().getUnit().getUnitManaMax());
      currentGameSave.getGame().getUnit().clearInventory();
      currentGameSave.getStory().getOpeningPassage();
      currentPassage = currentGameSave.getGame().getStory().getOpeningPassage();
      view.addLinksToButtons(currentPassage);
      // Get a reference to the opening passage
      updatePlayerStats();
      GameSaveDAO.getInstance().update(getCurrentGameSave());
  }

  public void setCurrentPassage (Passage passage){
    this.currentPassage = passage;
  }
}


