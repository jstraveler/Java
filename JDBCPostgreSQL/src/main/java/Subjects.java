import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Subjects {
    private final static Logger logger = Logger.getLogger(Subjects.class.getName());
    private Integer ID;
    private String subjectName;
    private Vector<String> data = new Vector<String>();

    public Subjects(){

    }

    public Subjects(Integer ID, String subjectName) {
        this.ID = ID;
        data.add(0, ID.toString());
        this.subjectName = subjectName;
        data.add(1, subjectName);
    }

    // SELECT
    Vector<Vector<String>> readData(Connection connection) throws SQLException {
        logger.log(Level.INFO, "Read data");
        Vector<Vector<String>> subjectsArray = new Vector<Vector<String>>();
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM subjects");
        ResultSet resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                Vector<String> row = new Vector<String>();
                row.add(Integer.toString(resultSet.getInt("id")));
                row.add(resultSet.getString("name"));
                subjectsArray.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        readStatement.close();
        resultSet.close();
        return subjectsArray;
    }

    // INSERT
    void insertData(Connection connection, String[] add) throws SQLException {
        logger.log(Level.INFO, "Insert data");
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO subjects (name) VALUES (?);");
        insertStatement.setString(1, add[0]);
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    // UPDATE
    void updateData(Connection connection, String[] update) throws SQLException {
        if(update[0].equals("")) {
            logger.log(Level.INFO, "Select the line to update");
        } else {
            logger.log(Level.INFO, "Update data");
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE subjects SET name = ? WHERE id = ?;");
            updateStatement.setString(1, update[1]);
            updateStatement.setInt(2, Integer.parseInt(update[0]));
            updateStatement.executeUpdate();
            updateStatement.close();
        }
    }

    // DELETE
    void deleteData(Connection connection, String[] delete) throws SQLException {
        if(delete[0].equals("")) {
            logger.log(Level.INFO, "Select the line to delete");
        } else {
            logger.log(Level.INFO, "Delete data");
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM subjects WHERE id = ?;");
            deleteStatement.setInt(1, Integer.parseInt(delete[0]));
            deleteStatement.executeUpdate();
            deleteStatement.close();
        }
    }

    void backUpDatabase() {

    }

    void restoreDatabase() {

    }

    // CREATE
    void createTable() {

    }

    public Integer getID() {
        return ID;
    }

    public void setID( Integer ID){
        this.ID = ID;
        data.add(0, ID.toString());
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName){
        this.subjectName = subjectName;
        data.add(1, subjectName);
    }

    public Vector<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Subjects {" +
                "ID=" + ID +
                ", subjectName='" + subjectName +
                '}';
    }
}
