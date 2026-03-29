package com.tutorialspoint.jdbc.com.quanlygara.view;

import com.tutorialspoint.jdbc.com.quanlygara.controller.KhachHangController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangView extends JFrame {
    private KhachHangController controller = new KhachHangController();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTenKH, txtDienThoai, txtEmail, txtDiaChi, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnRefresh;

    public KhachHangView() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Quản Lý Khách Hàng");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input
        JPanel panelInput = new JPanel(new GridLayout(5, 2));
        panelInput.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField();
        panelInput.add(txtTenKH);
        panelInput.add(new JLabel("Điện Thoại:"));
        txtDienThoai = new JTextField();
        panelInput.add(txtDienThoai);
        panelInput.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelInput.add(txtEmail);
        panelInput.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        panelInput.add(txtDiaChi);
        panelInput.add(new JLabel("Tìm Kiếm:"));
        txtSearch = new JTextField();
        panelInput.add(txtSearch);

        // Buttons
        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        btnRefresh = new JButton("Làm Mới");

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnSearch);
        panelButtons.add(btnRefresh);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Mã KH", "Tên KH", "Điện Thoại", "Email", "Địa Chỉ"}, 0);
        table = new JTable(tableModel);

        // Add to frame
        add(panelInput, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Event listeners
        btnAdd.addActionListener(e -> addKhachHang());
        btnUpdate.addActionListener(e -> updateKhachHang());
        btnDelete.addActionListener(e -> deleteKhachHang());
        btnSearch.addActionListener(e -> searchKhachHang());
        btnRefresh.addActionListener(e -> loadData());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<KhachHangDTO> list = controller.getAllKhachHang();
        for (KhachHangDTO dto : list) {
            tableModel.addRow(new Object[]{dto.getMaKH(), dto.getTenKH(), dto.getDienThoai(), dto.getEmail(), dto.getDiaChi()});
        }
    }

    private void addKhachHang() {
        String tenKH = txtTenKH.getText();
        String dienThoai = txtDienThoai.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        controller.addKhachHang(tenKH, dienThoai, email, diaChi);
        loadData();
        clearFields();
    }

    private void updateKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Integer maKH = (Integer) tableModel.getValueAt(selectedRow, 0);
            String tenKH = txtTenKH.getText();
            String dienThoai = txtDienThoai.getText();
            String email = txtEmail.getText();
            String diaChi = txtDiaChi.getText();
            controller.updateKhachHang(maKH, tenKH, dienThoai, email, diaChi);
            loadData();
            clearFields();
        }
    }

    private void deleteKhachHang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Integer maKH = (Integer) tableModel.getValueAt(selectedRow, 0);
            controller.deleteKhachHang(maKH);
            loadData();
        }
    }

    private void searchKhachHang() {
        String search = txtSearch.getText();
        tableModel.setRowCount(0);
        List<KhachHangDTO> list = controller.searchByTenKH(search);
        for (KhachHangDTO dto : list) {
            tableModel.addRow(new Object[]{dto.getMaKH(), dto.getTenKH(), dto.getDienThoai(), dto.getEmail(), dto.getDiaChi()});
        }
    }

    private void clearFields() {
        txtTenKH.setText("");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
    }
}
