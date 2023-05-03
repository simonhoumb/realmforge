package no.ntnu.idatg2001.entityinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;
import no.ntnu.idatg2001.BackEnd.entityinformation.playerclasses.Ranger;
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
      ArrayList<String> testInventory = new ArrayList<>(unit.getEntityInventory());
      assertDoesNotThrow(() -> unit.removeFromInventory("Bow"));
      assertFalse(unit.getEntityInventory().contains("Bow"));
      assertNotEquals(testInventory, unit.getEntityInventory());
    }

    @Test
    void removeItemThatIsNotInInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getEntityInventory());
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> unit.removeFromInventory("Shield"));
      assertEquals(testInventory, unit.getEntityInventory());
    }
  }

  @Nested
  class addToInventoryTest {
    @Test
    void addOneItemToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getEntityInventory());
      unit.addToInventory("Jewel");
      assertTrue(unit.getEntityInventory().contains("Jewel"));
      assertEquals(testInventory.size() + 1, unit.getEntityInventory().size());
    }

    @Test
    void addMultipleItemsToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(unit.getEntityInventory());
      ArrayList<String> itemsToAdd = new ArrayList<>();
      itemsToAdd.add("Jewel");
      itemsToAdd.add("Map");
      itemsToAdd.add("Bread");
      unit.addToInventory(itemsToAdd);
      assertTrue(unit.getEntityInventory().containsAll(itemsToAdd));
      assertEquals(testInventory.size() + itemsToAdd.size(),
          unit.getEntityInventory().size());
    }
  }
}