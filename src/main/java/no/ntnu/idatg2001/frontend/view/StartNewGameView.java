package no.ntnu.idatg2001.frontend.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.frontend.controller.StartNewGameController;
import org.controlsfx.control.ListSelectionView;

public class StartNewGameView extends BorderPane {

  private ResourceBundle resourceBundle;
  private StartNewGameController controller;
  private HBox hbox;
  private VBox vBox1;
  private VBox vBox2;
  private TableView<Story> storyTableView;
  private TableColumn<Story, String> storyColumn;
  private TableView<Goal> goalTableView;
  private TableColumn<Goal, String> goalColumn;
  private TableColumn<Goal,String> goalDescriptionColumn;
  private Label playerLabel;
  private Label nameLabel;
  private ButtonBar buttonBar;
  private TextField nameField;
  private ComboBox<String> classComboBox;
  private Label goalsLabel;
  private Label classStatsLabel;
  private TextField healthTextField;
  private TextField goldTextField;
  private VBox buttonHBox;
  private Button addGoalButton;
  private Button removeGoalButton;
  private Button editGoalButton;
  private Button startButton;
  private Button backButton;
  private TextField manaTextField;
  private TextField damageTextField;
  private TextField criticalChanceTextField;
  private TextField armourTextField;

  public StartNewGameView() {
    resourceBundle = ResourceBundle.getBundle("languages/startNewGameView", SettingsModel.getInstance().getLocale());
    createLayout();
    createClassStatsRow();
    String cssFile = "/css/StartNewGameView.css";
    setBackground(new Background(new BackgroundFill(Color.rgb(25, 25, 25, 0.9), null, null)));
    getStylesheets().add(cssFile);
    update();
  }

  private void createLayout() {
    createButtonHBox();
    createComboBox();
    createAddGoalButton();
    createRemoveGoalButton();
    storyTableView = new TableView<>();
    storyTableView.setPrefHeight(600);
    storyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    storyColumn = new TableColumn<>(resourceBundle.getString("startNewGame.storyColumn"));
    storyTableView.getColumns().add(storyColumn);
    Separator separateTableviewTop = new Separator();
    separateTableviewTop.setPadding(new Insets(100, 0, 20, 0));
    Separator separateTableviewBottom = new Separator();
    separateTableviewBottom.setPadding(new Insets(20, 0, 0, 0));
    VBox storyVBox = new VBox();
    storyVBox.getChildren().addAll(separateTableviewTop, storyTableView, separateTableviewBottom);
    setLeft(storyVBox);

    hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(20);

    vBox1 = new VBox();
    vBox1.setAlignment(Pos.CENTER); // Align to the top left
    vBox1.setSpacing(10);

    createGoalTableView();

    playerLabel = new Label(resourceBundle.getString("startNewGame.playerLabel"));
    playerLabel.setPadding(new Insets(5));
    playerLabel.setFont(new Font("System Bold", 12));

    Separator separator1 = new Separator();
    separator1.setMaxWidth(300);
    separator1.setPadding(new Insets(10, 0, 10, 0));

    nameLabel = new Label(resourceBundle.getString("startNewGame.nameLabel"));

    nameField = new TextField();
    nameField.setMaxWidth(200);

    Separator separator2 = new Separator();
    separator2.setMaxWidth(300);
    separator2.setPadding(new Insets(10, 0, 10, 0));

    goalsLabel = new Label(resourceBundle.getString("startNewGame.goalsLabel"));
    goalsLabel.setFont(new Font("System Bold", 12));

    Separator separator3 = new Separator();
    separator3.setMaxWidth(300);
    separator3.setPadding(new Insets(10, 0, 10, 0));

    HBox buttonHBox = new HBox();
    buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(addGoalButton, removeGoalButton);
    buttonHBox.setAlignment(Pos.CENTER_LEFT);
    buttonHBox.setSpacing(10);
    buttonHBox.setPrefWidth(300);
    buttonHBox.getChildren().add(buttonBar);

    vBox1.getChildren().addAll(playerLabel, separator1, nameLabel, nameField,
        separator2, classComboBox, separator3, goalsLabel, goalTableView, buttonHBox);

    vBox2 = new VBox();
    vBox2.setAlignment(Pos.CENTER); // Center alignment
    vBox2.setSpacing(10);

    Separator separator4 = new Separator();
    separator4.setPadding(new Insets(0, 20, 0, 20));

    // Create a new VBox for the selection and stats
    VBox selectionAndStatsBox = new VBox();
    selectionAndStatsBox.setAlignment(Pos.CENTER); // Align to the bottom left
    selectionAndStatsBox.setSpacing(10);
    selectionAndStatsBox.setPadding(new Insets(10));
    selectionAndStatsBox.getChildren().addAll(vBox2); // Add goalListSelectionView before vBox2

    hbox.getChildren().addAll(vBox1, separator4, selectionAndStatsBox); // Add selectionAndStatsBox instead of goalSelectionBox

    setCenter(hbox);
  }

  private void createClassStatsRow() {
    classStatsLabel = new Label(resourceBundle.getString("startNewGame.classStatsLabel"));
    classStatsLabel.setFont(new Font("System Bold", 12));

    GridPane classStatsGridPane = new GridPane();
    classStatsGridPane.setHgap(15);
    classStatsGridPane.setVgap(10);
    classStatsGridPane.setAlignment(Pos.CENTER);

    Label healthLabel = new Label(resourceBundle.getString("startNewGame.healthLabel")+": ");
    healthTextField = new TextField();
    healthTextField.setMaxWidth(100);
    healthTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    healthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        healthTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label manaLabel = new Label(resourceBundle.getString("startNewGame.manaLabel")+": ");
    manaTextField = new TextField();
    manaTextField.setMaxWidth(100);
    manaTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    manaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        manaTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label goldLabel = new Label(resourceBundle.getString("startNewGame.goldLabel")+": ");
    goldTextField = new TextField();
    goldTextField.setMaxWidth(100);
    goldTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    goldTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        goldTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label damageLabel = new Label(resourceBundle.getString("startNewGame.damageLabel")+": ");
    damageTextField = new TextField();
    damageTextField.setMaxWidth(100);
    damageTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    damageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        damageTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label criticalChanceLabel = new Label(resourceBundle.getString("startNewGame.criticalChanceLabel")+": ");
    criticalChanceTextField = new TextField();
    criticalChanceTextField.setMaxWidth(100);
    criticalChanceTextField.disableProperty().bind(
        classComboBox.valueProperty().isNotEqualTo(PlayerClass.CUSTOM.getClassName()));
    criticalChanceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        criticalChanceTextField.setText(newValue.replaceAll("\\D", ""));
      }
    });

    Label armourLabel = new Label(resourceBundle.getString("startNewGame.armorLabel")+": ");
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

    vBox2.getChildren().addAll(classStatsLabel, classStatsGridPane, buttonHBox);
  }


  private void createComboBox() {
    classComboBox = new ComboBox<>();
    classComboBox.setPromptText(resourceBundle.getString("startNewGame.classComboBoxPromptText"));
    classComboBox.setPrefWidth(200);
    classComboBox.setOnAction(event -> {
      if (classComboBox.getValue() != null) {
        controller.updateClassStats();
      }
    });
  }

  private void createGoalTableView() {
    goalTableView = new TableView<>();
    createGoalColumn();
    createGoalDescriptionColumn();
    goalTableView.getColumns().addAll(goalColumn, goalDescriptionColumn);
    // Custom cell factory to add buttons to each cell
  }

  private void createAddGoalButton() {
    addGoalButton = new Button(resourceBundle.getString("startNewGame.addGoalButton"));
    addGoalButton.setMinWidth(200);
    addGoalButton.setOnAction(event -> {
      controller.onAddGoalPressed();
    });
  }

  private void createRemoveGoalButton() {
    removeGoalButton = new Button(resourceBundle.getString("startNewGame.removeGoalButton"));
    removeGoalButton.setPrefWidth(200);
    removeGoalButton.setOnAction(event -> {
        boolean confirmed = AlertHelper.showConfirmationAlert(getScene().getWindow(), resourceBundle.getString("alert.confirmation"),
            resourceBundle.getString("alert.removeGoalConfirmation"));
        if (confirmed) {
          controller.onRemoveGoalPressed(event);
        } else {
          event.consume();
        }
      });
  }
  private void createGoalColumn() {
    goalColumn = new TableColumn<>(resourceBundle.getString("startNewGame.goalColumn"));
    goalColumn.setPrefWidth(200);
    goalColumn.setCellValueFactory(new PropertyValueFactory<>("goalType"));
  }

  private void createGoalDescriptionColumn() {
    goalDescriptionColumn = new TableColumn<>(resourceBundle.getString("startNewGame.goalDescriptionColumn"));
    goalDescriptionColumn.setPrefWidth(200);
    goalDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("goalValue"));
  }

  private void createButtonHBox() {
    buttonHBox = new VBox();
    buttonHBox.setPadding(new Insets(100, 0, 0, 0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setPrefHeight(40.0);
    buttonHBox.setSpacing(20);
    startButton = new Button(resourceBundle.getString("startNewGame.startButton"));
    startButton.setPrefSize(100, 40);
    startButton.setOnAction(event -> {
          controller.onStartButtonPressed(event);
        });
    backButton = new Button(resourceBundle.getString("startNewGame.backButton"));
    backButton.setPrefSize(100, 40);
    backButton.setCancelButton(true);
    backButton.setOnAction(event -> {
     boolean confirm = AlertHelper.showConfirmationAlert(getScene().getWindow(), resourceBundle.getString("alert.confirmation"),
          resourceBundle.getString("alert.backToMainMenuConfirmation"));
     if (confirm) {
       try {
         controller.onBackToMainMenuButtonPressed(event);
       } catch (IOException e) {
         e.printStackTrace();
       }
     } else {
       event.consume();
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

  public ComboBox<String> getClassComboBox() {
    return classComboBox;
  }

  public TextField getNameField() {
    return nameField;
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

  public Button getBackButton() {
    return backButton;
  }

  public TextField getHealthTextField() {
    return healthTextField;
  }

  public TextField getGoldTextField() {
    return goldTextField;
  }

  public TableColumn<Goal, String> getGoalColumn() {
    return goalColumn;
  }

  public TableColumn<Goal, String> getGoalDescriptionColumn() {
    return goalDescriptionColumn;
  }

  public TableView<Goal> getGoalTableView() {
    return goalTableView;
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
    startButton.setText(resourceBundle.getString("startNewGame.start"));
    backButton.setText(resourceBundle.getString("startNewGame.back"));
  }

  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }


  public void setController(StartNewGameController controller) {
    this.controller = controller;
  }
}