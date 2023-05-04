package no.ntnu.idatg2001.backend;

import java.util.Objects;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
  private MediaPlayer mediaPlayer;
  private static final MusicPlayer instance = new MusicPlayer();

  private MusicPlayer() {
    String musicFile = "/music/One-Bard-Band.mp3";
    Media music = new Media(Objects.requireNonNull(getClass().getResource(musicFile)).toExternalForm());
    mediaPlayer = new MediaPlayer(music);
  }

  public void playMusic() {
    mediaPlayer.setVolume(SettingsModel.getInstance().getVolumeSliderValue() / 100);
    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    mediaPlayer.setCycleCount(mediaPlayer.INDEFINITE);
    if (!SettingsModel.getInstance().isMuted()) {
      mediaPlayer.play();
    }
  }

  public void pauseMusic() {
    mediaPlayer.pause();
  }

  public void startMusic() {
    mediaPlayer.play();
  }

  public void musicVolume(double volume) {
    mediaPlayer.setVolume(volume);
  }

  public static MusicPlayer getInstance() {
    return instance;
  }

}
