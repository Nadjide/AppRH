package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageEmployeesWindow extends JFrame{

    public ManageEmployeesWindow() {
        setTitle("Gestion des employés");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // see all the employees in my database

        // Create the table
        JTable employeesTable = new JTable();
        DefaultTableModel employeesTableModel = new DefaultTableModel();
        employeesTableModel.addColumn("ID");
        employeesTableModel.addColumn("Prénom");
        employeesTableModel.addColumn("Nom");
        employeesTableModel.addColumn("Email");
        employeesTableModel.addColumn("Adresse");
        employeesTableModel.addColumn("Téléphone");
        employeesTableModel.addColumn("Date de naissance");
        employeesTableModel.addColumn("Date d'embauche");
        employeesTable.setModel(employeesTableModel);
        JScrollPane employeesTableScrollPane = new JScrollPane(employeesTable);
        employeesTableScrollPane.setPreferredSize(new Dimension(550, 200));
        add(employeesTableScrollPane);

        // create a table in the window
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
                employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate});
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // create a button redirecting to the add employee window
        JButton addEmployeeButton = new JButton("Ajouter un employé");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow();
                addEmployeeWindow.setVisible(true);
            }
        });
        add(addEmployeeButton);


        // create a button to refresh the table
        JButton refreshTableButton = new JButton("Rafraîchir la table");
        refreshTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeesTableModel.setRowCount(0);
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
                        employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate});
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        add(refreshTableButton);

        // create a button to delete an employee
        JButton deleteEmployeeButton = new JButton("Supprimer un employé");
        deleteEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé");
                } else {
                    int employeeId = (int) employeesTableModel.getValueAt(selectedRow, 0);
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cet employé ?");
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
                            Statement statement = connection.createStatement();
                            statement.executeUpdate("DELETE FROM employees WHERE id = " + employeeId);
                            employeesTableModel.removeRow(selectedRow);
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                        }
                    }
                }
            }
        });
        add(deleteEmployeeButton);

        // create a button open the ModifyEmployeeWindow window
        JButton modifyEmployeeButton = new JButton("Modifier un employé");
        modifyEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé");
                } else {
                    int employeeId = (int) employeesTableModel.getValueAt(selectedRow, 0);
                    ModifyEmployeeWindow modifyEmployeeWindow = new ModifyEmployeeWindow(employeeId);
                    modifyEmployeeWindow.setVisible(true);
                }
            }
        });
        add(modifyEmployeeButton);

        // create a return button
        JButton returnButton = new JButton("Retour");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(returnButton);

        // Details of employees
        JButton showDetailsButton = new JButton("Détails");
        showDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé");
                } else {
                    Object[] employeeDetails = new Object[8];
                    for (int i = 0; i < 8; i++) {
                        employeeDetails[i] = employeesTableModel.getValueAt(selectedRow, i);
                    }
                    String detailsMessage = String.format("ID: %d\nPrénom: %s\nNom: %s\nEmail: %s\nAdresse: %s\nTéléphone: %s\nDate de naissance: %s\nDate d'embauche: %s",
                            employeeDetails[0], employeeDetails[1], employeeDetails[2], employeeDetails[3],
                            employeeDetails[4], employeeDetails[5], employeeDetails[6], employeeDetails[7]);
                    JOptionPane.showMessageDialog(null, detailsMessage, "Détails de l'employé", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        add(showDetailsButton);

        // create a button to search an employee
        JButton searchEmployeeButton = new JButton("Rechercher un employé");
        searchEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Entrez le NOM de l'employé à rechercher");
                employeesTableModel.setRowCount(0);
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava", "root", "");
                    Statement statement = connection.createStatement();
                    // recherche par le nom
                    ResultSet employees = statement.executeQuery("SELECT * FROM employees WHERE last_name LIKE '%" + search + "%'");
                    while (employees.next()) {
                        int id = employees.getInt("id");
                        String firstName = employees.getString("first_name");
                        String lastName = employees.getString("last_name");
                        String email = employees.getString("email");
                        String address = employees.getString("address");
                        String phone = employees.getString("phone");
                        String birthDate = employees.getString("birth_date");
                        String hireDate = employees.getString("hire_date");
                        employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate});
                    }
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
        add(searchEmployeeButton);

    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
