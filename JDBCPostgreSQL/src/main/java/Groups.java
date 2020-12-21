import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Groups {
    private final static Logger logger = Logger.getLogger(Subjects.class.getName());
    private Integer ID;
    private String groupName;
    private Vector<String> data = new Vector<String>();

    public Groups(){

    }

    public Groups(Integer ID, String groupName) {
        this.ID = ID;
        data.add(0, ID.toString());
        this.groupName = groupName;
        data.add(1, groupName);
    }

    // SELECT
    Vector<Vector<String>> readData(Connection connection) throws SQLException {
        logger.log(Level.INFO, "Read data");
        Vector<Vector<String>> groupsArray = new Vector<Vector<String>>();
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM groups");
        ResultSet resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                Vector<String> row = new Vector<String>();
                row.add(Integer.toString(resultSet.getInt("id")));
                row.add(resultSet.getString("name"));
                groupsArray.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        readStatement.close();
        resultSet.close();
        return groupsArray;
    }

    // INSERT
    void insertData(Connection connection, String[] add) throws SQLException {
        logger.log(Level.INFO, "Insert data");
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO groups (name) VALUES (?);");
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
                    .prepareStatement("UPDATE groups SET name = ? WHERE id = ?;");
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
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM groups WHERE id = ?;");
            deleteStatement.setInt(1, Integer.parseInt(delete[0]));
            deleteStatement.executeUpdate();
            deleteStatement.close();
        }
    }

    /*
    (IntStream.range(0, delete.length)
                .filter(i -> "".equals(delete[i]))
                .findFirst()
                .orElse(-1)) == -1)
     */

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName){
        this.groupName = groupName;
        data.add(1, groupName);
    }

    public Vector<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Groups {" +
                "ID=" + ID +
                ", groupName='" + groupName +
                '}';
    }
}
