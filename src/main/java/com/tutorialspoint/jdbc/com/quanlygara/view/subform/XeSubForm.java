package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.jdbc.com.quanlygara.controller.HieuXeController;
import com.tutorialspoint.jdbc.com.quanlygara.controller.KhachHangController;
import com.tutorialspoint.jdbc.com.quanlygara.controller.XeController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.HieuXeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.XeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;
import com.tutorialspoint.jdbc.com.quanlygara.util.UIUtils;

public class XeSubForm extends JPanel {
    private final XeController controller = new XeController();
    private final KhachHangController khController = new KhachHangController();
    private final HieuXeController hxController = new HieuXeController();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtBienSo;
    private final JTextField txtMauXe;
    private final JTextField txtNamSanXuat;
    private final JComboBox<KhachHangDTO> cbKhachHang;
    private final JComboBox<HieuXeDTO> cbHieuXe;
    private final JTextField txtSearch;
    private final JLabel carImageLabel;

    public XeSubForm() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create input panel with rounded corners - make it more compact
        UIUtils.RoundedPanel inputPanel = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5)); // Reduced spacing
        inputPanel.setBorder(new EmptyBorder(10, 15, 10, 15)); // Reduced padding
        
        // Add shadow effect
        inputPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(10, 15, 10, 15) // Reduced padding
        ));
        
        // Create title panel
        UIUtils.RoundedPanel titlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        JLabel titleLabel = new JLabel("Thông Tin Xe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Style labels
        Font labelFont = new Font("Arial", Font.PLAIN, 12); // Restored font size
        inputPanel.add(createStyledLabel("Biển số:", labelFont));
        txtBienSo = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219)); // Reduced radius
        inputPanel.add(txtBienSo);
        
        inputPanel.add(createStyledLabel("Màu xe:", labelFont));
        txtMauXe = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtMauXe);
        
        inputPanel.add(createStyledLabel("Năm sản xuất:", labelFont));
        txtNamSanXuat = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219));
        inputPanel.add(txtNamSanXuat);
        
        inputPanel.add(createStyledLabel("Khách hàng:", labelFont));
        cbKhachHang = new JComboBox<>();
        cbKhachHang.setFont(new Font("Arial", Font.PLAIN, 12)); // Restored font size
        cbKhachHang.setBorder(BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(4, new Color(189, 195, 199), 1), // Reduced radius
            new EmptyBorder(4, 8, 4, 8) // Reduced padding
        ));
        cbKhachHang.setBackground(Color.WHITE);
        inputPanel.add(cbKhachHang);
        
        inputPanel.add(createStyledLabel("Hiệu xe:", labelFont));
        cbHieuXe = new JComboBox<>();
        cbHieuXe.setFont(new Font("Arial", Font.PLAIN, 12)); // Restored font size
        cbHieuXe.setBorder(BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(4, new Color(189, 195, 199), 1), // Reduced radius
            new EmptyBorder(4, 8, 4, 8) // Reduced padding
        ));
        cbHieuXe.setBackground(Color.WHITE);
        inputPanel.add(cbHieuXe);
        
        inputPanel.add(createStyledLabel("Tìm kiếm:", labelFont));
        txtSearch = new UIUtils.ModernTextField(4, new Color(189, 195, 199), new Color(52, 152, 219)); // Reduced radius
        inputPanel.add(txtSearch);

        // Create image panel with rounded corners - reduced size
        UIUtils.RoundedPanel imagePanel = new UIUtils.RoundedPanel(15, Color.WHITE);
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10) // Reduced padding
        ));
        
        // Create image title panel
        UIUtils.RoundedPanel imageTitlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        imageTitlePanel.setLayout(new BorderLayout());
        imageTitlePanel.setBorder(new EmptyBorder(6, 12, 6, 12)); // Reduced padding
        
        JLabel imageTitleLabel = new JLabel("Hình Ảnh Hiệu Xe");
        imageTitleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Restored font size
        imageTitleLabel.setForeground(Color.WHITE);
        imageTitlePanel.add(imageTitleLabel, BorderLayout.CENTER);
        
        carImageLabel = new JLabel("", SwingConstants.CENTER);
        carImageLabel.setPreferredSize(new java.awt.Dimension(250, 100)); // Further reduced size
        carImageLabel.setBorder(new UIUtils.RoundedBorder(8, new Color(189, 195, 199), 1)); // Reduced radius
        carImageLabel.setBackground(Color.WHITE);
        carImageLabel.setOpaque(true);
        
        // Add placeholder text
        carImageLabel.setText("Chọn hiệu xe");
        carImageLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Restored font size
        carImageLabel.setForeground(Color.GRAY);
        
        imagePanel.add(imageTitlePanel, BorderLayout.NORTH);
        imagePanel.add(carImageLabel, BorderLayout.CENTER);

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

        // Create table with rounded panel and original height
        tableModel = new DefaultTableModel(new String[] {"Mã Xe", "Biển Số", "Màu Xe", "Năm SX", "Tên KH", "Hiệu Xe"}, 0) {
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
        scrollPane.setPreferredSize(new java.awt.Dimension(0, 1000)); // Increased height for better visibility

        // Create table title panel
        UIUtils.RoundedPanel tableTitlePanel = new UIUtils.RoundedPanel(10, new Color(41, 128, 185));
        tableTitlePanel.setLayout(new BorderLayout());
        tableTitlePanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        JLabel tableTitleLabel = new JLabel("Danh Sách Xe");
        tableTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tableTitleLabel.setForeground(Color.WHITE);
        tableTitlePanel.add(tableTitleLabel, BorderLayout.CENTER);

        // Layout components with better proportions - more compact
        JPanel top = new JPanel(new BorderLayout(5, 5)); // Reduced spacing
        top.setBackground(new Color(245, 247, 250));
        
        // Combine input and image panels horizontally - more compact
        UIUtils.RoundedPanel inputContainer = new UIUtils.RoundedPanel(15, Color.WHITE);
        inputContainer.setLayout(new BorderLayout(0, 5)); // Reduced spacing
        inputContainer.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1),
            new EmptyBorder(8, 12, 8, 12) // Reduced padding
        ));
        inputContainer.add(titlePanel, BorderLayout.NORTH);
        inputContainer.add(inputPanel, BorderLayout.CENTER);
        
        // Create a horizontal panel for input and image - adjust proportions - more compact
        JPanel topContent = new JPanel(new BorderLayout(10, 0)); // Reduced spacing
        topContent.setBackground(new Color(245, 247, 250));
        topContent.add(inputContainer, BorderLayout.CENTER);
        topContent.add(imagePanel, BorderLayout.EAST);
        
        top.add(topContent, BorderLayout.CENTER);
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

        btnAdd.addActionListener(e -> addXe());
        btnUpdate.addActionListener(e -> updateXe());
        btnDelete.addActionListener(e -> deleteXe());
        btnSearch.addActionListener(e -> searchXe());
        btnRefresh.addActionListener(e -> loadData());

        // Add event listener for car brand combobox
        cbHieuXe.addActionListener(e -> updateCarImage());

        loadCombos();
        loadData();
    }

    private void loadCombos() {
        cbKhachHang.removeAllItems();
        List<KhachHangDTO> khs = khController.getAllKhachHang();
        for (KhachHangDTO kh : khs) {
            cbKhachHang.addItem(kh);
        }

        cbHieuXe.removeAllItems();
        List<HieuXeDTO> hxs = hxController.getAllHieuXe();
        for (HieuXeDTO hx : hxs) {
            cbHieuXe.addItem(hx);
        }
    }

    private void loadData() {
        System.out.println("Loading data for xe table...");
        tableModel.setRowCount(0);
        List<XeDTO> list = controller.getAllXe();
        System.out.println("Found " + list.size() + " xe records");
        for (XeDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaXe(), dto.getBienSo(), dto.getMauXe(), dto.getNamSanXuat(), dto.getTenKH(), dto.getTenHieuXe()});
        }
        System.out.println("Table now has " + tableModel.getRowCount() + " rows");
        
        // Force UI refresh
        table.revalidate();
        table.repaint();
    }

    private void addXe() {
        if (txtBienSo.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Bien so khong duoc de trong.");
            return;
        }
        try {
            Integer namSX = Integer.parseInt(txtNamSanXuat.getText().trim());
            KhachHangDTO kh = (KhachHangDTO) cbKhachHang.getSelectedItem();
            HieuXeDTO hx = (HieuXeDTO) cbHieuXe.getSelectedItem();
            controller.addXe(txtBienSo.getText().trim(), txtMauXe.getText().trim(), namSX, kh.getMaKH(), hx.getMaHieuXe());
            loadData();
            clearFields();
            DataChangeNotifier.notifyDataChanged("Xe");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nam san xuat phai la so.");
        }
    }

    private void updateXe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can sua.");
            return;
        }
        Integer maXe = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            Integer namSX = Integer.parseInt(txtNamSanXuat.getText().trim());
            KhachHangDTO kh = (KhachHangDTO) cbKhachHang.getSelectedItem();
            HieuXeDTO hx = (HieuXeDTO) cbHieuXe.getSelectedItem();
            controller.updateXe(maXe, txtBienSo.getText().trim(), txtMauXe.getText().trim(), namSX, kh.getMaKH(), hx.getMaHieuXe());
            loadData();
            DataChangeNotifier.notifyDataChanged("Xe");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nam san xuat phai la so.");
        }
    }

    private void deleteXe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bản ghi cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer maXe = (Integer) tableModel.getValueAt(selectedRow, 0);
        String bienSo = String.valueOf(tableModel.getValueAt(selectedRow, 1));
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa xe " + bienSo + "?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Attempting to delete xe with maXe: " + maXe + ", bienSo: " + bienSo);
                controller.deleteXe(maXe);
                System.out.println("Delete operation completed successfully");
                
                // Clear table selection before reload
                table.clearSelection();
                
                // Reload data
                loadData();
                
                // Force UI refresh
                table.revalidate();
                table.repaint();
                
                // Clear form fields
                clearFields();
                
                // Notify data change
                DataChangeNotifier.notifyDataChanged("Xe");
                
                JOptionPane.showMessageDialog(this, "Đã xóa xe thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (RuntimeException e) {
                System.err.println("Error deleting xe: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchXe() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);
        List<XeDTO> list = controller.searchByBienSo(keyword);
        for (XeDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaXe(), dto.getBienSo(), dto.getMauXe(), dto.getNamSanXuat(), dto.getTenKH(), dto.getTenHieuXe()});
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtBienSo.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            txtMauXe.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            txtNamSanXuat.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            String tenKH = String.valueOf(tableModel.getValueAt(selectedRow, 4));
            String tenHieuXe = String.valueOf(tableModel.getValueAt(selectedRow, 5));
            
            // Set customer combo box
            for (int i = 0; i < cbKhachHang.getItemCount(); i++) {
                KhachHangDTO kh = cbKhachHang.getItemAt(i);
                if (kh.getTenKH().equals(tenKH)) {
                    cbKhachHang.setSelectedItem(kh);
                    break;
                }
            }
            
            // Set car brand combo box and update image
            for (int i = 0; i < cbHieuXe.getItemCount(); i++) {
                HieuXeDTO hx = cbHieuXe.getItemAt(i);
                if (hx.getTenHieuXe().equals(tenHieuXe)) {
                    cbHieuXe.setSelectedItem(hx);
                    break;
                }
            }
            
            // Update car image after setting the combobox
            SwingUtilities.invokeLater(() -> updateCarImage());
        }
    }

    private void clearFields() {
        txtBienSo.setText("");
        txtMauXe.setText("");
        txtNamSanXuat.setText("");
        showPlaceholder(); // Clear image when clearing fields
    }

    public void refresh() {
        loadCombos();
        loadData();
    }

    private void updateCarImage() {
        HieuXeDTO selectedHieuXe = (HieuXeDTO) cbHieuXe.getSelectedItem();
        if (selectedHieuXe != null) {
            String tenHieuXe = selectedHieuXe.getTenHieuXe();
            if (tenHieuXe != null && !tenHieuXe.trim().isEmpty()) {
                loadCarImage(tenHieuXe.trim());
            } else {
                showPlaceholder();
            }
        } else {
            showPlaceholder();
        }
    }

    private void loadCarImage(String carBrand) {
        try {
            // Use absolute path to the Hieuxe folder
            String basePath = "C:\\Users\\THIS PC\\IdeaProjects\\demov2\\demoquanly\\src\\main\\java\\com\\tutorialspoint\\jdbc\\Hieuxe\\";
            String imagePath = basePath + carBrand + ".png";
            File imageFile = new File(imagePath);
            
            if (!imageFile.exists()) {
                // Try with .jpg extension
                imagePath = basePath + carBrand + ".jpg";
                imageFile = new File(imagePath);
            }
            
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                
                // Target width is 330, calculate height to maintain aspect ratio
                int targetWidth = 330;
                int targetHeight = (int) ((double) originalHeight / originalWidth * targetWidth);
                
                // Make sure height doesn't exceed panel height (150)
                if (targetHeight > 150) {
                    targetHeight = 150;
                    targetWidth = (int) ((double) originalWidth / originalHeight * targetHeight);
                }
                
                Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                carImageLabel.setIcon(scaledIcon);
                carImageLabel.setText("");
                System.out.println("Loaded image: " + imagePath + " (Original: " + originalWidth + "x" + originalHeight + ", Scaled: " + targetWidth + "x" + targetHeight + ")");
            } else {
                System.out.println("Image not found: " + imagePath);
                showPlaceholder();
            }
        } catch (Exception e) {
            System.err.println("Error loading car image for " + carBrand + ": " + e.getMessage());
            e.printStackTrace();
            showPlaceholder();
        }
    }

    private void showPlaceholder() {
        carImageLabel.setIcon(null);
        carImageLabel.setText("Không có hình ảnh");
        carImageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        carImageLabel.setForeground(Color.GRAY);
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
