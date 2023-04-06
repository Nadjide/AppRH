package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ModifyEmployeeWindow extends JFrame {
    public ModifyEmployeeWindow(int employeeId) {
        setTitle("Modifier un employé");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        String[] employeeData = new String[9];
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
            String query = "SELECT * FROM employees WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeId);
            ResultSet employee = preparedStatement.executeQuery();
            if (employee.next()) {
                for (int i = 1; i <= 9; i++) {
                    employeeData[i - 1] = employee.getString(i);
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

// Create text fields with the current employee information
        JLabel idLabel = new JLabel("ID: " + employeeData[0]);
        add(idLabel);
        add(new JLabel(""));

        JLabel firstNameLabel = new JLabel("Prénom:");
        add(firstNameLabel);
        JTextField firstNameTextField = new JTextField(employeeData[1]);
        add(firstNameTextField);

        JLabel lastNameLabel = new JLabel("Nom:");
        add(lastNameLabel);
        JTextField lastNameTextField = new JTextField(employeeData[2]);
        add(lastNameTextField);

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);
        JTextField emailTextField = new JTextField(employeeData[3]);
        add(emailTextField);

        JLabel addressLabel = new JLabel("Adresse:");
        add(addressLabel);
        JTextField addressTextField = new JTextField(employeeData[4]);
        add(addressTextField);

        JLabel phoneLabel = new JLabel("Téléphone:");
        add(phoneLabel);
        JTextField phoneTextField = new JTextField(employeeData[5]);
        add(phoneTextField);

        JLabel birthDateLabel = new JLabel("Date de naissance:");
        add(birthDateLabel);
        JTextField birthDateTextField = new JTextField(employeeData[6]);
        add(birthDateTextField);

        JLabel hireDateLabel = new JLabel("Date d'embauche:");
        add(hireDateLabel);
        JTextField hireDateTextField = new JTextField(employeeData[7]);
        add(hireDateTextField);

        JLabel salaryLabel = new JLabel("Salaire:");
        add(salaryLabel);
        JTextField salaryTextField = new JTextField(employeeData[8]);
        add(salaryTextField);

        // Add buttons to submit the changes and cancel
        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");

                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String address = addressTextField.getText();
                    String phone = phoneTextField.getText();
                    String birthDate = birthDateTextField.getText();
                    String hireDate = hireDateTextField.getText();
                    Integer salary = Integer.parseInt(salaryTextField.getText());

                    String updateQuery = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, address = ?, phone = ?, birth_date = ?, hire_date = ?, salary = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, address);
                    preparedStatement.setString(5, phone);
                    preparedStatement.setString(6, birthDate);
                    preparedStatement.setString(7, hireDate);
                    preparedStatement.setInt(8, salary);
                    preparedStatement.setInt(9, employeeId);
                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Les informations de l'employé ont été mises à jour.");
                    dispose();

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        add(modifyButton);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(cancelButton);

        // Set the window to be visible
        setVisible(true);

    }
    public static void main(String[] args) {
        new ModifyEmployeeWindow(1);
    }
}
