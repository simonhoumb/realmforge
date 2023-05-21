package no.ntnu.idatg2001.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;

/**
 * Provides access to game data stored in the database. Each Game object in the database acts as
 * a save file. This class implements the DAO interface for the Game class.
 * It is a singleton class with instance methods for performing create, read,
 * update and delete operations.
 *
 * @author Simon Husås Houmb
 * @version 1.0
 */
public class GameDAO implements DAO<Game> {
  //TODO Make mock/test database for testing the database instead of using the business database.
  private final EntityManagerFactory emf;
  private EntityManager em;

  private static final GameDAO instance = new GameDAO();

  /** Constructs an GameDAO instance, initializing the EntityManagerFactory and EntityManager. */
  private GameDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the GameDAO instance.
   *
   * @return the GameDAO instance.
   */
  public static GameDAO getInstance() {
    return instance;
  }

  /**
   * Adds a new game to the database if it does not already exist.
   *
   * @param game the account to be added.
   * @throws IllegalArgumentException if the account already exists.
   * @throws IllegalArgumentException if an account with the same account number exists.
   * @throws IllegalArgumentException if an account with the same email exists.
   */
  @Override
  public void add(Game game) {
    if (GameDAO.getInstance().getAll().contains(game)) {
      throw new IllegalArgumentException("Instance of game already exists in the database.");
    } else if (GameDAO.getInstance().getAllGameIds().contains(game.getId())) {
      throw new IllegalArgumentException(
          "Game with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      if (!em.contains(game)) {
        game = em.merge(game);
      }
      this.em.persist(game);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes a Game from the database.
   *
   * @param game the account to be removed.
   */
  @Override
  public void remove(Game game) {
    Game foundGame = em.find(Game.class, game.getId());
    em.getTransaction().begin();
    if (!em.contains(foundGame)) {
      foundGame = em.merge(foundGame);
    }
    em.remove(foundGame);
    em.getTransaction().commit();
  }

  /**
   * Updates an existing game in the database.
   *
   * @param game the game to be updated.
   */
  @Override
  public void update(Game game) {
    em.getTransaction().begin();
    em.merge(game);
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator over all accounts in the database.
   *
   * @return an iterator over accounts.
   */
  @Override
  public Iterator<Game> iterator() {
    TypedQuery<Game> query = this.em.createQuery("SELECT a FROM Game a", Game.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds an account by its id.
   *
   * @param id the account id to search for.
   * @return an Optional containing the account if found, otherwise empty.
   */
  @Override
  public Optional<Game> find(Long id) {
    return Optional.ofNullable(em.find(Game.class, id));
  }

  /**
   * Retrieves all accounts in the database.
   *
   * @return a list of all accounts.
   */
  @Override
  public List<Game> getAll() {
    return em.createQuery("SELECT a FROM Game a", Game.class).getResultList();
  }

  public List<Story> getAllStories() {
    return em.createQuery("SELECT a.story FROM Game a", Story.class).getResultList();
  }

  public Story getGameStory() {
    return em.createQuery("SELECT a.story FROM Game a", Story.class).getSingleResult();
  }

  public List<Passage> getAllPassagesInStory(Game game) {
    return em.createQuery("SELECT a.story.passages FROM Game a", Passage.class).getResultList();
  }

  /**
   * Returns all game ids in the database.
   *
   * @return All game ids in the database as a List of Long.
   */
  public List<Long> getAllGameIds() {
    return em.createQuery("SELECT a.id FROM Game a", Long.class).getResultList();
  }


  /** Prints details for all games in the database. */
  @Override
  public void printAllDetails() {
    for (Game game : getAll()) {
      System.out.println(
          "Game Details"
              + " :: "
              + game.getId()
              + " :: "
              + game.getUnit().getUnitName()
              + " :: "
              + game.getStory().getTitle()
              + " :: "
              + game.getGoals().size());
    }
  }

  /** Closes the EntityManager and EntityManagerFactory. */
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
