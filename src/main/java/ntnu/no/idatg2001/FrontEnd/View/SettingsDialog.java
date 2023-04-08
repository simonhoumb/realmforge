package ntnu.no.idatg2001.FrontEnd.View;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModel;

public class SettingsDialog extends Dialog<ButtonType> {

  private Dialog<ButtonType> settingsDialog;
  private ChoiceBox<String> languageSelection;
  private Slider volumeSlider;
  private CheckBox muteCheckBox;
  private ButtonType saveButton;
  private ButtonType cancelButton;
  private ResourceBundle resourceBundle;

  private SettingsModel settings = new SettingsModel();

  public SettingsDialog(SettingsModel model) {
    settingsDialog = new Dialog<>();
    initStyle(StageStyle.TRANSPARENT);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add(("css/settingsStyleSheet.css"));
  }

  public GridPane layout() {
    Locale locale = new Locale(settings.getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    // Create language selection box
    languageSelection = new ChoiceBox<>();
    languageSelection.getItems().addAll(
        resourceBundle.getString("settings.language.english"),
        resourceBundle.getString("settings.language.norwegian"),
        resourceBundle.getString("settings.language.french"));
    languageSelection.setValue(resourceBundle.getString("settings.language.selected"));

    // Create volume slider
    volumeSlider = new Slider(0, 100, 50);
    volumeSlider.setShowTickLabels(false);
    volumeSlider.setShowTickMarks(false);
    volumeSlider.setMajorTickUnit(50);
    volumeSlider.setMinorTickCount(5);
    volumeSlider.setBlockIncrement(10);

    // Create mute check box
    muteCheckBox = new CheckBox(resourceBundle.getString("settings.muteLable"));
    // Create layout and add components
    GridPane layout = new GridPane();
    layout.setHgap(10);
    layout.setVgap(10);
    layout.addRow(0, new Label(resourceBundle.getString("settings.language")), languageSelection);
    layout.addRow(1, new Label(resourceBundle.getString("settings.volume")), volumeSlider);
    layout.addRow(2, new Label(resourceBundle.getString("settings.mute")), muteCheckBox);
    settingsDialog.getDialogPane().setContent(layout);

    // Add buttons to dialog
    createSaveButton();
    createCancelButton();
    return layout;
  }

  private ButtonType createSaveButton() {
    saveButton = new ButtonType(resourceBundle.getString("settings.save"),
        ButtonData.OK_DONE);
    settingsDialog.getDialogPane().getButtonTypes().add(saveButton);
    return saveButton;
  }

  private ButtonType createCancelButton() {
    cancelButton = new ButtonType(resourceBundle.getString("settings.cancel"),
        ButtonData.CANCEL_CLOSE);
    settingsDialog.getDialogPane().getButtonTypes().add(cancelButton);
    return cancelButton;
  }

  public void setLanguageSelection(String language) {
    languageSelection.setValue(language);
  }

  public String getLanguageSelection() {
    return languageSelection.getValue();
  }

  public void setVolumeSliderValue(double volumeSliderValue) {
    volumeSlider.setValue(volumeSliderValue);
  }

  public double getVolumeSliderValue() {
    return volumeSlider.getValue();
  }

  public void setMuteCheckBoxSelected(boolean isMuted) {
    muteCheckBox.setSelected(isMuted);
  }

  public boolean isMuteCheckBoxSelected() {
    return muteCheckBox.isSelected();
  }

  public CheckBox getMuteCheckBox() {
    return muteCheckBox;
  }

  public Slider getVolumeSlider() {
    return volumeSlider;
  }
  public ChoiceBox<String> getLanguage() {
    return languageSelection;
  }

  public Stage getDialogStage() {
    return (Stage)settingsDialog.getDialogPane().getScene().getWindow();
  }

  public Scene getDialogScene() {
    return settingsDialog.getDialogPane().getScene();
  }

  public Dialog<ButtonType> getDialog() {
    return settingsDialog;

  }

  public ButtonType getSaveButton() {
    return saveButton;
  }

  public ButtonType getCancelButton() {
    return cancelButton;
  }
}

