package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Story;

/**
 * Provides access to story data stored in the database. This class implements the DAO interface
 * for the Story class. It is a singleton class with instance methods for performing create, read,
 * update and delete operations.
 *
 * @version 1.0
 */
public class StoryDAO implements DAO<Story> {

  private final EntityManagerFactory emf;
  private final EntityManager em;
  private static final StoryDAO INSTANCE = new StoryDAO();

  /**
   * Constructs an StoryDAO instance, initializing the EntityManagerFactory and EntityManager.
   */
  private StoryDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the StoryDAO instance.
   *
   * @return the StoryDAO instance.
   */
  public static StoryDAO getInstance() {
    return INSTANCE;
  }

  /**
   * Adds a new story to the database if it does not already exist.
   *
   * @param story the story to be added.
   * @throws IllegalArgumentException if the story already exists.
   * @throws IllegalArgumentException if a story with the same story number exists.
   * @throws IllegalArgumentException if a story with the same email exists.
   */
  @Override
  public void add(Story story) {
    if (StoryDAO.getInstance().getAll().contains(story)) {
      throw new IllegalArgumentException("Instance of story already exists in the database.");
    } else if (StoryDAO.getInstance().getAllStoryIds().contains(story.getId())) {
      throw new IllegalArgumentException(
          "Story with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      if (!em.contains(story)) {
        story = em.merge(story);
      }
      this.em.persist(story);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes a story from the database.
   *
   * @param story the story to be removed.
   * @throws IllegalArgumentException if the story does not exist.
   */
  @Override
  public void remove(Story story) {
    Story foundStory = em.find(Story.class, story.getId());
    em.getTransaction().begin();
    if (!em.contains(foundStory)) {
      foundStory = em.merge(foundStory);
    }
    em.remove(foundStory);
    em.getTransaction().commit();
  }

  /**
   * Updates a story in the database.
   *
   * @param story the story to be updated.
   * @throws IllegalArgumentException if the story does not exist.
   */
  @Override
  public void update(Story story) {
    em.getTransaction().begin();
    em.merge(story);
    em.getTransaction().commit();

  }

  /**
   * Returns an iterator of all story instances in the database.
   *
   * @return An iterator of all stories in the database.
   */
  @Override
  public Iterator<Story> iterator() {
    TypedQuery<Story> query = em.createQuery("SELECT s FROM Story s", Story.class);
    return query.getResultList().iterator();
  }

  /**
   * Returns an optional of the story with the given id.
   *
   * @param id the id of the story to be returned.
   * @return an optional of the story with the given id.
   */
  @Override
  public Optional<Story> find(Long id) {
    return Optional.ofNullable(em.find(Story.class, id));
  }

  /**
   * Returns a list of all story instances in the database.
   *
   * @return a list of all story instances in the database.
   */
  @Override
  public List<Story> getAll() {
    return em.createQuery("SELECT s FROM Story s", Story.class).getResultList();
  }

  /**
   * Returns a list of all story ids in the database.
   *
   * @return a list of all story ids in the database.
   */
  public List<Long> getAllStoryIds() {
    return em.createQuery("SELECT s.id FROM Story s", Long.class).getResultList();
  }

  /**
   * Prints all details of all story instances in the database.
   */
  @Override
  public void printAllDetails() {
    for (Story story : this.getAll()) {
      System.out.println("Story Details:"
          + "\n\tStory ID: " + story.getId()
          + "\n\tStory Name: " + story.getTitle()
          + "\n\tStory Description: " + story.getOpeningPassage());
    }

  }

  /**
   * Closes the EntityManager and EntityManagerFactory.
   */
  @Override
  public void close() {
    if (em.isOpen()) {
      this.em.close();
    }
    if (emf.isOpen()) {
      this.emf.close();
    }
  }
}
