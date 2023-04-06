package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.dto.SimpleCategoryDto;
import pl.sda.entity.Category;
import pl.sda.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

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

    public List<SimpleCategoryDto> findAllCategories() {
        Set<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new SimpleCategoryDto(category.getId(), category.getName()))
                .toList();
    }

    public Category findCategoryById(Long id) throws IllegalAccessException {
        if (id != null) {
            return categoryRepository.findById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }

    public void deleteCategory(Long id) throws IllegalAccessException {
        if (id != null) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }
}
