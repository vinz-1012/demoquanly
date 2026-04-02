package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.jdbc.com.quanlygara.controller.KhachHangController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;
import com.tutorialspoint.jdbc.com.quanlygara.util.UIUtils;

public class KhachHangSubForm extends JPanel {
    private final KhachHangController controller = new KhachHangController();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtTenKH;
    private final JTextField txtDienThoai;
    private final JTextField txtEmail;
    private final JTextField txtDiaChi;
    private final JTextField txtSearch;

    public KhachHangSubForm() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create input panel with rounded corners - make it more compact
        UIUtils.RoundedPanel inputPanel = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        // Add shadow effect
        inputPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        // Create title panel
        UIUtils.RoundedPanel titlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        JLabel titleLabel = new JLabel("Thông Tin Khách Hàng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Style labels
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        inputPanel.add(createStyledLabel("Tên khách hàng:", labelFont));
        txtTenKH = new UIUtils.ModernTextField(6, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtTenKH);
        
        inputPanel.add(createStyledLabel("Điện thoại:", labelFont));
        txtDienThoai = new UIUtils.ModernTextField(6, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtDienThoai);
        
        inputPanel.add(createStyledLabel("Email:", labelFont));
        txtEmail = new UIUtils.ModernTextField(6, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtEmail);
        
        inputPanel.add(createStyledLabel("Địa chỉ:", labelFont));
        txtDiaChi = new UIUtils.ModernTextField(6, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtDiaChi);
        
        inputPanel.add(createStyledLabel("Tìm kiếm:", labelFont));
        txtSearch = new UIUtils.ModernTextField(6, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtSearch);

        // Create buttons panel - make it more compact
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButtons.setBackground(new Color(245, 247, 250));
        panelButtons.setBorder(new EmptyBorder(5, 0, 5, 0));
        
        JButton btnAdd = new UIUtils.ModernButton("Thêm", 10, new Color(46, 204, 113), new Color(39, 174, 96), new Color(34, 153, 84));
        JButton btnUpdate = new UIUtils.ModernButton("Sửa", 10, new Color(52, 152, 219), new Color(41, 128, 185), new Color(31, 97, 141));
        JButton btnDelete = new UIUtils.ModernButton("Xóa", 10, new Color(231, 76, 60), new Color(192, 57, 43), new Color(168, 31, 18));
        JButton btnSearch = new UIUtils.ModernButton("Tìm kiếm", 10, new Color(241, 196, 15), new Color(243, 156, 18), new Color(230, 126, 34));
        JButton btnRefresh = new UIUtils.ModernButton("Làm mới", 10, new Color(149, 165, 166), new Color(127, 140, 141), new Color(108, 117, 125));
        
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnSearch);
        panelButtons.add(btnRefresh);

        // Create table with rounded panel and original height
        tableModel = new DefaultTableModel(new String[] {"Mã KH", "Tên Khách Hàng", "Điện Thoại", "Email", "Địa Chỉ"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25); // Original row height
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1));
        // Remove preferred height to let it use natural height

        // Create table title panel
        UIUtils.RoundedPanel tableTitlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        tableTitlePanel.setLayout(new BorderLayout());
        tableTitlePanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        JLabel tableTitleLabel = new JLabel("Danh Sách Khách Hàng");
        tableTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tableTitleLabel.setForeground(Color.WHITE);
        tableTitlePanel.add(tableTitleLabel, BorderLayout.CENTER);

        // Layout components with better proportions
        JPanel top = new JPanel(new BorderLayout(8, 8)); // Reduced spacing
        top.setBackground(new Color(245, 247, 250));
        
        // Combine title and input panels - make more compact
        UIUtils.RoundedPanel inputContainer = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputContainer.setLayout(new BorderLayout(0, 8)); // Reduced spacing
        inputContainer.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(12, 15, 12, 15) // Reduced padding
        ));
        inputContainer.add(titlePanel, BorderLayout.NORTH);
        inputContainer.add(inputPanel, BorderLayout.CENTER);
        
        top.add(inputContainer, BorderLayout.CENTER);
        top.add(panelButtons, BorderLayout.SOUTH);

        // Combine table title and table with more space for table
        UIUtils.RoundedPanel tableContainer = new UIUtils.RoundedPanel(15, Color.WHITE);
        tableContainer.setLayout(new BorderLayout(0, 8)); // Reduced spacing
        tableContainer.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(12, 15, 12, 15) // Reduced padding
        ));
        tableContainer.add(tableTitlePanel, BorderLayout.NORTH);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // Set weight distribution - give more space to table
        add(top, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addKhachHang());
        btnUpdate.addActionListener(e -> updateKhachHang());
        btnDelete.addActionListener(e -> deleteKhachHang());
        btnSearch.addActionListener(e -> searchKhachHang());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<KhachHangDTO> list = controller.getAllKhachHang();
        for (KhachHangDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaKH(), dto.getTenKH(), dto.getDienThoai(), dto.getEmail(), dto.getDiaChi()});
        }
    }

    private void addKhachHang() {
        if (txtTenKH.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        controller.addKhachHang(txtTenKH.getText().trim(), txtDienThoai.getText().trim(), txtEmail.getText().trim(), txtDiaChi.getText().trim());
        loadData();
        clearFields();
        DataChangeNotifier.notifyDataChanged("KhachHang");
    }

    private void updateKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bản ghi cần sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer maKH = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.updateKhachHang(maKH, txtTenKH.getText().trim(), txtDienThoai.getText().trim(), txtEmail.getText().trim(), txtDiaChi.getText().trim());
        loadData();
        DataChangeNotifier.notifyDataChanged("KhachHang");
    }

    private void deleteKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bản ghi cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer maKH = (Integer) tableModel.getValueAt(selectedRow, 0);
        String tenKH = String.valueOf(tableModel.getValueAt(selectedRow, 1));
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa khách hàng " + tenKH + "?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteKhachHang(maKH);
                loadData();
                clearFields();
                DataChangeNotifier.notifyDataChanged("KhachHang");
                JOptionPane.showMessageDialog(this, "Đã xóa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchKhachHang() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);
        List<KhachHangDTO> list = controller.searchByTenKH(keyword);
        for (KhachHangDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaKH(), dto.getTenKH(), dto.getDienThoai(), dto.getEmail(), dto.getDiaChi()});
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtTenKH.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            txtDienThoai.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            txtEmail.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            txtDiaChi.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        }
    }

    private void clearFields() {
        txtTenKH.setText("");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
    }

    // Helper methods for creating styled components
    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        textField.setBackground(Color.WHITE);
        return textField;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    public void refresh() {
        loadData();
    }
}
