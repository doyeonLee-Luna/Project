package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.phone.dto.MobileDto;

public class AddMobileDialog extends JDialog {
    private JTextField modelNameField, brandField, priceField;
    private JButton addButton;

    public AddMobileDialog(MobileManager parent, DefaultTableModel tableModel) {
        setTitle("Add Mobile");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        modelNameField = new JTextField();
        brandField = new JTextField();
        priceField = new JTextField();

        inputPanel.add(new JLabel("Model Name"));
        inputPanel.add(modelNameField);
        inputPanel.add(new JLabel("Brand"));
        inputPanel.add(brandField);
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        buttonPanel.add(addButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String modelName = modelNameField.getText();
            String brand = brandField.getText();
            double price = Integer.parseInt(priceField.getText());
            parent.insertMobile(new MobileDto(0, brand, modelName, price, 0));
            dispose();
        });
    }
}


