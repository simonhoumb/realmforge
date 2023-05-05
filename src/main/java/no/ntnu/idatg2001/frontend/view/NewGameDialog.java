package no.ntnu.idatg2001.frontend.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    this.resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
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
    //vBox.setAlignment(Pos.CENTER);
    vBox.getChildren().addAll(playNewStoryButton, createStoryButton, backToMainMenuButton);
    layout.getChildren().add(vBox);
    getDialogPane().setHeader(newGameLabel);
    getDialogPane().setContent(layout);
    getDialogPane().setPrefHeight(220);
  }

  private void createNewStoryButton() {
    createStoryButton = new Button(resourceBundle.getString("createStoryButton"));
    createStoryButton.setPrefSize(150,30);
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
    playNewStoryButton.setPrefSize(150,30);
    playNewStoryButton.setOnAction(event -> controller.onStartGameButtonPressed(event));
  }

  private void createCloseNewGameDialogButton() {
    backToMainMenuButton = new Button(resourceBundle.getString("backToMainMenuButton"));
    backToMainMenuButton.setOnAction(controller::onCloseSource);
  }

  private void createNewGameLabel() {
    newGameLabel = new Label();
    newGameLabel.setText(resourceBundle.getString("newGameHeaderText"));
    newGameLabel.setPadding(new Insets(5,0,0,10));
    newGameLabel.setWrapText(true);
  }
}

