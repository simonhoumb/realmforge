package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The UnitDAO class implements the DAO interface for the Unit class.
 */
public class UnitDAO implements DAO<Unit> {
  private final EntityManagerFactory emf;
  private final EntityManager em;

  private static final UnitDAO instance = new UnitDAO();

  /** Constructs an UnitDAO instance, initializing the EntityManagerFactory and EntityManager. */
  private UnitDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  /**
   * Returns the UnitDAO instance.
   *
   * @return the UnitDAO instance.
   */
  public static UnitDAO getInstance() {
    return instance;
  }

  /**
   * Adds a new Unit to the database if it does not already exist.
   *
   * @param unit the account to be added.
   * @throws IllegalArgumentException if the Unit already exists.
   * @throws IllegalArgumentException if an account with the same account number exists.
   * @throws IllegalArgumentException if an account with the same email exists.
   */
  @Override
  public void add(Unit unit) {
    if (UnitDAO.getInstance().getAll().contains(unit)) {
      throw new IllegalArgumentException("Instance of game already exists in the database.");
    } else if (UnitDAO.getInstance().getAllUnitIds().contains(unit.getId())) {
      throw new IllegalArgumentException(
          "Game with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      if (!em.contains(unit)) {
        unit = em.merge(unit);
      }
      this.em.persist(unit);
      this.em.getTransaction().commit();
    }
  }

  /**
   * Removes a Game from the database.
   *
   * @param unit the account to be removed.
   */
  @Override
  public void remove(Unit unit) {
    Unit foundUnit = em.find(Unit.class, unit.getId());
    em.getTransaction().begin();
    if (!em.contains(foundUnit)) {
      foundUnit = em.merge(foundUnit);
    }
    em.remove(foundUnit);
    em.getTransaction().commit();
  }

  /**
   * Updates an existing game in the database.
   *
   * @param unit the game to be updated.
   */
  @Override
  public void update(Unit unit) {
    em.getTransaction().begin();
    em.merge(unit);
    em.getTransaction().commit();
  }

  /**
   * Returns an iterator over all accounts in the database.
   *
   * @return an iterator over accounts.
   */
  @Override
  public Iterator<Unit> iterator() {
    TypedQuery<Unit> query = this.em.createQuery("SELECT a FROM Unit a", Unit.class);
    return query.getResultList().iterator();
  }

  @Override
  public Optional<Unit> find(Long id) {
    Unit unit = em.find(Unit.class, id);
    return Optional.ofNullable(unit);
  }


  /**
   * Finds an account by its id.
   *
   * @param unitName the account id to search for.
   * @return an Optional containing the account if found, otherwise empty.
   */
  public Optional<Unit> find(String unitName) {
    TypedQuery<Unit> query = em.createQuery(
        "SELECT u FROM Unit u WHERE u.unitName = :unitName", Unit.class);
    query.setParameter("unitName", unitName);
    try {
      Unit unit = query.getSingleResult();
      return Optional.of(unit);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  /**
   * Retrieves all accounts in the database.
   *
   * @return a list of all accounts.
   */
  @Override
  public List<Unit> getAll() {
    return em.createQuery("SELECT a FROM Unit a", Unit.class).getResultList();
  }



  /**
   * Returns all game ids in the database.
   *
   * @return All game ids in the database as a List of Long.
   */
  public List<Long> getAllUnitIds() {
    return em.createQuery("SELECT a.id FROM Unit a", Long.class).getResultList();
  }


  /** Prints details for all games in the database. */
  @Override
  public void printAllDetails() {
    for (Unit unit : getAll()) {
      System.out.println(
          "Game Details"
              + " :: "
              + unit.getId()
              + " :: "
              + " :: "
              + unit.getUnitName()
              + " :: "
              + unit.getUnit());
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
