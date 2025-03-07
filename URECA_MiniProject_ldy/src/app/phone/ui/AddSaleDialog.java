package app.phone.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import app.phone.dto.SaleDto;

public class AddSaleDialog extends JDialog {
    private JTextField custIdField, mobileIdField, quantityField, totalPriceField, saleDateField;
    private JButton addButton;
    private SaleManager parent;
    private DefaultTableModel tableModel;

    public AddSaleDialog(SaleManager parent, DefaultTableModel tableModel) { 
        this.parent = parent;
        this.tableModel = tableModel; // 테이블 모델 저장
        setTitle("Add Sale");
        setSize(350, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        custIdField = new JTextField();
        mobileIdField = new JTextField();
        quantityField = new JTextField();
        totalPriceField = new JTextField();
        saleDateField = new JTextField();

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
        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                int custId = Integer.parseInt(custIdField.getText());
                int mobileId = Integer.parseInt(mobileIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double totalPrice = Double.parseDouble(totalPriceField.getText());
                String saleDate = saleDateField.getText();

                SaleDto newSale = new SaleDto(0, custId, mobileId, quantity, totalPrice, saleDate);
                parent.insertSale(newSale);

                // 테이블에 추가
                tableModel.addRow(new Object[]{0, custId, mobileId, quantity, totalPrice, saleDate});

                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Customer ID, Mobile ID, Quantity, and Total Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
