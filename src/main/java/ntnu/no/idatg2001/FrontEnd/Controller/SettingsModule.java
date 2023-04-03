package ntnu.no.idatg2001.FrontEnd.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class SettingsModule {

  private boolean musicMuted;
  private float volume;
  private String language;

  public SettingsModule() {
    // Initialize default settings
    musicMuted = false;
    volume = 0.5f;
    language = "en";
  }

  public boolean isMusicMuted() {
    return musicMuted;
  }

  public void setMusicMuted(boolean musicMuted) {
    this.musicMuted = musicMuted;
  }

  public float getVolume() {
    return volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void saveSettings() throws Exception {
    // Save settings to a properties file
    Properties props = new Properties();
    props.setProperty("musicMuted", Boolean.toString(musicMuted));
    props.setProperty("volume", Float.toString(volume));
    props.setProperty("language", language);
    props.store(new FileOutputStream("settings.properties"), null);
  }

  public void loadSettings() throws Exception {
    // Load settings from a properties file
    Properties props = new Properties();
    props.load(new FileInputStream("settings.properties"));
    musicMuted = Boolean.parseBoolean(props.getProperty("musicMuted", "false"));
    volume = Float.parseFloat(props.getProperty("volume", "0.5"));
    language = props.getProperty("language", "en");
  }
}
