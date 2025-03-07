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

import app.phone.dao.MobileDao;
import app.phone.dto.MobileDto;

public class MobileManager extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton searchButton, addButton, editButton, listButton;
    private JTextField searchField;
    
    private MobileDao mobileDao = new MobileDao();

    public MobileManager() {
    	setLayout(new BorderLayout());
    	
    	JLabel label = new JLabel("📱 Mobile Management");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);


        // Table
        tableModel = new DefaultTableModel(new Object[]{"Mobile ID", "Brand", "Model Name", "Price", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        
        listMobile();

        // Search Panel
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(400, 28));
        
        searchButton = new JButton("Search");
        
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search Model Name"));
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
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        searchButton.addActionListener(e -> {
            String searchWord = searchField.getText();
            if (!searchWord.isBlank()) {
                listMobile(searchWord);
            }
        });

        addButton.addActionListener(e -> {
            AddMobileDialog addDialog = new AddMobileDialog(this, tableModel);
            addDialog.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // 선택된 행 가져오기
            if (selectedRow >= 0) {
                int mobileId = (int) tableModel.getValueAt(selectedRow, 0); // 첫 번째 컬럼 (ID) 값 가져오기
                EditMobileDialog editDialog = new EditMobileDialog(this, mobileId);
                editDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a mobile to edit.");
            }
        });

        listButton.addActionListener(e -> listMobile());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // 더블 클릭 이벤트
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        int mobileId = (int) tableModel.getValueAt(selectedRow, 0); // 첫 번째 열에서 ID 가져오기
                        EditMobileDialog editDialog = new EditMobileDialog(MobileManager.this, mobileId);
                        editDialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }

    private void listMobile() {
        clearTable();
        List<MobileDto> mobileList = mobileDao.listMobile();
        for (MobileDto mobile : mobileList) {
            tableModel.addRow(new Object[]{mobile.getMobileId(), mobile.getBrand(), mobile.getModel(), mobile.getPrice(), mobile.getStock()});
        }
    }

    private void listMobile(String searchWord) {
        clearTable();
        List<MobileDto> mobileList = mobileDao.listMobile(searchWord);
        for (MobileDto mobile : mobileList) {
            tableModel.addRow(new Object[]{mobile.getMobileId(), mobile.getBrand(), mobile.getModel(), mobile.getPrice(), mobile.getStock()});
        }
    }

    public MobileDto detailMobile(int mobileId) {
        return mobileDao.detailMobile(mobileId);
    }

    public void insertMobile(MobileDto mobile) {
        int ret = mobileDao.insertMobile(mobile);
        if (ret == 1) {
            listMobile();
        }
    }

    public void updateMobile(MobileDto mobile) {
        int ret = mobileDao.updateMobile(mobile);
        if (ret == 1) {
            listMobile();
        }
    }

    public void deleteMobile(int mobileId) {
        int ret = mobileDao.deleteMobile(mobileId);
        if (ret == 1) {
            listMobile();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MobileManager().setVisible(true));
    }
}
