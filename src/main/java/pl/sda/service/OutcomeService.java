package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.dto.SimpleOutcomeDto;
import pl.sda.entity.Outcome;
import pl.sda.repository.OutcomeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<SimpleOutcomeDto> findAllOutcomes() {
        Set<Outcome> outcomes = outcomeRepository.findAll();
        return outcomes.stream()
                .map(outcome -> new SimpleOutcomeDto(outcome.getId(),
                        outcome.getAmount() + ", " + outcome.getAddDate() + ", " + outcome.getComment(),
                        outcome.getCategory().getId())).toList();
    }

    public List<SimpleOutcomeDto> findByDate(LocalDate fromDate, LocalDate toDate) {
        Set<Outcome> outcomes = outcomeRepository.findByDate(fromDate, toDate);
        return outcomes.stream()
                .map(outcome -> new SimpleOutcomeDto(outcome.getId(),
                        outcome.getAmount() + ", " + outcome.getAddDate() + ", " + outcome.getComment(),
                        outcome.getCategory().getId())).toList();
    }

    public List<SimpleOutcomeDto> findByCategory(Long categoryId) {
        Set<Outcome> outcomes = outcomeRepository.findByCategory(categoryId);
        return outcomes.stream()
                .map(outcome -> new SimpleOutcomeDto(outcome.getId(),
                        outcome.getAmount() + ", " + outcome.getAddDate() + ", " + outcome.getComment(),
                        outcome.getCategory().getId())).toList();
    }

    public void deleteOutcome(Long id) throws IllegalAccessException {
        if (id != null) {
            outcomeRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }

    public Long totalSumOfOutcomes() {
        return outcomeRepository.totalSum();
    }

    public List<String> groupOutcomesByCategories() {
        return outcomeRepository.groupByCategory().stream()
                .map(objects -> "sum: " + objects[0] + " number of outcomes: " + objects[1] + " category id:" + objects[2]).toList();
    }
};