package ntnu.no.idatg2001.FrontEnd.View;

import javafx.application.Platform;
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

public class ExitDialog extends Dialog<ButtonType> {

  public ExitDialog() {
    initStyle(StageStyle.TRANSPARENT);

    Label titleLabel = new Label("Exit application");
    titleLabel.setFont(Font.font(18));

    Label messageLabel = new Label("Are you sure you want to exit?");
    messageLabel.setFont(Font.font(14));

    ButtonType yesButtonType = new ButtonType("Yes");
    ButtonType cancelButtonType = new ButtonType("Back");

    getDialogPane().getButtonTypes().addAll(yesButtonType, cancelButtonType);

    VBox vbox = new VBox(titleLabel, messageLabel);
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(10);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(vbox);
    stackPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

    getDialogPane().setContent(stackPane);
    getDialogPane().getStylesheets().add(("css/ExitConfirmationDialogStyleSheet.css"));


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
}

