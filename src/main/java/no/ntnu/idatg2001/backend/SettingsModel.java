package no.ntnu.idatg2001.backend;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

/**
 * The SettingsModel class is a singleton class that handles the settings of the game.
 */
public class SettingsModel {

  private final String filepath;
  private double volumeSliderValue;
  private boolean isMuted;
  private String languageSelection;
  private final Properties properties;
  private final DoubleProperty volume = new SimpleDoubleProperty();

  private static final SettingsModel instance = new SettingsModel();

  /**
   * Constructor of SettingsModel.
   */
  private SettingsModel() {
    properties = new Properties();
    filepath = "src/main/resources/settings/settings.properties";
    loadSettings();
  }

  /**
   * Saves the settings and write it to a file called settings.properties.
   */
  public void saveSettings() {
    try (OutputStream outputStream = new FileOutputStream(filepath)) {
      // Update the properties with new settings data
      properties.setProperty("languageSelection", getLanguageSelection());
      properties.setProperty("volumeSliderValue", String.valueOf(getVolumeSliderValue()));
      properties.setProperty("isMuted", Boolean.toString(isMuted()));
      // Save the updated properties to the file
      properties.store(outputStream, "Settings");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets instance of the singleton SettingsModel.
   *
   * @return the instance
   */
  public static SettingsModel getInstance() {
    return instance;
  }

  /**
   * Loads the settings from the file called settings.properties.
   */
  public void loadSettings() {
    try (InputStream inputStream = new FileInputStream(filepath)) {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    volumeSliderValue = Double.parseDouble(properties.getProperty("volumeSliderValue", "50"));
    isMuted = Boolean.parseBoolean(properties.getProperty("isMuted", "false"));
    languageSelection = properties.getProperty("languageSelection", "English");
  }

  /**
   * Gets instance of the singleton SettingsModel.
   *
   * @return the instance
   */
  public double getVolumeSliderValue() {
    return volumeSliderValue;
  }

  /**
   * Sets volume slider value.
   *
   * @param volume the volume
   */
  public void setVolumeSliderValue(double volume) {
    this.volumeSliderValue = volume;
    volumeProperty().set(volume);
  }

  /**
   * Is muted boolean.
   *
   * @return the boolean
   */
  public boolean isMuted() {
    return isMuted;
  }

  /**
   * Sets muted.
   *
   * @param muted the muted
   */
  public void setMuted(boolean muted) {
    isMuted = muted;
  }

  /**
   * Gets language selection.
   *
   * @return the language selection
   */
  public String getLanguageSelection() {
    return languageSelection;
  }

  /**
   * Sets language selection.
   *
   * @param languageSelection the language selection
   */
  public void setLanguageSelection(String languageSelection) {
    this.languageSelection = languageSelection;
  }

  /**
   * Gets volume.
   *
   * @return the volume
   */
  public double getVolume() {
    return volume.get();
  }

  /**
   * Volume property double property.
   *
   * @return the double property
   */
  public DoubleProperty volumeProperty() {
    return volume;
  }

  /**
   * Adds a listener to the volume property.
   *
   * @param listener the listener
   */
  public void addVolumeChangeListener(ChangeListener<Number> listener) {
    volume.addListener(listener);
  }

  /**
   * Gets the locale of the selected language.
   *
   * @return the locale
   */
  public Locale getLocale() {
    ResourceBundle languageCodes = ResourceBundle.getBundle("settings/languagecodes");
    String languageCode = languageCodes.getString(languageSelection.toLowerCase());
    String countryCode = languageCode.toUpperCase();
    return new Locale(languageCode, countryCode);
  }

}