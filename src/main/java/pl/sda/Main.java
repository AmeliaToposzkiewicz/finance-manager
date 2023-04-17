package pl.sda;

import pl.sda.dto.SimpleCategoryDto;
import pl.sda.dto.SimpleIncomeDto;
import pl.sda.dto.SimpleOutcomeDto;
import pl.sda.repository.CategoryRepository;
import pl.sda.repository.IncomeRepository;
import pl.sda.repository.OutcomeRepository;
import pl.sda.service.CategoryService;
import pl.sda.service.IncomeService;
import pl.sda.service.OutcomeService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME_ENV = "DB_NAME";

    private static final String DB_USER_ENV = "DB_USER";

    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, IOException {
        try (final Connection connection = DriverManager.getConnection(JDBC_URL + System.getenv(DB_NAME_ENV),
                System.getenv(DB_USER_ENV), System.getenv(DB_PASSWORD_ENV))) {

            DbInitializer dbInitializer = new DbInitializer(connection);
            dbInitializer.initDb();

            final CategoryRepository categoryRepository = new CategoryRepository();
            final CategoryService categoryService = new CategoryService(categoryRepository);
            final IncomeRepository incomeRepository = new IncomeRepository();
            final IncomeService incomeService = new IncomeService(incomeRepository);
            final OutcomeRepository outcomeRepository = new OutcomeRepository();
            final OutcomeService outcomeService = new OutcomeService(outcomeRepository, categoryService);

            while (true) {
                System.out.println("Type operation");
                System.out.println("0 - Exit program");
                System.out.println("1 - Add new category");
                System.out.println("2 - Delete category");
                System.out.println("3 - Find all categories");
                System.out.println("4 - Add new income");
                System.out.println("5 - Find all incomes");
                System.out.println("6 - Delete income");
                System.out.println("7 - Add new outcome");
                System.out.println("8 - Find all outcomes");
                System.out.println("9 - Delete outcome");
                System.out.println("10 - Find all outcomes and incomes");
                System.out.println("11 - Find outcomes by date");
                int selectedOperation = SCANNER.nextInt();
                SCANNER.nextLine();
                switch (selectedOperation) {
                    case 0 -> {
                        System.exit(0);
                    }
                    case 1 -> {
                        System.out.println("Type name");
                        String categoryName = SCANNER.nextLine();
                        try {
                            categoryService.addCategory(categoryName);
                        } catch (IllegalAccessException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        System.out.println("Provide id of category to delete");
                        Long categoryId = SCANNER.nextLong();
                        try {
                            categoryService.deleteCategory(categoryId);
                        } catch (IllegalAccessException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 3 -> {
                        System.out.println("ALl categories: ");
                        List<SimpleCategoryDto> findAllCategoriesList = categoryService.findAllCategories();
                        findAllCategoriesList.forEach(simpleCategoryDto -> System.out.println(simpleCategoryDto.toString()));
                    }
                    case 4 -> {
                        System.out.println("Type amount");
                        Long incomeAmount = SCANNER.nextLong();
                        System.out.println("Type day");
                        int incomeDay = SCANNER.nextInt();
                        System.out.println("Type month");
                        int incomeMonth = SCANNER.nextInt();
                        System.out.println("Type year");
                        int incomeYear = SCANNER.nextInt();
                        SCANNER.nextLine();
                        System.out.println("Type comment");
                        String incomeComment = SCANNER.nextLine();

                        LocalDate incomeDate = LocalDate.of(incomeYear, incomeMonth, incomeDay);

                        try {
                            incomeService.addIncome(incomeAmount, incomeDate, incomeComment);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 5 -> {
                        System.out.println("ALl incomes: ");
                        List<SimpleIncomeDto> findAllIncomesList = incomeService.findAllIncomes();
                        findAllIncomesList.forEach(simpleIncomeDto -> System.out.println(simpleIncomeDto.toString()));
                    }
                    case 6 -> {
                        System.out.println("Provide id of income to delete");
                        Long incomeId = SCANNER.nextLong();
                        try {
                            incomeService.deleteIncome(incomeId);
                        } catch (IllegalAccessException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 7 -> {
                        System.out.println("Type amount");
                        Long outcomeAmount = SCANNER.nextLong();
                        System.out.println("Type day");
                        int outcomeDay = SCANNER.nextInt();
                        System.out.println("Type month");
                        int outcomeMonth = SCANNER.nextInt();
                        System.out.println("Type year");
                        int outcomeYear = SCANNER.nextInt();
                        SCANNER.nextLine();
                        System.out.println("Type comment");
                        String outcomeComment = SCANNER.nextLine();
                        System.out.println("Provide id of category");
                        Long categoryId = SCANNER.nextLong();

                        LocalDate outcomeDate = LocalDate.of(outcomeYear, outcomeMonth, outcomeDay);

                        try {
                            outcomeService.addOutcome(outcomeAmount, outcomeDate, outcomeComment, categoryId);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 8 -> {
                        System.out.println("ALl outcomes: ");
                        List<SimpleOutcomeDto> findAllOutcomesList = outcomeService.findAllOutcomes();
                        findAllOutcomesList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                    }
                    case 9 -> {
                        System.out.println("Provide id of outcome to delete");
                        Long outcomeId = SCANNER.nextLong();
                        try {
                            outcomeService.deleteOutcome(outcomeId);
                        } catch (IllegalAccessException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    case 10 -> {
                        List<SimpleOutcomeDto> findAllOutcomesList = outcomeService.findAllOutcomes();
                        List<SimpleIncomeDto> findAllIncomesList = incomeService.findAllIncomes();
                        System.out.println("ALl outcomes: ");
                        findAllOutcomesList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                        System.out.println("ALl incomes: ");
                        findAllIncomesList.forEach(simpleIncomeDto -> System.out.println(simpleIncomeDto.toString()));
                    }
                    case 11 -> {
                        System.out.println("Provide first date");
                        System.out.println("Type day");
                        int fromDay = SCANNER.nextInt();
                        System.out.println("Type month");
                        int fromMonth = SCANNER.nextInt();
                        System.out.println("Type year");
                        int fromYear = SCANNER.nextInt();
                        LocalDate fromDate = LocalDate.of(fromYear, fromMonth, fromDay);

                        System.out.println("Provide second date");
                        System.out.println("Type day");
                        int toDay = SCANNER.nextInt();
                        System.out.println("Type month");
                        int toMonth = SCANNER.nextInt();
                        System.out.println("Type year");
                        int toYear = SCANNER.nextInt();
                        LocalDate toDate = LocalDate.of(toYear, toMonth, toDay);

                        System.out.println("Result: ");
                        List<SimpleOutcomeDto> findOutcomesByDateList = outcomeService.findByDate(fromDate, toDate);
                        findOutcomesByDateList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));

                    }
                }
            }
        }
    }
}