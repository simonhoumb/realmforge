package ntnu.no.idatg2001.gameinformation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import ntnu.no.idatg2001.BackEnd.gameinformation.Link;
import ntnu.no.idatg2001.BackEnd.gameinformation.Passage;
import ntnu.no.idatg2001.BackEnd.gameinformation.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StoryTest {
  Story story;
  Passage openingPassage;
  Link dungeonLink1;

  @BeforeEach
  void setUp() {
    openingPassage = new Passage("Dungeon", "Some Dead Dude");
    dungeonLink1 = new Link("Open Dungeon Door", "Dungeon");
    openingPassage.addLink(dungeonLink1);
    story = new Story("MyStory", openingPassage);
  }

  @Test
  void addPassageTest() {
    Map<Link, Passage> testPassages = new HashMap<>(story.getPassages());
    Passage courtyard = new Passage("Courtyard", "This is the courtyard");
    assertTrue(story.addPassage(courtyard));
    assertNotEquals(testPassages, story.getPassages());
    assertEquals(testPassages.size() + 1, story.getPassages().size());
  }

  @Nested
  class removePassageTest {
    @Test
    void passageIsNotLinkedToOtherPassages() {
      Map<Link, Passage> testPassages = new HashMap<>(story.getPassages());
      story.removePassage(dungeonLink1);
      assertFalse(testPassages.containsValue(openingPassage));
    }
  }

  @Nested
  class getBrokenLinksTest {
    @Test
    void brokenLinkFound() {
      Link dungeonLink2 = new Link("Fall Down Hatch", "");
      openingPassage.addLink(dungeonLink2);
      assertFalse(story.getBrokenLinks().contains(dungeonLink2));
    }
  }

}