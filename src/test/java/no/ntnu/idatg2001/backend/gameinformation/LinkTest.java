package no.ntnu.idatg2001.backend.gameinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.backend.actions.ItemAction;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LinkTest {
  Link link;

  @BeforeEach
  void setup() {
    link = new Link("testText", "testReference");
  }

  @AfterEach
  void tearDown() {
    link = null;
  }

  @Nested
  class getAndSetId {
    @Test
    void setIdSetsAndGetsCorrectId() {
      link.setId(10L);
      assertEquals(10L, link.getId());
    }
  }

  @Nested
  class getAndSetText {
    @Test
    void setTextSetsAndGetsCorrectText() {
      link.setText("newText");
      assertEquals("newText", link.getText());
    }
  }

  @Nested
  class getAndSetReference {
    @Test
    void setReferenceSetsAndGetsCorrectReference() {
      link.setReference("newReference");
      assertEquals("newReference", link.getReference());
    }
  }

  @Nested
  class addActionTest {
    @Test
    void validActionAddedDoesNotThrowException() {
      Action testAction = new GoldAction(10);
      assertTrue(link.addAction(testAction));
      assertTrue(link.getActions().contains(testAction));
    }

    @Test
    void notValidActionAddedThrowsException() {
      assertFalse(link.addAction(null));
      assertTrue(link.getActions().isEmpty());
    }
  }
  @Test
  void testRemoveActionIsNotNull() {
    Action action1 = new ItemAction("Action 1");
    Action action2 = new ItemAction("Action 2");
    link.addAction(action1);
    link.addAction(action2);
    boolean removed = link.removeAction(action1);

    assertTrue(removed);
    assertFalse(link.getActions().contains(action1));
    assertTrue(link.getActions().contains(action2));
  }

  @Test
  void testRemoveActionIsNull() {
    Action action1 = new ItemAction("Action 1");
    Action action2 = new ItemAction("Action 2");
    link.addAction(action1);
    link.addAction(action2);
    boolean removed = link.removeAction(null);

    assertFalse(removed);
    assertTrue(link.getActions().contains(action1));
    assertTrue(link.getActions().contains(action2));
  }

  @Nested
  class equalsTest {
    @Test
    void equalsReturnsTrueWhenTextAndReferenceIsSame() {
      Link link2 = new Link("testText", "testReference");
      assertEquals(link, link2);
    }

    @Test
    void equalsReturnsTrueWhenOnlyReferenceIsSame() {
      Link link2 = new Link("notSameText", "testReference");
      assertEquals(link, link2);
    }

    @Test
    void equalsReturnsFalseWhenOnlyTextIsSame() {
      Link link2 = new Link("testText", "notSameReference");
      assertNotEquals(link, link2);
    }

    @Test
    void equalsReturnsTrueWhenTextAndReferenceIsNotSame() {
      Link link2 = new Link("notSameText", "notSameReference");
      assertNotEquals(link, link2);
    }
  }
}