package org.example;

import javax.swing.*;
import java.awt.*;

public class AddEmployeeWindow extends JFrame {

    public AddEmployeeWindow() {
        setTitle("Nouvel employé");
        setSize(1376, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel formPanel = new JPanel(new FlowLayout());
        JLabel firstNameLabel = new JLabel("Prénom");
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200, 30));
        JLabel lastNameLabel = new JLabel("Nom");
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200, 30));
        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        JLabel salaryLabel = new JLabel("Salaire");
        JTextField salaryField = new JTextField();
        salaryField.setPreferredSize(new Dimension(200, 30));
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
    }

    public static void main(String[] args) {
        new AddEmployeeWindow();
    }
}
