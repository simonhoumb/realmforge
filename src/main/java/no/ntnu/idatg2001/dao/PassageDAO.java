package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Game;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class PassageDAO implements DAO<Passage> {

  private final EntityManagerFactory emf;
  private EntityManager em;

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

  @Override
  public void update(Passage passage) {
    em.getTransaction().begin();
    em.merge(passage);
    em.getTransaction().commit();

  }

  @Override
  public Iterator<Passage> iterator() {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p", Passage.class);
    return query.getResultList().iterator();
  }

  @Override
  public Optional<Passage> find(Long id) {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p WHERE p.id = :id",
        Passage.class);
    query.setParameter("id", id);
    return Optional.ofNullable(query.getSingleResult());
  }

  @Override
  public List<Passage> getAll() {
    TypedQuery<Passage> query = em.createQuery("SELECT p FROM Passage p", Passage.class);
    return query.getResultList();
  }

  public List<Long> getAllPassagesIds() {
    TypedQuery<Long> query = em.createQuery("SELECT p.id FROM Passage p", Long.class);
    return query.getResultList();
  }

  @Override
  public void printAllDetails() {
    for (Passage passage : this.getAll()) {
      System.out.println(passage.toString());
    }
  }

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
