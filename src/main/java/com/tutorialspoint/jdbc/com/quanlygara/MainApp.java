package com.tutorialspoint.jdbc.com.quanlygara;

import com.tutorialspoint.jdbc.com.quanlygara.view.MainForm;
import com.tutorialspoint.jdbc.com.quanlygara.view.LoginDialog;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class MainApp {
    // Entry point of application - Show login before main form
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                //  Run UI thread to avoid UI lag
                try {
                    // Show login form first
                    LoginDialog loginDialog = new LoginDialog(null);
                    loginDialog.setVisible(true);
                    
                    // Check if login was successful
                    if (loginDialog.isLoginSuccess()) {
                        NhanVien loggedInUser = loginDialog.getLoggedInUser();
                        
                        // Open main form after successful login
                        MainForm mainForm = new MainForm();
                        
                        mainForm.setVisible(true);

                        JOptionPane.showMessageDialog(mainForm,
                            "Chào mừng " + loggedInUser.getTenNV() + " (" + loggedInUser.getChucVu() + ")",
                            "Đăng nhập thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.exit(0);
                    }
                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Lỗi khởi tạo ứng dụng: " + e.getMessage(),
                        "Lỗi hệ thống",
                        JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            });
        } catch (Exception e) {
            System.err.println("Lỗi nghiêm trọng khi khởi tạo ứng dụng: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
