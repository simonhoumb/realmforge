package no.ntnu.idatg2001.entityinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



class UnitTest {
  private static Unit unit;
  @BeforeEach
  void setUpEach() {
     unit = new Ranger("Legolas");
}
  @Nested
  class removeFromInventoryTest {
    @Test
    void removeItemThatIsInInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getUnitInventory());
      assertDoesNotThrow(() -> unit.removeFromInventory("Bow"));
      assertFalse(unit.getUnitInventory().contains("Bow"));
      assertNotEquals(testInventory, unit.getUnitInventory());
    }

    @Test
    void removeItemThatIsNotInInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getUnitInventory());
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> unit.removeFromInventory("Shield"));
      assertEquals(testInventory, unit.getUnitInventory());
    }
  }

  @Nested
  class addToInventoryTest {
    @Test
    void addOneItemToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getUnitInventory());
      unit.addToInventory("Jewel");
      assertTrue(unit.getUnitInventory().contains("Jewel"));
      assertEquals(testInventory.size() + 1, unit.getUnitInventory().size());
    }

    @Test
    void addMultipleItemsToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getUnitInventory());
      ArrayList<String> itemsToAdd = new ArrayList<>();
      itemsToAdd.add("Jewel");
      itemsToAdd.add("Map");
      itemsToAdd.add("Bread");
      unit.addToInventory(itemsToAdd);
      assertTrue(unit.getUnitInventory().containsAll(itemsToAdd));
      assertEquals(testInventory.size() + itemsToAdd.size(),
          unit.getUnitInventory().size());
    }
  }
}