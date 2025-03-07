package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.phone.dto.CustomerDto;

public class EditCustomerDialog extends JDialog{
	private JTextField nameField, addressField, phoneField;
	private JButton updateButton, deleteButton;
	private int custId;
	private JButton addButton;
	private CustomerManager parent;
	
	  public EditCustomerDialog(CustomerManager parent, int custId) {
		  this.parent = parent;
		  this.custId = custId;
		  setTitle("Edit Customer");
		  setSize(300, 250);
		  setLayout(new BorderLayout());
		  setLocationRelativeTo(parent);
		  
		  CustomerDto customer = parent.detailCustomer(custId);
		  if ( customer == null ) {
			  JOptionPane.showMessageDialog(this,  "Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
			  dispose();
			  return;
		  }
		  
		  JPanel inputPanel = new JPanel();
	        inputPanel.setLayout(new GridLayout(4, 2));
	        
	        nameField = new JTextField(customer.getName());
	        addressField = new JTextField(customer.getAddress());
	        phoneField = new JTextField(customer.getPhone());
	        
	        inputPanel.add(new JLabel("Customer Name"));
	        inputPanel.add(nameField);	     
	        inputPanel.add(new JLabel("Address"));
	        inputPanel.add(addressField);	  
	        inputPanel.add(new JLabel("Phone"));
	        inputPanel.add(phoneField);	  
	        
	        JPanel buttonPanel = new JPanel();
	        updateButton = new JButton("Update");
	        deleteButton = new JButton("Delete");
	        buttonPanel.add(updateButton);
	        buttonPanel.add(deleteButton);

	        add(inputPanel, BorderLayout.CENTER);
	        add(buttonPanel, BorderLayout.SOUTH);
	        
	        updateButton.addActionListener(e -> {
	        	try {
	        		String customerName = nameField.getText();
	        		String address = addressField.getText();
	        		String phone = phoneField.getText();
	        		parent.updateCustomer(new CustomerDto(custId, customerName, address, phone));
	        		dispose();
	        	} catch (Exception ex) {
	        		JOptionPane.showMessageDialog(this, "An error occured while updating", "Error", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        	
	        });
	        
	        deleteButton.addActionListener(e -> {
	        	int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
	        	if ( confirm == JOptionPane.YES_OPTION) {
	        		parent.deleteCustomer(custId);
	        		dispose();
	        	}
	        });

	  }
}
