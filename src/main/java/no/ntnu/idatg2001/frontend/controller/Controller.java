package no.ntnu.idatg2001.frontend.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;
import no.ntnu.idatg2001.frontend.view.View;

public abstract class Controller<T> {
  private View<T> view;
  SettingsDialog settingsDialog = new SettingsDialog(this);

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public abstract void onSettingsViewButtonPressed();

  public void onSettingSaveButtonPressed(ActionEvent event) {
    // Update the model with new settings data
    SettingsModel.getInstance().setLanguageSelection(settingsDialog.getLanguageSelection());
    SettingsModel.getInstance().setVolumeSliderValue(settingsDialog.getVolumeSliderValue());
    SettingsModel.getInstance().setMuted(settingsDialog.isMuteCheckBoxSelected());

    // Save the settings data to a file.
    SettingsModel.getInstance().saveSettings();

    //updates Main Menu.
    //menuView.updateMainMenu();

    // Close the settings dialog.
    onCloseSource(event);
  }

  public void onExitApplication(ActionEvent event) {
    event.consume();
    GameDAO.getInstance().close();
    Platform.exit();
    System.exit(0);
  }
}
