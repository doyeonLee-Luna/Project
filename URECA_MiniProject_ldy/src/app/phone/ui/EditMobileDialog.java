package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.phone.dto.MobileDto;

public class EditMobileDialog extends JDialog {
    private JTextField modelNameField, brandField, priceField, stockField;
    private JButton updateButton, deleteButton;
    private int mobileId;
    private MobileManager parent;

    public EditMobileDialog(MobileManager parent, int mobileId) {
        this.parent = parent;
        this.mobileId = mobileId;
        setTitle("Edit Mobile");
        setSize(300, 250);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        MobileDto mobile = parent.detailMobile(mobileId);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        modelNameField = new JTextField(mobile.getModel());
        brandField = new JTextField(mobile.getBrand());
        priceField = new JTextField(String.valueOf(mobile.getPrice()));
        stockField = new JTextField(String.valueOf(mobile.getStock()));

        inputPanel.add(new JLabel("Model Name"));
        inputPanel.add(modelNameField);
        inputPanel.add(new JLabel("Brand"));
        inputPanel.add(brandField);
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Stock"));
        inputPanel.add(stockField);

        JPanel buttonPanel = new JPanel();
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            try {
                String modelName = modelNameField.getText();
                String brand = brandField.getText();
                double price = Integer.parseInt(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                parent.updateMobile(new MobileDto(mobileId, brand, modelName, price, stock));
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price and stock must be entered as numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                parent.deleteMobile(mobileId);
                dispose();
            }
        });
    }
}
