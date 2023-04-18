package pl.sda.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import pl.sda.DbConnection;
import pl.sda.entity.Category;
import pl.sda.entity.Outcome;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OutcomeRepository {
    public void insert(Outcome outcome) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(outcome);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Outcome> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Outcome> outcomes = entityManager.createQuery("from Outcome", Outcome.class).getResultList();
        entityManager.close();
        return new HashSet<>(outcomes);
    }

    public Set<Outcome> findByDate(LocalDate fromDate, LocalDate toDate) {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Outcome> outcomes = entityManager.createQuery("from Outcome where addDate between :fromDate and :toDate", Outcome.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();
        entityManager.close();
        return new HashSet<>(outcomes);
    }

    public Set<Outcome> findByCategory(Long categoryId) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Category category = entityManager.find(Category.class, categoryId);
        List<Outcome> outcomes = entityManager.createQuery("from Outcome where category = :category", Outcome.class)
                .setParameter("category", category)
                .getResultList();
        entityManager.close();
        return new HashSet<>(outcomes);
    }

    public void deleteById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Optional<Outcome> outcome = Optional.ofNullable(entityManager.find(Outcome.class, id));
        outcome.ifPresent(entityManager::remove);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Long totalSum() {
        EntityManager entityManager = DbConnection.getEntityManager();
        Long result = entityManager.createQuery("select sum(amount) from Outcome", Long.class).getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Object[]> groupByCategory() {
        EntityManager entityManager = DbConnection.getEntityManager();
        TypedQuery<Object[]> query = entityManager.createQuery(
                "select sum(amount), count(id), category.id from Outcome group by category ", Object[].class);
        List<Object[]> result = query.getResultList();
        entityManager.close();
        return result;
    }
}
