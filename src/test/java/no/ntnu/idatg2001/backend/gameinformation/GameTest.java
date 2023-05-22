package no.ntnu.idatg2001.backend.gameinformation;

import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Ranger;
import no.ntnu.idatg2001.backend.goals.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {
  private Game game;
  private Unit unit;
  private Story story;
  private Passage openingPassage;
  private List<Goal> goals;

  @BeforeEach
  void setUp() {
    unit = new Ranger("test");
    openingPassage = new Passage("Opening Passage", new StringBuilder("Opening Passage"));
    story = new Story("test", openingPassage);
    goals = new ArrayList<>();
    game = new Game(unit, story, goals);
  }

  @Test
  void testGetId() {
    game.setId(1L);
    assertEquals(1L, game.getId());
  }

  @Nested
  class testGetAndSetUnit {
    @Test
    void testGetUnit () {
      assertNotNull(game.getUnit());
      assertEquals(unit, game.getUnit());
    }

    @Test
    void testSetUnit () {
      Unit newUnit = new Ranger("test");
      game.setUnit(newUnit);
      assertEquals(newUnit, game.getUnit());
    }
  }

  @Nested
  class testGetAndSetStory {
    @Test
    void testGetStory () {
      assertNotNull(game.getStory());
      assertEquals(story, game.getStory());
    }

    @Test
    void testSetStory () {
      Story newStory = new Story();
      game.setStory(newStory);
      assertEquals(newStory, game.getStory());
    }
  }

  @Nested
  class testGetAndSetGoals {
    @Test
      void testGetGoals () {
      assertNotNull(game.getGoals());
      assertEquals(goals, game.getGoals());
    }

    @Test
      void testSetGoals () {
      List<Goal> newGoals = new ArrayList<>();
      game.setGoals(newGoals);
      assertEquals(newGoals, game.getGoals());
    }
  }

  @Test
  void testBegin() {
    story.setOpeningPassage(openingPassage);
    assertEquals(openingPassage, game.begin());
  }

  @Test
  void testGo() {
    Passage passage = new Passage("Passage", new StringBuilder("Passage"));
    Link link = new Link("Reference", "Passage");
    story.addPassage(passage);

    assertEquals(passage, game.go(link));
  }
}
