import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableAction {
    private final static Logger logger = Logger.getLogger(TableAction.class.getName());

    // CURSOR
    String[] cursor(Connection connection, String[] group) throws SQLException {
        logger.log(Level.INFO, "Read data");
        String[] row = {"", "", "", "", ""};
        PreparedStatement readStatement = connection.prepareStatement("select groups.name, avg(marks.value) as average\n" +
                "from groups\n" +
                "join people on groups.id = people.group_id\n" +
                "join marks on people.id = marks.student_id\n" +
                "where groups.name = ? \n" +
                "group by groups.name\n" +
                "order by groups.name");
        readStatement.setString(1, group[0]);
        ResultSet resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                row[0] = (resultSet.getString("name"));
                BigDecimal bd = BigDecimal.valueOf(resultSet.getDouble("average"));
                bd = bd.setScale(3, RoundingMode.CEILING);
                row[1] = (Double.toString(bd.doubleValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        readStatement = connection.prepareStatement("select groups.name, avg(marks.value) as average\n" +
                "from groups\n" +
                "join people on groups.id = people.group_id\n" +
                "join marks on people.id = marks.student_id\n" +
                "where groups.name = ? \n" +
                "group by groups.name\n" +
                "order by groups.name");
        readStatement.setString(1, group[1]);
        resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                row[2] = (resultSet.getString("name"));
                BigDecimal bd = BigDecimal.valueOf(resultSet.getDouble("average"));
                bd = bd.setScale(3, RoundingMode.CEILING);
                row[3] = (Double.toString(bd.doubleValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(row[3]) - Double.parseDouble(row[1]));
        bd = bd.setScale(3, RoundingMode.CEILING);
        row[4] = (Double.toString(bd.doubleValue()));
        readStatement.close();
        resultSet.close();
        return row;
    }

    // SELECT
    Vector<Vector<String>> readData(Connection connection) throws SQLException {
        logger.log(Level.INFO, "Read data");
        Vector<Vector<String>> marksArray = new Vector<Vector<String>>();
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM marks");
        ResultSet resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                Vector<String> row = new Vector<String>();
                row.add(Integer.toString(resultSet.getInt("id")));
                row.add(Integer.toString(resultSet.getInt("student_id")));
                row.add(Integer.toString(resultSet.getInt("subject_id")));
                row.add(Integer.toString(resultSet.getInt("teacher_id")));
                row.add(Integer.toString(resultSet.getInt("value")));
                marksArray.add(row);

            /*marks.setID(resultSet.getInt("id"));
            marks.setStudentID(resultSet.getInt("student_id"));
            marks.setSubjectID(resultSet.getInt("subject_id"));
            marks.setTeacherID(resultSet.getInt("teacher_id"));
            marks.setValue(resultSet.getInt("value"));
            logger.log(Level.INFO, "Data read from the database: " + marks.toString());*/
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        readStatement.close();
        resultSet.close();
        return marksArray;
    }

    // INSERT
    void insertData(Connection connection, String[] add) throws SQLException {
        logger.log(Level.INFO, "Insert data");
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO marks (student_id, subject_id, teacher_id, value) VALUES (?, ?, ?, ?);");

        insertStatement.setInt(1, Integer.parseInt(add[0]));
        insertStatement.setInt(2, Integer.parseInt(add[1]));
        insertStatement.setInt(3, Integer.parseInt(add[2]));
        insertStatement.setInt(4, Integer.parseInt(add[3]));
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    // UPDATE
    void updateData(Connection connection, String[] update) throws SQLException {
        logger.log(Level.INFO, "Update data");
        PreparedStatement updateStatement = connection
                .prepareStatement("UPDATE marks SET student_id = ?, subject_id = ?, teacher_id = ?, value = ? WHERE id = ?;");
        updateStatement.setInt(1, Integer.parseInt(update[1]));
        updateStatement.setInt(2, Integer.parseInt(update[2]));
        updateStatement.setInt(3, Integer.parseInt(update[3]));
        updateStatement.setInt(4, Integer.parseInt(update[4]));
        updateStatement.setInt(5, Integer.parseInt(update[0]));
        updateStatement.executeUpdate();
        updateStatement.close();
    }

    // DELETE
    void deleteData(Connection connection, String[] delete) throws SQLException {
        logger.log(Level.INFO, "Delete data");
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM marks WHERE student_id = ? AND subject_id = ? AND teacher_id = ?;");
        deleteStatement.setInt(1, Integer.parseInt(delete[0]));
        deleteStatement.setInt(2, Integer.parseInt(delete[1]));
        deleteStatement.setInt(3, Integer.parseInt(delete[2]));
        deleteStatement.executeUpdate();
        deleteStatement.close();
    }

    void backUpDatabase() {

    }

    void restoreDatabase() {

    }

    // CREATE
    void createTable() {

    }
}
