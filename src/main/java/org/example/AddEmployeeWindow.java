package org.example;

import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.StatementWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddEmployeeWindow extends JFrame {

    public AddEmployeeWindow() {
        setTitle("Nouvel employé");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        JLabel firstNameLabel = new JLabel("Prénom");
        JTextField firstNameField = new JTextField();
        firstNameField.setMaximumSize(new Dimension(400, 50));
        JLabel lastNameLabel = new JLabel("Nom");
        JTextField lastNameField = new JTextField();
        lastNameField.setMaximumSize(new Dimension(400, 50));
        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(400, 50));
        JLabel salaryLabel = new JLabel("Salaire");
        JTextField salaryField = new JTextField();
        salaryField.setMaximumSize(new Dimension(400, 50));
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);
        add(formPanel);

        JButton submitButton = new JButton("Ajouter");

        submitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                insertData(firstNameField.getText(), lastNameField.getText(),
                        emailField.getText(), salaryField.getText());
            }
        });
        formPanel.add(submitButton);

        // add return button
        JButton returnButton = new JButton("Retour");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



    private void insertData(String firstName, String lastName, String email, String salary) {
        String url = "jdbc:mysql://localhost:3306/testJava";
        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO employees (first_name, last_name, email, salary) VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + salary + "')";
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "L'employé a été ajouté avec succès");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new AddEmployeeWindow();
    }
}
