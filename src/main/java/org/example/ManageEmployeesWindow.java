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

    // show all employees in a table
    // add a button to delete an employee
    // add a button to edit an employee

    public ManageEmployeesWindow() {
        setTitle("Gestion des employés");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // show all employees in a table
        // add a button to delete an employee
        // add a button to edit an employee

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteButton = new JButton("Supprimer");
        panel.add(deleteButton);
        JButton editButton = new JButton("Modifier");
        panel.add(editButton);
        JButton showEmployeesButton = new JButton("Afficher les employés");
        panel.add(showEmployeesButton);
        JButton addEmployeeButton = new JButton("Ajouter un employé");
        panel.add(addEmployeeButton);
        add(panel, BorderLayout.SOUTH);


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
    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
