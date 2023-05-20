package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;


/**
 * Provides access to game save data stored in the database. Each GameSave object in the database acts as
 * a save file. This class implements the DAO interface for the GameSave class.
 * It is a singleton class with instance methods for performing create, read,
 * update and delete operations.
 *
 * @author Simon Husås Houmb
 * @version 1.0
 */
public class GameSaveDAO implements DAO<GameSave> {

  //TODO Make mock/test database for testing the database instead of using the business database.
  private final EntityManagerFactory emf;
  private EntityManager em;

  private static final GameSaveDAO instance = new GameSaveDAO();

  /** Constructs an GameSaveDAO instance, initializing the EntityManagerFactory and EntityManager. */
  private GameSaveDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the GameSaveDAO instance.
   *
   * @return the GameSaveDAO instance.
   */
  public static GameSaveDAO getInstance() {
    return instance;
  }

  /**
   * Adds a new GameSave to the database if it does not already exist.
   *
   * @param gameSave the game save to be added.
   * @throws IllegalArgumentException if the account already exists.
   * @throws IllegalArgumentException if an account with the same account number exists.
   * @throws IllegalArgumentException if an account with the same email exists.
   */
  @Override
  public void add(GameSave gameSave) {
    if (no.ntnu.idatg2001.dao.GameDAO.getInstance().getAll().contains(gameSave)) {
      throw new IllegalArgumentException("Instance of gameSave already exists in the database.");
    } else if (no.ntnu.idatg2001.dao.GameDAO.getInstance().getAllGameIds().contains(gameSave.getId())) {
      throw new IllegalArgumentException(
          "GameSave with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      if (!em.contains(gameSave)) {
        gameSave = em.merge(gameSave);
      }
      this.em.persist(gameSave);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes a GameSave from the database.
   *
   * @param gameSave the game save to be removed.
   */
  @Override
  public void remove(GameSave gameSave) {
    GameSave foundGameSave = em.find(GameSave.class, gameSave.getId());
    em.getTransaction().begin();
    if (!em.contains(foundGameSave)) {
      foundGameSave = em.merge(foundGameSave);
    }
    em.remove(foundGameSave);
    em.getTransaction().commit();
  }

  /**
   * Updates an existing game save in the database.
   *
   * @param gameSave the game save to be updated.
   */
  @Override
  public void update(GameSave gameSave) {
    em.getTransaction().begin();
    em.merge(gameSave);
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator over all game saves in the database.
   *
   * @return an iterator over game saves.
   */
  @Override
  public Iterator<GameSave> iterator() {
    TypedQuery<GameSave> query = this.em.createQuery("SELECT a FROM GameSave a", GameSave.class);
    return query.getResultList().iterator();
  }

  /**
   * Finds a game save by its id.
   *
   * @param id the game save id to search for.
   * @return an Optional containing the gameSave if found, otherwise empty.
   */
  @Override
  public Optional<GameSave> find(Long id) {
    return Optional.ofNullable(em.find(GameSave.class, id));
  }

  /**
   * Retrieves all accounts in the database.
   *
   * @return a list of all accounts.
   */
  @Override
  public List<GameSave> getAll() {
    return em.createQuery("SELECT a FROM GameSave a", GameSave.class).getResultList();
  }

  /**
   * Returns all game ids in the database.
   *
   * @return All game ids in the database as a List of Long.
   */
  public List<Long> getAllGameIds() {
    return em.createQuery("SELECT a.id FROM GameSave a", Long.class).getResultList();
  }


  /** Prints details for all game saves in the database. */
  @Override
  public void printAllDetails() {
    for (GameSave gameSave : getAll()) {
      System.out.println(
          "Game Details"
              + " :: "
              + gameSave.getId()
              + " :: "
              + gameSave.getSaveName()
              + " :: "
              + gameSave.getTimeOfSave()
              + " :: "
              + gameSave.getPlayerName());
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
