package com.tutorialspoint.jdbc.com.quanlygara.view;

import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;
import com.tutorialspoint.jdbc.com.quanlygara.service.NhanVienService;
import com.tutorialspoint.jdbc.com.quanlygara.util.UIUtils;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginDialog extends JDialog {
    // Login form - only allows managers to login
    private JTextField txtUsername; // Username input field
    private JPasswordField txtPassword; // Password input field
    private JButton btnLogin, btnExit;
    private NhanVienService nhanVienService;
    private NhanVien loggedInUser;
    private boolean loginSuccess = false; // Flag to check if login is successful

    public LoginDialog(Frame parent) {
        super(parent, "Đăng Nhập Hệ Thống", true);
        this.nhanVienService = new NhanVienService();
        initComponents();
        setupEvents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        try {
            setSize(450, 350);
            setResizable(false);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            // Header panel with gradient
            JPanel headerPanel = new UIUtils.RoundedPanel(20, new Color(41, 128, 185), new Color(52, 152, 219), true) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(52, 152, 219, 200), 
                                                              0, getHeight(), new Color(41, 128, 185, 200));
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
            };
            headerPanel.setPreferredSize(new Dimension(0, 80));
            headerPanel.setLayout(new BorderLayout());
            
            JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            headerPanel.add(titleLabel, BorderLayout.CENTER);

            // Main content panel
            JPanel mainPanel = new UIUtils.RoundedPanel(15, Color.WHITE);
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Username
            gbc.gridx = 0; gbc.gridy = 0;
            JLabel lblUsername = new JLabel("Tên đăng nhập:");
            lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
            mainPanel.add(lblUsername, gbc);

            gbc.gridx = 1; gbc.gridy = 0;
            txtUsername = new JTextField(15);
            txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
            txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            mainPanel.add(txtUsername, gbc);

            // Password
            gbc.gridx = 0; gbc.gridy = 1;
            JLabel lblPassword = new JLabel("Mật khẩu:");
            lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            mainPanel.add(lblPassword, gbc);

            gbc.gridx = 1; gbc.gridy = 1;
            txtPassword = new JPasswordField(15);
            txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
            mainPanel.add(txtPassword, gbc);

            // Button panel
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            
            btnLogin = new JButton("Đăng Nhập");
            btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
            btnLogin.setBackground(new Color(41, 128, 185));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFocusPainted(false);
            btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnExit = new JButton("Thoát");
            btnExit.setFont(new Font("Arial", Font.BOLD, 14));
            btnExit.setBackground(new Color(231, 76, 60));
            btnExit.setForeground(Color.WHITE);
            btnExit.setFocusPainted(false);
            btnExit.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
            btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));

            buttonPanel.add(btnLogin);
            buttonPanel.add(btnExit);
            mainPanel.add(buttonPanel, gbc);

            // Add panels to dialog
            JPanel outerPanel = new JPanel(new BorderLayout());
            outerPanel.setBackground(new Color(245, 247, 250));
            outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            outerPanel.add(headerPanel, BorderLayout.NORTH);
            outerPanel.add(mainPanel, BorderLayout.CENTER);

            add(outerPanel, BorderLayout.CENTER);

            // Set default button
            getRootPane().setDefaultButton(btnLogin);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khởi tạo giao diện đăng nhập: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupEvents() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Enter key to login
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };

        txtUsername.addKeyListener(enterKeyListener);
        txtPassword.addKeyListener(enterKeyListener);
    }

    private void performLogin() {
        try {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập tên đăng nhập và mật khẩu!", 
                    "Thông báo", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Sử dụng TenNV làm username và mật khẩu mặc định là "123456"
            // Chỉ cho phép nhân viên có chức vụ "Quản lý" đăng nhập
            NhanVien user = nhanVienService.login(username, password);
            
            if (user != null) {
                loggedInUser = user;
                loginSuccess = true;
                JOptionPane.showMessageDialog(this, 
                    "Đăng nhập thành công! Chào mừng Quản lý " + user.getTenNV(), 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                // Check if user exists but is not manager
                try {
                    List<NhanVien> employees = nhanVienService.findByTenNV(username.trim());
                    if (!employees.isEmpty()) {
                        NhanVien employee = employees.get(0);
                        String chucVu = employee.getChucVu();
                        if (chucVu != null && !chucVu.equalsIgnoreCase("Quan ly") && !chucVu.equals("Quản lý")) {
                            JOptionPane.showMessageDialog(this, 
                                "Tài khoản này không có quyền quản lý!\nChỉ nhân viên có chức vụ 'Quản lý' mới được đăng nhập.", 
                                "Lỗi phân quyền", 
                                JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, 
                                "Tên đăng nhập hoặc mật khẩu không đúng!", 
                                "Lỗi đăng nhập", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Tên đăng nhập hoặc mật khẩu không đúng!", 
                            "Lỗi đăng nhập", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Tên đăng nhập hoặc mật khẩu không đúng!", 
                        "Lỗi đăng nhập", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
                txtPassword.setText("");
                txtPassword.requestFocus();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi hệ thống: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public NhanVien getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}
