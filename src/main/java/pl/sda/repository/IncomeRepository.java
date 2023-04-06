package pl.sda.repository;

import jakarta.persistence.EntityManager;
import pl.sda.DbConnection;
import pl.sda.entity.Income;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncomeRepository {
    public void insert(Income income) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(income);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Income> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Income> incomes = entityManager.createQuery("from Income", Income.class).getResultList();
        entityManager.close();
        return new HashSet<>(incomes);
    }
}
