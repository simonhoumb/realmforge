package no.ntnu.idatg2001.frontend.view.dialogs;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.EndViewController;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class YouDiedDialog extends Dialog {
    private GameController controller;
    private Button returnButton;
    private ResourceBundle resourceBundle;
    private Label titleLabel;

    public YouDiedDialog() {
      resourceBundle = ResourceBundle
          .getBundle("languages/YouDiedDialog", SettingsModel.getInstance().getLocale());
      getDialogPane().getStylesheets().add("/css/addActionDialog.css");
      createCustomTitleHeader();
      createUI();
    }

  private void createCustomTitleHeader() {
    titleLabel = new Label(resourceBundle.getString("dialog.title"));
    titleLabel.getStyleClass().add("dialog-title"); // Apply CSS style class to the title label

    // Set up the layout
    HBox headerBox = new HBox();
    headerBox.setAlignment(Pos.CENTER_LEFT);
    headerBox.setPadding(new Insets(0, 0, 0, 0));
    headerBox.getChildren().add(titleLabel);

    StackPane headerPane = new StackPane();
    headerPane.setPadding(new Insets(2, 2, 0, 0));
    headerPane.getChildren().add(headerBox);

    getDialogPane().setHeader(headerPane);
    titleLabel.setPadding(new Insets(10, 0, 0, 10));
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
      getDialogPane().setContent(container);
    }

    private void createReturnButton() {
      returnButton = new JFXButton(resourceBundle.getString("returnButton"));
      returnButton.setOnAction(event -> {
        try {
          controller.onBackToMainMenuButtonPressed(event);
          controller.onCloseSource(event);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }


    public void setController(GameController controller) {
      this.controller = controller;
    }
  }
