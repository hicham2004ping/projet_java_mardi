package ma.prodenta.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    private DatabaseConnection() {}

    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {

                Properties props = new Properties();
                props.load(DatabaseConnection.class.getResourceAsStream("/config/db.properties"));

                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);

                connection.setAutoCommit(true);
                System.out.println("✅ Connexion BDD établie !");
            }
        } catch (Exception e) {
            throw new SQLException("Connexion DB échouée : " + e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Connexion fermée");
            } catch (SQLException e) {
                System.err.println("❌ Erreur fermeture connexion : " + e.getMessage());
            }
        }
    }
}
