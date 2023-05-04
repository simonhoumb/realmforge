package no.ntnu.idatg2001.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.Test;

class HealthActionTest {

  @Test
   void settingEntityHealthAsLessThanZero() {
    Unit player = new Ranger("TestRanger");
    int expectedHealth = 0;
    player.setUnitHealth(-100);
    int actualHealth = player.getUnitHealth();
    assertEquals(expectedHealth, actualHealth);
  }

  @Test
  void settingEntityHealthAsMoreThanMaxHealth() {
    Unit player = new Ranger("TestRanger");
    int expectedHealth = player.getUnitHealthMax(); //Expect it to set it to max health and not above
    player.setUnitHealth(99999);
    int actualHealth = player.getUnitHealth();
    assertEquals(expectedHealth, actualHealth);
  }
}