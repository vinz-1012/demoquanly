package com.tutorialspoint.jdbc.com.quanlygara.view;

import com.tutorialspoint.jdbc.com.quanlygara.view.subform.KhachHangSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.XeSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.NhanVienSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.PhieuSuaChuaSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.NhaCungCapSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.ThongKeSubForm;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class MainForm extends JFrame {
    private KhachHangSubForm khachHangTab;
    private XeSubForm xeTab;
    private NhanVienSubForm nhanVienTab;
    private PhieuSuaChuaSubForm phieuTab;
    private NhaCungCapSubForm nhaCungCapTab;
    private ThongKeSubForm thongKeTab;

    public MainForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quan ly Gara - MainForm");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("He thong quan ly gara - MainForm/SubForm", SwingConstants.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        khachHangTab = new KhachHangSubForm();
        xeTab = new XeSubForm();
        nhanVienTab = new NhanVienSubForm();
        phieuTab = new PhieuSuaChuaSubForm();
        nhaCungCapTab = new NhaCungCapSubForm();
        thongKeTab = new ThongKeSubForm();

        tabs.addTab("Khach Hang", khachHangTab);
        tabs.addTab("Xe", xeTab);
        tabs.addTab("Nhan Vien", nhanVienTab);
        tabs.addTab("Phieu Sua Chua", phieuTab);
        tabs.addTab("Nha Cung Cap", nhaCungCapTab);
        tabs.addTab("Thong Ke JPQL", thongKeTab);

        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            if (idx == 1) { // Xe
                xeTab.refresh();
            } else if (idx == 0) { // Khach Hang
                khachHangTab.refresh();
            } else if (idx == 2) { // Nhan Vien
                nhanVienTab.refresh();
            } else if (idx == 3) { // Phieu Sua Chua
                phieuTab.refresh();
            } else if (idx == 5) { // Thong Ke
                thongKeTab.refresh();
            }
        });

        JPanel content = new JPanel(new BorderLayout());
        content.add(header, BorderLayout.NORTH);
        content.add(tabs, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }
}
