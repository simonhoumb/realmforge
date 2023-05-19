package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatg2001.GameSave;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Mage;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Rogue;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Warrior;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.backend.goals.GoldGoal;
import no.ntnu.idatg2001.backend.goals.HealthGoal;
import no.ntnu.idatg2001.backend.goals.InventoryGoal;
import no.ntnu.idatg2001.backend.goals.ScoreGoal;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.GameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;
import no.ntnu.idatg2001.frontend.view.StartNewGameView;
import no.ntnu.idatg2001.backend.utility.CheckIfValid;

public class StartNewGameController extends Controller<StartNewGameView> {

  CheckIfValid checkIfValid = new CheckIfValid();

  public StartNewGameController(StartNewGameView view) {
    this.view = view;
    configureStoryTableView();
    populateStoryTableView();
  }

  public void configureStoryTableView() {
    view.getStoryColumn().setCellValueFactory(new PropertyValueFactory<>("title"));
  }
  public void populateStoryTableView() {
    view.getStoryTableView().getItems().clear();
    view.getStoryTableView()
        .setItems(FXCollections.observableArrayList(StoryDAO.getInstance().getAll()));
  }

  private Unit createUnitBySelectedClass() {
    String mageString = this.view.getResourceBundle().getString("startNewGame.classType.mage");
    String rogueString = this.view.getResourceBundle().getString("startNewGame.classType.rogue");
    String warriorString = this.view.getResourceBundle().getString("startNewGame.classType.warrior");
    String rangerString = this.view.getResourceBundle().getString("startNewGame.classType.ranger");
    if (getSelectedClassInComboBox() != null){
      if (getSelectedClassInComboBox().equals(mageString)) {
        return new Mage(view.getNameField().getText());
      } else if (getSelectedClassInComboBox().equals(rogueString)) {
        return new Rogue(view.getNameField().getText());
      } else if (getSelectedClassInComboBox().equals(warriorString)) {
        return new Warrior(view.getNameField().getText());
      } else if (getSelectedClassInComboBox().equals(rangerString)) {
        return new Ranger(view.getNameField().getText());
      }
    }
    return null;
  }

  private List<Goal> getSelectedGoals() {
    ArrayList<Goal> selectedGoals = new ArrayList<>();
    if (view.getHealthGoalCheckBox().isSelected() && checkIfValid.isInteger(view.getHealthGoalField().getText())) {
      selectedGoals.add(new HealthGoal(Integer.parseInt(view.getHealthGoalField().getText())));
    }
    if (view.getGoldGoalCheckBox().isSelected() && checkIfValid.isInteger(view.getGoldGoalField().getText())) {
      selectedGoals.add(new GoldGoal(Integer.parseInt(view.getGoldGoalField().getText())));
    }
    if (view.getInventoryGoalCheckBox().isSelected()) {
      ArrayList<String> itemGoals = new ArrayList<>();
      itemGoals.add(view.getInventoryGoalField().getText());
      selectedGoals.add(new InventoryGoal(itemGoals));
    }
    if (view.getScoreGoalCheckBox().isSelected() && checkIfValid.isInteger(view.getScoreGoalField().getText())) {
      selectedGoals.add(new ScoreGoal(Integer.parseInt(view.getScoreGoalField().getText())));
    }
    return selectedGoals;
  }

  public void onStartButtonPressed(ActionEvent event) {
    event.consume();

    if (isValidStart()) {
      Game newGame = new Game(createUnitBySelectedClass(),
          getSelectedStoryInTableView(),
          getSelectedGoals());
      GameDAO.getInstance().add(newGame);
      GameSave newGameSave = new GameSave(newGame, newGame.getUnit().getUnitName());
      GameSaveDAO.getInstance()
          .add(newGameSave);
      GameController gameController;
      GameView gameView;
      gameView = new GameView(newGameSave);
      gameController = new GameController(gameView);
      gameView.setController(gameController);
      Scene newScene = view.getScene();
      newScene.setRoot(gameView);
    } else {
      showAlerts();
    }
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    event.consume();
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = view.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  public Story getSelectedStoryInTableView() {
    return view.getStoryTableView().getSelectionModel().getSelectedItem();
  }

  public String getSelectedClassInComboBox() {
    return view.getClassComboBox().getSelectionModel().getSelectedItem();
  }

  private boolean areTextFieldsValid() {
    for (TextField textField : view.getGoalTextFields()) {
      if (!textField.isDisabled()
          && (textField.getText().isEmpty() || textField.getText().isBlank())) {
        System.out.println("Text field is empty");
        return false;
      }
    }
    System.out.println("Text fields are valid");
    return true;
  }

  private boolean isValidStart() {
    return areTextFieldsValid() && getSelectedStoryInTableView() != null
        && !view.getNameField().getText().isEmpty() && !view.getNameField().getText().isBlank()
        && createUnitBySelectedClass() != null;
  }

  private void showAlerts() {
    Alert alert = new Alert(AlertType.ERROR, "Please choose a story, a class and a name."
        + "\n" + "If you choose a goal, please fill in the field.");
    alert.initOwner(view.getScene().getWindow());
    alert.showAndWait();
  }
}
