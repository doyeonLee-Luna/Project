package app.phone.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainUI extends JFrame {
    public MainUI() {
        setTitle("📊 Mobile & Customer Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Mobile 관리 탭 추가	
        tabbedPane.addTab("📱 Mobile", new MobileManager());

        // Customer 관리 탭 추가
        tabbedPane.addTab("👥 Customer", new CustomerManager());
        
        // Sale 관리 탭 추가
        tabbedPane.addTab("💰 Sale", new SaleManager());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}