import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class People {
    private final static Logger logger = Logger.getLogger(People.class.getName());
    private Integer ID;
    private String firstName;
    private String lastName;
    private String patherName;
    private Integer groupID;
    private String type;
    private Vector<String> data = new Vector<String>();

    public People(){

    }

    public People(Integer ID, String firstName, String lastName, String patherName, Integer groupID, String type) {
        this.ID = ID;
        data.add(0, ID.toString());
        this.firstName = firstName;
        data.add(1, firstName);
        this.lastName = lastName;
        data.add(2, lastName);
        this.patherName = patherName;
        data.add(3, patherName);
        this.groupID = groupID;
        data.add(4, groupID.toString());
        this.type = type;
        data.add(5, type);
    }

    // SELECT
    Vector<Vector<String>> readData(Connection connection) throws SQLException {
        logger.log(Level.INFO, "Read data");
        Vector<Vector<String>> peopleArray = new Vector<Vector<String>>();
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM people");
        ResultSet resultSet = readStatement.executeQuery();
        try {
            while(resultSet.next()) {
                Vector<String> row = new Vector<String>();
                row.add(Integer.toString(resultSet.getInt("id")));
                row.add(resultSet.getString("first_name"));
                row.add(resultSet.getString("last_name"));
                row.add(resultSet.getString("pather_name"));
                row.add(Integer.toString(resultSet.getInt("group_id")));
                row.add(resultSet.getString("type"));
                peopleArray.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        readStatement.close();
        resultSet.close();
        return peopleArray;
    }

    // INSERT
    void insertData(Connection connection, String[] add) throws SQLException {
        if(add[4].equals("")) {
            logger.log(Level.INFO, "Enter data in the form");
        } else {
            logger.log(Level.INFO, "Insert data");
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO people (first_name, last_name, pather_name, group_id, type) VALUES (?, ?, ?, ?, ?);");
            insertStatement.setString(1, add[0]);
            insertStatement.setString(2, add[1]);
            insertStatement.setString(3, add[2]);
            insertStatement.setInt(4, Integer.parseInt(add[3]));
            insertStatement.setString(5, add[4]);
            insertStatement.executeUpdate();
            insertStatement.close();
        }
    }

    // UPDATE
    void updateData(Connection connection, String[] update) throws SQLException {
        if(update[0].equals("")) {
            logger.log(Level.INFO, "Select the line to update");
        } else {
            logger.log(Level.INFO, "Update data");
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE people SET first_name = ?, last_name = ?, pather_name = ?, group_id = ?, type = ? WHERE id = ?;");
            updateStatement.setString(1, update[1]);
            updateStatement.setString(2, update[2]);
            updateStatement.setString(3, update[3]);
            updateStatement.setInt(4, Integer.parseInt(update[4]));
            updateStatement.setString(5, update[5]);
            updateStatement.setInt(6, Integer.parseInt(update[0]));
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
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM people WHERE id = ?;");
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
        data.add(1, firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
        data.add(2, lastName);
    }

    public String getPatherName() {
        return patherName;
    }

    public void setPatherName(String patherName){
        this.patherName = patherName;
        data.add(3, patherName);
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID){
        this.groupID = groupID;
        data.add(4, groupID.toString());
    }

    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
        data.add(5, type);
    }

    public Vector<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "People {" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patherName='" + patherName + '\'' +
                ", groupID=" + groupID + '\'' +
                ", type='" + type +
                '}';
    }
}
