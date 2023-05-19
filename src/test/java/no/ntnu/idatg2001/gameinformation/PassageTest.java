package no.ntnu.idatg2001.gameinformation;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PassageTest {
  Passage passage;

  @BeforeEach
  void setup() {
    passage = new Passage("testTitle", new StringBuilder("testContent"));
  }

  @AfterEach
  void tearDown() {
    passage = null;
  }

  @Nested
  class addLinkTest {
    @Test
    void validLinkAddedDoesNotThrowException() {
      Link testLink = new Link("testText", "testReference");
      assertTrue(passage.addLink(testLink));
      assertTrue(passage.getLinks().contains(testLink));
    }

    @Test
    void notValidLinkAddedThrowsException() {
      assertFalse(passage.addLink(null));
      assertTrue(passage.getLinks().isEmpty());
    }
  }

  @Nested
  class hasLinksTest {
    @Test
    void passageHasLinks() {
      Link testLink = new Link("testText", "testReference");
      passage.addLink(testLink);
      assertTrue(passage.hasLinks());
    }

    @Test
    void passageHasNoLinks() {
      Link testLink = new Link("testText", "testReference");
      assertFalse(passage.hasLinks());
    }
  }
}