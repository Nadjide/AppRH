package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
        JLabel adressLabel = new JLabel("Adresse");
        JTextField addressField = new JTextField();
        addressField.setMaximumSize(new Dimension(400, 50));
        JLabel phoneLabel = new JLabel("Téléphone");
        JTextField phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(400, 50));
        JLabel birthDateLabel = new JLabel("Date de naissance");
        JTextField birthDateField = new JTextField();
        birthDateField.setMaximumSize(new Dimension(400, 50));
        JLabel hireDateLabel = new JLabel("Date d'embauche");
        JTextField hireDateField = new JTextField();
        hireDateField.setMaximumSize(new Dimension(400, 50));
        JLabel salaryLabel = new JLabel("Salaire");
        JTextField salaryField = new JTextField();
        salaryField.setMaximumSize(new Dimension(400, 50));


        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(adressLabel);
        formPanel.add(addressField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(birthDateLabel);
        formPanel.add(birthDateField);
        formPanel.add(hireDateLabel);
        formPanel.add(hireDateField);
        formPanel.add(salaryLabel);
        formPanel.add(salaryField);
        add(formPanel);

        JButton submitButton = new JButton("Ajouter");

        submitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                insertData(firstNameField.getText(), lastNameField.getText(),
                        emailField.getText(), addressField.getText(), phoneField.getText(),birthDateField.getText(), hireDateField.getText(), salaryField.getText());
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
        formPanel.add(returnButton);
    }



    private void insertData(String firstName, String lastName, String email,String addressField, String phoneFieldText, String birthDateFieldText, String hireDateFieldText, String salaryFieldText) {
        String url = "jdbc:mysql://localhost:3306/testJava";
        String username = "root";
        String password = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO employees (first_name, last_name, email, address, phone, birth_date, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, addressField);
            preparedStatement.setString(5, phoneFieldText);
            preparedStatement.setString(6, birthDateFieldText);
            preparedStatement.setString(7, hireDateFieldText);
            preparedStatement.setString(8, salaryFieldText);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "L'employé a été ajouté avec succès");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddEmployeeWindow();
    }
}
