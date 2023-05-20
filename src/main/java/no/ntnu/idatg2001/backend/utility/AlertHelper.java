package no.ntnu.idatg2001.backend.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class AlertHelper {

  private final String  cssFile = "/css/alerts.css";

  private AlertHelper() {
    throw new IllegalStateException("Utility class");
  }

  public static void showAlert(AlertType type,Window window, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.getDialogPane().getStylesheets().add("/css/alerts.css");
    alert.initStyle(StageStyle.UNDECORATED);
    alert.initOwner(window);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.showAndWait();
  }

  public static boolean showConfirmationAlert(Window window,String title, String message) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.initOwner(window);
    alert.getDialogPane().getStylesheets().add("/css/alerts.css");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.setAlwaysOnTop(true);
    stage.toFront();

    ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
    return result == ButtonType.OK;
  }

  public static void showErrorAlert(Window window, String title, String message) {
    showAlert(AlertType.ERROR, window, title, message);
  }

  public static void showWarningAlert(Window window,String title, String message) {
    showAlert(AlertType.WARNING,window, title, message);
  }

  public static void showInformationAlert(Window window,String title, String message) {
    showAlert(AlertType.INFORMATION,window, title, message);
  }
}

