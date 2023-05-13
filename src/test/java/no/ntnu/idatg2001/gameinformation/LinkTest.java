package no.ntnu.idatg2001.gameinformation;

import static org.junit.jupiter.api.Assertions.*;

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