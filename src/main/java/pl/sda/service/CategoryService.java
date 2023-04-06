package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.entity.Category;
import pl.sda.repository.CategoryRepository;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String name) throws IllegalAccessException {
        if (!StringUtils.isNullOrEmpty(name)) {
            Category category = new Category(name);
            categoryRepository.insert(category);
        } else {
            throw new IllegalAccessException("Some input data are null or empty");
        }
    }

    public void deleteCategory(Long id) throws IllegalAccessException {
        if (id != null) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }

    public Category findCategoryById(Long id) throws IllegalAccessException {
        if (id != null) {
            return categoryRepository.findById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }
}
