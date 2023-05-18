package no.ntnu.idatg2001.frontend.view.dialogs;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import no.ntnu.idatg2001.backend.MusicPlayer;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.Controller;

public class SettingsDialog<T extends Controller<T>> extends Dialog<ButtonType> {
  private ChoiceBox<String> languageSelection;
  private Slider volumeSlider;
  private CheckBox muteCheckBox;
  private Button saveButton;
  private Button cancelButton;
  private ResourceBundle resourceBundle;
  private T controller;

  public SettingsDialog(T controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    initModality(Modality.APPLICATION_MODAL);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add(("css/settingsStyleSheet.css"));
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    this.resourceBundle = ResourceBundle.getBundle("languages/settingsDialog", locale);
    createSaveButton();
    createCancelButton();
    createMuteCheckBox();
    createVolumeSlider();
    createChoiceBox();
    createLayout();
  }

  private void createSaveButton() {
    saveButton = new Button(resourceBundle.getString("settings.save"));
    getDialogPane().getChildren().add(saveButton);
    saveButton.setOnAction(event -> controller.onSettingSaveButtonPressed(event));
  }

  private void createCancelButton() {
    cancelButton = new Button(resourceBundle.getString("settings.cancel"));
    cancelButton.setCancelButton(true);
    cancelButton.setOnAction(event -> {
      setVolumeSliderValue(SettingsModel.getInstance().getVolumeSliderValue());
      getMuteCheckBox().setSelected(SettingsModel.getInstance().isMuted());
      controller.onCloseSource(event);
    });
    getDialogPane().getChildren().add(cancelButton);
  }

  private void createMuteCheckBox() {
    muteCheckBox = new CheckBox(resourceBundle.getString("settings.muteLable"));
    muteCheckBox.setSelected(SettingsModel.getInstance().isMuted());
    muteCheckBox.selectedProperty().addListener((observable, oldValue, newValue)-> {
      if (newValue.equals(Boolean.TRUE)) {
        MusicPlayer.getInstance().pauseMusic();
      } else {
        MusicPlayer.getInstance().startMusic();
      }
    });
  }

  private void createVolumeSlider() {
    volumeSlider = new Slider();
    volumeSlider.setValue(SettingsModel.getInstance().getVolumeSliderValue());
    volumeSlider.setShowTickLabels(false);
    volumeSlider.setShowTickMarks(false);
    volumeSlider.setMajorTickUnit(50);
    volumeSlider.setMinorTickCount(5);
    volumeSlider.setBlockIncrement(10);
    volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      double volume = getVolumeSliderValue() / 100;
      MusicPlayer.getInstance().musicVolume(volume);
    });
  }

  private void createChoiceBox() {
    languageSelection = new ChoiceBox<>();
    languageSelection.getItems().addAll(
        resourceBundle.getString("settings.language.english"),
        resourceBundle.getString("settings.language.norwegian"),
        resourceBundle.getString("settings.language.french"),
        resourceBundle.getString("settings.language.german"));
    languageSelection.setValue(resourceBundle.getString("settings.language.selected"));
  }

  private void createLayout() {
    GridPane gridLayout = new GridPane();
    VBox layout = new VBox();
    layout.setSpacing(10);
    gridLayout.setHgap(10);
    gridLayout.setVgap(10);
    gridLayout.addRow(0, new Label(resourceBundle.getString("settings.language")), languageSelection);
    gridLayout.addRow(1, new Label(resourceBundle.getString("settings.volume")), volumeSlider);
    gridLayout.addRow(2, new Label(resourceBundle.getString("settings.mute")), muteCheckBox);
    gridLayout.setAlignment(Pos.CENTER);
    HBox buttonBox = new HBox();
    buttonBox.setSpacing(10);
    buttonBox.getChildren().addAll(saveButton,cancelButton);
    buttonBox.setAlignment(Pos.BOTTOM_CENTER);
    layout.getChildren().addAll(gridLayout,buttonBox);
    getDialogPane().setContent(layout);
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

  public void setMuteCheckBox(CheckBox muteCheckBox) {
    this.muteCheckBox = muteCheckBox;
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
}

