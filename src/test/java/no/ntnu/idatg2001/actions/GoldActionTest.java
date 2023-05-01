package no.ntnu.idatg2001.actions;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.BackEnd.actions.Action;
import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;
import no.ntnu.idatg2001.BackEnd.entityinformation.playerclasses.Mage;
import no.ntnu.idatg2001.BackEnd.actions.GoldAction;
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