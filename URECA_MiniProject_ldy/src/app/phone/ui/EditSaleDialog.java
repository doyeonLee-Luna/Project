package app.phone.ui;

import javax.swing.*;
import java.awt.*;
import app.phone.dto.SaleDto;

public class EditSaleDialog extends JDialog {
    private JTextField custIdField, mobileIdField, quantityField, totalPriceField, saleDateField;
    private JButton updateButton, deleteButton;
    private int saleId;
    private SaleManager parent;

    public EditSaleDialog(SaleManager parent, int saleId) {
        this.parent = parent;
        this.saleId = saleId;
        setTitle("Edit Sale");
        setSize(350, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        SaleDto sale = parent.detailSale(saleId);
        if (sale == null) {
            JOptionPane.showMessageDialog(this, "Sale not found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        custIdField = new JTextField(String.valueOf(sale.getCustId()));
        mobileIdField = new JTextField(String.valueOf(sale.getMobileId()));
        quantityField = new JTextField(String.valueOf(sale.getQuantity()));
        totalPriceField = new JTextField(String.valueOf(sale.getTotalPrice()));
        saleDateField = new JTextField(sale.getSaleDate());

        inputPanel.add(new JLabel("Customer ID"));
        inputPanel.add(custIdField);
        inputPanel.add(new JLabel("Mobile ID"));
        inputPanel.add(mobileIdField);
        inputPanel.add(new JLabel("Quantity"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Total Price"));
        inputPanel.add(totalPriceField);
        inputPanel.add(new JLabel("Sale Date"));
        inputPanel.add(saleDateField);

        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            try {
                int custId = Integer.parseInt(custIdField.getText());
                int mobileId = Integer.parseInt(mobileIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double totalPrice = Double.parseDouble(totalPriceField.getText());
                String saleDate = saleDateField.getText();
                
                parent.updateSale(new SaleDto(saleId, custId, mobileId, quantity, totalPrice, saleDate));
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                parent.deleteSale(saleId);
                dispose();
            }
        });
    }
}
