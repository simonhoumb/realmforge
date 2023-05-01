package no.ntnu.idatg2001.FrontEnd.Controller;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBase;
import javafx.scene.media.MediaPlayer;
import no.ntnu.idatg2001.FrontEnd.View.ExitDialogView;
import no.ntnu.idatg2001.FrontEnd.View.SettingsDialog;

public class SettingsController {

  private SettingsModel settingsModel;
  private SettingsDialog settingsDialog;
  private MediaPlayer mediaPlayer;
  private ResourceBundle resources;
  private ExitDialogView exitDialogView;

  public SettingsController(SettingsModel settingsModel, SettingsDialog settingsDialog, MediaPlayer mediaPlayer) throws Exception {
    this.settingsModel = settingsModel;
    this.settingsDialog = settingsDialog;
    this.mediaPlayer = mediaPlayer;

    settingsDialog.getDialogPane().setContent(settingsDialog.layout());
    settingsDialog.getDialogPane().getButtonTypes().addAll(settingsDialog.getSaveButton(), settingsDialog.getCancelButton());
    initHandlers();
  }

  private void initHandlers() throws Exception {
    // Handle save button
    ButtonBase saveButton = (ButtonBase) settingsDialog.getDialogPane().lookupButton(settingsDialog.getSaveButton());
    saveButton.addEventHandler(ActionEvent.ACTION, event -> {
      // Update the model with new settings data
      settingsModel.setLanguageSelection(settingsDialog.getLanguageSelection());
      settingsModel.setVolumeSliderValue(settingsDialog.getVolumeSliderValue());
      settingsModel.setMuted(settingsDialog.isMuteCheckBoxSelected());


      // Save the settings data to a file
      settingsModel.saveSettings();

      // Close the settings dialog
      settingsDialog.close();
    });

    settingsDialog.getMuteCheckBox().selectedProperty().addListener((observable, oldValue, newValue)-> {
      if (newValue) {
        mediaPlayer.pause();
      } else {
        mediaPlayer.play();
    }});

    settingsDialog.getVolumeSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
      double volume = settingsDialog.getVolumeSliderValue() / 100;
      mediaPlayer.setVolume(volume);
    });


    // Handle cancel button
    settingsDialog.getDialog().getDialogPane().lookupButton(settingsDialog.getCancelButton())
        .addEventHandler(ActionEvent.ACTION, event -> {
          // Close the settings dialog
          settingsDialog.close();
        });
  }

  public void showAndWait() {
    // Load the settings data from a file into the model
    settingsModel.loadSettings();

    // Update the view with the settings data from the model
    settingsDialog.setLanguageSelection(settingsModel.getLanguageSelection());
    settingsDialog.setVolumeSliderValue(settingsModel.getVolumeSliderValue());
    settingsDialog.setMuteCheckBoxSelected(settingsModel.isMuted());

    // Show the settings dialog
    settingsDialog.showAndWait();
  }

  public void close() {
    settingsDialog.close();
  }

  public void setLanguageSelection(String language) {
    settingsDialog.setLanguageSelection(language);
  }

  public String getLanguageSelection() {
    return settingsDialog.getLanguageSelection();
  }

  public void setVolumeSliderValue(double volumeSliderValue) {
    settingsDialog.setVolumeSliderValue(volumeSliderValue);
  }

  public double getVolumeSliderValue() {
    return settingsDialog.getVolumeSliderValue();
  }

  public void setMuteCheckBoxSelected(boolean isMuted) {
    settingsDialog.setMuteCheckBoxSelected(isMuted);
  }

  public boolean isMuteCheckBoxSelected() {
    return settingsDialog.isMuteCheckBoxSelected();
  }
  public void changeLanguage(String language) {

  }
}