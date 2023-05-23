package no.ntnu.idatg2001.backend.gameinformation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.goals.Goal;

/**
 * The GameSave class represents a game save.
 */
@Entity
@Table(name = "game_save")
public class GameSave {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String saveName;
  private LocalDateTime timeOfSave;
  private String playerName;
  @OneToOne (cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "game_id")
  private Game game;
  @OneToOne
  @JoinColumn(name = "passage_id")
  private Passage lastSavedPassage;


  /**
   * Constructor for GameSave.
   *
   * @param unit The unit to be saved.
   * @param story The story to be saved.
   * @param goals The goals to be saved.
   * @param playerName The player name to be saved.
   */
  public GameSave(Unit unit, Story story, List<Goal> goals, String playerName) {
    this.game = new Game(unit, story, goals);
    this.saveName = story.getTitle();
    this.playerName = playerName;
    this.timeOfSave = LocalDateTime.now();
  }

  /**
   * Sets the unit health.
   *
   * @param health The health to be set.
   */
  public void setUnitHealth(int health) {
    this.game.getUnit().setUnitHealth(health);
  }


  public GameSave() {}

  /**
   * Gets the id.
   *
   * @return The id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id The id to be set.
   */
  public void setId(Long id) {
    this.id = id;
  }


  /**
   * gets the save name.
   *
   * @return The save name.
   */
  public String getSaveName() {
    return saveName;
  }

  /**
   * Sets the save name.
   *
   * @param lastSavedPassage The save name to be set.
   */
  public void setLastSavedPassage(
      Passage lastSavedPassage) {
    this.lastSavedPassage = lastSavedPassage;
  }

  /**
   * Sets the Time of Save.
   *
   * @param timeOfSave The time of save to be set.
   */
  public void setTimeOfSave(LocalDateTime timeOfSave) {
    this.timeOfSave = timeOfSave;
  }

  /**
   * Sets the Player Name.
   *
   * @param playerName The player name to be set.
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Sets the Game.
   *
   * @param game The game to be set.
   */
  public void setGame(Game game) {
    this.game = game;
  }

  /**
   * gets the time of save.
   *
   * @return The time of save.
   */
  public LocalDateTime getTimeOfSave() {
    return timeOfSave;
  }

  /**
   * gets the time of save formatted.
   *
   * @return The time of save formatted.
   */
  public String getTimeOfSaveFormatted() {
    return timeOfSave.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
  }

  /**
   * gets the player name.
   *
   * @return The player name.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * gets the game.
   *
   * @return The game.
   */
  public Game getGame() {
    return game;
  }

  /**
   * gets the last saved passage.
   *
   * @return The last saved passage.
   */
  public Passage getLastSavedPassage() {
    return lastSavedPassage;
  }

  /**
   * sets the last saved passage.
   *
   * @param lastSavedPassage The last saved passage to be set.
   */
  public void savePassage(Passage lastSavedPassage) {
    this.lastSavedPassage = lastSavedPassage;
  }

  /**
   * gets the goals.
   *
   * @return The goals.
   */
  public List<Goal> getGoals() {
    return this.game.getGoals();
  }

  /**
   * gets the story.
   *
   * @return The story.
   */
  public Story getStory() {
    return this.game.getStory();
  }

  /**
   * gets the Story and last passage.
   *
   * @return The story and last passage.
   */
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
