package no.ntnu.idatg2001.frontend.view;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.EndViewController;
import no.ntnu.idatg2001.frontend.controller.GameController;
import no.ntnu.idatg2001.frontend.controller.MainMenuController;

public class EndGameView extends BorderPane {

  private EndViewController controller;
  private Button returnButton;
  private ResourceBundle resourceBundle;

  public EndGameView() {
    resourceBundle = ResourceBundle
        .getBundle("languages/EndGameView", SettingsModel.getInstance().getLocale());
    getStylesheets().add("/css/EndGameView.css");
    createUI();
    setBackground(new Background(new BackgroundFill(Color.rgb(25, 25, 25, 0.9), null, null)));
  }

  private void createUI() {
    // Create the main container for the view
    createReturnButton();
    VBox container = new VBox();
    container.setAlignment(Pos.CENTER);
    container.setSpacing(20);

    // Create the end game message
    Label messageLabel = new Label(resourceBundle.getString("endGameMessage"));
    // Create the return to main menu button
    // Add the components to the container
    container.getChildren().addAll(messageLabel, returnButton);
    // Set the container as the center of the BorderPane
    setCenter(container);
  }

  private void createReturnButton() {
    returnButton = new JFXButton(resourceBundle.getString("returnButton"));
    returnButton.setOnAction(event -> {
      try {
        controller.onBackToMainMenuButtonPressed(event);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }


  public void setController(EndViewController controller) {
    this.controller = controller;
  }
}