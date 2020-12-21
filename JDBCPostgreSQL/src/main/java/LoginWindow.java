import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginWindow {
    String log = "";
    String pass = "";
    Connect connect = new Connect();
    Connection connection = null;
    volatile boolean ready = false;

    public LoginWindow() {
        JFrame jframe = new JFrame("Login window");
        jframe.setLayout(new FlowLayout());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocation(500, 200);
        jframe.setSize(350, 350);

        JTextField textField = new JTextField("postgres");
        JPasswordField passwordField = new JPasswordField("123");

        /*JPanel infoPanel = new JPanel();
        JTextField jtf = new JTextField(10);
        infoPanel.add(jtf);
        jframe.add(infoPanel, BorderLayout.SOUTH);*/

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(new JLabel("User name: ", SwingConstants.CENTER));
        northPanel.add(textField);
        northPanel.add(new JLabel("Password: ", SwingConstants.CENTER));
        northPanel.add(passwordField);
        jframe.add(northPanel, BorderLayout.NORTH);


        JPanel southPanel = new JPanel();
        JButton insertButton = new JButton("Login");
        southPanel.add(insertButton);
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                log = (textField.getText());
                pass = new String(passwordField.getPassword());
                System.out.println("Log: " + log + ", Pass: " + pass);


                if (connect.checkJDBCDriver()) {
                    connection = connect.getConnection(log, pass);
                } else {
                    //System.exit(1);
                    // Handle action events.
                    /*jtf.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            // Show text when user presses ENTER.
                            jtf.setText("Incorret login");
                        }
                    });*/
                }

                if (connection != null) {
                    System.out.println("You successfully connected to database now");
                    ready = true;
                    // Create the frame on the event dispatching thread.
                    SwingUtilities.invokeLater(
                            new Runnable() {
                                public void run() {
                                    new MainWindow(connection);
                                }
                            }
                    );
                    jframe.setVisible(false); //you can't see me!
                    jframe.dispose(); //Destroy the JFrame object

                } else {
                    System.out.println("Failed to make connection to database");
                    //System.exit(1);
                }

            }
        });


        jframe.add(southPanel, BorderLayout.SOUTH);
        jframe.pack();
        jframe.setVisible(true);
    }

    Connection getConnection() {
        return connection;
    }
}