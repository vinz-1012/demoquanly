package com.tutorialspoint.jdbc.com.quanlygara;

import com.tutorialspoint.jdbc.com.quanlygara.view.MainForm;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm view = new MainForm();
            view.setVisible(true);
        });
    }
}