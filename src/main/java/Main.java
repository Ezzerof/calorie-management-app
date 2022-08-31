import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        String jdbcURL = "jdbc:postgresql://localhost:5432/dishes";
        String username = "postgres";
        String password = "password";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to PostgreSQL server.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error in connection to PostgreSQL server.");
            e.printStackTrace();
        }

        new MainPage().startApp();



    }

}
