package no.ntnu.idatg2001;

import java.util.Date;

public class SavedGames {
  private String name;
  private Date date;
  private String PlayerName;

  public SavedGames(String name, Date date, String playerName) {
    this.name = name;
    this.date = date;
    this.PlayerName = playerName;
  }
  public String getName() {
    return name;
  }

  public Date getDate() {
    return date;
  }

  public String getPlayerName() {
    return PlayerName;
  }
}
