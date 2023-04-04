package ntnu.no.idatg2001.FrontEnd.Controller;

import com.sun.jdi.DoubleValue;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

public class SettingsModel {

  private String filepath;

  private double volumeSliderValue;
  private boolean isMuted;
  private String languageSelection;
  private Properties properties;
  private final DoubleProperty volume = new SimpleDoubleProperty();

  public SettingsModel() {
    properties = new Properties();
    filepath = "src/main/resources/settings/settings.properties";
    loadSettings();
  }

  public void saveSettings() {
    try (OutputStream outputStream = new FileOutputStream(filepath)){
      // Update the properties with new settings data
      properties.setProperty("languageSelection", getLanguageSelection());
      properties.setProperty("volumeSliderValue", String.valueOf(getVolumeSliderValue()));
      properties.setProperty("isMuted", Boolean.toString(isMuted()));
      // Save the updated properties to the file
      properties.store(outputStream, "Settings");

    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Saved");
  }

  public void loadSettings() {
    try (InputStream inputStream = new FileInputStream(filepath)) {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    volumeSliderValue = Double.parseDouble(properties.getProperty("volumeSliderValue", "50"));
    isMuted = Boolean.parseBoolean(properties.getProperty("isMuted", "false"));
    languageSelection = properties.getProperty("languageSelection", "English");
    System.out.println("Loaded");
  }

  public double getVolumeSliderValue() {
    return volumeSliderValue;
  }

  public void setVolumeSliderValue(double volume) {
    this.volumeSliderValue = volume;
    volumeProperty().set(volume);
  }

  public boolean isMuted() {
    return isMuted;
  }

  public void setMuted(boolean muted) {
    isMuted = muted;
  }

  public String getLanguageSelection() {
    return languageSelection;
  }

  public void setLanguageSelection(String languageSelection) {
    this.languageSelection = languageSelection;
  }

  public double getVolume() {
    return volume.get();
  }

  public DoubleProperty volumeProperty() {
    return volume;
  }

  public void addVolumeChangeListener(ChangeListener<Number> listener) {
    volume.addListener(listener);
  }
  public Locale getLocale() {
    ResourceBundle languageCodes = ResourceBundle.getBundle("languages/languagecodes");
    String languageCode = languageCodes.getString(languageSelection.toLowerCase());
    String countryCode = languageCode.toUpperCase();
    return new Locale(languageCode, countryCode);
  }
}