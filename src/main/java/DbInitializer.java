import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInitializer {
    private final Connection connection;

    public DbInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initDb() throws IOException, SQLException {
        try (InputStream categories = getClass().getResourceAsStream("/sql/categories_ddl.sql");
             InputStream incomes = getClass().getResourceAsStream("/sql/incomes_ddl.sql");
             InputStream outcomes = getClass().getResourceAsStream("/sql/outcomes_ddl.sql")) {
            executeSqlFromResource(categories);
            executeSqlFromResource(incomes);
            executeSqlFromResource(outcomes);
        }
    }

    private void executeSqlFromResource(InputStream inputStream) throws IOException, SQLException {
        if (inputStream == null) {
            System.err.println("Failed to open resource");
            return;
        }
        String sql = new String(inputStream.readAllBytes());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();


    }
}
