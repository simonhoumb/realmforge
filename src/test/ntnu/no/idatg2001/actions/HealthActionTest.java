package ntnu.no.idatg2001.actions;

import static org.junit.jupiter.api.Assertions.*;

import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.Test;

class HealthActionTest {

  @Test
  void settingEntityHealthAsLessThanZero() {
    Entity player = new Ranger("TestRanger");
    int expectedHealth = 0;
    player.setEntityHealth(-100);
    int actualHealth = player.getEntityHealth();
    assertEquals(expectedHealth, actualHealth);
  }

  @Test
  void settingEntityHealthAsMoreThanMaxHealth() {
    Entity player = new Ranger("TestRanger");
    int expectedHealth = player.getEntityHealthMax(); //Expect it to set it to max health and not above
    player.setEntityHealth(99999);
    int actualHealth = player.getEntityHealth();
    assertEquals(expectedHealth, actualHealth);
  }
}