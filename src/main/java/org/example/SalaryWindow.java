package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
    private JTable table;
    private JTextField searchField;
    private JButton searchButton;
    private DefaultTableModel tableModel;

    public SalaryWindow() {
        setTitle("Gestion des salaires");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTable();
        createSearchPanel();

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
                dispose();
            }
        });
        buttonPanel.add(returnButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createTable() {
        String[] columnNames = {"ID", "Prénom", "Nom", "Email", "Adresse", "Téléphone", "Date de naissance", "Date d'embauche", "Salaire"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        loadEmployees();
    }

    private void createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> searchEmployees());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
    }


    private void loadEmployees() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
            Statement statement = connection.createStatement();
            ResultSet employees = statement.executeQuery("SELECT * FROM employees");
            while (employees.next()) {
                int id = employees.getInt("id");
                String firstName = employees.getString("first_name");
                String lastName = employees.getString("last_name");
                String email = employees.getString("email");
                String address = employees.getString("address");
                String phone = employees.getString("phone");
                String birthDate = employees.getString("birth_date");
                String hireDate = employees.getString("hire_date");
                double salary = employees.getDouble("salary");
                Object[] rowData = {id, firstName, lastName, email, address, phone, birthDate, hireDate, salary};
                tableModel.addRow(rowData);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void searchEmployees() {
        String searchQuery = searchField.getText().trim();

        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        if (searchQuery.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // Utilisez un RowFilter.regexFilter pour filtrer les résultats
            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchQuery);
            sorter.setRowFilter(rowFilter);
        }
    }

    public static void main(String[] args) {
        new SalaryWindow();
    }
}
