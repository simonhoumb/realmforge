package ntnu.no.idatg2001.FrontEnd.View;

import javafx.scene.Scene;
import javafx.scene.control.*;
    import javafx.scene.layout.*;
    import javafx.stage.*;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsController;

public class SettingsView extends Dialog<ButtonType> {

  private Dialog<ButtonType> dialog;
  private ChoiceBox<String> languageSelection;
  private Slider volumeSlider;
  private CheckBox muteCheckBox;

  public SettingsView() {
    initStyle(StageStyle.TRANSPARENT);
    // Create language selection box
    languageSelection = new ChoiceBox<>();
    languageSelection.getItems().addAll("English", "Norwegian", "French");
    languageSelection.setValue("English");

    // Create volume slider
    volumeSlider = new Slider(0, 100, 50);
    volumeSlider.setShowTickLabels(true);
    volumeSlider.setShowTickMarks(true);
    volumeSlider.setMajorTickUnit(50);
    volumeSlider.setMinorTickCount(5);
    volumeSlider.setBlockIncrement(10);

    // Create mute check box
    muteCheckBox = new CheckBox("Mute");

    // Create layout and add components
    GridPane layout = new GridPane();
    layout.setHgap(10);
    layout.setVgap(10);
    layout.addRow(0, new Label("Language:"), languageSelection);
    layout.addRow(1, new Label("Volume:"), volumeSlider);
    layout.addRow(2, muteCheckBox);

    ButtonType saveButtonType = new ButtonType("Save");
    ButtonType cancelButtonType = new ButtonType("Cancel");


    // Add buttons to dialog
    getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);
    getDialogPane().setContent(layout);
    getDialogPane().getStylesheets().add(("css/ExitConfirmationDialogStyleSheet.css"));
  }

  /*
  public void showAndWait() {
    dialog.showAndWait();
  }

  public void close() {
    dialog.close();
  }


   */
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

  public Stage getDialogStage() {
    return (Stage)dialog.getDialogPane().getScene().getWindow();
  }

  public Scene getDialogScene() {
    return dialog.getDialogPane().getScene();
  }

  public Dialog<ButtonType> getDialog() {
    return dialog;

  }
  public ButtonType getSaveButton() {
    return getSaveButton();
  }
  public ButtonType getCancelButton() {
    return getCancelButton();
  }
}
