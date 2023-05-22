package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.goals.Goal;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GoalsDialog extends Dialog {
  private ResourceBundle resourceBundle;
  private Label titleLabel;
  private JFXButton confirmButton;
  private JFXButton cancelButton;
  private JFXButton restartButton;
  private GameController controller;
  private StackPane layout;
  private HBox hBox;
  private Pane goalPane;

  public GoalsDialog(GameController controller) {
    this.controller = controller;
    initStyle(StageStyle.UNDECORATED);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    resourceBundle = ResourceBundle.getBundle("languages/goalDialog", SettingsModel.getInstance().getLocale());
    getDialogPane().getStylesheets().add(("css/GoalsDialog.css"));
    createLayout();

  }

  private void createLayout() {
    createCustomTitleHeader();

    goalPane = new Pane();
    ScrollPane scrollPane = new ScrollPane(goalPane);
    scrollPane.getStyleClass().add("scroll-pane");
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(300); // Adjust the height as needed

    // Add each goal to the goal pane
    List<Goal> goals = controller.getCurrentGameSave().getGame().getGoals();
    for (int i = 0; i < goals.size(); i++) {
      Goal goal = goals.get(i);
      Pane goalPaneItem = createGoalPaneItem(goal);
      goalPaneItem.setLayoutY(i * 40); // Adjust the vertical spacing between goals
      goalPane.getChildren().add(goalPaneItem);
    }
    createEndGameButton();
    createRestartButton();
    createCancelButton();
    VBox vBox = new VBox(confirmButton, restartButton, cancelButton);
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER);
    hBox = new HBox(goalPane,vBox);
    hBox.setSpacing(10);
    layout = new StackPane();
    layout.getChildren().add(hBox);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10,10,10,10));
    getDialogPane().setContent(layout);
    getDialogPane().setMinHeight(120);
  }


  private Pane createGoalPaneItem(Goal goal) {
    Pane goalPaneItem = new Pane();
    goalPaneItem.getStyleClass().add("goal-pane-item");

    // Create labels to display goal information
    Label goalTypeLabel = new Label(goal.getGoalType().toString());
    goalTypeLabel.getStyleClass().add("goal-type-label");


    // Create a label for current progress
    Label progressLabel = new Label(controller.getCurrentAmount(goal) + " / " + goal.getGoalValue().toString());
    progressLabel.getStyleClass().add("progress-label");

    // Create a disabled checkbox for goal completion
    CheckBox completionCheckBox = new CheckBox();
    completionCheckBox.setSelected(controller.isGoalReached(goal));
    completionCheckBox.setDisable(true);

    // Set the position and styling of the labels and checkbox
    goalTypeLabel.setLayoutX(10);
    goalTypeLabel.setLayoutY(10);
;
    progressLabel.setLayoutX(250);
    progressLabel.setLayoutY(10);

    completionCheckBox.setLayoutX(400);
    completionCheckBox.setLayoutY(10);

    // Add the labels and checkbox to an HBox
    HBox hbox = new HBox(goalTypeLabel, progressLabel, completionCheckBox);
    hbox.setSpacing(10);

    // Add the HBox to the goal pane item
    goalPaneItem.getChildren().add(hbox);

    return goalPaneItem;
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

  private void createEndGameButton() {
    confirmButton = new JFXButton(resourceBundle.getString("dialog.endGame"));
    confirmButton.setOnAction(e -> {
      // Check if all goals are completed before ending the game
      if (controller.areAllGoalsCompleted()) {
        controller.onEndGameButtonPressed();
        controller.onCloseSource(e);
      }
    });

    // Set the initial state of the button as disabled
    confirmButton.setDisable(true);
    updateEndGameButtonState();
  }

  private void createRestartButton(){
    restartButton = new JFXButton(resourceBundle.getString("dialog.restart"));
    restartButton.setOnAction(e -> {
      controller.onRestartGameButtonPressed();
      controller.onCloseSource(e);
    });
  }

  private void updateEndGameButtonState() {
    confirmButton.setDisable(!controller.areAllGoalsCompleted());
  }

  private void createCancelButton(){
    cancelButton = new JFXButton(resourceBundle.getString("dialog.cancel"));
    cancelButton.setOnAction(e -> controller.onCloseSource(e));
  }


  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }
}
