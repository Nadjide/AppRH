package org.example;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login {
    private JFrame frame;
    private JLabel labelUsername, labelPassword;
    private JTextField textUsername;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    private Connection conn;
    private Statement stmt;

    public Login() {
        createForm();
        createDatabaseConnection();
        addComponentsToFrame();
        addActionListeners();
        displayForm();
    }

    private void createForm() {
        frame = new JFrame("Login Form");
        labelUsername = new JLabel("Username: ");
        labelPassword = new JLabel("Password: ");
        textUsername = new JTextField(20);
        fieldPassword = new JPasswordField(20);
        buttonLogin = new JButton("Login");
    }

    private void createDatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void addComponentsToFrame() {
        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 2));
        container.add(labelUsername);
        container.add(textUsername);
        container.add(labelPassword);
        container.add(fieldPassword);
        container.add(new Label());
        container.add(buttonLogin);
    }

    private void addActionListeners() {
        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textUsername.getText();
                char[] password = fieldPassword.getPassword();
                try {
                    String query = "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + new String(password) + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        frame.dispose();
                        MainWindow mainWindow = new MainWindow();
                        mainWindow.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Login failed.");
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
    }

    private void displayForm() {
        frame.setSize(400, 120);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
