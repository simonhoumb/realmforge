package no.ntnu.idatg2001.backend.entityinformation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerClassTest {

  @Test
  void testValueOfPlayerClass() {
    PlayerClass mage = PlayerClass.valueOfPlayerClass("Mage");
    PlayerClass rogue = PlayerClass.valueOfPlayerClass("Rogue");
    PlayerClass warrior = PlayerClass.valueOfPlayerClass("Warrior");
    PlayerClass ranger = PlayerClass.valueOfPlayerClass("Ranger");
    PlayerClass custom = PlayerClass.valueOfPlayerClass("Custom");

    assertNotNull(mage);
    assertNotNull(rogue);
    assertNotNull(warrior);
    assertNotNull(ranger);
    assertNotNull(custom);

    assertEquals(PlayerClass.MAGE, mage);
    assertEquals(PlayerClass.ROGUE, rogue);
    assertEquals(PlayerClass.WARRIOR, warrior);
    assertEquals(PlayerClass.RANGER, ranger);
    assertEquals(PlayerClass.CUSTOM, custom);
  }

  @Test
  void testValueOfClassNumber() {
    PlayerClass mage = PlayerClass.valueOfClassNumber(1);
    PlayerClass rogue = PlayerClass.valueOfClassNumber(2);
    PlayerClass warrior = PlayerClass.valueOfClassNumber(3);
    PlayerClass ranger = PlayerClass.valueOfClassNumber(4);
    PlayerClass custom = PlayerClass.valueOfClassNumber(5);

    assertNotNull(mage);
    assertNotNull(rogue);
    assertNotNull(warrior);
    assertNotNull(ranger);
    assertNotNull(custom);

    assertEquals(PlayerClass.MAGE, mage);
    assertEquals(PlayerClass.ROGUE, rogue);
    assertEquals(PlayerClass.WARRIOR, warrior);
    assertEquals(PlayerClass.RANGER, ranger);
    assertEquals(PlayerClass.CUSTOM, custom);
  }

  @Test
  void testGetClassName() {
    assertEquals("Mage", PlayerClass.MAGE.getClassName());
    assertEquals("Rogue", PlayerClass.ROGUE.getClassName());
    assertEquals("Warrior", PlayerClass.WARRIOR.getClassName());
    assertEquals("Ranger", PlayerClass.RANGER.getClassName());
    assertEquals("Custom", PlayerClass.CUSTOM.getClassName());
  }

  @Test
  void testGetClassNumber() {
    assertEquals(1, PlayerClass.MAGE.getClassNumber());
    assertEquals(2, PlayerClass.ROGUE.getClassNumber());
    assertEquals(3, PlayerClass.WARRIOR.getClassNumber());
    assertEquals(4, PlayerClass.RANGER.getClassNumber());
    assertEquals(5, PlayerClass.CUSTOM.getClassNumber());
  }
}
