package ntnu.no.idatg2001.FrontEnd.Controller;

import javafx.scene.control.ButtonType;
import ntnu.no.idatg2001.FrontEnd.View.SettingsView;

public class SettingsController {

  private SettingsView settingsView;

  public SettingsController(SettingsView settingsView) {
    this.settingsView = settingsView;
    initHandlers();
  }

  private void initHandlers() {
    // Handle save button
    settingsView.getDialog().getDialogPane().lookupButton(settingsView.getSaveButton());
    // TODO: Implement save functionality}

    // Handle cancel button
    settingsView.getDialog().getDialogPane().lookupButton(settingsView.getCancelButton());
      settingsView.close();
  }

  public void showAndWait() {
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
}
