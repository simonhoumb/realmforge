package no.ntnu.idatg2001.frontend.view;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.Controller;

public class ExitDialog<T extends Controller<T>> extends Dialog {
  private ResourceBundle resourceBundle;
  private Label titleLabel;
  private Label messageLabel;
  private Button confirmButton;
  private Button cancelButton;
  private StackPane layout;
  private T controller;

  public ExitDialog(T controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    getDialogPane().getStylesheets().add(("css/exitDialog.css"));
    createConfirmButton();
    createCancelButton();
    createLayout();
  }

  private void createLayout() {
    titleLabel = new Label(resourceBundle.getString("exit.title"));
    titleLabel.setFont(Font.font(18));
    messageLabel = new Label(resourceBundle.getString("exit.message"));
    messageLabel.setFont(Font.font(14));
    HBox buttonBox = new HBox(confirmButton, cancelButton);
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);
    buttonBox.setSpacing(10);
    VBox vbox = new VBox(titleLabel, messageLabel,buttonBox);
    vbox.setSpacing(10);
    vbox.setFillWidth(true);

    layout = new StackPane();
    layout.getChildren().add(vbox);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(10,10,10,10));
    getDialogPane().setContent(layout);
    getDialogPane().setMinHeight(120);
  }

  private void createConfirmButton(){
    confirmButton = new Button(resourceBundle.getString("exit.confirmButton"));
    confirmButton.setOnAction(event -> {
      controller.onExitApplication(event);
    });
  }

  private void createCancelButton() {
    cancelButton = new Button(resourceBundle.getString("exit.cancelButton"));
    cancelButton.setCancelButton(true);
    cancelButton.setOnAction(event -> {
      controller.onCloseSource(event);
    });
  }
}

