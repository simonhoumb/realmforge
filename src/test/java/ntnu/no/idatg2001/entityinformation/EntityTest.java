package ntnu.no.idatg2001.entityinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;
import ntnu.no.idatg2001.BackEnd.entityinformation.playerclasses.Ranger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



class EntityTest {
  private static Entity entity;
  @BeforeEach
  void setUpEach() {
     entity = new Ranger("Legolas");
}
  @Nested
  class removeFromInventoryTest {
    @Test
    void removeItemThatIsInInventory() {
      ArrayList<String> testInventory = new ArrayList<>(entity.getEntityInventory());
      assertDoesNotThrow(() -> entity.removeFromInventory("Bow"));
      assertFalse(entity.getEntityInventory().contains("Bow"));
      assertNotEquals(testInventory, entity.getEntityInventory());
    }

    @Test
    void removeItemThatIsNotInInventory() {
      ArrayList<String> testInventory = new ArrayList<>(entity.getEntityInventory());
      Exception thrown = assertThrows(IllegalArgumentException.class,
          () -> entity.removeFromInventory("Shield"));
      assertEquals(testInventory, entity.getEntityInventory());
    }
  }

  @Nested
  class addToInventoryTest {
    @Test
    void addOneItemToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(entity.getEntityInventory());
      entity.addToInventory("Jewel");
      assertTrue(entity.getEntityInventory().contains("Jewel"));
      assertEquals(testInventory.size() + 1, entity.getEntityInventory().size());
    }

    @Test
    void addMultipleItemsToInventory() {
      ArrayList<String> testInventory = new ArrayList<>(entity.getEntityInventory());
      ArrayList<String> itemsToAdd = new ArrayList<>();
      itemsToAdd.add("Jewel");
      itemsToAdd.add("Map");
      itemsToAdd.add("Bread");
      entity.addToInventory(itemsToAdd);
      assertTrue(entity.getEntityInventory().containsAll(itemsToAdd));
      assertEquals(testInventory.size() + itemsToAdd.size(),
          entity.getEntityInventory().size());
    }
  }
}