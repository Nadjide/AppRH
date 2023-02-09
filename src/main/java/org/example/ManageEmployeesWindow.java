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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // show all employees in a table
        // add a button to delete an employee
        // add a button to edit an employee

        JButton showEmployeesButton = new JButton("Afficher les employés");
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
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        add(showEmployeesButton, BorderLayout.NORTH);
    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
