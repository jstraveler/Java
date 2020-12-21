import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    private final static Logger logger = Logger.getLogger(Connect.class.getName());
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres"; // 5432
    static final String USER = "postgres";
    static final String PASS = "123";

    boolean checkJDBCDriver() {
        logger.log(Level.INFO, "Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, "PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
        logger.log(Level.INFO, "PostgreSQL JDBC Driver successfully connected");
        return true;
    }

    Connection getConnection(String user, String pass) {
        Connection connection = null;
        if(checkLogin(user, pass)) {
            try {
                connection = DriverManager.getConnection(DB_URL, user, pass);
            } catch (SQLException e) {
                logger.log(Level.INFO, "Connection Failed");
                e.printStackTrace();
            }
            return connection;
        } else {
            logger.log(Level.INFO, "User/password incorrect");
            return null;
        }
    }

    boolean checkLogin(String user, String pass) {
        return user.equals(USER) & pass.equals(PASS);
    }
}
