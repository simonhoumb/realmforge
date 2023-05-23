package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

/**
 * Provides access to passage data stored in the database. This class implements the DAO interface
 * for the Passage class. It is a singleton class with instance methods for performing create, read,
 * update and delete operations.
 *
 * @version 1.0
 */
public class PassageDAO implements DAO<Passage> {

  private final EntityManagerFactory emf;
  private final EntityManager em;

  private static final PassageDAO instance = new PassageDAO();

  /**
   * Constructs an PassageDAO instance, initializing the EntityManagerFactory and EntityManager.
   */
  private PassageDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the PassageDAO instance.
   *
   * @return the PassageDAO instance.
   */
  public static PassageDAO getInstance() {
    return instance;
  }

  /**
   * Adds a new passage to the database if it does not already exist.
   *
   * @param passage the passage to be added.
   * @throws IllegalArgumentException if the passage already exists.
   * @throws IllegalArgumentException if a passage with the same passage number exists.
   * @throws IllegalArgumentException if a passage with the same email exists.
   */
  @Override
  public void add(Passage passage) {
    if (PassageDAO.getInstance().getAll().contains(passage)) {
      throw new IllegalArgumentException("Instance of passage already exists in the database.");
    } else if (PassageDAO.getInstance().getAllPassagesIds().contains(passage.getId())) {
      throw new IllegalArgumentException(
          "Passage with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      if (!em.contains(passage)) {
        passage = em.merge(passage);
      }
      this.em.persist(passage);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes a passage from the database.
   *
   * @param passage the passage to be removed.
   */
  @Override
  public void remove(Passage passage) {
    Passage foundPassage = em.find(Passage.class, passage.getId());
    em.getTransaction().begin();
    if (!em.contains(foundPassage)) {
      foundPassage = em.merge(foundPassage);
    }
    em.remove(foundPassage);
    em.getTransaction().commit();
  }

  /**
   * Updates all fields of a passage in the database.
   *
   * @param passage the passage to be updated.
   */
  @Override
  public void update(Passage passage) {
    em.getTransaction().begin();
    em.merge(passage);
    em.getTransaction().commit();

  }

  /**
   * Returns an iterator of all passages in the database.
   *
   * @return an iterator of all passages in the database.
   */
  @Override
  public Iterator<Passage> iterator() {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p", Passage.class);
    return query.getResultList().iterator();
  }

  /**
   * Returns an optional of a passage with the given id.
   *
   * @param id the id of the passage to be returned.
   * @return an optional of a passage with the given id.
   */
  @Override
  public Optional<Passage> find(Long id) {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p WHERE p.id = :id",
        Passage.class);
    query.setParameter("id", id);
    return Optional.ofNullable(query.getSingleResult());
  }

  /**
   * Gets all passages in the database.
   *
   * @return a list of all passages in the database.
   */
  @Override
  public List<Passage> getAll() {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p", Passage.class);
    return query.getResultList();
  }

  /**
   * Gets all passages ids in the database.
   *
   * @return a list of all passages ids in the database.
   */
  public List<Long> getAllPassagesIds() {
    TypedQuery<Long> query = em.createQuery("SELECT p.id FROM Passage p", Long.class);
    return query.getResultList();
  }

  /**
   * Prints all passages in the database.
   */
  @Override
  public void printAllDetails() {
    for (Passage passage : this.getAll()) {
      System.out.println(passage.toString());
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
