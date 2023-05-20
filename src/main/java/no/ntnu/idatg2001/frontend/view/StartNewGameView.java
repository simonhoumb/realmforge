package no.ntnu.idatg2001.frontend.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.frontend.controller.StartNewGameController;

public class StartNewGameView extends BorderPane {

  private ResourceBundle resourceBundle;
  private StartNewGameController controller;
  private HBox hbox;
  private VBox vBox1;
  private VBox vBox2;
  private TableView<Story> storyTableView;
  private TableColumn<Story, String> storyColumn;
  private Label playerLabel;
  private Label nameLabel;
  private TextField nameField;
  private ComboBox<String> classComboBox;
  private Label goalsLabel;
  private Label classStatsLabel;
  private TextField healthTextField;
  private TextField goldTextField;
  private TextField healthGoalField;
  private TextField goldGoalField;
  private TextField inventoryGoalField;
  private TextField scoreGoalField;
  private List<TextField> goalTextFields;
  private CheckBox healthGoalCheckBox;
  private CheckBox goldGoalCheckBox;
  private CheckBox inventoryGoalCheckBox;
  private CheckBox scoreGoalCheckBox;
  private HBox buttonHBox;
  private Button startButton;
  private Button backButton;
  private TextField manaTextField;
  private TextField damageTextField;
  private TextField criticalChanceTextField;
  private TextField armourTextField;

  public StartNewGameView() {
    resourceBundle = ResourceBundle.getBundle("languages/startNewGameView", SettingsModel.getInstance().getLocale());
    createComboBox();
    createGoalCheckBoxes();
    createGoalTextFields();
    String cssFile = "/css/StartNewGameView.css";
    setBackground(new Background(new BackgroundFill(Color.rgb(25, 25, 25, 0.9), null, null)));
    getStylesheets().add(cssFile);
    createButtonHBox();
    createLayout();
    update();
  }

  private void createLayout() {
    storyTableView = new TableView<>();
    storyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    storyColumn = new TableColumn<>("Story");
    storyTableView.getColumns().add(storyColumn);
    setLeft(storyTableView);

    hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(20);

    vBox1 = new VBox();
    vBox1.setAlignment(Pos.CENTER); // Center alignment
    vBox1.setSpacing(10);

    playerLabel = new Label("Player");
    playerLabel.setPadding(new Insets(5));
    playerLabel.setFont(new Font("System Bold", 12));

    vBox2 = new VBox();
    vBox2.setAlignment(Pos.CENTER); // Center alignment
    vBox2.setSpacing(10);

    Separator separator1 = new Separator();
    separator1.setMaxWidth(300);
    separator1.setPadding(new Insets(10, 0, 10, 0));

    nameLabel = new Label("Name");

    nameField = new TextField();
    nameField.setMaxWidth(200);

    Separator separator2 = new Separator();
    separator2.setMaxWidth(300);
    separator2.setPadding(new Insets(10, 0, 10, 0));

    goalsLabel = new Label("Goals");
    goalsLabel.setFont(new Font("System Bold", 12));

    Separator separator3 = new Separator();
    separator3.setMaxWidth(300);
    separator3.setPadding(new Insets(10, 0, 10, 0));

    createGoalCheckBoxes();
    createGoalTextFields();

    GridPane goalGridPane = createGoalGridPane();
    goalGridPane.add(healthGoalCheckBox, 0, 0);
    goalGridPane.add(healthGoalField, 1, 0);

    goalGridPane.add(goldGoalCheckBox, 0, 1);
    goalGridPane.add(goldGoalField, 1, 1);

    goalGridPane.add(inventoryGoalCheckBox, 0, 2);
    goalGridPane.add(inventoryGoalField, 1, 2);

    goalGridPane.add(scoreGoalCheckBox, 0, 3);
    goalGridPane.add(scoreGoalField, 1, 3);

    createClassStatsRow();

    vBox1.getChildren().addAll(playerLabel, separator1, nameLabel, nameField, separator2,
        classComboBox, separator3, goalsLabel, goalGridPane, buttonHBox);

    Separator separator4 = new Separator();
    separator4.setPadding(new Insets(0, 20, 0, 20));

    hbox.getChildren().addAll(vBox1, separator4, vBox2);

    setCenter(hbox);
  }



  private void createClassStatsRow() {
    classStatsLabel = new Label("Class Stats");
    classStatsLabel.setFont(new Font("System Bold", 12));

    GridPane classStatsGridPane = new GridPane();
    classStatsGridPane.setHgap(15);
    classStatsGridPane.setVgap(10);
    classStatsGridPane.setAlignment(Pos.TOP_CENTER);

    Label healthLabel = new Label("Health:");
    healthTextField = new TextField();
    healthTextField.setMaxWidth(100);
    healthTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    healthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        healthTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label manaLabel = new Label("Mana:");
    manaTextField = new TextField();
    manaTextField.setMaxWidth(100);
    manaTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    manaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        manaTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label goldLabel = new Label("Gold:");
    goldTextField = new TextField();
    goldTextField.setMaxWidth(100);
    goldTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    goldTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        goldTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label damageLabel = new Label("Damage:");
    damageTextField = new TextField();
    damageTextField.setMaxWidth(100);
    damageTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    damageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        damageTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label criticalChanceLabel = new Label("Critical Chance:");
    criticalChanceTextField = new TextField();
    criticalChanceTextField.setMaxWidth(100);
    criticalChanceTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    criticalChanceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        criticalChanceTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label armourLabel = new Label("Armour:");
    armourTextField = new TextField();
    armourTextField.setMaxWidth(100);
    armourTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    armourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        armourTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    classStatsGridPane.add(healthLabel, 0, 0);
    classStatsGridPane.add(healthTextField, 1, 0);
    classStatsGridPane.add(manaLabel, 0, 1);
    classStatsGridPane.add(manaTextField, 1, 1);
    classStatsGridPane.add(goldLabel, 0, 2);
    classStatsGridPane.add(goldTextField, 1, 2);
    classStatsGridPane.add(damageLabel, 0, 3);
    classStatsGridPane.add(damageTextField, 1, 3);
    classStatsGridPane.add(criticalChanceLabel, 0, 4);
    classStatsGridPane.add(criticalChanceTextField, 1, 4);
    classStatsGridPane.add(armourLabel, 0, 5);
    classStatsGridPane.add(armourTextField, 1, 5);

    vBox2.getChildren().addAll(classStatsLabel, classStatsGridPane);
  }



  private GridPane createGoalGridPane() {
    GridPane goalGridPane = new GridPane();
    goalGridPane.setHgap(15);
    goalGridPane.setVgap(10);
    goalGridPane.setAlignment(Pos.CENTER);
    goalGridPane.setPrefSize(100, 200);
    return goalGridPane;
  }

  private void createComboBox() {
    classComboBox = new ComboBox<>();
    classComboBox.setPromptText("Class");
    classComboBox.setPrefWidth(200);
    classComboBox.setOnAction(event -> {
      if (classComboBox.getValue() != null) {
        controller.updateClassStats();
      }
    });
  }

  private void createGoalCheckBoxes() {
    healthGoalCheckBox = new CheckBox("Health");
    goldGoalCheckBox = new CheckBox("Gold");
    inventoryGoalCheckBox = new CheckBox("Inventory");
    scoreGoalCheckBox = new CheckBox("Score");
  }

  private void createGoalTextFields() {
    String digitRegexPattern = "-?\\d*";
    healthGoalField = new TextField();
    healthGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches(digitRegexPattern)) {
        healthGoalField.setText(oldValue);
      }
    });
    healthGoalField.setMaxWidth(200);
    healthGoalField.disableProperty().bind(healthGoalCheckBox.selectedProperty().not());

    goldGoalField = new TextField();
    goldGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches(digitRegexPattern)) {
        goldGoalField.setText(oldValue);
      }
    });
    goldGoalField.setMaxWidth(200);
    goldGoalField.disableProperty().bind(goldGoalCheckBox.selectedProperty().not());

    inventoryGoalField = new TextField();
    inventoryGoalField.setMaxWidth(200);
    inventoryGoalField.disableProperty().bind(inventoryGoalCheckBox.selectedProperty().not());

    scoreGoalField = new TextField();
    scoreGoalField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches(digitRegexPattern)) {
        scoreGoalField.setText(oldValue);
      }
    });
    scoreGoalField.setMaxWidth(200);
    scoreGoalField.disableProperty().bind(scoreGoalCheckBox.selectedProperty().not());

    goalTextFields = new ArrayList<>();
    goalTextFields.add(healthGoalField);
    goalTextFields.add(goldGoalField);
    goalTextFields.add(inventoryGoalField);
    goalTextFields.add(scoreGoalField);
  }

  private void createButtonHBox() {
    buttonHBox = new HBox();
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setPrefHeight(40.0);
    buttonHBox.setSpacing(10);
    startButton = new Button("Start");
    startButton.setPrefSize(100, 40);
    startButton.setOnAction(event -> controller.onStartButtonPressed(event));
    backButton = new Button("Back");
    backButton.setPrefSize(100, 40);
    backButton.setCancelButton(true);
    backButton.setOnAction(event -> {
      try {
        controller.onBackToMainMenuButtonPressed(event);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    buttonHBox.getChildren().addAll(startButton, backButton);
  }

  public TableView<Story> getStoryTableView() {
    return storyTableView;
  }

  public TableColumn<Story, String> getStoryColumn() {
    return storyColumn;
  }

  public CheckBox getHealthGoalCheckBox() {
    return healthGoalCheckBox;
  }

  public CheckBox getGoldGoalCheckBox() {
    return goldGoalCheckBox;
  }

  public CheckBox getInventoryGoalCheckBox() {
    return inventoryGoalCheckBox;
  }

  public CheckBox getScoreGoalCheckBox() {
    return scoreGoalCheckBox;
  }

  public ComboBox<String> getClassComboBox() {
    return classComboBox;
  }

  public TextField getNameField() {
    return nameField;
  }

  public TextField getHealthGoalField() {
    return healthGoalField;
  }

  public TextField getGoldGoalField() {
    return goldGoalField;
  }

  public TextField getInventoryGoalField() {
    return inventoryGoalField;
  }

  public TextField getScoreGoalField() {
    return scoreGoalField;
  }

  public TextField getArmourTextField() {
    return armourTextField;
  }

  public TextField getCriticalChanceTextField() {
    return criticalChanceTextField;
  }

  public TextField getDamageTextField() {
    return damageTextField;
  }

  public TextField getManaTextField() {
    return manaTextField;
  }

  public Button getStartButton() {
    return startButton;
  }

  public Button getBackButton() {
    return backButton;
  }

  public TextField getHealthTextField() {
    return healthTextField;
  }

  public TextField getGoldTextField() {
    return goldTextField;
  }

  public void update() {
    storyColumn.setText(resourceBundle.getString("startNewGame.story"));
    playerLabel.setText(resourceBundle.getString("startNewGame.player"));
    nameLabel.setText(resourceBundle.getString("startNewGame.name"));
    classComboBox.setPromptText(resourceBundle.getString("startNewGame.class"));
    classComboBox.getItems().clear();
    classComboBox.getItems().addAll(
        resourceBundle.getString("startNewGame.classType.mage"),
        resourceBundle.getString("startNewGame.classType.rogue"),
        resourceBundle.getString("startNewGame.classType.warrior"),
        resourceBundle.getString("startNewGame.classType.ranger"),
        resourceBundle.getString("startNewGame.classType.custom"));
    healthGoalCheckBox.setText(resourceBundle.getString("startNewGame.health"));
    goldGoalCheckBox.setText(resourceBundle.getString("startNewGame.gold"));
    inventoryGoalCheckBox.setText(resourceBundle.getString("startNewGame.inventory"));
    scoreGoalCheckBox.setText(resourceBundle.getString("startNewGame.score"));
    startButton.setText(resourceBundle.getString("startNewGame.start"));
    backButton.setText(resourceBundle.getString("startNewGame.back"));
  }

  public List<TextField> getGoalTextFields() {
    return goalTextFields;
  }

  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }


  public void setController(StartNewGameController controller) {
    this.controller = controller;
  }
}