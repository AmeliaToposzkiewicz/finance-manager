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

    public static void main(String[] args) throws SQLException, IOException, IllegalAccessException {
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
                showMenu();
                int selectedOperation = SCANNER.nextInt();
                SCANNER.nextLine();
                switch (selectedOperation) {
                    case 0 -> System.exit(0);
                    case 1 -> createCategory(categoryService);
                    case 2 -> {
                        List<SimpleCategoryDto> findAllCategoriesList = categoryService.findAllCategories();
                        System.out.println("ALl categories: ");
                        findAllCategoriesList.forEach(simpleCategoryDto -> System.out.println(simpleCategoryDto.toString()));
                    }
                    case 3 -> removeCategory(categoryService);
                    case 4 -> createIncome(incomeService);
                    case 5 -> {
                        List<SimpleIncomeDto> findAllIncomesList = incomeService.findAllIncomes();
                        System.out.println("ALl incomes: ");
                        findAllIncomesList.forEach(simpleIncomeDto -> System.out.println(simpleIncomeDto.toString()));
                    }
                    case 6 -> removeIncome(incomeService);
                    case 7 -> createOutcome(outcomeService);
                    case 8 -> {
                        List<SimpleOutcomeDto> findAllOutcomesList = outcomeService.findAllOutcomes();
                        System.out.println("ALl outcomes: ");
                        findAllOutcomesList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                    }
                    case 9 -> removeOutcome(outcomeService);
                    case 10 -> {
                        System.out.println("Provide first date");
                        LocalDate fromDate = createDate();

                        System.out.println("Provide second date");
                        LocalDate toDate = createDate();

                        List<SimpleOutcomeDto> findOutcomesByDateList = outcomeService.findByDate(fromDate, toDate);
                        System.out.println("Outcomes between " + fromDate + " and " + toDate);
                        findOutcomesByDateList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                    }
                    case 11 -> {
                        System.out.println("Provide id of category");
                        Long categoryId = SCANNER.nextLong();
                        List<SimpleOutcomeDto> findOutcomesByCategoryList = outcomeService.findByCategory(categoryId);
                        System.out.println("Outcomes in category " + categoryService.findCategoryById(categoryId).getName() + ": ");
                        findOutcomesByCategoryList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                    }
                    case 12 -> outcomeService.groupOutcomesByCategories().forEach(System.out::println);
                    case 13 -> {
                        List<SimpleOutcomeDto> findAllOutcomesList = outcomeService.findAllOutcomes();
                        List<SimpleIncomeDto> findAllIncomesList = incomeService.findAllIncomes();
                        System.out.println("ALl outcomes: ");
                        findAllOutcomesList.forEach(simpleOutcomeDto -> System.out.println(simpleOutcomeDto.toString()));
                        System.out.println("ALl incomes: ");
                        findAllIncomesList.forEach(simpleIncomeDto -> System.out.println(simpleIncomeDto.toString()));
                    }
                    case 14 -> {
                        Long totalIncomes = incomeService.totalSumOfIncomes();
                        Long totalOutcomes = outcomeService.totalSumOfOutcomes();
                        long balance = totalIncomes - totalOutcomes;
                        System.out.println("Incomes: " + totalIncomes + " Outcomes: " + totalOutcomes + "\nBalance: " + balance);

                    }
                    default -> System.err.println(selectedOperation + " is invalid option. Please try again!");
                }
            }
        }
    }

    public static void showMenu() {
        System.out.println("Type operation");
        System.out.println("0  - Exit program");
        System.out.println("============= CATEGORY =============");
        System.out.println("1  - Add new category");
        System.out.println("2  - Find all categories");
        System.out.println("3  - Delete category");
        System.out.println("============= INCOME ===============");
        System.out.println("4  - Add new income");
        System.out.println("5  - Find all incomes");
        System.out.println("6  - Delete income");
        System.out.println("============= OUTCOME ==============");
        System.out.println("7  - Add new outcome");
        System.out.println("8  - Find all outcomes");
        System.out.println("9  - Delete outcome");
        System.out.println("10 - Find outcomes by date");
        System.out.println("11 - Find outcomes by category");
        System.out.println("12 - Group outcomes by categories");
        System.out.println("============= OTHER =================");
        System.out.println("13 - Find all outcomes and incomes");
        System.out.println("14 - Check balance");
    }

    public static LocalDate createDate() {
        System.out.println("Type day");
        int day = SCANNER.nextInt();
        System.out.println("Type month");
        int month = SCANNER.nextInt();
        System.out.println("Type year");
        int year = SCANNER.nextInt();
        return LocalDate.of(year, month, day);
    }

    public static void createCategory(CategoryService categoryService) {
        System.out.println("Type name");
        String categoryName = SCANNER.nextLine();
        try {
            categoryService.addCategory(categoryName);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void createIncome(IncomeService incomeService) {
        System.out.println("Type amount");
        Long incomeAmount = SCANNER.nextLong();
        LocalDate incomeDate = createDate();
        SCANNER.nextLine();
        System.out.println("Type comment");
        String incomeComment = SCANNER.nextLine();
        try {
            incomeService.addIncome(incomeAmount, incomeDate, incomeComment);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createOutcome(OutcomeService outcomeService) {
        System.out.println("Type amount");
        Long outcomeAmount = SCANNER.nextLong();
        LocalDate outcomeDate = createDate();
        SCANNER.nextLine();
        System.out.println("Type comment");
        String outcomeComment = SCANNER.nextLine();
        System.out.println("Provide id of category");
        Long categoryId = SCANNER.nextLong();
        try {
            outcomeService.addOutcome(outcomeAmount, outcomeDate, outcomeComment, categoryId);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeCategory(CategoryService categoryService) {
        System.out.println("Provide id of category to delete");
        Long categoryId = SCANNER.nextLong();
        try {
            categoryService.deleteCategory(categoryId);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void removeIncome(IncomeService incomeService) {
        System.out.println("Provide id of income to delete");
        Long incomeId = SCANNER.nextLong();
        try {
            incomeService.deleteIncome(incomeId);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void removeOutcome(OutcomeService outcomeService) {
        System.out.println("Provide id of outcome to delete");
        Long outcomeId = SCANNER.nextLong();
        try {
            outcomeService.deleteOutcome(outcomeId);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
    }
}