package pl.sda.repository;

import pl.sda.DbConnection;
import pl.sda.entity.Category;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class CategoryRepository {
    public void insert(Category category) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Category> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Category> categories = entityManager.createQuery("from Category", Category.class).getResultList();
        entityManager.close();
        return new HashSet<>(categories);
    }

    public Category findById(Long id) {
        EntityManager entityManager = DbConnection.getEntityManager();
        return entityManager.find(Category.class, id);
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
