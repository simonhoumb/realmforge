package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModule;

public class GeneralSettingsView extends VBox {
  private JFXCheckBox soundEffectsCheckbox;

  public GeneralSettingsView(SettingsModule settingsModel) {
    Label titleLabel = new Label("General SettingsModule");
    titleLabel.getStyleClass().add("title");

    soundEffectsCheckbox = new JFXCheckBox("Enable Music Effects");
    soundEffectsCheckbox.setSelected(true);
    soundEffectsCheckbox.setPadding(new Insets(10));

    setAlignment(Pos.CENTER);
    setSpacing(20);
    setPadding(new Insets(20));
    getChildren().addAll(titleLabel, soundEffectsCheckbox);
  }

  public JFXCheckBox getSoundEffectsCheckbox() {
    return soundEffectsCheckbox;
  }
}