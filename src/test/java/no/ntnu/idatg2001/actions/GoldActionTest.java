package ntnu.no.idatg2001.actions;

import static org.junit.jupiter.api.Assertions.*;

import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Mage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldActionTest {
  Entity player;
  @BeforeEach
  void setUp() {
    player = new Mage("Gandalf");
    player.setGold(100);
  }

  @AfterEach
  void tearDown() {
    player = null;
  }

  @Test
  void executeGoldActionAddsGold() {
    Action goldAction = new GoldAction(100);
    goldAction.execute(player);
    assertEquals(200, player.getGold());
  }
}