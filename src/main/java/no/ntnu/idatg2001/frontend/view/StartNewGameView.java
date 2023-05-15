package no.ntnu.idatg2001.frontend.view;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.frontend.controller.StartNewGameController;

public class StartNewGameView extends BorderPane {

  private StartNewGameController controller;

  private VBox vBox1;
  private VBox vBox2;
  private TextField nameField;

  private CheckBox healthGoalCheckbox;
  private CheckBox goldGoalCheckbox;
  private CheckBox inventoryGoalCheckbox;
  private CheckBox scoreGoalCheckbox;

  private ComboBox<String> classComboBox;

  public StartNewGameView() {
    createCheckBoxes();
    createLayout();
  }

  private void createLayout() {
    TableView<String> table = new TableView<>();
    TableColumn<String, String> storyColumn = new TableColumn<>("Story");
    table.getColumns().add(storyColumn);
    setLeft(table);

    // Create VBox for player information
    vBox1 = new VBox();
    vBox1.setAlignment(javafx.geometry.Pos.TOP_CENTER);
    setCenter(vBox1);

    // Add player label with padding and font
    Label playerLabel = new Label("Player");
    playerLabel.setPadding(new Insets(5));
    playerLabel.setFont(new Font("System Bold", 12));

    // Create VBox for player details
    vBox2 = new VBox();
    vBox2.setAlignment(javafx.geometry.Pos.CENTER);
    vBox2.setPrefSize(100, 200);
    vBox2.setSpacing(10);
    vBox1.getChildren().add(vBox2);

    // Add separator, name label, and text field
    Separator separator1 = new Separator();
    separator1.setMaxWidth(300);

    Label nameLabel = new Label("Name");

    nameField = new TextField();
    nameField.setMaxWidth(200);

    // Add class combo box
    classComboBox = new ComboBox<>();
    classComboBox.setPrefWidth(200);
    classComboBox.setPromptText("Class");

    // Add separator and goal checkboxes
    Separator separator2 = new Separator();
    separator2.setMaxWidth(300);

    Label goalsLabel = new Label("Goals");
    goalsLabel.setFont(new Font("System Bold", 12));

    Separator separator3 = new Separator();
    separator3.setMaxWidth(300);

    vBox1.getChildren().add(playerLabel);
    vBox2.getChildren().add(separator1);

    vBox2.getChildren().add(nameLabel);
    vBox2.getChildren().add(nameField);
    vBox2.getChildren().add(classComboBox);

    vBox2.getChildren().add(separator2);
    vBox2.getChildren().add(goalsLabel);

    vBox2.getChildren().add(healthGoalCheckbox);
    vBox2.getChildren().add(goldGoalCheckbox);
    vBox2.getChildren().add(inventoryGoalCheckbox);
    vBox2.getChildren().add(scoreGoalCheckbox);

    vBox2.getChildren().add(separator3);
  }

  private void createCheckBoxes() {
    healthGoalCheckbox = new CheckBox("Health Goal");
    healthGoalCheckbox.setMinWidth(150);

    goldGoalCheckbox = new CheckBox("Gold Goal");
    goldGoalCheckbox.setMinWidth(150);

    inventoryGoalCheckbox = new CheckBox("Inventory Goal");
    inventoryGoalCheckbox.setMinWidth(150);

    scoreGoalCheckbox = new CheckBox("Score Goal");
    scoreGoalCheckbox.setMinWidth(150);
  }

  public VBox getVBox1() {
    return vBox1;
  }


  public VBox getVBox2() {
    return vBox2;
  }


  public CheckBox getHealthGoalCheckbox() {
    return healthGoalCheckbox;
  }

  public void setHealthGoalCheckbox(CheckBox healthGoalCheckbox) {
    this.healthGoalCheckbox = healthGoalCheckbox;
  }

  public CheckBox getGoldGoalCheckbox() {
    return goldGoalCheckbox;
  }

  public void setGoldGoalCheckbox(CheckBox goldGoalCheckbox) {
    this.goldGoalCheckbox = goldGoalCheckbox;
  }

  public CheckBox getInventoryGoalCheckbox() {
    return inventoryGoalCheckbox;
  }

  public void setInventoryGoalCheckbox(CheckBox inventoryGoalCheckbox) {
    this.inventoryGoalCheckbox = inventoryGoalCheckbox;
  }

  public CheckBox getScoreGoalCheckbox() {
    return scoreGoalCheckbox;
  }

  public void setScoreGoalCheckbox(CheckBox scoreGoalCheckbox) {
    this.scoreGoalCheckbox = scoreGoalCheckbox;
  }

  public ComboBox<String> getClassComboBox() {
    return classComboBox;
  }

  public void setClassComboBox(ComboBox<String> classComboBox) {
    this.classComboBox = classComboBox;
  }

  public void setController(StartNewGameController controller) {
    this.controller = controller;
  }
}