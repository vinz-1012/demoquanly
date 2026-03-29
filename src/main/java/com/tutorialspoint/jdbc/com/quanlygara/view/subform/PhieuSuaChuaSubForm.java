package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
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
import com.tutorialspoint.jdbc.com.quanlygara.controller.PhieuSuaChuaController;
import com.tutorialspoint.jdbc.com.quanlygara.controller.XeController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.NhanVienDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.PhieuSuaChuaDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.XeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

public class PhieuSuaChuaSubForm extends JPanel {
    private final PhieuSuaChuaController controller = new PhieuSuaChuaController();
    private final XeController xeController = new XeController();
    private final NhanVienController nvController = new NhanVienController();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JComboBox<XeDTO> cbXe;
    private final JComboBox<NhanVienDTO> cbNhanVien;
    private final JTextField txtNgayNhan;
    private final JTextField txtNgayTra;
    private final JComboBox<String> cbTrangThai;
    private final JTextField txtGhiChu;
    private final JTextField txtSearchTrangThai;

    public PhieuSuaChuaSubForm() {
        setLayout(new BorderLayout(8, 8));

        JPanel panelInput = new JPanel(new GridLayout(7, 2, 6, 6));
        panelInput.add(new JLabel("Xe:"));
        cbXe = new JComboBox<>();
        panelInput.add(cbXe);
        panelInput.add(new JLabel("Nhan vien:"));
        cbNhanVien = new JComboBox<>();
        panelInput.add(cbNhanVien);
        panelInput.add(new JLabel("Ngay nhan (yyyy-MM-dd):"));
        txtNgayNhan = new JTextField();
        panelInput.add(txtNgayNhan);
        panelInput.add(new JLabel("Ngay tra (yyyy-MM-dd):"));
        txtNgayTra = new JTextField();
        panelInput.add(txtNgayTra);
        panelInput.add(new JLabel("Trang thai:"));
        cbTrangThai = new JComboBox<>(new String[]{"Dang sua", "Hoan thanh"});
        panelInput.add(cbTrangThai);
        panelInput.add(new JLabel("Ghi chu:"));
        txtGhiChu = new JTextField();
        panelInput.add(txtGhiChu);
        panelInput.add(new JLabel("Tim theo trang thai:"));
        txtSearchTrangThai = new JTextField();
        panelInput.add(txtSearchTrangThai);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnSearch = new JButton("Tim");
        JButton btnRefresh = new JButton("Lam moi");
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnSearch);
        panelButtons.add(btnRefresh);

        tableModel = new DefaultTableModel(new String[] {"Ma Phieu", "Bien so", "Ten KH", "Ten NV", "Ngay nhan", "Ngay tra", "Trang thai", "Ghi chu"}, 0) {
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

        btnAdd.addActionListener(e -> addPhieuSuaChua());
        btnUpdate.addActionListener(e -> updatePhieuSuaChua());
        btnSearch.addActionListener(e -> searchPhieuSuaChua());
        btnRefresh.addActionListener(e -> loadData());

        loadCombos();
        loadData();
    }

    private void loadCombos() {
        cbXe.removeAllItems();
        List<XeDTO> xes = xeController.getAllXe();
        for (XeDTO xe : xes) {
            cbXe.addItem(xe);
        }

        cbNhanVien.removeAllItems();
        List<NhanVienDTO> nvs = nvController.getAllNhanVien();
        for (NhanVienDTO nv : nvs) {
            cbNhanVien.addItem(nv);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<PhieuSuaChuaDTO> list = controller.getAllPhieuSuaChua();
        for (PhieuSuaChuaDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaPhieu(), dto.getBienSo(), dto.getTenKH(), dto.getTenNV(), dto.getNgayNhan(), dto.getNgayTra(), dto.getTrangThai(), dto.getGhiChu()});
        }
    }

    private void addPhieuSuaChua() {
        try {
            XeDTO xe = (XeDTO) cbXe.getSelectedItem();
            NhanVienDTO nv = (NhanVienDTO) cbNhanVien.getSelectedItem();
            LocalDate ngayNhan = LocalDate.parse(txtNgayNhan.getText().trim());
            LocalDate ngayTra = txtNgayTra.getText().isBlank() ? null : LocalDate.parse(txtNgayTra.getText().trim());
            String trangThai = (String) cbTrangThai.getSelectedItem();
            controller.addPhieuSuaChua(xe.getMaXe(), nv.getMaNV(), ngayNhan, ngayTra, trangThai, txtGhiChu.getText().trim());
            loadData();
            clearFields();
            DataChangeNotifier.notifyDataChanged("PhieuSuaChua");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Du lieu khong hop le: " + e.getMessage());
        }
    }

    private void updatePhieuSuaChua() {
        int selectedRow = table.getSelectedRow();
        System.out.println("=== UI: updatePhieuSuaChua - selectedRow: " + selectedRow + " ===");
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can sua.");
            return;
        }
        try {
            System.out.println("=== UI: updatePhieuSuaChua called ===");
            Integer maPhieu = (Integer) tableModel.getValueAt(selectedRow, 0);
            XeDTO xe = (XeDTO) cbXe.getSelectedItem();
            NhanVienDTO nv = (NhanVienDTO) cbNhanVien.getSelectedItem();
            
            // Check null values before parsing
            String ngayNhanText = txtNgayNhan.getText().trim();
            String ngayTraText = txtNgayTra.getText().trim();
            LocalDate ngayNhan = ngayNhanText.isEmpty() ? null : LocalDate.parse(ngayNhanText);
            LocalDate ngayTra = ngayTraText.isEmpty() ? null : LocalDate.parse(ngayTraText);
            String trangThai = (String) cbTrangThai.getSelectedItem();
            
            System.out.println("UI: Calling controller.updatePhieuSuaChua with maPhieu=" + maPhieu + ", trangThai=" + trangThai);
            controller.updatePhieuSuaChua(maPhieu, xe.getMaXe(), nv.getMaNV(), ngayNhan, ngayTra, trangThai, txtGhiChu.getText().trim());
            loadData();
            DataChangeNotifier.notifyDataChanged("PhieuSuaChua");
            System.out.println("UI: updatePhieuSuaChua completed");
        } catch (Exception e) {
            System.err.println("UI: Exception in updatePhieuSuaChua: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Du lieu khong hop le: " + e.getMessage());
        }
    }

    private void searchPhieuSuaChua() {
        String trangThai = txtSearchTrangThai.getText().trim();
        tableModel.setRowCount(0);
        List<PhieuSuaChuaDTO> list = controller.getPhieuSuaChuaByTrangThai(trangThai);
        for (PhieuSuaChuaDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaPhieu(), dto.getBienSo(), dto.getTenKH(), dto.getTenNV(), dto.getNgayNhan(), dto.getNgayTra(), dto.getTrangThai(), dto.getGhiChu()});
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String bienSo = String.valueOf(tableModel.getValueAt(selectedRow, 1));
            String tenNV = String.valueOf(tableModel.getValueAt(selectedRow, 3));
            // Set combo boxes
            for (int i = 0; i < cbXe.getItemCount(); i++) {
                XeDTO xe = cbXe.getItemAt(i);
                if (xe.getBienSo().equals(bienSo)) {
                    cbXe.setSelectedItem(xe);
                    break;
                }
            }
            for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                NhanVienDTO nv = cbNhanVien.getItemAt(i);
                if (nv.getTenNV().equals(tenNV)) {
                    cbNhanVien.setSelectedItem(nv);
                    break;
                }
            }
            txtNgayNhan.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            txtNgayTra.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
            cbTrangThai.setSelectedItem(tableModel.getValueAt(selectedRow, 6));
            txtGhiChu.setText(String.valueOf(tableModel.getValueAt(selectedRow, 7)));
        }
    }

    private void clearFields() {
        txtNgayNhan.setText("");
        txtNgayTra.setText("");
        txtGhiChu.setText("");
    }

    public void refresh() {
        loadCombos();
        loadData();
    }
}
