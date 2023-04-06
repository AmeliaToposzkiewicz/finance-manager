package pl.sda.service;

import com.mysql.cj.util.StringUtils;
import pl.sda.dto.SimpleIncomeDto;
import pl.sda.entity.Income;
import pl.sda.repository.IncomeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    public List<SimpleIncomeDto> findAllIncomes() {
        Set<Income> incomes = incomeRepository.findAll();
        return incomes.stream()
                .map(income -> new SimpleIncomeDto(income.getId(),
                        income.getAmount() + ", " + income.getAddDate() + ", " + income.getComment()))
                .toList();
    }

    public void deleteIncome(Long id) throws IllegalAccessException {
        if (id != null) {
            incomeRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("Input data is null");
        }
    }

}
