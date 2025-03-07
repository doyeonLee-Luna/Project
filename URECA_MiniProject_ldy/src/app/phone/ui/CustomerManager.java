package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import app.phone.dao.CustomerDao;
import app.phone.dto.CustomerDto;

public class CustomerManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchField;

    private CustomerDao customerDao = new CustomerDao();

    public CustomerManager() {
    	setLayout(new BorderLayout());


        // 테이블 초기화
        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Name", "Address", "phone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        listCustomer(); // 고객 목록 불러오기

        // 검색 패널
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 28));
        searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Name Search"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // 버튼 패널
        addButton = new JButton("Add");
        editButton = new JButton("Edit & Delete");
        listButton = new JButton("List");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        // 레이아웃 추가
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 이벤트 리스너
        searchButton.addActionListener(e -> {
            String searchWord = searchField.getText();
            if (!searchWord.isBlank()) {
                listCustomer(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddCustomerDialog addDialog = new AddCustomerDialog(this);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int custId = (int) tableModel.getValueAt(selectedRow, 0);
                EditCustomerDialog editDialog = new EditCustomerDialog(this, custId);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
            }
        });

        listButton.addActionListener(e -> listCustomer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        int custId = (int) tableModel.getValueAt(selectedRow, 0);
                        EditCustomerDialog editDialog = new EditCustomerDialog(CustomerManager.this, custId);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listCustomer() {
        clearTable();
        List<CustomerDto> customerList = customerDao.listCustomer();
        for (CustomerDto customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustId(), customer.getName(), customer.getAddress(), customer.getPhone()});
        }
    }

    private void listCustomer(String searchWord) {
        clearTable();
        List<CustomerDto> customerList = customerDao.listCustomer(searchWord);
        for (CustomerDto customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustId(), customer.getName(), customer.getAddress(), customer.getPhone()});
        }
    }

    public void insertCustomer(CustomerDto customer) {
        int ret = customerDao.insertCustomer(customer);
        if (ret == 1) {
            listCustomer();
        }
    }

    public void updateCustomer(CustomerDto customer) {
        int ret = customerDao.updateCustomer(customer);
        if (ret == 1) {
            listCustomer();
        }
    }

    public void deleteCustomer(int custId) {
        int ret = customerDao.deleteCustomer(custId);
        if (ret == 1) {
            listCustomer();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerManager().setVisible(true));
    }

	public CustomerDto detailCustomer(int custId) {
		return customerDao.detailCustomer(custId);
	}
}
