package no.ntnu.idatg2001.frontend.view.dialogs;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.MainMenuController;

public class NewGameDialog extends Dialog {
  private ResourceBundle resourceBundle;
  private Label newGameLabel;
  private Button playNewStoryButton;
  private Button createStoryButton;
  private Button backToMainMenuButton;
  private StackPane layout;
  private MainMenuController controller;

  public NewGameDialog(MainMenuController controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add(("css/newGameDialog.css"));
    this.resourceBundle = ResourceBundle.getBundle("languages/newGameDialog",
        SettingsModel.getInstance().getLocale());
    createNewGameLabel();
    createNewGameButton();
    createNewStoryButton();
    createCloseNewGameDialogButton();
    createLayout();
  }

  private void createLayout() {
    layout = new StackPane();
    layout.setAlignment(Pos.CENTER);
    layout.setPrefWidth(Region.USE_COMPUTED_SIZE);
    VBox vBox = new VBox();
    vBox.setPadding(new Insets(10, 0, 0, 0));
    vBox.setSpacing(10);
    vBox.getContentBias();
    vBox.fillWidthProperty();
    vBox.setFillWidth(true);
    HBox buttonCloseBox = new HBox();
    buttonCloseBox.setAlignment(Pos.CENTER_RIGHT);
    buttonCloseBox.setSpacing(10);
    buttonCloseBox.getChildren().add(backToMainMenuButton);
    vBox.getChildren().addAll(playNewStoryButton, createStoryButton, buttonCloseBox);
    layout.getChildren().add(vBox);
    getDialogPane().setHeader(newGameLabel);
    getDialogPane().setContent(layout);
    getDialogPane().setPrefHeight(220);
  }

  private void createNewStoryButton() {
    createStoryButton = new Button(resourceBundle.getString("createStoryButton"));
    createStoryButton.setPrefSize(300,30);
    createStoryButton.setOnAction(event -> {
      try {
        controller.onCreateStoryButtonPressed(event);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void createNewGameButton() {
    playNewStoryButton = new Button(resourceBundle.getString("playNewStoryButton"));
    playNewStoryButton.setPrefSize(300,30);
    playNewStoryButton.setOnAction(event -> controller.onStartGameButtonPressed(event));
  }

  private void createCloseNewGameDialogButton() {
    backToMainMenuButton = new Button(resourceBundle.getString("backToMainMenuButton"));
    backToMainMenuButton.setCancelButton(true);
    backToMainMenuButton.setOnAction(controller::onCloseSource);
  }

  private void createNewGameLabel() {
    newGameLabel = new Label();
    newGameLabel.setText(resourceBundle.getString("newGameHeaderText"));
    newGameLabel.setPadding(new Insets(5,0,0,10));
    newGameLabel.setWrapText(true);
  }
}

