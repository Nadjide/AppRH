package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{

    public MainWindow() {
        setTitle("Tableau de bord");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add the welcome message
        JPanel welcomePanel = new JPanel(new FlowLayout());
        JLabel welcomeLabel = new JLabel("Bienvenue dans l'application de gestion des employés");
        welcomePanel.add(welcomeLabel);
        add(welcomePanel, BorderLayout.NORTH);

        // Add a short description of the company
        JPanel descriptionPanel = new JPanel(new FlowLayout());
        JLabel descriptionLabel = new JLabel("Cette application vous permet de gérer les employés de votre entreprise");
        descriptionPanel.add(descriptionLabel);
        add(descriptionPanel, BorderLayout.CENTER);



        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addEmployeeButton = new JButton("Nouvel employé");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the add employee window
                AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow();
                addEmployeeWindow.setVisible(true);
            }
        });
        buttonPanel.add(addEmployeeButton);

        JButton manageEmployeesButton = new JButton("Gestion des employés");
        manageEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the manage employees window
                ManageEmployeesWindow manageEmployeesWindow = new ManageEmployeesWindow();
                manageEmployeesWindow.setVisible(true);
            }
        });
        buttonPanel.add(manageEmployeesButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
