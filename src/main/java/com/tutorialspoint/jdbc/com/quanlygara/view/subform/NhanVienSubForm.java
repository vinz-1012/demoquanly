package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.tutorialspoint.jdbc.com.quanlygara.controller.NhanVienController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.NhanVienDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;
import com.tutorialspoint.jdbc.com.quanlygara.util.UIUtils;

public class NhanVienSubForm extends JPanel {
    private final NhanVienController controller = new NhanVienController();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtTenNV;
    private final JTextField txtDienThoai;
    private final JComboBox<String> cbChucVu;
    private final JTextField txtLuong;
    private final JTextField txtSearch;

    public NhanVienSubForm() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create input panel with rounded corners - make it more compact
        UIUtils.RoundedPanel inputPanel = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5)); // Reduced spacing
        inputPanel.setBorder(new EmptyBorder(10, 15, 10, 15)); // Reduced padding
        
        // Add shadow effect
        inputPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(10, 15, 10, 15) // Reduced padding
        ));
        
        // Create title panel
        UIUtils.RoundedPanel titlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(6, 12, 6, 12)); // Reduced padding
        
        JLabel titleLabel = new JLabel("Thông Tin Nhân Viên");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Restored font size
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Style labels
        Font labelFont = new Font("Arial", Font.PLAIN, 12); // Restored font size
        inputPanel.add(createStyledLabel("Tên nhân viên:", labelFont));
        txtTenNV = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219)); // Reduced radius
        inputPanel.add(txtTenNV);
        
        inputPanel.add(createStyledLabel("Điện thoại:", labelFont));
        txtDienThoai = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtDienThoai);
        
        inputPanel.add(createStyledLabel("Chức vụ:", labelFont));
        cbChucVu = new JComboBox<>(new String[]{"Kỹ thuật", "Quản lý"});
        cbChucVu.setFont(new Font("Arial", Font.PLAIN, 12)); // Restored font size
        cbChucVu.setBorder(BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(4, new Color(189, 195, 199), 1), // Reduced radius
            new EmptyBorder(4, 8, 4, 8) // Reduced padding
        ));
        cbChucVu.setBackground(Color.WHITE);
        inputPanel.add(cbChucVu);
        inputPanel.add(createStyledLabel("Lương:", labelFont));
        txtLuong = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtLuong);
        
        inputPanel.add(createStyledLabel("Tìm kiếm:", labelFont));
        txtSearch = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtSearch);

        // Create buttons panel - more compact
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButtons.setBackground(new Color(245, 247, 250));
        panelButtons.setBorder(new EmptyBorder(3, 0, 3, 0)); // Reduced padding
        
        JButton btnAdd = new UIUtils.ModernButton("Thêm", 8, new Color(46, 204, 113), new Color(39, 174, 96), new Color(34, 153, 84)); // Reduced radius
        JButton btnUpdate = new UIUtils.ModernButton("Sửa", 8, new Color(52, 152, 219), new Color(41, 128, 185), new Color(31, 97, 141));
        JButton btnDelete = new UIUtils.ModernButton("Xóa", 8, new Color(231, 76, 60), new Color(192, 57, 43), new Color(168, 31, 18));
        JButton btnSearch = new UIUtils.ModernButton("Tìm kiếm", 8, new Color(241, 196, 15), new Color(243, 156, 18), new Color(230, 126, 34));
        JButton btnRefresh = new UIUtils.ModernButton("Làm mới", 8, new Color(149, 165, 166), new Color(127, 140, 141), new Color(108, 117, 125));
        
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnSearch);
        panelButtons.add(btnRefresh);

        // Create table with rounded panel
        tableModel = new DefaultTableModel(new String[] {"Mã NV", "Tên Nhân Viên", "Điện Thoại", "Chức Vụ", "Lương"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1));

        // Create table title panel
        UIUtils.RoundedPanel tableTitlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        tableTitlePanel.setLayout(new BorderLayout());
        tableTitlePanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel tableTitleLabel = new JLabel("Danh Sách Nhân Viên");
        tableTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tableTitleLabel.setForeground(Color.WHITE);
        tableTitlePanel.add(tableTitleLabel, BorderLayout.CENTER);

        // Layout components - more compact
        JPanel top = new JPanel(new BorderLayout(5, 5)); // Reduced spacing
        top.setBackground(new Color(245, 247, 250));
        
        // Combine title and input panels - more compact
        UIUtils.RoundedPanel inputContainer = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputContainer.setLayout(new BorderLayout(0, 5)); // Reduced spacing
        inputContainer.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(8, 12, 8, 12) // Reduced padding
        ));
        inputContainer.add(titlePanel, BorderLayout.NORTH);
        inputContainer.add(inputPanel, BorderLayout.CENTER);
        
        top.add(inputContainer, BorderLayout.CENTER);
        top.add(panelButtons, BorderLayout.SOUTH);

        // Combine table title and table - more compact
        UIUtils.RoundedPanel tableContainer = new UIUtils.RoundedPanel(15, Color.WHITE);
        tableContainer.setLayout(new BorderLayout(0, 5)); // Reduced spacing
        tableContainer.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(8, 12, 8, 12) // Reduced padding
        ));
        tableContainer.add(tableTitlePanel, BorderLayout.NORTH);
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addNhanVien());
        btnUpdate.addActionListener(e -> updateNhanVien());
        btnDelete.addActionListener(e -> deleteNhanVien());
        btnSearch.addActionListener(e -> searchNhanVien());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<NhanVienDTO> list = controller.getAllNhanVien();
        for (NhanVienDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaNV(), dto.getTenNV(), dto.getDienThoai(), dto.getChucVu(), dto.getLuong()});
        }
    }

    private void addNhanVien() {
        if (txtTenNV.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Ten NV khong duoc de trong.");
            return;
        }
        try {
            BigDecimal luong = new BigDecimal(txtLuong.getText().trim());
            controller.addNhanVien(txtTenNV.getText().trim(), txtDienThoai.getText().trim(), (String) cbChucVu.getSelectedItem(), luong);
            loadData();
            clearFields();
            DataChangeNotifier.notifyDataChanged("NhanVien");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Luong phai la so.");
        }
    }

    private void updateNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can sua.");
            return;
        }
        Integer maNV = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            BigDecimal luong = new BigDecimal(txtLuong.getText().trim());
            controller.updateNhanVien(maNV, txtTenNV.getText().trim(), txtDienThoai.getText().trim(), (String) cbChucVu.getSelectedItem(), luong);
            loadData();
            DataChangeNotifier.notifyDataChanged("NhanVien");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Luong phai la so.");
        }
    }

    private void deleteNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can xoa.");
            return;
        }
        Integer maNV = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.deleteNhanVien(maNV);
        loadData();
        clearFields();
        DataChangeNotifier.notifyDataChanged("NhanVien");
    }

    private void searchNhanVien() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);
        List<NhanVienDTO> list = controller.searchByTenNV(keyword);
        for (NhanVienDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaNV(), dto.getTenNV(), dto.getDienThoai(), dto.getChucVu(), dto.getLuong()});
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtTenNV.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            txtDienThoai.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            cbChucVu.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
            txtLuong.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        }
    }

    private void clearFields() {
        txtTenNV.setText("");
        txtDienThoai.setText("");
        txtLuong.setText("");
    }

    public void refresh() {
        loadData();
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
}
