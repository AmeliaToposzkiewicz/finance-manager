package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.entity.Income;
import pl.sda.entity.Outcome;
import pl.sda.repository.OutcomeRepository;

import java.time.LocalDate;

public class OutcomeService {
    private final OutcomeRepository outcomeRepository;
    private final CategoryService categoryService;

    public OutcomeService(OutcomeRepository outcomeRepository, CategoryService categoryService) {
        this.outcomeRepository = outcomeRepository;
        this.categoryService = categoryService;
    }

    public void addOutcome(Long amount, LocalDate addDate, String comment, Long categoryId) throws IllegalAccessException {
        if (amount != null && addDate != null && !StringUtils.isNullOrEmpty(comment) && categoryId != null) {
            Outcome outcome = new Outcome(amount, addDate, comment, categoryService.findCategoryById(categoryId));
            outcomeRepository.insert(outcome);
        } else {
            throw new IllegalAccessException("Some input data are null or empty");
        }
    }
}
