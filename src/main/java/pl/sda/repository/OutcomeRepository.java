package pl.sda.repository;

import jakarta.persistence.EntityManager;
import pl.sda.DbConnection;
import pl.sda.entity.Outcome;

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

    public void deleteById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Optional<Outcome> outcome = Optional.ofNullable(entityManager.find(Outcome.class, id));
        outcome.ifPresent(entityManager::remove);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
