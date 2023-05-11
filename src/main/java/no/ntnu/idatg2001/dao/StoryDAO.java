package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Story;

public class StoryDAO implements DAO<Story>{

  private final EntityManagerFactory emf;
  private EntityManager em;


  private static final StoryDAO instance = new StoryDAO();

  private StoryDAO() {
    this.emf = Persistence.createEntityManagerFactory("gamedb");
    this.em = this.emf.createEntityManager();
  }

  public static StoryDAO getInstance() {
    return instance;
  }

  @Override
  public void add(Story story) {
    if (StoryDAO.getInstance().getAll().contains(story)) {
      throw new IllegalArgumentException("Instance of game already exists in the database.");
    } else if (GameDAO.getInstance().getAllGameIds().contains(story.getId())) {
      throw new IllegalArgumentException(
          "Game with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(story);
      this.em.getTransaction().commit();
    }
  }

  @Override
  public void remove(Story story) {
    Story foundStory = em.find(Story.class, story.getId());
    em.getTransaction().begin();
    em.remove(foundStory);
    em.getTransaction().commit();
  }

  @Override
  public void update(Story story) {
    em.getTransaction().begin();
    em.merge(story);
    em.flush();
    em.getTransaction().commit();
  }

  @Override
  public Iterator<Story> iterator() {
    TypedQuery<Story> query = this.em.createQuery("SELECT a FROM Story a", Story.class);
    return query.getResultList().iterator();
  }

  @Override
  public Optional<Story> find(String id) {
    return Optional.ofNullable(em.find(Story.class, id));
  }

  @Override
  public List<Story> getAll() {
    return em.createQuery("SELECT a FROM Story a", Story.class).getResultList();
  }

  public List<Long> getAllGameIds() {
    return em.createQuery("SELECT a.id FROM Story a", Long.class).getResultList();
  }

  @Override
  public void printAllDetails() {
    for (Story story : getAll()) {
      System.out.println(
          "Story Details"
              + " :: "
              + story.getId()
              + " :: "
              + story.getTitle()
              + " :: "
              + story.getOpeningPassage()
              + " :: "
              + story.getPassages());
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

