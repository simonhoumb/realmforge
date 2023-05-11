package no.ntnu.idatg2001.frontend.view;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.GameController;
import no.ntnu.idatg2001.frontend.controller.MainMenuController;

public class PauseMenuDialog extends Dialog<ButtonType> {
  private ResourceBundle resourceBundle;
  private final GameController controller;
  private JFXButton resumeButton;
  private JFXButton saveButton;
  private JFXButton loadButton;
  private JFXButton settingsButton;
  private JFXButton exitToMenuButton;
  private JFXButton exitToDesktopButton;

  public PauseMenuDialog(GameController controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    initModality(Modality.APPLICATION_MODAL);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add(("css/settingsStyleSheet.css"));
    this.resourceBundle = ResourceBundle.getBundle("languages/settingsDialog", SettingsModel.getInstance().getLocale());
    createExitToMenuButton();
    createExitToDesktop();
    createResumeButton();
    createSaveButton();
    createLoadButton();
    createSettingsButton();
    createLayout();
  }

  public void createLayout() {
      Label headerLabel = new Label("Pause");
      headerLabel.setAlignment(Pos.CENTER);
      headerLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
      headerLabel.setPadding(new Insets(5, 0, 0, 5));
      headerLabel.setFont(new Font(19));
      this.getDialogPane().setHeader(headerLabel);
      HBox exitButtonsHBox = new HBox(exitToMenuButton, exitToDesktopButton);
      exitButtonsHBox.setSpacing(10);
      exitButtonsHBox.setAlignment(Pos.CENTER);
      exitButtonsHBox.setPadding(new Insets(20, 0, 0, 0));
      VBox contentVBox = new VBox(resumeButton, saveButton, loadButton, settingsButton,
          exitButtonsHBox);
      contentVBox.setSpacing(10);
      contentVBox.setAlignment(Pos.CENTER);

      this.getDialogPane().setContent(contentVBox);
    }

    private void createResumeButton() {
      resumeButton = new JFXButton("Resume");
      resumeButton.setCancelButton(true);
      resumeButton.setOnAction(controller::onCloseSource);
    }

    private void createSaveButton() {
      saveButton = new JFXButton("Save");
      saveButton.setOnAction(event -> {//TODO add save game dialog
        });
    }

    private void createLoadButton() {
      loadButton = new JFXButton("Load");
      loadButton.setOnAction(event -> {//TODO add load game dialog
      });
    }

    private void createSettingsButton() {
      settingsButton = new JFXButton("Settings");
      settingsButton.setOnAction(event -> {//TODO make it open settings dialog
        });
    }

    private void createExitToMenuButton() {
      exitToMenuButton = new JFXButton("Exit To Menu");
      exitToMenuButton.setOnAction(event -> {
        try {
          controller.onBackToMainMenuButtonPressed(event);
          controller.onCloseSource(event);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

    private void createExitToDesktop() {
      exitToDesktopButton = new JFXButton("Exit To Desktop");
      exitToDesktopButton.setOnAction(controller::onExitApplication);
    }

}
