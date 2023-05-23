package no.ntnu.idatg2001.backend;

import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The MusicPlayer class is a singleton class that plays music.
 */
public class MusicPlayer {
  private final MediaPlayer mediaPlayer;
  private static final MusicPlayer instance = new MusicPlayer();

  /**
   * The constructor of the class.
   */
  private MusicPlayer() {
    String musicFile = "/music/One-Bard-Band.mp3";
    Media music = new Media(Objects.requireNonNull(getClass()
        .getResource(musicFile)).toExternalForm());
    mediaPlayer = new MediaPlayer(music);
  }

  /**
   * Play music.
   */
  public void playMusic() {
    mediaPlayer.setVolume(SettingsModel.getInstance().getVolumeSliderValue() / 100);
    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    if (!SettingsModel.getInstance().isMuted()) {
      mediaPlayer.play();
    }
  }


  /**
   * Pause music.
   */
  public void pauseMusic() {
    mediaPlayer.pause();
  }

  /**
   * Stop music.
   */
  public void startMusic() {
    mediaPlayer.play();
  }

  /**
   * Music volume.
   *
   * @param volume the volume
   */
  public void musicVolume(double volume) {
    mediaPlayer.setVolume(volume);
  }

  /**
   * Gets instance of the singleton MusicPlayer.
   *
   * @return the instance
   */
  public static MusicPlayer getInstance() {
    return instance;
  }

}
