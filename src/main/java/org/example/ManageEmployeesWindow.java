package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageEmployeesWindow extends JFrame{

    // show all employees in a table
    // add a button to delete an employee
    // add a button to edit an employee

    public ManageEmployeesWindow() {
        setTitle("Gestion des employés");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        

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

    }
    public static void main(String[] args) {
        new ManageEmployeesWindow();
    }
}
