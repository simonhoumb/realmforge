package no.ntnu.idatg2001;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

@Entity
public class GameSave {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String saveName;
  private LocalDateTime timeOfSave;
  private String playerName;
  @ManyToOne
  private Game game;
  @ManyToOne
  private Passage lastSavedPassage;


  public GameSave(Game gameToSave, String playerName) {
    this.game = gameToSave;
    this.saveName = gameToSave.getStory().getTitle();
    this.playerName = playerName;
    this.timeOfSave = LocalDateTime.now();
  }

  public GameSave() {}

  public Long getId() {
    return id;
  }

  public String getSaveName() {
    return saveName;
  }

  public LocalDateTime getTimeOfSave() {
    return timeOfSave;
  }

  public String getTimeOfSaveFormatted() {
    return timeOfSave.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
  }

  public String getPlayerName() {
    return playerName;
  }

  public Game getGame() {
    return game;
  }

  public Passage getLastSavedPassage() {
    return lastSavedPassage;
  }

  public void savePassage(Passage lastSavedPassage) {
    this.lastSavedPassage = lastSavedPassage;
  }
}
