package ntnu.no.idatg2001.FrontEnd.View;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;

public class ExitDialogView extends Dialog<ButtonType> {
  private ResourceBundle resourceBundle;
  private Dialog<ButtonType> exitDialog;
  private Label titleLabel;
  private Label messageLabel;
  private ButtonType yesButtonType;
  private ButtonType cancelButtonType;

  public ExitDialogView(SettingsModel settings) {
    exitDialog = new Dialog<>();
    initStyle(StageStyle.TRANSPARENT);
    createDialogContent(settings);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add(("css/exitDialog.css"));

  }


  private StackPane createDialogContent(SettingsModel settings) {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    titleLabel = new Label(resourceBundle.getString("exit.title"));
    titleLabel.setFont(Font.font(18));

    messageLabel = new Label(resourceBundle.getString("exit.message"));
    messageLabel.setFont(Font.font(14));

    VBox vbox = new VBox(titleLabel, messageLabel);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);

    StackPane layout = new StackPane();
    layout.getChildren().add(vbox);
    layout.setAlignment(Pos.CENTER);
    layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

    getDialogPane().setContent(layout);
    createConfirmButton();
    createCancelButton();
    getDialogPane().getButtonTypes().addAll(yesButtonType, cancelButtonType);
    getDialogPane().setPrefHeight(140);

    setResultConverter(dialogButton -> {
      if (dialogButton == yesButtonType) {
        System.exit(0);
      } else if (dialogButton == cancelButtonType) {
        exitDialog.close();
        close();
      }
      return dialogButton;
    });
    return layout;
  }

  private void createConfirmButton(){
    ButtonType confirmButton = new ButtonType(resourceBundle.getString("yesButton"));
    exitDialog.getDialogPane().getButtonTypes().add(confirmButton);
    yesButtonType = confirmButton;
  }
  private void createCancelButton() {
    ButtonType cancelButton = new ButtonType(resourceBundle.getString("cancelButton"));
    exitDialog.getDialogPane().getButtonTypes().add(cancelButton);
    cancelButtonType = cancelButton;
  }

  public ButtonType getCancelButtonType() {
    return cancelButtonType;
  }
  public ButtonType getYesButtonType() {
    return yesButtonType;
  }
}

