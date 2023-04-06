package pl.sda.repository;

import pl.sda.DbConnection;
import pl.sda.entity.Category;
import jakarta.persistence.EntityManager;

import java.util.Optional;


public class CategoryRepository {
    public void insert(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        Optional<Category> category = Optional.ofNullable(entityManager.find(Category.class, id));
        category.ifPresent(entityManager::remove);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
