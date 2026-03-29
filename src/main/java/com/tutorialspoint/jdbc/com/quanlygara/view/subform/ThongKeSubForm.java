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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.tutorialspoint.jdbc.com.quanlygara.controller.ThongKeController;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HopDongCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeListener;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;
import com.tutorialspoint.jdbc.com.quanlygara.service.ThongKeService;

public class ThongKeSubForm extends JPanel implements DataChangeListener {
    private final ThongKeController controller = new ThongKeController();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    // Class-level button reference for Hóa Đơn tab
    private JButton btnHoaDonLoad;
    private JButton btnLoiNhuanLoad;
    private JButton btnTaiLai;
    private JComboBox<String> cbTrangThaiFilter;
    // Class-level button reference for Chi Tiết Nhập Hàng tab
    private JButton btnChiTietNhapRefresh;
    // Class-level button reference for Phiếu Sửa + Dịch Vụ tab
    private JButton btnPhieuSuaDichVuRefresh;

    public ThongKeSubForm() {
        DataChangeNotifier.addListener(this);
        setLayout(new BorderLayout());

        // Tab 1: Tổng quan
        JPanel panelTongQuan = createTongQuanPanel();
        tabbedPane.addTab("Tong quan", panelTongQuan);

        // Tab 2: Danh sách hóa đơn
        JPanel panelHoaDon = createHoaDonPanel();
        tabbedPane.addTab("Danh sach hoa don", panelHoaDon);

        // Tab 3: Phieu sua + dich vu (CRUD)
        JPanel panelPhieuSuaDichVuCrud = createPhieuSuaDichVuCrudPanel();
        tabbedPane.addTab("Phieu sua + dich vu CRUD", panelPhieuSuaDichVuCrud);

        // Tab 4: Chi tiet nhap hang (CRUD)
        JPanel panelNhapHang = createChiTietNhapCrudPanel();
        tabbedPane.addTab("Chi tiet nhap hang", panelNhapHang);

        // Tab 5: Loi nhuan
        JPanel panelLoiNhuan = createLoiNhuanPanel();
        tabbedPane.addTab("Loi nhuan", panelLoiNhuan);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTongQuanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel lblTongKhachHang = new JLabel("0");
        JLabel lblTongDoanhThu = new JLabel("0");

        JPanel panelCard = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCard.add(createCard("Tong khach hang", lblTongKhachHang));
        panelCard.add(createCard("Tong doanh thu (VND)", lblTongDoanhThu));

        DefaultTableModel modelTrangThai = new DefaultTableModel(new String[] {"Trang thai", "So luong phieu"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableTrangThai = new JTable(modelTrangThai);

        JPanel panelSearch = new JPanel(new BorderLayout());
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        filterPanel.setBackground(new java.awt.Color(240, 240, 240));
        filterPanel.setOpaque(true);
        filterPanel.add(new JLabel("Lọc trạng thái:"));
        cbTrangThaiFilter = new JComboBox<>(new String[]{"Tất cả", "Dang sua", "Hoan thanh"});
        cbTrangThaiFilter.setSelectedIndex(0);
        cbTrangThaiFilter.setPreferredSize(new java.awt.Dimension(120, 25));
        filterPanel.add(cbTrangThaiFilter);
        
        // Add refresh button
        btnTaiLai = new JButton("Tải lại thống kê");
        filterPanel.add(btnTaiLai);
        
        System.out.println("[DEBUG] Created filter combobox with items: Tất cả, Dang sua, Hoan thanh");
        
        panelSearch.add(filterPanel, BorderLayout.CENTER);

        DefaultTableModel modelTimPhieu = new DefaultTableModel(new String[] {"Ma phieu", "Ngay nhan", "Ngay tra", "Trang thai"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableTimPhieu = new JTable(modelTimPhieu);

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCenter.add(new JScrollPane(tableTrangThai));
        panelCenter.add(new JScrollPane(tableTimPhieu));

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.add(panelSearch, BorderLayout.NORTH);
        mainContent.add(panelCenter, BorderLayout.CENTER);

        panel.add(panelCard, BorderLayout.NORTH);
        panel.add(mainContent, BorderLayout.CENTER);

        btnTaiLai.addActionListener(e -> {
            Long tongKhach = controller.getTongKhachHang();
            BigDecimal tongDoanhThu = controller.getTongDoanhThu();
            lblTongKhachHang.setText(String.valueOf(tongKhach));
            lblTongDoanhThu.setText(tongDoanhThu.toPlainString());

            modelTrangThai.setRowCount(0);
            List<Object[]> rows = controller.getSoLuongPhieuTheoTrangThai();
            for (Object[] row : rows) {
                modelTrangThai.addRow(new Object[] {String.valueOf(row[0]), row[1]});
            }
        });

        cbTrangThaiFilter.addActionListener(e -> {
            try {
                System.out.println("[DEBUG] Combobox filter selected: " + cbTrangThaiFilter.getSelectedItem());
                String selectedFilter = (String) cbTrangThaiFilter.getSelectedItem();
                
                // Debug: Show all available statuses first
                List<Object[]> allStatuses = controller.getSoLuongPhieuTheoTrangThai();
                System.out.println("[DEBUG] All available statuses in database:");
                for (Object[] status : allStatuses) {
                    System.out.println("  - '" + status[0] + "' : " + status[1] + " records");
                }
                
                // Filter the search table (modelTimPhieu)
                modelTimPhieu.setRowCount(0);
                List<PhieuSuaChua> filteredList;
                
                if ("Tất cả".equals(selectedFilter)) {
                    // Load all repair slips
                    filteredList = controller.getAllPhieuSuaChua();
                    System.out.println("[DEBUG] Loading all repair slips, count: " + filteredList.size());
                } else {
                    // Filter by selected status
                    filteredList = controller.timPhieuTheoTrangThai(selectedFilter);
                    System.out.println("[DEBUG] Filtering by status: '" + selectedFilter + "', count: " + filteredList.size());
                    if (filteredList.isEmpty()) {
                        System.out.println("[DEBUG] WARNING: No records found for status '" + selectedFilter + "'");
                        System.out.println("[DEBUG] Available statuses are:");
                        for (Object[] status : allStatuses) {
                            System.out.println("  - '" + status[0] + "'");
                        }
                    }
                }
                
                for (PhieuSuaChua item : filteredList) {
                    modelTimPhieu.addRow(new Object[] {
                        item.getMaPhieu(),
                        item.getNgayNhan(),
                        item.getNgayTra(),
                        item.getTrangThai()
                    });
                }
                
                // Also refresh the status table (modelTrangThai)
                modelTrangThai.setRowCount(0);
                List<Object[]> statusRows;
                if ("Tất cả".equals(selectedFilter)) {
                    statusRows = controller.getSoLuongPhieuTheoTrangThai();
                } else {
                    // Get count for specific status
                    List<PhieuSuaChua> statusSpecificList = controller.timPhieuTheoTrangThai(selectedFilter);
                    statusRows = new java.util.ArrayList<>();
                    statusRows.add(new Object[]{selectedFilter, statusSpecificList.size()});
                }
                
                for (Object[] row : statusRows) {
                    modelTrangThai.addRow(new Object[] {String.valueOf(row[0]), row[1]});
                }
                
            } catch (Exception ex) {
                System.err.println("[ERROR] Error in combobox filter: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi lọc trạng thái: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTaiLai.addActionListener(e -> {
            Long tongKhach = controller.getTongKhachHang();
            BigDecimal tongDoanhThu = controller.getTongDoanhThu();
            lblTongKhachHang.setText(String.valueOf(tongKhach));
            lblTongDoanhThu.setText(tongDoanhThu.toPlainString());

            modelTrangThai.setRowCount(0);
            List<Object[]> rows = controller.getSoLuongPhieuTheoTrangThai();
            for (Object[] row : rows) {
                modelTrangThai.addRow(new Object[] {String.valueOf(row[0]), row[1]});
            }
        });

        // Load initial data
        btnTaiLai.doClick();
        
        // Load all repair slips for search table
        modelTimPhieu.setRowCount(0);
        List<PhieuSuaChua> allPhieu = controller.getAllPhieuSuaChua();
        for (PhieuSuaChua item : allPhieu) {
            modelTimPhieu.addRow(new Object[] {
                item.getMaPhieu(),
                item.getNgayNhan(),
                item.getNgayTra(),
                item.getTrangThai()
            });
        }

        return panel;
    }

    private JPanel createHoaDonPanel() {
        // Đăng ký lắng nghe sự kiện thay đổi dữ liệu để tự động reload danh sách hóa đơn
        DataChangeNotifier.addListener(entityName -> {
            if ("HoaDon".equals(entityName) || "PhieuSuaChua".equals(entityName) || "deletePhieuSuaChua".equals(entityName)) {
                SwingUtilities.invokeLater(() -> {
                    if (btnHoaDonLoad != null) btnHoaDonLoad.doClick();
                });
            }
        });
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[] {"Ma HD", "Ten KH", "Bien so", "Tong tien", "Ngay thanh toan"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnHoaDonLoad = new JButton("Tai danh sach");
        JButton btnDelete = new JButton("Xoa hoa don");

        btnHoaDonLoad.addActionListener(e -> {
            model.setRowCount(0);
            List<Object[]> list = controller.getDanhSachHoaDon();
            for (Object[] row : list) {
                model.addRow(row);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(panel, "Vui long chon hoa don can xoa.");
                return;
            }
            Integer maHD = (Integer) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(
                panel,
                "Ban co chac chan muon xoa hoa don " + maHD + "?",
                "Xac nhan xoa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.deleteHoaDon(maHD);
                    // Refresh table
                    model.setRowCount(0);
                    List<Object[]> list = controller.getDanhSachHoaDon();
                    for (Object[] row : list) {
                        model.addRow(row);
                    }
                    // Notify data change
                    DataChangeNotifier.notifyDataChanged("HoaDon");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Loi khi xoa hoa don: " + ex.getMessage());
                }
            }
        });

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButtons.add(btnHoaDonLoad);
        panelButtons.add(btnDelete);

        panel.add(panelButtons, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPhieuSuaDichVuCrudPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));

        JPanel form = new JPanel(new GridLayout(3, 4, 6, 6));
        JComboBox<PhieuSuaChua> cbPhieu = new JComboBox<>();
        JTextField txtDichVu = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();

        form.add(new JLabel("Phieu: "));
        form.add(cbPhieu);
        form.add(new JLabel("Dich vu: "));
        form.add(txtDichVu);
        form.add(new JLabel("So luong: "));
        form.add(txtSoLuong);
        form.add(new JLabel("Don gia: "));
        form.add(txtDonGia);

        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
            btnPhieuSuaDichVuRefresh = new JButton("Lam moi");

        form.add(btnAdd);
        form.add(btnUpdate);
        form.add(btnDelete);
            form.add(btnPhieuSuaDichVuRefresh);

        DefaultTableModel model = new DefaultTableModel(new String[] {"MaPhieu", "MaDV", "TenDV", "SoLuong", "DonGia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing of TenDV column (index 2)
                return column == 2;
            }
        };
        JTable table = new JTable(model);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        Runnable loadData = () -> {
            cbPhieu.removeAllItems();
            for (PhieuSuaChua p : controller.getAllPhieuSuaChua()) {
                cbPhieu.addItem(p);
            }

            model.setRowCount(0);
            for (Object[] row : controller.getAllChiTietDichVu()) {
                model.addRow(row);
            }
        };

        // Đăng ký lắng nghe sự kiện thay đổi dữ liệu để tự động reload combobox phiếu
        DataChangeNotifier.addListener(entityName -> {
            if ("PhieuSuaChua".equals(entityName) || "deletePhieuSuaChua".equals(entityName)) {
                SwingUtilities.invokeLater(loadData);
            }
        });

            btnPhieuSuaDichVuRefresh.addActionListener(e -> loadData.run());

        btnAdd.addActionListener(e -> {
            try {
                PhieuSuaChua phieu = (PhieuSuaChua) cbPhieu.getSelectedItem();
                String tenDV = txtDichVu.getText().trim();
                if (phieu == null || tenDV.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Chon phieu va nhap ten dich vu.");
                    return;
                }
                Integer sl = Integer.valueOf(txtSoLuong.getText().trim());
                BigDecimal dg = new BigDecimal(txtDonGia.getText().trim());
                controller.addChiTietDichVuWithServiceName(phieu.getMaPhieu(), tenDV, sl, dg);
                loadData.run();
                DataChangeNotifier.notifyDataChanged("ChiTietDichVu");
                DataChangeNotifier.notifyDataChanged("PhieuSuaChua"); // Also notify PhieuSuaChua change
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi du lieu: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chon dong can sua.");
                return;
            }
            try {
                Integer maPhieu = Integer.valueOf(model.getValueAt(row, 0).toString());
                Integer maDV = Integer.valueOf(model.getValueAt(row, 1).toString());
                Integer sl = Integer.valueOf(txtSoLuong.getText().trim());
                BigDecimal dg = new BigDecimal(txtDonGia.getText().trim());
                controller.updateChiTietDichVu(maPhieu, maDV, sl, dg);
                loadData.run();
                DataChangeNotifier.notifyDataChanged("ChiTietDichVu");
                DataChangeNotifier.notifyDataChanged("PhieuSuaChua"); // Also notify PhieuSuaChua change
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi du lieu: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chon dong can xoa.");
                return;
            }
            Integer maPhieu = Integer.valueOf(model.getValueAt(row, 0).toString());
            Integer maDV = Integer.valueOf(model.getValueAt(row, 1).toString());
            controller.deleteChiTietDichVu(maPhieu, maDV);
            loadData.run();
            DataChangeNotifier.notifyDataChanged("ChiTietDichVu");
            DataChangeNotifier.notifyDataChanged("PhieuSuaChua"); // Also notify PhieuSuaChua change
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer maPhieu = Integer.valueOf(model.getValueAt(row, 0).toString());
                String tenDV = model.getValueAt(row, 2).toString();
                Integer soLuong = Integer.valueOf(model.getValueAt(row, 3).toString());
                String donGia = model.getValueAt(row, 4).toString();

                for (int i = 0; i < cbPhieu.getItemCount(); i++) {
                    if (cbPhieu.getItemAt(i).getMaPhieu().equals(maPhieu)) {
                        cbPhieu.setSelectedIndex(i);
                        break;
                    }
                }
                txtDichVu.setText(tenDV);
                txtSoLuong.setText(String.valueOf(soLuong));
                txtDonGia.setText(donGia);
            }
        });

        loadData.run();
        return panel;
    }

    private JPanel createChiTietNhapCrudPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));

        JPanel form = new JPanel(new GridLayout(3, 4, 6, 6));
        JComboBox<HopDongCungCap> cbHopDong = new JComboBox<>();
        JTextField txtPhuTung = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();

        form.add(new JLabel("Hop dong: "));
        form.add(cbHopDong);
        form.add(new JLabel("Phu tung: "));
        form.add(txtPhuTung);
        form.add(new JLabel("So luong: "));
        form.add(txtSoLuong);
        form.add(new JLabel("Don gia: "));
        form.add(txtDonGia);

        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        btnChiTietNhapRefresh = new JButton("Lam moi");

        form.add(btnAdd);
        form.add(btnUpdate);
        form.add(btnDelete);
        form.add(btnChiTietNhapRefresh);

        DefaultTableModel model = new DefaultTableModel(new String[] {"MaHopDong", "MaPT", "TenPT", "SoLuong", "DonGia"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing of TenPT column (index 2)
                return column == 2;
            }
        };
        JTable table = new JTable(model);

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        Runnable loadData = () -> {
            System.out.println("[DEBUG] Reloading HopDongCungCap combobox after data change...");
            cbHopDong.removeAllItems();
            java.util.List<HopDongCungCap> hopDongList = controller.getAllHopDongCungCap();
            System.out.println("[DEBUG] HopDongCungCap count: " + hopDongList.size());
            for (HopDongCungCap hd : hopDongList) {
                System.out.println("[DEBUG] Adding HopDongCungCap: " + hd);
                cbHopDong.addItem(hd);
            }

            model.setRowCount(0);
            java.util.List<Object[]> chiTietList = controller.getAllChiTietNhapHang();
            System.out.println("[DEBUG] ChiTietNhapHang row count: " + chiTietList.size());
            for (Object[] row : chiTietList) {
                model.addRow(row);
            }
        };

        // Đăng ký lắng nghe sự kiện thay đổi dữ liệu để tự động reload combobox hợp đồng và nhà cung cấp
        DataChangeNotifier.addListener(entityName -> {
            if ("HopDongCungCap".equals(entityName) || "deleteHopDongCungCap".equals(entityName)
                || "NhaCungCap".equals(entityName) || "deleteNhaCungCap".equals(entityName)) {
                SwingUtilities.invokeLater(loadData);
            }
        });

        btnChiTietNhapRefresh.addActionListener(e -> loadData.run());

        btnAdd.addActionListener(e -> {
            try {
                HopDongCungCap hd = (HopDongCungCap) cbHopDong.getSelectedItem();
                String tenPT = txtPhuTung.getText().trim();
                if (hd == null || tenPT.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Chon hop dong va nhap ten phu tung.");
                    return;
                }
                Integer sl = Integer.valueOf(txtSoLuong.getText().trim());
                BigDecimal dg = new BigDecimal(txtDonGia.getText().trim());
                controller.addChiTietNhapHangWithPartName(hd.getMaHopDong(), tenPT, sl, dg);
                loadData.run();
                DataChangeNotifier.notifyDataChanged("ChiTietNhap");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi du lieu: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chon dong can sua.");
                return;
            }
            try {
                Integer maHopDong = Integer.valueOf(model.getValueAt(row, 0).toString());
                Integer maPT = Integer.valueOf(model.getValueAt(row, 1).toString());
                Integer sl = Integer.valueOf(txtSoLuong.getText().trim());
                BigDecimal dg = new BigDecimal(txtDonGia.getText().trim());
                controller.updateChiTietNhapHang(maHopDong, maPT, sl, dg);
                loadData.run();
                DataChangeNotifier.notifyDataChanged("ChiTietNhap");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi du lieu: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chon dong can xoa.");
                return;
            }
            Integer maHopDong = Integer.valueOf(model.getValueAt(row, 0).toString());
            Integer maPT = Integer.valueOf(model.getValueAt(row, 1).toString());
            controller.deleteChiTietNhapHang(maHopDong, maPT);
            loadData.run();
            DataChangeNotifier.notifyDataChanged("ChiTietNhap");
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer maHopDong = Integer.valueOf(model.getValueAt(row, 0).toString());
                String tenPT = model.getValueAt(row, 2).toString();
                Integer soLuong = Integer.valueOf(model.getValueAt(row, 3).toString());
                String donGia = model.getValueAt(row, 4).toString();

                for (int i = 0; i < cbHopDong.getItemCount(); i++) {
                    if (cbHopDong.getItemAt(i).getMaHopDong().equals(maHopDong)) {
                        cbHopDong.setSelectedIndex(i);
                        break;
                    }
                }
                txtPhuTung.setText(tenPT);
                txtSoLuong.setText(String.valueOf(soLuong));
                txtDonGia.setText(donGia);
            }
        });

        loadData.run();
        return panel;
    }

    private JPanel createLoiNhuanPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel lblDoanhThu = new JLabel("Doanh thu: 0");
        JLabel lblChiPhiNV = new JLabel("Chi phi nhan vien: 0");
        JLabel lblChiPhiPT = new JLabel("Chi phi phu tung: 0");
        JLabel lblLoiNhuan = new JLabel("Loi nhuan: 0");

        btnLoiNhuanLoad = new JButton("Tinh lai");
        btnLoiNhuanLoad.addActionListener(e -> {
            BigDecimal doanhThu = controller.getTongDoanhThu();
            BigDecimal chiPhiNV = controller.getChiPhiNhanVien();
            BigDecimal chiPhiPT = controller.getChiPhiPhuTung();
            BigDecimal loiNhuan = controller.getLoiNhuanTong();

            lblDoanhThu.setText("Doanh thu: " + doanhThu.toPlainString());
            lblChiPhiNV.setText("Chi phi nhan vien: " + chiPhiNV.toPlainString());
            lblChiPhiPT.setText("Chi phi phu tung: " + chiPhiPT.toPlainString());
            lblLoiNhuan.setText("Loi nhuan: " + loiNhuan.toPlainString());
        });

        panel.add(lblDoanhThu);
        panel.add(lblChiPhiNV);
        panel.add(lblChiPhiPT);
        panel.add(lblLoiNhuan);
        panel.add(btnLoiNhuanLoad);

        return panel;
    }

    @Override
    public void onDataChanged(String entityName) {
        // Refresh for ALL entity changes including PhieuSuaChua, NhaCungCap, HopDongCungCap
        System.out.println("=== Data changed: " + entityName + " ===");
        if ("PhieuSuaChua".equals(entityName) || "NhaCungCap".equals(entityName) || "HopDongCungCap".equals(entityName) || "deletePhieuSuaChua".equals(entityName) || "deleteNhaCungCap".equals(entityName)) {
            refresh();
        }
    }

    public void refresh() {
        // Force immediate UI update and reload ALL tab data from controller/service
        SwingUtilities.invokeLater(() -> {
            // Tab 0: Tổng quan
            JPanel panelTongQuan = (JPanel) tabbedPane.getComponentAt(0);
            refreshTongQuanPanel(panelTongQuan);

            // Tab 1: Danh sách hóa đơn
            if (btnHoaDonLoad != null) btnHoaDonLoad.doClick();

            // Tab 2: Phiếu sửa + dịch vụ CRUD
            if (btnPhieuSuaDichVuRefresh != null) btnPhieuSuaDichVuRefresh.doClick();

            // Tab 3: Chi tiết nhập hàng CRUD
            if (btnChiTietNhapRefresh != null) btnChiTietNhapRefresh.doClick();

            // Tab 4: Lợi nhuận
            if (btnLoiNhuanLoad != null) btnLoiNhuanLoad.doClick();

            // Force repaint of all tabs
            tabbedPane.repaint();
            tabbedPane.revalidate();
        });
    }

    private void refreshTongQuanPanel(JPanel panel) {
        System.out.println("=== Refreshing Tong Quan panel ===");
        try {
            // Directly click the btnTaiLai button
            if (btnTaiLai != null) {
                System.out.println("[DEBUG] Clicking btnTaiLai directly");
                btnTaiLai.doClick();
            } else {
                System.err.println("[ERROR] btnTaiLai is null");
            }
        } catch (Exception e) {
            System.err.println("Error refreshing Tong Quan panel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateLabelsDirectly(JPanel panel, Long customerCount, java.math.BigDecimal totalRevenue) {
        System.out.println("Updating labels directly...");

        // Find all labels in the panel and update them
        updateAllLabelsInContainer(panel, customerCount, totalRevenue);

        // Force UI refresh
        panel.repaint();
        panel.revalidate();

        System.out.println("Labels updated and UI refreshed");
    }

    private void updateAllLabelsInContainer(java.awt.Container container, Long customerCount, java.math.BigDecimal totalRevenue) {
        for (int i = 0; i < container.getComponentCount(); i++) {
            java.awt.Component comp = container.getComponent(i);

            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String currentText = label.getText();

                // Update customer count labels (showing "0" initially)
                if (currentText.equals("0")) {
                    label.setText(String.valueOf(customerCount));
                    System.out.println("Updated customer count label: " + customerCount);
                }
                // Update revenue labels (containing "VND")
                else if (currentText.contains("VND")) {
                    label.setText(String.format("%.0f", totalRevenue.doubleValue()) + " VND");
                    System.out.println("Updated revenue label: " + totalRevenue);
                }
            }
            // Recursively check nested containers
            else if (comp instanceof java.awt.Container) {
                updateAllLabelsInContainer((java.awt.Container) comp, customerCount, totalRevenue);
            }
        }
    }

    private void refreshHoaDonPanel(JPanel panel) {
        System.out.println("=== Refreshing Hoa Don panel ===");
        // Find and click "Tai danh sach" button
        findAndClickButton(panel, "Tai danh sach");

        // If button click doesn't work, directly load data
        for (int i = 0; i < panel.getComponentCount(); i++) {
            java.awt.Component comp = panel.getComponent(i);
            if (comp instanceof JTable) {
                JTable table = (JTable) comp;
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                List<Object[]> list = controller.getDanhSachHoaDon();
                for (Object[] row : list) {
                    model.addRow(row);
                }
                System.out.println("Directly loaded Hoa Don data");
                return;
            }
        }
    }

    private void findAndClickButton(java.awt.Container container, String buttonText) {
        System.out.println("[DEBUG] Looking for button with text: '" + buttonText + "'");
        for (int i = 0; i < container.getComponentCount(); i++) {
            java.awt.Component comp = container.getComponent(i);
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String btnText = btn.getText();
                System.out.println("[DEBUG] Found button: '" + btnText + "'");
                System.out.println("[DEBUG] Comparing with target: '" + buttonText + "' -> equals: " + buttonText.equals(btnText));
                if (buttonText.equals(btnText)) {
                    System.out.println("Clicking " + buttonText + " button");
                    btn.doClick();
                    return;
                }
            } else if (comp instanceof java.awt.Container) {
                // Recursively search in sub-containers
                findAndClickButton((java.awt.Container) comp, buttonText);
            }
        }
        System.out.println(buttonText + " button not found");
    }

    private JPanel createCard(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        JLabel lblTitle = new JLabel(title);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }
}
