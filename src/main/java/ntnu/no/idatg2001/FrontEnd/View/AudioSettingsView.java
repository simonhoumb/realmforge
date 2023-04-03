package ntnu.no.idatg2001.FrontEnd.View;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ntnu.no.idatg2001.FrontEnd.Controller.SettingsModule;

public class AudioSettingsView extends VBox {
  private JFXSlider volumeSlider;

  public AudioSettingsView(SettingsModule settings) {
    Label titleLabel = new Label("Audio SettingsModule");
    titleLabel.getStyleClass().add("title");

    volumeSlider = new JFXSlider();
    volumeSlider.setMin(0.0);
    volumeSlider.setMax(1.0);
    volumeSlider.setValue(1.0);
    volumeSlider.setPrefWidth(300);

    HBox volumeBox = new HBox(new Label("Volume"), volumeSlider);
    volumeBox.setAlignment(Pos.CENTER);
    volumeBox.setSpacing(20);

    setAlignment(Pos.CENTER);
    setSpacing(20);
    setPadding(new Insets(20));
    getChildren().addAll(titleLabel, volumeBox);
  }

  public JFXSlider getVolumeSlider() {
    return volumeSlider;
  }
}