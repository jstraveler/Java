import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainWindow {
    Connection connection;
    MainWindow(Connection connection) {
        // Set up the JFrame.
        this.connection = connection;
        JFrame jfrm = new JFrame("Main Window");
        jfrm.setLayout(new FlowLayout());
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocation(500, 200);
        jfrm.setSize(600, 600);

        // Create the tabbed pane.
        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("Marks", new MarksPanel(connection));
        jtp.addTab("People", new PeoplePanel(connection));
        jtp.addTab("Subjects", new SubjectsPanel(connection));
        jtp.addTab("Groups", new GroupsPanel(connection));
        jtp.addTab("Cursor", new CursorPanel(connection));
        jfrm.add(jtp);

        // Display the frame.
        jfrm.setVisible(true);
    }
}

class MarksPanel extends JPanel {
    String[] columnsName = {"#", "Student", "Subject", "Teacher", "Mark"};
    Marks marks;
    JTextField textFieldStudentID, textFieldSubjectID, textFieldTeacherID, textFieldValue;
    DefaultTableModel tableModel;
    JPanel panelButton, textFields;
    JButton viewButton, addButton, updateButton, deleteButton;
    JTable table;
    JScrollPane jsp;
    Vector<Vector<String>> data;
    String ID;

    public MarksPanel(Connection connection) {
        marks = new Marks();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsName);
        panelButton = new JPanel();
        setLayout(new BorderLayout());

        textFields = new JPanel();
        textFields.setLayout(new GridLayout(8, 1, 5, 5));
        textFieldStudentID = new JTextField(10);
        textFieldSubjectID = new JTextField(10);
        textFieldTeacherID = new JTextField(10);
        textFieldValue = new JTextField(10);
        textFields.add(new JLabel("Student ID", SwingConstants.CENTER));
        textFields.add(textFieldStudentID);
        textFields.add(new JLabel("Subject ID", SwingConstants.CENTER));
        textFields.add(textFieldSubjectID);
        textFields.add(new JLabel("Teacher ID", SwingConstants.CENTER));
        textFields.add(textFieldTeacherID);
        textFields.add(new JLabel("Value", SwingConstants.CENTER));
        textFields.add(textFieldValue);
        add(textFields, BorderLayout.EAST);

        viewButton = new JButton("VIEW");
        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        panelButton.add(viewButton);
        panelButton.add(addButton);
        panelButton.add(updateButton);
        panelButton.add(deleteButton);
        add(panelButton, BorderLayout.NORTH);

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

        viewMarksPanel(connection);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                viewMarksPanel(connection);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addMarksPanel(connection);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteMarksPanel(connection);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateMarksPanel(connection);
            }
        });
    }

    private void viewMarksPanel(Connection connection) {
        if (connection != null) {
            tableModel.setRowCount(0);
            try {
                data = marks.readData(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Vector<String> row : data) {
                tableModel.addRow(row);
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void addMarksPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] add = {"", "", "", ""};
                add[0] = textFieldStudentID.getText();
                add[1] = textFieldSubjectID.getText();
                add[2] = textFieldTeacherID.getText();
                add[3] = textFieldValue.getText();
                marks.insertData(connection, add);
                tableModel.setRowCount(0);
                viewMarksPanel(connection);
                //table.repaint();
                //tableModel.fireTableDataChanged();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void deleteMarksPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] delete = {""};
                delete[0] = ID;
                marks.deleteData(connection, delete);
                tableModel.setRowCount(0);
                viewMarksPanel(connection);
                //table.repaint();
                //tableModel.fireTableDataChanged();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void updateMarksPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] update = {"", "", "", "", ""};
                update[0] = ID;
                update[1] = textFieldStudentID.getText();
                update[2] = textFieldSubjectID.getText();
                update[3] = textFieldTeacherID.getText();
                update[4] = textFieldValue.getText();
                marks.updateData(connection, update);
                tableModel.setRowCount(0);
                viewMarksPanel(connection);
                //table.repaint();
                //tableModel.fireTableDataChanged();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int selectedRowIndex = table.getSelectedRow();
        ID = model.getValueAt(selectedRowIndex, 0).toString();
        textFieldStudentID.setText(model.getValueAt(selectedRowIndex, 1).toString());
        textFieldSubjectID.setText(model.getValueAt(selectedRowIndex, 2).toString());
        textFieldTeacherID.setText(model.getValueAt(selectedRowIndex, 3).toString());
        textFieldValue.setText(model.getValueAt(selectedRowIndex, 4).toString());
    }
}

class PeoplePanel extends JPanel {
    String[] columnsName = {"#", "First Name", "Last Name", "Pather Name", "Group", "Type"};
    People people;
    DefaultTableModel tableModel;
    JPanel panelButton, textFields;
    JButton viewButton, addButton, updateButton, deleteButton;
    JScrollPane jsp;
    JTable table;
    Vector<Vector<String>> data;
    String ID;
    JTextField textFieldFirstName, textFieldLastName, textFieldPatherName, textFieldGroupID, textFieldType;

    public PeoplePanel(Connection connection) {
        people = new People();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsName);
        panelButton = new JPanel();
        setLayout(new BorderLayout());

        textFields = new JPanel();
        textFields.setLayout(new GridLayout(10, 1, 5, 5));
        textFieldFirstName = new JTextField(10);
        textFieldLastName = new JTextField(10);
        textFieldPatherName = new JTextField(10);
        textFieldGroupID = new JTextField(10);
        textFieldType = new JTextField(10);
        textFields.add(new JLabel("First Name", SwingConstants.CENTER));
        textFields.add(textFieldFirstName);
        textFields.add(new JLabel("Last Name", SwingConstants.CENTER));
        textFields.add(textFieldLastName);
        textFields.add(new JLabel("Pather Name", SwingConstants.CENTER));
        textFields.add(textFieldPatherName);
        textFields.add(new JLabel("Group", SwingConstants.CENTER));
        textFields.add(textFieldGroupID);
        textFields.add(new JLabel("Type", SwingConstants.CENTER));
        textFields.add(textFieldType);
        add(textFields, BorderLayout.EAST);

        viewButton = new JButton("VIEW");
        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        panelButton.add(viewButton);
        panelButton.add(addButton);
        panelButton.add(updateButton);
        panelButton.add(deleteButton);
        add(panelButton, BorderLayout.NORTH);

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

        viewPeoplePanel(connection);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                viewPeoplePanel(connection);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addPeoplePanel(connection);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deletePeoplePanel(connection);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updatePeoplePanel(connection);
            }
        });
    }

    private void viewPeoplePanel(Connection connection) {
        if (connection != null) {
            tableModel.setRowCount(0);
            try {
                data = people.readData(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Vector<String> row : data) {
                tableModel.addRow(row);
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void addPeoplePanel(Connection connection) {
        if (connection != null) {
            try {
                String[] add = {"", "", "", "", ""};
                add[0] = textFieldFirstName.getText();
                add[1] = textFieldLastName.getText();
                add[2] = textFieldPatherName.getText();
                add[3] = textFieldGroupID.getText();
                add[4] = textFieldType.getText();
                people.insertData(connection, add);
                tableModel.setRowCount(0);
                viewPeoplePanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void deletePeoplePanel(Connection connection) {
        if (connection != null) {
            try {
                String[] delete = {""};
                delete[0] = ID;
                people.deleteData(connection, delete);
                tableModel.setRowCount(0);
                viewPeoplePanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void updatePeoplePanel(Connection connection) {
        if (connection != null) {
            try {
                String[] update = {"", "", "", "", "", ""};
                update[0] = ID;
                update[1] = textFieldFirstName.getText();
                update[2] = textFieldLastName.getText();
                update[3] = textFieldPatherName.getText();
                update[4] = textFieldGroupID.getText();
                update[5] = textFieldType.getText();
                people.updateData(connection, update);
                tableModel.setRowCount(0);
                viewPeoplePanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int selectedRowIndex = table.getSelectedRow();
        ID = model.getValueAt(selectedRowIndex, 0).toString();
        textFieldFirstName.setText(model.getValueAt(selectedRowIndex, 1).toString());
        textFieldLastName.setText(model.getValueAt(selectedRowIndex, 2).toString());
        textFieldPatherName.setText(model.getValueAt(selectedRowIndex, 3).toString());
        textFieldGroupID.setText(model.getValueAt(selectedRowIndex, 4).toString());
        textFieldType.setText(model.getValueAt(selectedRowIndex, 5).toString());
    }
}

class GroupsPanel extends JPanel {
    String[] columnsName = {"#", "Group"};
    Groups groups;
    DefaultTableModel tableModel;
    JTable table;
    JPanel panelButton, textFields;
    JButton viewButton, addButton, updateButton, deleteButton;
    JScrollPane jsp;
    Vector<Vector<String>> data;
    String ID;
    JTextField textFieldGroupName;

    public GroupsPanel(Connection connection) {
        groups = new Groups();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsName);
        panelButton = new JPanel();
        setLayout(new BorderLayout());

        textFields = new JPanel();
        textFields.setLayout(new GridLayout(2, 1, 25, 25));
        textFieldGroupName = new JTextField(10);
        textFields.add(new JLabel("Group", SwingConstants.CENTER));
        textFields.add(textFieldGroupName);
        add(textFields, BorderLayout.EAST);

        viewButton = new JButton("VIEW");
        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        panelButton.add(viewButton);
        panelButton.add(addButton);
        panelButton.add(updateButton);
        panelButton.add(deleteButton);
        add(panelButton, BorderLayout.NORTH);

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(270);
        jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

        viewGroupsPanel(connection);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                viewGroupsPanel(connection);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addGroupsPanel(connection);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteGroupsPanel(connection);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateGroupsPanel(connection);
            }
        });
    }

    private void viewGroupsPanel(Connection connection) {
        if (connection != null) {
            tableModel.setRowCount(0);
            try {
                data = groups.readData(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Vector<String> row : data) {
                tableModel.addRow(row);
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void addGroupsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] add = {""};
                add[0] = textFieldGroupName.getText();
                groups.insertData(connection, add);
                tableModel.setRowCount(0);
                viewGroupsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void deleteGroupsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] delete = {""};
                delete[0] = ID;
                groups.deleteData(connection, delete);
                tableModel.setRowCount(0);
                viewGroupsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void updateGroupsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] update = {"", ""};
                update[0] = ID;
                update[1] = textFieldGroupName.getText();
                groups.updateData(connection, update);
                tableModel.setRowCount(0);
                viewGroupsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int selectedRowIndex = table.getSelectedRow();
        ID = model.getValueAt(selectedRowIndex, 0).toString();
        textFieldGroupName.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }
}

class SubjectsPanel extends JPanel {
    String[] columnsName = {"#", "Subject"};
    Subjects subjects;
    DefaultTableModel tableModel;
    JPanel panelButton;
    JPanel textFields;
    JButton viewButton;
    JButton addButton;
    JButton updateButton;
    JButton deleteButton;
    JScrollPane jsp;
    Vector<Vector<String>> data;
    JTable table;
    String ID;
    JTextField textFieldSubjectName;

    public SubjectsPanel(Connection connection) {
        subjects = new Subjects();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsName);
        panelButton = new JPanel();
        setLayout(new BorderLayout());

        textFields = new JPanel();
        textFields.setLayout(new GridLayout(2, 1, 25, 25));
        textFieldSubjectName = new JTextField(10);
        textFields.add(new JLabel("Subject", SwingConstants.CENTER));
        textFields.add(textFieldSubjectName);
        add(textFields, BorderLayout.EAST);

        viewButton = new JButton("VIEW");
        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        panelButton.add(viewButton);
        panelButton.add(addButton);
        panelButton.add(updateButton);
        panelButton.add(deleteButton);
        add(panelButton, BorderLayout.NORTH);

        table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(270);
        jsp = new JScrollPane(table);
        add(jsp, BorderLayout.CENTER);

        viewSubjectsPanel(connection);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                viewSubjectsPanel(connection);
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                addSubjectsPanel(connection);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                deleteSubjectsPanel(connection);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateSubjectsPanel(connection);
            }
        });
    }

    private void viewSubjectsPanel(Connection connection) {
        if (connection != null) {
            tableModel.setRowCount(0);
            try {
                data = subjects.readData(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Vector<String> row : data) {
                tableModel.addRow(row);
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void addSubjectsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] add = {""};
                add[0] = textFieldSubjectName.getText();
                subjects.insertData(connection, add);
                tableModel.setRowCount(0);
                viewSubjectsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void deleteSubjectsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] delete = {""};
                delete[0] = ID;
                subjects.deleteData(connection, delete);
                tableModel.setRowCount(0);
                viewSubjectsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void updateSubjectsPanel(Connection connection) {
        if (connection != null) {
            try {
                String[] update = {"", ""};
                update[0] = ID;
                update[1] = textFieldSubjectName.getText();
                subjects.updateData(connection, update);
                tableModel.setRowCount(0);
                viewSubjectsPanel(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int selectedRowIndex = table.getSelectedRow();
        ID = model.getValueAt(selectedRowIndex, 0).toString();
        textFieldSubjectName.setText(model.getValueAt(selectedRowIndex, 1).toString());
    }
}

class CursorPanel extends JPanel {
    TableAction tableAction;
    JPanel panelButton, textFields;
    JButton viewButton;
    String[] data;
    String ID;
    JTextField textFieldGroupName1, textFieldGroupName2;
    JTextArea textAreaAverage;

    public CursorPanel(Connection connection) {
        tableAction = new TableAction();
        panelButton = new JPanel();
        setLayout(new BorderLayout());

        textFields = new JPanel();
        textFields.setLayout(new GridLayout(3, 1, 5, 5));
        textFieldGroupName1 = new JTextField("10_2019");
        textFieldGroupName2 = new JTextField("11_2020");
        textAreaAverage = new JTextArea(5, 10);

        textFields.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textFields.add(new JLabel("Group Name", SwingConstants.CENTER));
        textFields.add(textFieldGroupName1);
        textFields.add(new JLabel("Group Name", SwingConstants.CENTER));
        textFields.add(textFieldGroupName2);
        textFields.add(new JLabel("Average", SwingConstants.CENTER));
        textFields.add(textAreaAverage);

        add(textFields, BorderLayout.CENTER);

        viewButton = new JButton("VIEW");
        panelButton.add(viewButton);
        add(panelButton, BorderLayout.SOUTH);

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                getCursor(connection);
            }
        });
    }

    private void getCursor(Connection connection) {
        if (connection != null) {
            try {
                String[] group = {"", ""};
                group[0] = textFieldGroupName1.getText();
                group[1] = textFieldGroupName2.getText();
                data = tableAction.cursor(connection, group);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            textAreaAverage.setText("");
            textAreaAverage.append(textFieldGroupName1.getText() + "\n" +
                    data[1] + "\n" +
                    textFieldGroupName2.getText() + "\n" +
                    data[3] + "\n" +
                    "Average\n" +
                    data[4]
            );
        } else {
            System.out.println("Failed to make connection to database");
            //System.exit(1);
        }
    }
}