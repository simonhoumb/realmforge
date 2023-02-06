package ntnu.no.idatg2001.playerinformation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  Player player = new PlayerBuilder().setPlayerName("John")
      .setPlayerClass(PlayerClass.valueOfClassNumber(2)).getPlayer();
  @Test
  void setPlayerName() {
    player.setPlayerName("Sett");
    assertSame("Sett", player.getPlayerName());
  }

  @Test
  void setPlayerHealthPoints() {
    player.setPlayerHealthPoints(-10);
  }

  @Test
  void setPlayerScore() {
  }

  @Test
  void setGold() {
  }

  @Test
  void setPlayerClass() {

  }
}