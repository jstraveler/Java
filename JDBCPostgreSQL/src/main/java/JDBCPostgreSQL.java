import java.sql.*;
import javax.swing.*;

public class JDBCPostgreSQL {
    static LoginWindow loginWindow = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        loginWindow = new LoginWindow();
                    }
                }
        );
    }
}