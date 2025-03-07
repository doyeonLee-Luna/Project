package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import app.phone.dao.SaleDao;
import app.phone.dto.SaleDto;

public class SaleManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchField;
    
    private SaleDao saleDao = new SaleDao();

    public SaleManager() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("🛒 Sales Management");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Sale ID", "Customer ID", "Mobile ID", "Quantity", "Total Price", "Sale Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        
        listSales(); // 초기 데이터 로딩

        // Search Panel
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 28));

        searchButton = new JButton("Search");
        
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by Sale Date"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Button Panel
        addButton = new JButton("Add");
        editButton = new JButton("Edit & Delete");
        listButton = new JButton("List");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(listButton);

        // Layout
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        searchButton.addActionListener(e -> {
            String searchWord = searchField.getText();
            if (!searchWord.isBlank()) {
                listSales(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddSaleDialog addDialog = new AddSaleDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // 선택된 행 가져오기
            if (selectedRow >= 0) {
                int saleId = (int) tableModel.getValueAt(selectedRow, 0); // 첫 번째 컬럼 (ID) 값 가져오기
                EditSaleDialog editDialog = new EditSaleDialog(this, saleId);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a sale to edit.");
            }
        });

        listButton.addActionListener(e -> listSales());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 더블 클릭 이벤트
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        int saleId = (int) tableModel.getValueAt(selectedRow, 0); // 첫 번째 열에서 ID 가져오기
                        EditSaleDialog editDialog = new EditSaleDialog(SaleManager.this, saleId);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listSales() {
        clearTable();
        List<SaleDto> saleList = saleDao.listSales();
        for (SaleDto sale : saleList) {
            tableModel.addRow(new Object[]{sale.getSaleId(), sale.getCustId(), sale.getMobileId(), sale.getQuantity(), sale.getTotalPrice(), sale.getSaleDate()});
        }
    }

    private void listSales(String searchWord) {
        clearTable();
        List<SaleDto> saleList = saleDao.listSales(searchWord);
        for (SaleDto sale : saleList) {
            tableModel.addRow(new Object[]{sale.getSaleId(), sale.getCustId(), sale.getMobileId(), sale.getQuantity(), sale.getTotalPrice(), sale.getSaleDate()});
        }
    }

    public SaleDto detailSale(int saleId) {
        return saleDao.detailSale(saleId);
    }

    public void insertSale(SaleDto sale) {
        int ret = saleDao.insertSale(sale);
        if (ret == 1) {
            listSales();
        }
    }

    public void updateSale(SaleDto sale) {
        int ret = saleDao.updateSale(sale);
        if (ret == 1) {
            listSales();
        }
    }

    public void deleteSale(int saleId) {
        int ret = saleDao.deleteSale(saleId);
        if (ret == 1) {
            listSales();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaleManager().setVisible(true));
    }
}
