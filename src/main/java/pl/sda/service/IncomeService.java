package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import jakarta.persistence.Column;
import pl.sda.entity.Income;
import pl.sda.repository.IncomeRepository;

import java.time.LocalDate;

public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void addIncome(Long amount, LocalDate addDate, String comment) throws IllegalAccessException {
        if (amount != null && addDate != null && !StringUtils.isNullOrEmpty(comment)) {
            Income income = new Income(amount, addDate, comment);
            incomeRepository.insert(income);
        } else {
            throw new IllegalAccessException("Some input data are null or empty");
        }
    }

}
