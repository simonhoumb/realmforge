package ntnu.no.idatg2001.FrontEnd.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import ntnu.no.idatg2001.FrontEnd.View.ExitDialog;
import ntnu.no.idatg2001.FrontEnd.View.MainMenuView;
import ntnu.no.idatg2001.FrontEnd.View.SettingsView;

public class SettingsController {

  private SettingsModel settingsModel;
  private SettingsView settingsView;
  private MediaPlayer mediaPlayer;
  private ResourceBundle resources;
  private ExitDialog exitDialog;

  public SettingsController(SettingsModel settingsModel, SettingsView settingsView, MediaPlayer mediaPlayer, ExitDialog exitDialog) throws Exception {
    this.settingsModel = settingsModel;
    this.settingsView = settingsView;
    this.mediaPlayer = mediaPlayer;
    this.exitDialog = exitDialog;

    settingsView.getDialogPane().getStylesheets().add(("css/ExitConfirmationDialogStyleSheet.css"));
    settingsView.getDialogPane().setContent(settingsView.layout());
    settingsView.getDialogPane().getButtonTypes().addAll(settingsView.getSaveButton(), settingsView.getCancelButton());
    initHandlers();
  }

  private void initHandlers() throws Exception {
    // Handle save button
    ButtonBase saveButton = (ButtonBase) settingsView.getDialogPane().lookupButton(settingsView.getSaveButton());
    saveButton.addEventHandler(ActionEvent.ACTION, event -> {
      // Update the model with new settings data
      settingsModel.setLanguageSelection(settingsView.getLanguageSelection());
      settingsModel.setVolumeSliderValue(settingsView.getVolumeSliderValue());
      settingsModel.setMuted(settingsView.isMuteCheckBoxSelected());


      // Save the settings data to a file
      settingsModel.saveSettings();

      // Close the settings dialog
      settingsView.close();
    });

    settingsView.getMuteCheckBox().selectedProperty().addListener((observable, oldValue, newValue)-> {
      if (newValue) {
        mediaPlayer.pause();
      } else {
        mediaPlayer.play();
    }});

    settingsView.getVolumeSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
      double volume = settingsView.getVolumeSliderValue() / 100;
      mediaPlayer.setVolume(volume);
    });


    // Handle cancel button
    settingsView.getDialog().getDialogPane().lookupButton(settingsView.getCancelButton())
        .addEventHandler(ActionEvent.ACTION, event -> {
          // Close the settings dialog
          settingsView.close();
        });
  }

  public void showAndWait() {
    // Load the settings data from a file into the model
    settingsModel.loadSettings();

    // Update the view with the settings data from the model
    settingsView.setLanguageSelection(settingsModel.getLanguageSelection());
    settingsView.setVolumeSliderValue(settingsModel.getVolumeSliderValue());
    settingsView.setMuteCheckBoxSelected(settingsModel.isMuted());

    // Show the settings dialog
    settingsView.showAndWait();
  }

  public void close() {
    settingsView.close();
  }

  public void setLanguageSelection(String language) {
    settingsView.setLanguageSelection(language);
  }

  public String getLanguageSelection() {
    return settingsView.getLanguageSelection();
  }

  public void setVolumeSliderValue(double volumeSliderValue) {
    settingsView.setVolumeSliderValue(volumeSliderValue);
  }

  public double getVolumeSliderValue() {
    return settingsView.getVolumeSliderValue();
  }

  public void setMuteCheckBoxSelected(boolean isMuted) {
    settingsView.setMuteCheckBoxSelected(isMuted);
  }

  public boolean isMuteCheckBoxSelected() {
    return settingsView.isMuteCheckBoxSelected();
  }
  public void changeLanguage(String language) {

  }
}