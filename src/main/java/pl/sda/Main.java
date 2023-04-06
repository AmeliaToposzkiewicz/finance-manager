package pl.sda;

import pl.sda.repository.CategoryRepository;
import pl.sda.repository.IncomeRepository;
import pl.sda.service.CategoryService;
import pl.sda.service.IncomeService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
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

            while (true) {
                System.out.println("Type operation");
                System.out.println("0 - Exit program");
                System.out.println("1 - Add new category");
                System.out.println("2 - Delete category");
                System.out.println("3 - Add new income");
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
                }

            }
        }
    }
}