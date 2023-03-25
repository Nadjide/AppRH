package org.example;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
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
        employeesTableModel.addColumn("Salaire");
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
                Integer salary = employees.getInt("salary");
                employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate, salary});
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
                        Integer salary = employees.getInt("salary");
                        employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate, salary});
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
                    Object[] employeeDetails = new Object[9];
                    for (int i = 0; i < 9; i++) {
                        employeeDetails[i] = employeesTableModel.getValueAt(selectedRow, i);
                    }
                    String detailsMessage = String.format("ID: %s %nPrénom: %s %nNom: %s %nEmail: %s %nAdresse: %s %nTéléphone: %s %nDate de naissance: %s %nDate d'embauche: %s %nSalaire: %s",
                            employeeDetails[0], employeeDetails[1], employeeDetails[2], employeeDetails[3],
                            employeeDetails[4], employeeDetails[5], employeeDetails[6], employeeDetails[7], employeeDetails[8]);
                    JOptionPane.showMessageDialog(null, detailsMessage, "Détails de l'employé", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        add(showDetailsButton);


        // add button to generate PDF file of employees selected
        JButton generatePDFButton = new JButton("Générer PDF");
        generatePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = employeesTable.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner au moins un employé");
                } else {
                    try {
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream("employees.pdf"));
                        document.open();
                        document.add(new Paragraph("Détails de l'employé"));
                        document.add(new Paragraph(" "));
                        PdfPTable table = new PdfPTable(9);
                        table.addCell("ID");
                        table.addCell("Prénom");
                        table.addCell("Nom");
                        table.addCell("Email");
                        table.addCell("Adresse");
                        table.addCell("Téléphone");
                        table.addCell("Date de naissance");
                        table.addCell("Date d'embauche");
                        table.addCell("Salaire");
                        for (int selectedRow : selectedRows) {
                            for (int j = 0; j < 9; j++) {
                                table.addCell(employeesTableModel.getValueAt(selectedRow, j).toString());
                            }
                        }
                        document.add(table);
                        document.close();
                        JOptionPane.showMessageDialog(null, "Le fichier PDF a été généré avec succès");
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });
        add(generatePDFButton);

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
                        Integer salary = employees.getInt("salary");
                        employeesTableModel.addRow(new Object[]{id, firstName, lastName, email, address, phone, birthDate, hireDate, salary});
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
