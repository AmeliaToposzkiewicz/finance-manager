package pl.sda.repository;

import jakarta.persistence.EntityManager;
import pl.sda.DbConnection;
import pl.sda.entity.Category;
import pl.sda.entity.Income;

public class IncomeRepository {
    public void insert(Income income) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(income);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
