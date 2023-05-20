package no.ntnu.idatg2001.backend.gameinformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

@Entity
@Table(name = "game_save")
public class GameSave {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String saveName;
  private LocalDateTime timeOfSave;
  private String playerName;
  @OneToOne
  @JoinColumn(name = "game_id")
  private Game game;
  @OneToOne
  @JoinColumn(name = "passage_id")
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
  public Long setId(Long id) {
    return this.id = id;
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

  public String getStoryAndLastPassage() {
    StringBuilder location = new StringBuilder(this.saveName + " - ");
    if (this.lastSavedPassage == null) {
      location.append(this.game.begin().getTitle());
    } else {
      location.append(this.lastSavedPassage.getTitle());
    }
    return location.toString();
  }
}
