import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Marks {
    private final static Logger logger = Logger.getLogger(Marks.class.getName());
    private Integer ID;
    private Integer studentID;
    private Integer subjectID;
    private Integer teacherID;
    private Integer value;
    private Vector<String> data = new Vector<String>();

    public Marks(){

    }

    public Marks(Integer ID, Integer studentID, Integer subjectID, Integer teacherID, Integer value) {
        this.ID = ID;
        data.add(0, ID.toString());
        this.studentID = studentID;
        data.add(1, studentID.toString());
        this.subjectID = subjectID;
        data.add(2, subjectID.toString());
        this.teacherID = teacherID;
        data.add(3, teacherID.toString());
        this.value = value;
        data.add(4, value.toString());
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
        if(Arrays.asList(add).contains("")) {
            logger.log(Level.INFO, "Enter data in the form");
        } else {
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
    }

    // UPDATE
    void updateData(Connection connection, String[] update) throws SQLException {
        if(Arrays.asList(update).contains("")) {
            logger.log(Level.INFO, "Enter data in the form");
        } else {
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
    }

    // DELETE
    void deleteData(Connection connection, String[] delete) throws SQLException {
        if(delete[0].equals("")) {
            logger.log(Level.INFO, "Select the line to delete");
        } else {
            logger.log(Level.INFO, "Delete data");
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM marks WHERE id = ?;");
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

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID){
        this.studentID = studentID;
        data.add(1, studentID.toString());
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID){
        this.subjectID = subjectID;
        data.add(2, subjectID.toString());
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID){
        this.teacherID = teacherID;
        data.add(3, teacherID.toString());
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
        data.add(4, value.toString());
    }

    public Vector<String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Marks {" +
                "ID=" + ID +
                ", studentID='" + studentID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", value=" + value +
                '}';
    }
}
