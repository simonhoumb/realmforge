package no.ntnu.idatg2001.backend.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The AlertHelper class is a utility class for showing alerts.
 */
public class AlertHelper {

  /**
   * The constructor of the class.
   */
  private AlertHelper() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Show alert.
   *
   * @param type    the type
   * @param window  the window
   * @param title   the title
   * @param message the message
   */
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

  /**
   * Show confirmation alert.
   *
   * @param window  the window
   * @param title   the title
   * @param message the message
   * @return the boolean
   */
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

  /**
   * Show alert.
   *
   * @param window    the window.
   * @param title   the title
   * @param message the message
   */
  public static void showErrorAlert(Window window, String title, String message) {
    showAlert(AlertType.ERROR, window, title, message);
  }

  /**
   * Show alert.
   *
   * @param window    the window.
   * @param title   the title
   * @param message the message
   */
  public static void showWarningAlert(Window window,String title, String message) {
    showAlert(AlertType.WARNING,window, title, message);
  }

  /**
   * Show alert.
   *
   * @param window    the window.
   * @param title   the title
   * @param message the message
   */
  public static void showInformationAlert(Window window,String title, String message) {
    showAlert(AlertType.INFORMATION,window, title, message);
  }
}

