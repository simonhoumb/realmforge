package no.ntnu.idatg2001.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;

public class StoryDAO implements DAO<Story>{

  private final EntityManagerFactory emf;
  private EntityManager em;
  private static final StoryDAO INSTANCE = new StoryDAO();

  private StoryDAO() {
   this.emf = Persistence.createEntityManagerFactory("gamedb");
   this.em = this.emf.createEntityManager();
  }

  public static StoryDAO getInstance() {
    return INSTANCE;
  }

  @Override
  public void add(Story story) {
    if (StoryDAO.getInstance().getAll().contains(story)) {
      throw new IllegalArgumentException("Instance of story already exists in the database.");
    } else if (StoryDAO.getInstance().getAllStoryIds().contains(story.getId())) {
      throw new IllegalArgumentException(
          "Story with the same id already exists in the database.");
    } else {
      this.em.getTransaction().begin();
      this.em.persist(story);
      this.em.getTransaction().commit();
    }
  }

  @Override
  public void remove(Story story) {
    em.getTransaction().begin();
    if (!em.contains(story)) {
      story = em.merge(story);
    }
    em.remove(story);
    em.getTransaction().commit();
  }

  @Override
  public void update(Story story) {
    em.getTransaction().begin();
    em.merge(story);
    em.getTransaction().commit();

  }

  @Override
  public Iterator<Story> iterator() {
    TypedQuery<Story> query = em.createQuery("SELECT s FROM Story s", Story.class);
    return query.getResultList().iterator();
  }

  @Override
  public Optional<Story> find(Long id) {
    return Optional.ofNullable(em.find(Story.class, id));
  }

  @Override
  public List<Story> getAll() {
    return em.createQuery("SELECT s FROM Story s", Story.class).getResultList();
  }

  public List<Long> getAllStoryIds() {
    return em.createQuery("SELECT s.id FROM Story s", Long.class).getResultList();
  }

  @Override
  public void printAllDetails() {
    for (Story story : this.getAll()) {
      System.out.println("Story Details:"
          + "\n\tStory ID: " + story.getId()+
          "\n\tStory Name: " + story.getTitle()+
          "\n\tStory Description: " + story.getOpeningPassage());
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
