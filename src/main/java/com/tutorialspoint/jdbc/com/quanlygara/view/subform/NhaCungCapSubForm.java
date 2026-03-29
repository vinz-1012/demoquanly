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

import com.tutorialspoint.jdbc.com.quanlygara.controller.NhaCungCapController;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhaCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

public class NhaCungCapSubForm extends JPanel {
    private final NhaCungCapController controller = new NhaCungCapController();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtTenNCC;
    private final JTextField txtDienThoai;
    private final JTextField txtDiaChi;
    private final JTextField txtSearch;

    public NhaCungCapSubForm() {
        setLayout(new BorderLayout(8, 8));

        JPanel panelInput = new JPanel(new GridLayout(4, 2, 6, 6));
        panelInput.add(new JLabel("Ten NCC:"));
        txtTenNCC = new JTextField();
        panelInput.add(txtTenNCC);
        panelInput.add(new JLabel("Dien thoai:"));
        txtDienThoai = new JTextField();
        panelInput.add(txtDienThoai);
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

        tableModel = new DefaultTableModel(new String[] {"Ma NCC", "Ten NCC", "Dien thoai", "Dia chi"}, 0) {
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

        btnAdd.addActionListener(e -> addNhaCungCap());
        btnUpdate.addActionListener(e -> updateNhaCungCap());
        btnDelete.addActionListener(e -> deleteNhaCungCap());
        btnSearch.addActionListener(e -> searchNhaCungCap());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<NhaCungCap> list = controller.getAllNhaCungCap();
        for (NhaCungCap item : list) {
            tableModel.addRow(new Object[] {item.getMaNCC(), item.getTenNCC(), item.getDienThoai(), item.getDiaChi()});
        }
    }

    private void addNhaCungCap() {
        if (txtTenNCC.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Ten NCC khong duoc de trong.");
            return;
        }
        controller.addNhaCungCap(txtTenNCC.getText().trim(), txtDienThoai.getText().trim(), txtDiaChi.getText().trim());
        loadData();
        clearFields();
        DataChangeNotifier.notifyDataChanged("NhaCungCap");
    }

    private void updateNhaCungCap() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can sua.");
            return;
        }
        Integer maNCC = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.updateNhaCungCap(maNCC, txtTenNCC.getText().trim(), txtDienThoai.getText().trim(), txtDiaChi.getText().trim());
        loadData();
        DataChangeNotifier.notifyDataChanged("NhaCungCap");
    }

    private void deleteNhaCungCap() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi can xoa.");
            return;
        }
        Integer maNCC = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.deleteNhaCungCap(maNCC);
        loadData();
        clearFields();
        DataChangeNotifier.notifyDataChanged("NhaCungCap");
    }

    private void searchNhaCungCap() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);
        List<NhaCungCap> list = controller.searchByTenNCC(keyword);
        for (NhaCungCap item : list) {
            tableModel.addRow(new Object[] {item.getMaNCC(), item.getTenNCC(), item.getDienThoai(), item.getDiaChi()});
        }
    }

    private void fillFormFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtTenNCC.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            txtDienThoai.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            txtDiaChi.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        }
    }

    private void clearFields() {
        txtTenNCC.setText("");
        txtDienThoai.setText("");
        txtDiaChi.setText("");
    }
}
