import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ConnectTest {
    private final static Logger logger = Logger.getLogger(ConnectTest.class.getName());

    @Test
    void getConnection1() {
        try(Connection connection = new Connect().getConnection("F", "1")) {
            assertNull(connection);
            logger.log(Level.INFO, "Bad request connection to PostgreSQL JDBC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getConnection2() {
        try(Connection connection = new Connect().getConnection("postgres", "123")) {
            assertTrue(connection.isValid(0));
            assertFalse(connection.isClosed());
            logger.log(Level.INFO, "Good request connection to PostgreSQL JDBC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}