package no.ntnu.idatg2001.frontend.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class EndGameView extends BorderPane {

  private GameController controller;

  public EndGameView() {
    createUI();
  }

  private void createUI() {
    // Create the main container for the view
    VBox container = new VBox();
    container.setAlignment(Pos.CENTER);
    container.setSpacing(20);

    // Create the end game message
    Label messageLabel = new Label("Congratulations!\nYou've reached the end.");

    // Create the return to main menu button
    Button returnButton = new Button("Return to Main Menu");
    returnButton.setOnAction(event -> {
      // Handle the button click event to return to the main menu
      // Add your code here to navigate to the main menu view
    });

    // Add the components to the container
    container.getChildren().addAll(messageLabel, returnButton);

    // Set the container as the center of the BorderPane
    setCenter(container);
  }

  public void setController(GameController controller) {
    this.controller = controller;
  }
}