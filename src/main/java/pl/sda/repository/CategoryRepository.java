package pl.sda.repository;

import pl.sda.DbConnection;
import pl.sda.entity.Category;
import jakarta.persistence.EntityManager;


public class CategoryRepository {
    public void insert(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
