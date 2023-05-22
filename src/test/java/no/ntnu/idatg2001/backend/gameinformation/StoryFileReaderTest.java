package no.ntnu.idatg2001.backend.gameinformation;

import no.ntnu.idatg2001.backend.actions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoryFileReaderTest {

  @Test
  void testReadFile_ValidFile() {
    StoryFileReader reader = new StoryFileReader();
    Story story = reader.readFile("src/test/resources/stories/valid_story.Paths");

    assertNotNull(story);
    assertEquals("Story Title", story.getTitle());

    Passage openingPassage = story.getOpeningPassage();
    assertNotNull(openingPassage);
    assertEquals("Opening Passage", openingPassage.getTitle());
    assertEquals("This is the opening passage.\nIt has some text.",
        openingPassage.getContent().toString());

    assertEquals(2, openingPassage.getLinks().size());

    Link link1 = openingPassage.getLinks().get(0);
    assertEquals("First Link", link1.getText());
    assertEquals("passage2", link1.getReference());

    assertEquals(2, link1.getActions().size());
    assertTrue(link1.getActions().get(0) instanceof DamageAction);
    assertEquals(10, link1.getActions().get(0).getValue());
    assertTrue(link1.getActions().get(1) instanceof HealthAction);
    assertEquals(50, link1.getActions().get(1).getValue());

    Link link2 = openingPassage.getLinks().get(1);
    assertEquals("Second Link", link2.getText());
    assertEquals("passage3", link2.getReference());

    assertEquals(1, link2.getActions().size());
    assertTrue(link2.getActions().get(0) instanceof ArmorAction);
    assertEquals(5, link2.getActions().get(0).getValue());

    assertEquals(3, story.getPassages().size());

    Passage passage2 = story.getPassage(link1);
    assertEquals("passage2", passage2.getTitle());
    assertEquals("This is passage 2.", passage2.getContent().toString());

    Passage passage3 = story.getPassage(link2);
    assertEquals("passage3", passage3.getTitle());
    assertEquals("This is passage 3.", passage3.getContent().toString());

    assertEquals(1, passage3.getLinks().size());
    Link link3 = passage3.getLinks().get(0);
    assertEquals("Final Link", link3.getText());
    assertEquals("passage4", link3.getReference());

    assertEquals(1, link3.getActions().size());
    assertTrue(link3.getActions().get(0) instanceof WinAction);
  }

  @Test
  void testReadFile_InvalidFile() {
    StoryFileReader reader = new StoryFileReader();
    Story story = reader.readFile("src/test/resources/stories/invalid_story.Paths");
    assertNull(story);
  }
}