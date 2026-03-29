package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import com.tutorialspoint.jdbc.com.quanlygara.controller.HieuXeController;
import com.tutorialspoint.jdbc.com.quanlygara.controller.KhachHangController;
import com.tutorialspoint.jdbc.com.quanlygara.controller.XeController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.HieuXeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.XeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

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

    public XeSubForm() {
        setLayout(new BorderLayout(8, 8));

        JPanel panelInput = new JPanel(new GridLayout(6, 2, 6, 6));
        panelInput.add(new JLabel("Bien so:"));
        txtBienSo = new JTextField();
        panelInput.add(txtBienSo);
        panelInput.add(new JLabel("Mau xe:"));
        txtMauXe = new JTextField();
        panelInput.add(txtMauXe);
        panelInput.add(new JLabel("Nam san xuat:"));
        txtNamSanXuat = new JTextField();
        panelInput.add(txtNamSanXuat);
        panelInput.add(new JLabel("Khach hang:"));
        cbKhachHang = new JComboBox<>();
        panelInput.add(cbKhachHang);
        panelInput.add(new JLabel("Hieu xe:"));
        cbHieuXe = new JComboBox<>();
        panelInput.add(cbHieuXe);
        panelInput.add(new JLabel("Tim theo bien so:"));
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

        tableModel = new DefaultTableModel(new String[] {"Ma Xe", "Bien so", "Mau xe", "Nam SX", "Ten KH", "Hieu xe"}, 0) {
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

        btnAdd.addActionListener(e -> addXe());
        btnUpdate.addActionListener(e -> updateXe());
        btnDelete.addActionListener(e -> deleteXe());
        btnSearch.addActionListener(e -> searchXe());
        btnRefresh.addActionListener(e -> loadData());

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
        tableModel.setRowCount(0);
        List<XeDTO> list = controller.getAllXe();
        for (XeDTO dto : list) {
            tableModel.addRow(new Object[] {dto.getMaXe(), dto.getBienSo(), dto.getMauXe(), dto.getNamSanXuat(), dto.getTenKH(), dto.getTenHieuXe()});
        }
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
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can xoa.");
            return;
        }
        Integer maXe = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            controller.deleteXe(maXe);
            loadData();
            clearFields();
            DataChangeNotifier.notifyDataChanged("Xe");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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
            // Set combo boxes
            for (int i = 0; i < cbKhachHang.getItemCount(); i++) {
                KhachHangDTO kh = cbKhachHang.getItemAt(i);
                if (kh.getTenKH().equals(tenKH)) {
                    cbKhachHang.setSelectedItem(kh);
                    break;
                }
            }
            for (int i = 0; i < cbHieuXe.getItemCount(); i++) {
                HieuXeDTO hx = cbHieuXe.getItemAt(i);
                if (hx.getTenHieuXe().equals(tenHieuXe)) {
                    cbHieuXe.setSelectedItem(hx);
                    break;
                }
            }
        }
    }

    private void clearFields() {
        txtBienSo.setText("");
        txtMauXe.setText("");
        txtNamSanXuat.setText("");
    }

    public void refresh() {
        loadCombos();
        loadData();
    }
}
