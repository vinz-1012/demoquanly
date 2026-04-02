package com.tutorialspoint.jdbc.com.quanlygara.view;

import com.tutorialspoint.jdbc.com.quanlygara.view.subform.KhachHangSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.XeSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.NhanVienSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.PhieuSuaChuaSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.NhaCungCapSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.subform.ThongKeSubForm;
import com.tutorialspoint.jdbc.com.quanlygara.util.UIUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

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
        setTitle("Hệ Thống Quản Lý Gara Ô Tô");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create rounded header panel with gradient background
        JPanel headerPanel = new UIUtils.RoundedPanel(20, new Color(41, 128, 185), new Color(52, 152, 219), true) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Add subtle gradient overlay
                GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 152, 219, 200), 
                                                          0, getHeight(), new Color(41, 128, 185, 200));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Add subtle inner shadow effect
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 18, 18);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new java.awt.Dimension(0, 80));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("HỆ THỐNG QUẢN LÝ GARA Ô TÔ", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setForeground(Color.WHITE);
        headerPanel.add(header, BorderLayout.CENTER);

        // Create modern tabbed pane with rounded corners
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 14));
        tabs.setBackground(new Color(245, 247, 250));
        tabs.setBorder(new UIUtils.RoundedBorder(15, new Color(200, 200, 200), 1));
        
        // Initialize all tabs
        khachHangTab = new KhachHangSubForm();
        xeTab = new XeSubForm();
        nhanVienTab = new NhanVienSubForm();
        phieuTab = new PhieuSuaChuaSubForm();
        nhaCungCapTab = new NhaCungCapSubForm();
        thongKeTab = new ThongKeSubForm();

        // Add tabs with better names
        tabs.addTab("Khách Hàng", khachHangTab);
        tabs.addTab("Xe", xeTab);
        tabs.addTab("Nhân Viên", nhanVienTab);
        tabs.addTab("Phiếu Sửa Chữa", phieuTab);
        tabs.addTab("Nhà Cung Cấp", nhaCungCapTab);
        tabs.addTab("Thống Kê & Báo Cáo", thongKeTab);

        // Add change listener for tab refreshing
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

        // Create main content panel with rounded corners and shadow
        getContentPane().setBackground(new Color(245, 247, 250)); // Light background
        
        UIUtils.RoundedPanel content = new UIUtils.RoundedPanel(20, Color.WHITE);
        content.setLayout(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add subtle shadow effect
        content.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new UIUtils.RoundedBorder(20, new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        content.add(headerPanel, BorderLayout.NORTH);
        content.add(tabs, BorderLayout.CENTER);

        // Create outer panel for shadow effect
        JPanel outerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
            }
        };
        outerPanel.setBackground(new Color(245, 247, 250));
        outerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        outerPanel.add(content, BorderLayout.CENTER);

        add(outerPanel, BorderLayout.CENTER);
    }
}
