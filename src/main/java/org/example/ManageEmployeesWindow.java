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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "");
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

        // create a button to add an employee
        JButton addEmployeeButton = new JButton("Ajouter un employé");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployeeWindow();
            }
        });
        add(addEmployeeButton);

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

        // create a button to modify an employee
        JButton modifyEmployeeButton = new JButton("Modifier un employé");
        modifyEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé");
                } else {
                    int employeeId = (int) employeesTableModel.getValueAt(selectedRow, 0);
                    new ModifyEmployeeWindow(employeeId);
                }
            }
            });


        // watch the details of an employee
        JButton seeDetailsButton = new JButton("Voir les détails");
        seeDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeesTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé");
                } else {
                    int employeeId = (int) employeesTableModel.getValueAt(selectedRow, 0);
                    new EmployeeDetailsWindow(employeeId);
                }
            }
        });

    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
