package ntnu.no.idatg2001.FrontEnd.Controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ntnu.no.idatg2001.FrontEnd.View.MainMenuView;
import ntnu.no.idatg2001.FrontEnd.View.SettingsView;

public class MainMenuController {

  public MainMenuController() {
  }

  public void exitButtonAction(ActionEvent actionEvent) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to quit?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK){
      Platform.exit();
    }
  }

  public void settingsButtonAction(ActionEvent actionEvent) throws IOException {

  }
}
