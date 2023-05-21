package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.backend.goals.GoldGoal;
import no.ntnu.idatg2001.backend.goals.HealthGoal;
import no.ntnu.idatg2001.backend.goals.InventoryGoal;
import no.ntnu.idatg2001.backend.goals.ScoreGoal;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.frontend.controller.StartNewGameController;

public class AddGoalDialog extends Dialog<Goal> {

  private ComboBox<String> goalTypeComboBox;
  private GridPane gridPane;
  private StartNewGameController controller;
  private JFXButton addButton;
  private JFXButton cancelButton;
  private Label titleLabel;
  private TextField numberText;
  private TextField letterText;
  private ButtonBar buttonBar;
  private TextField valueTextField; // Added field for goal value
  private ResourceBundle resourceBundle;

  public AddGoalDialog(StartNewGameController controller) {
    this.controller = controller;
    resourceBundle = ResourceBundle.getBundle("languages/addGoalDialog",
        SettingsModel.getInstance().getLocale());
    getDialogPane().getStylesheets().add(("css/addActionDialog.css"));
    createCustomTitleHeader();
    createGoalTypeComboBox();
    createTextFieldForIntegers();
    createTextFieldForLetters();
    createAddButton();
    createCancelButton();

    gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(addButton, cancelButton);

    valueTextField = new TextField(); // Added text field for setting goal value

    gridPane.add(new Label(resourceBundle.getString("dialog.goalType")), 0, 0);
    gridPane.add(goalTypeComboBox, 1, 0);
    gridPane.add(new Label(resourceBundle.getString("dialog.value")), 0, 1); // Added label for value
    gridPane.add(valueTextField, 1, 1); // Added text field for value
    gridPane.add(buttonBar, 2, 0, 1, 2); // Adjusted button bar position

    getDialogPane().setContent(gridPane);

    // Set the result converter to handle the selected goal type and value
    setResultConverter(buttonType -> {
      if (buttonType == ButtonType.OK) {
        String selectedGoalType = goalTypeComboBox.getValue();
        int value = Integer.parseInt(valueTextField.getText()); // Get the value from the text field
        Goal newGoal = createGoalByType(selectedGoalType);
        if (newGoal != null) {
          newGoal.setGoalValue(value); // Set the value for the goal
        }
        return newGoal;
      }
      return null;
    });
  }

  private void createCustomTitleHeader() {
    titleLabel = new Label(resourceBundle.getString("dialog.title"));
    titleLabel.getStyleClass().add("dialog-title"); // Apply CSS style class to the title label

    // Create close button
    JFXButton closeButton = new JFXButton("X");
    closeButton.setFocusTraversable(false);
    closeButton.getStyleClass().add("dialog-close-button");
    closeButton.setOnAction(event -> controller.onCloseSource(event)); // Close the dialog when the close button is clicked

    // Set up the layout
    HBox headerBox = new HBox();
    headerBox.setAlignment(Pos.CENTER_LEFT);
    headerBox.setPadding(new Insets(0, 0, 0, 0));
    headerBox.getChildren().add(titleLabel);

    StackPane headerPane = new StackPane();
    headerPane.setPadding(new Insets(2, 2, 0, 0));
    headerPane.getChildren().addAll(headerBox, closeButton);
    StackPane.setAlignment(closeButton, Pos.CENTER_RIGHT);

    getDialogPane().setHeader(headerPane);
    titleLabel.setPadding(new Insets(10, 0, 0, 10));
  }

  public Goal createGoalByType(String goalType) {
    if (goalType.equals(resourceBundle.getString("dialog.goldGoal"))) {
      return new GoldGoal(Integer.parseInt(getNumberText().getText()));
    } else if (goalType.equals(resourceBundle.getString("dialog.healthGoal"))) {
      return new HealthGoal(Integer.parseInt(getNumberText().getText()));
    } else if (goalType.equals(resourceBundle.getString("dialog.inventoryGoal"))) {
      return new InventoryGoal(getLetterText().getText());
    } else if (goalType.equals(resourceBundle.getString("dialog.scoreGoal"))) {
      return new ScoreGoal(Integer.parseInt(getNumberText().getText()));
    }
    return null;
  }

  private void createAddButton() {
    addButton = new JFXButton(resourceBundle.getString("dialog.addButton"));
    addButton.setDisable(true);
    addButton.setOnAction(event -> {
      if (!numberText.getText().isEmpty() && !numberText.getText().isBlank()
          || !letterText.getText().isEmpty() && !letterText.getText().isBlank()) {
        controller.OnAddGoalAddPressed();
        controller.onCloseSource(event);
      } else {
        AlertHelper.showWarningAlert(getDialogPane().getScene().getWindow(), resourceBundle.getString("dialog.warning"),
            resourceBundle.getString("dialog.warningMessage"));
      }
    });
    // Add listener to enable/disable the "Add" button based on ComboBox selection
    goalTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
      addButton.setDisable(newValue == null);
    });
  }

  private void createCancelButton() {
    cancelButton = new JFXButton(resourceBundle.getString("dialog.cancelButton"));
    cancelButton.setOnAction(event -> {
      boolean confirmed = AlertHelper.showConfirmationAlert(getDialogPane().getScene().getWindow(), resourceBundle.getString("dialog.conformation"),
          resourceBundle.getString("dialog.conformationMessage"));
      if (confirmed) {
        controller.onCloseSource(event);
      } else {
        event.consume();
      }
    });
  }

  private void createGoalTypeComboBox() {
    goalTypeComboBox = new ComboBox<>();
    goalTypeComboBox.setPlaceholder(new Label(resourceBundle.getString("dialog.goalTypePlaceholder")));
    goalTypeComboBox.getItems().addAll(resourceBundle.getString("dialog.goldGoal"),
        resourceBundle.getString("dialog.healthGoal"), resourceBundle.getString("dialog.inventoryGoal"),
        resourceBundle.getString("dialog.scoreGoal"));
    // Add listener to handle goal type selection
    goalTypeComboBox.valueProperty().addListener((ObservableValue<? extends String>
        observable, String oldValue, String newValue) -> {
      // Remove previous value text field, if present
      if (valueTextField != null) {
        gridPane.getChildren().remove(valueTextField);
      }

      // Create the appropriate value text field based on goal type
      if (newValue.equals(resourceBundle.getString("dialog.inventoryGoal"))) {
        valueTextField = createTextFieldForLetters();
      } else {
        valueTextField = createTextFieldForIntegers();
      }

      // Add the new value text field to the grid pane
      gridPane.add(valueTextField, 1, 1);
    });
  }

  private TextField createTextFieldForLetters() {
    letterText = new TextField();
    letterText.setPromptText(resourceBundle.getString("dialog.lettersPlaceholder"));

    // Add listener to allow only letters
    letterText.textProperty().addListener((ObservableValue<? extends String>
        observable, String oldValue, String newValue) -> {
      if (!newValue.matches("[a-zA-Z0-9]*")) {
        letterText.setText(newValue.replaceAll("[^a-zA-Z0-9]", ""));
      }
    });

    return letterText;
  }

  private TextField createTextFieldForIntegers() {
    numberText = new TextField();
    numberText.setPromptText(resourceBundle.getString("dialog.numbersPlaceholder"));

    // Add listener to allow only integers
    numberText.textProperty().addListener((ObservableValue<? extends String>
        observable, String oldValue, String newValue) -> {
      if (!newValue.matches("\\d*")) {
        numberText.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
    return numberText;
  }

  public ComboBox<String> getGoalTypeComboBox() {
    return goalTypeComboBox;
  }

  public TextField getLetterText() {
    return letterText;
  }

  public TextField getNumberText() {
    return numberText;
  }
}
