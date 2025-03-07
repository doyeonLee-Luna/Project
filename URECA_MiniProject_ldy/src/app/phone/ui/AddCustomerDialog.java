package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.phone.dto.CustomerDto;

public class AddCustomerDialog extends JDialog {
    private JTextField nameField, addressField, phoneField;
    private JButton addButton;
    private CustomerManager parent;

    public AddCustomerDialog(CustomerManager parent) {
        this.parent = parent;
        setTitle("Add Customer");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Address"));
        addressField = new JTextField();
        inputPanel.add(addressField);
        
        inputPanel.add(new JLabel("Phone"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);
        
        addButton = new JButton("Add");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        addButton = new JButton("Add");
        buttonPanel.add(addButton);
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            
            parent.insertCustomer(new CustomerDto(0, name, address, phone));
            dispose();
        });
        
       
    }
}