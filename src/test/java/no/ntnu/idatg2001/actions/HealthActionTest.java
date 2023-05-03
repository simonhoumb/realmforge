package no.ntnu.idatg2001.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;
import no.ntnu.idatg2001.BackEnd.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.Test;

class HealthActionTest {

  @Test
   void settingEntityHealthAsLessThanZero() {
    Unit player = new Ranger("TestRanger");
    int expectedHealth = 0;
    player.setEntityHealth(-100);
    int actualHealth = player.getEntityHealth();
    assertEquals(expectedHealth, actualHealth);
  }

  @Test
  void settingEntityHealthAsMoreThanMaxHealth() {
    Unit player = new Ranger("TestRanger");
    int expectedHealth = player.getEntityHealthMax(); //Expect it to set it to max health and not above
    player.setEntityHealth(99999);
    int actualHealth = player.getEntityHealth();
    assertEquals(expectedHealth, actualHealth);
  }
}