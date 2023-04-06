package pl.sda.repository;

import jakarta.persistence.EntityManager;
import pl.sda.DbConnection;
import pl.sda.entity.Outcome;

public class OutcomeRepository {
    public void insert(Outcome outcome) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(outcome);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
