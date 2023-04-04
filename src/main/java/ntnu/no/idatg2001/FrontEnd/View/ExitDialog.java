package ntnu.no.idatg2001.FrontEnd.View;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class ExitDialog extends Dialog<ButtonType> {
  private ResourceBundle resourceBundle;
  private SettingsModel settings = new SettingsModel();
  private Label titleLabel;
  private Label messageLabel;
  private ButtonType yesButtonType;
  private ButtonType cancelButtonType;

  public ExitDialog() {
    initStyle(StageStyle.TRANSPARENT);
    createDialogContent();
    setResultConverter(buttonType -> {
      if (buttonType == yesButtonType) {
        Platform.exit();
        System.exit(0);
      } else if (buttonType == cancelButtonType) {
        close();
      }
      return buttonType;
    });
  }
  private void createDialogContent() {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("exitDialog", locale);
    titleLabel = new Label(resourceBundle.getString("exit.title"));
    titleLabel.setFont(Font.font(18));

    messageLabel = new Label(resourceBundle.getString("exit.message"));
    messageLabel.setFont(Font.font(14));

    yesButtonType = new ButtonType(resourceBundle.getString("yesButton"));
    cancelButtonType = new ButtonType(resourceBundle.getString("cancelButton"));

    getDialogPane().getButtonTypes().addAll(yesButtonType, cancelButtonType);

    VBox vbox = new VBox(titleLabel, messageLabel);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(10);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(vbox);
    stackPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

    getDialogPane().setContent(stackPane);
    getDialogPane().getStylesheets().add(("css/ExitConfirmationDialogStyleSheet.css"));
  }

  public void updateLanguage(Locale locale) {
    resourceBundle = ResourceBundle.getBundle("exitDialog", locale);
    titleLabel.setText(resourceBundle.getString("exit.title"));
    messageLabel.setText(resourceBundle.getString("exit.message"));
    titleLabel.setText(resourceBundle.getString("exit.title"));
    messageLabel.setText(resourceBundle.getString("exit.message"));
    ((Button) getDialogPane().lookupButton(yesButtonType)).setText(resourceBundle.getString("yesButton"));
    ((Button) getDialogPane().lookupButton(cancelButtonType)).setText(resourceBundle.getString("cancelButton"));
  }
}

