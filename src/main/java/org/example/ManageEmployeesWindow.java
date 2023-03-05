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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // show all employees in a table
        // add a button to delete an employee
        // add a button to edit an employee
        // add a button to search for an employee
        // add a button to return to the main window

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteButton = new JButton("Supprimer");
        panel.add(deleteButton);
        JButton editButton = new JButton("Modifier");
        panel.add(editButton);
        JButton showEmployeesButton = new JButton("Afficher les employés");
        panel.add(showEmployeesButton);
        JButton searchButton = new JButton("Rechercher");
        panel.add(searchButton);
        JButton addEmployeeButton = new JButton("Ajouter un employé");
        panel.add(addEmployeeButton);
        JButton returnButton = new JButton("Retour");
        panel.add(returnButton);
        add(panel, BorderLayout.SOUTH);

        // add a button to return to the main window
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // return to the main window
                dispose();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // search for an employee
                String search = JOptionPane.showInputDialog("Rechercher un employé");
                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM employees WHERE first_Name = '" + search + "' OR last_Name = '" + search + "' OR email = '" + search + "'");

                    // create a table to display the employees
                    DefaultTableModel model = new DefaultTableModel();
                    model.setColumnIdentifiers(new String[]{"ID", "Nom", "Prénom", "Email", "Salaire"});

                    while(resultSet.next()){
                        model.addRow(new Object[]{resultSet.getInt("id"), resultSet.getString("first_Name"),
                                resultSet.getString("last_Name"), resultSet.getString("email"), resultSet.getInt("salary")});
                    }

                    //create a table to display the employees
                    JTable table = new JTable();
                    table.setModel(model);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);
                    add(new JScrollPane(table), BorderLayout.CENTER);
                    setVisible(true);

                    // add a button to edit an employee

                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // edit the selected employee
                            int row = table.getSelectedRow();
                            int id = (int) table.getValueAt(row, 0);
                            String firstName = (String) table.getValueAt(row, 1);
                            String lastName = (String) table.getValueAt(row, 2);
                            String email = (String) table.getValueAt(row, 3);
                            int salary = (int) table.getValueAt(row, 4);
                            try{
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                                Statement statement = connection.createStatement();
                                statement.executeUpdate("UPDATE employees SET first_Name = '" + firstName + "', last_Name = '" + lastName + "', email = '" + email + "', salary = " + salary + " WHERE id = " + id);
                                model.removeRow(row);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                    // add a button to delete an employee
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // delete the selected employee
                            int row = table.getSelectedRow();
                            int id = (int) table.getValueAt(row, 0);
                            try{
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                                Statement statement = connection.createStatement();
                                statement.executeUpdate("DELETE FROM employees WHERE id = " + id);
                                model.removeRow(row);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });


        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the add employee window
                AddEmployeeWindow addEmployeeWindow = new AddEmployeeWindow();
                addEmployeeWindow.setVisible(true);
            }
        });

        showEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // display all employees in a table

                try{
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

                    // create a table to display the employees
                    DefaultTableModel model = new DefaultTableModel();
                    model.setColumnIdentifiers(new String[]{"ID", "Nom", "Prénom", "Email", "Salaire"});

                    while(resultSet.next()){
                        model.addRow(new Object[]{resultSet.getInt("id"), resultSet.getString("first_Name"),
                                resultSet.getString("last_Name"), resultSet.getString("email"), resultSet.getInt("salary")});
                    }

                    //create a table to display the employees
                    JTable table = new JTable();
                    table.setModel(model);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);
                    add(new JScrollPane(table), BorderLayout.CENTER);
                    setVisible(true);

                    // add a button to edit an employee

                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // edit the selected employee
                            int row = table.getSelectedRow();
                            int id = (int) table.getValueAt(row, 0);
                            String firstName = (String) table.getValueAt(row, 1);
                            String lastName = (String) table.getValueAt(row, 2);
                            String email = (String) table.getValueAt(row, 3);
                            int salary = (int) table.getValueAt(row, 4);
                            try{
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                                Statement statement = connection.createStatement();
                                statement.executeUpdate("UPDATE employees SET first_Name = '" + firstName + "', last_Name = '" + lastName + "', email = '" + email + "', salary = " + salary + " WHERE id = " + id);
                                model.removeRow(row);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                    // add a button to delete an employee
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // delete the selected employee
                            int row = table.getSelectedRow();
                            int id = (int) table.getValueAt(row, 0);
                            try{
                                // alert the user that the employee has been deleted
                                // validate the deletion
                                int dialogResult = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet employé?", "Attention", JOptionPane.YES_NO_OPTION);
                                if(dialogResult == JOptionPane.YES_OPTION){
                                    // delete the employee
                                    try{
                                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testJava", "root", "");
                                        Statement statement = connection.createStatement();
                                        statement.executeUpdate("DELETE FROM employees WHERE id = " + id);
                                        JOptionPane.showMessageDialog(null, "L'employé a été supprimé avec succès");
                                        model.removeRow(row);
                                    }
                                    catch(Exception ex){
                                        ex.printStackTrace();
                                    }
                                }
                                model.removeRow(row);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
