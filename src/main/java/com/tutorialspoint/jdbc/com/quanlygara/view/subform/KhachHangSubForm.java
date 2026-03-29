package com.tutorialspoint.jdbc.com.quanlygara.view.subform;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.jdbc.com.quanlygara.controller.KhachHangController;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

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
        setLayout(new BorderLayout(8, 8));

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 6, 6));
        panelInput.add(new JLabel("Ten KH:"));
        txtTenKH = new JTextField();
        panelInput.add(txtTenKH);
        panelInput.add(new JLabel("Dien thoai:"));
        txtDienThoai = new JTextField();
        panelInput.add(txtDienThoai);
        panelInput.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelInput.add(txtEmail);
        panelInput.add(new JLabel("Dia chi:"));
        txtDiaChi = new JTextField();
        panelInput.add(txtDiaChi);
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

        tableModel = new DefaultTableModel(new String[] {"Ma KH", "Ten KH", "Dien thoai", "Email", "Dia chi"}, 0) {
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
            JOptionPane.showMessageDialog(this, "Ten KH khong duoc de trong.");
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
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can sua.");
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
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can xoa.");
            return;
        }
        Integer maKH = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.deleteKhachHang(maKH);
        loadData();
        clearFields();
        DataChangeNotifier.notifyDataChanged("KhachHang");
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

    public void refresh() {
        loadData();
    }
}
