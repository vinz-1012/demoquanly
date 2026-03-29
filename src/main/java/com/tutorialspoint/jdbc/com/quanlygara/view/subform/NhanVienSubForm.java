package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.jdbc.com.quanlygara.controller.NhanVienController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.NhanVienDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

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
        setLayout(new BorderLayout(8, 8));

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 6, 6));
        panelInput.add(new JLabel("Ten NV:"));
        txtTenNV = new JTextField();
        panelInput.add(txtTenNV);
        panelInput.add(new JLabel("Dien thoai:"));
        txtDienThoai = new JTextField();
        panelInput.add(txtDienThoai);
        panelInput.add(new JLabel("Chuc vu:"));
        cbChucVu = new JComboBox<>(new String[]{"Ky thuat", "Quan ly"});
        panelInput.add(cbChucVu);
        panelInput.add(new JLabel("Luong:"));
        txtLuong = new JTextField();
        panelInput.add(txtLuong);
        panelInput.add(new JLabel("Tim theo ten:"));
        txtSearch = new JTextField();
        panelInput.add(txtSearch);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        JButton btnSearch = new JButton("Tim");
        JButton btnRefresh = new JButton("Lam moi");
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnSearch);
        panelButtons.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[] {"Ma NV", "Ten NV", "Dien thoai", "Chuc vu", "Luong"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.add(panelInput, BorderLayout.CENTER);
        top.add(panelButtons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
}
