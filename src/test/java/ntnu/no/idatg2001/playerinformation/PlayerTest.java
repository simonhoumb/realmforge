package ntnu.no.idatg2001.playerinformation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  @Test
  void setPlayerName() {
  }

  @Test
  void setPlayerHealthPoints() {
    player.setPlayerHealthPoints(-10);
    assertSame( 0, player.getPlayerHealthPoints());
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