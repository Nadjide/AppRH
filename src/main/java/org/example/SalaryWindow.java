package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class SalaryWindow extends JFrame {
    public SalaryWindow() {
        setTitle("Gestion des salaires");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        double medanSalary = calculateMedianSalary();
        JLabel medianSalaryLabel = new JLabel("Le salaire médian est de " + medanSalary + "€");
        add(medianSalaryLabel, BorderLayout.NORTH);

        setVisible(true);

        // Add return button

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton returnButton = new JButton("Retour");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the main window
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            }
        });
        buttonPanel.add(returnButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private double calculateMedianSalary() {

        ArrayList<Double> salaries = new ArrayList<Double>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
            Statement statement = connection.createStatement();
            ResultSet employees = statement.executeQuery("SELECT salary FROM employees");
            while (employees.next()) {
                double salary = employees.getDouble("salary");
                salaries.add(salary);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        Collections.sort(salaries);

        int size = salaries.size();
        if (size % 2 == 0) {
            return (salaries.get(size / 2) + salaries.get(size / 2 - 1)) / 2;
        } else if (size > 0) {
            return salaries.get(size / 2);
        } else {
            return (salaries.get(size / 2 - 1) + salaries.get(size / 2)) / 2;
        }

    }

        public static void main (String[]args){
            new SalaryWindow();
        }
    }


