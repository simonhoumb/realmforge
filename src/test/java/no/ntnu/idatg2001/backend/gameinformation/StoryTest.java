package no.ntnu.idatg2001.backend.gameinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StoryTest {
  Story story;
  Passage openingPassage;
  Passage dungeonPassage;
  Link dungeonLink1;

  @BeforeEach
  void setUp() {
    openingPassage = new Passage("testOpeningPassage",
        new StringBuilder("This is the opening passage"));
    dungeonPassage = new Passage("Dungeon", new StringBuilder("This is the dungeon"));
    dungeonLink1 = new Link("Open Dungeon Door", "Dungeon");
    openingPassage.addLink(dungeonLink1);
    story = new Story("MyStory", openingPassage);
    story.addPassage(openingPassage);
    story.addPassage(dungeonPassage);
  }

  @AfterEach
  void tearDown() {
    openingPassage = null;
    dungeonPassage = null;
    dungeonLink1 = null;
    story = null;
  }

  @Test
  void addPassageTest() {
    Map<Link, Passage> testPassages = new HashMap<>(story.getPassages());
    Passage courtyard = new Passage("Courtyard", new StringBuilder("This is the courtyard"));
    assertTrue(story.addPassage(courtyard));
    assertNotEquals(testPassages, story.getPassages());
    assertEquals(testPassages.size() + 1, story.getPassages().size());
  }

  @Nested
  class removePassageTest {
    @Test
    void passageIsNotLinkedToOtherPassages() {
      Passage testPassage = new Passage("testPassage", new StringBuilder("This is a test"));
      story.addPassage(testPassage);
      assertDoesNotThrow(() -> story.removePassage(new Link("testLink", "testPassage")));
      assertFalse(story.getPassages().containsValue(testPassage));
    }

    @Test
    void passageIsLinkedToOtherPassages() {
      assertThrows(IllegalStateException.class,
          () -> story.removePassage(new Link("testLink", "Dungeon")));
      assertTrue(story.getPassages().containsValue(dungeonPassage));
    }
  }

  @Nested
  class getBrokenLinksTest {
    @Test
    void brokenLinkWithEmptyReferenceFound() {
      Link dungeonLink2 = new Link("Fall Down Hatch", "");
      openingPassage.addLink(dungeonLink2);
      assertTrue(story.getBrokenLinks().contains(dungeonLink2));
    }

    @Test
    void brokenLinkWithNotEmptyReferenceFound() {
      Link dungeonLink2 = new Link("Fall Down Hatch", "NonExistentPassage");
      openingPassage.addLink(dungeonLink2);
      assertTrue(story.getBrokenLinks().contains(dungeonLink2));
    }

    @Test
    void noBrokenLinksFound() {
      System.out.println(story.getBrokenLinks());
      assertTrue(story.getBrokenLinks().isEmpty());
    }
  }

  @Nested
  class getPassageTest {
    @Test
    void passageIsFound() {
      assertEquals(dungeonPassage, story.getPassage(dungeonLink1));
    }

    @Test
    void passageIsNotFound() {
      Link testLink = new Link("testText", "testReference");
      assertNull(story.getPassage(testLink));
    }
  }

}