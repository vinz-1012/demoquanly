package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.util.List;
import java.util.stream.Collectors;

import com.tutorialspoint.jdbc.com.quanlygara.dao.NhanVienDAO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.NhanVienDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public void save(NhanVien nhanVien) {
        nhanVienDAO.save(nhanVien);
    }

    public NhanVien findById(Integer id) {
        return nhanVienDAO.findById(id);
    }

    public List<NhanVien> findAll() {
        return nhanVienDAO.findAll();
    }

    public void update(NhanVien nhanVien) {
        nhanVienDAO.update(nhanVien);
    }

    public void delete(NhanVien nhanVien) {
        nhanVienDAO.delete(nhanVien);
    }

    public List<NhanVien> findByTenNV(String tenNV) {
        return nhanVienDAO.findByTenNV(tenNV);
    }

    public List<NhanVien> findByChucVu(String chucVu) {
        return nhanVienDAO.findByChucVu(chucVu);
    }

    // Convert to DTO
    public NhanVienDTO toDTO(NhanVien nhanVien) {
        return new NhanVienDTO(
            nhanVien.getMaNV(),
            nhanVien.getTenNV(),
            nhanVien.getDienThoai(),
            nhanVien.getChucVu(),
            nhanVien.getLuong()
        );
    }

    public List<NhanVienDTO> toDTOList(List<NhanVien> nhanViens) {
        return nhanViens.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Login method without creating new entities
    // Uses existing NhanVien table with TenNV as username
    // Default password: "123456" for all employees (can be customized)
    // Only allows employees with "Quan ly" or "Quản lý" role to login
    public NhanVien login(String username, String password) {
        // Authenticate employee - only managers can login
        try {
            if (username == null || username.trim().isEmpty()) {
                return null;
            }
            
            // Find employee by name (username)
            List<NhanVien> employees = findByTenNV(username.trim());
            
            if (employees.isEmpty()) {
                return null;
            }
            
            NhanVien employee = employees.get(0);
            
            //  Check if employee is manager
            String chucVu = employee.getChucVu();
            if (chucVu == null || (!chucVu.equalsIgnoreCase("Quan ly") && !chucVu.equals("Quản lý"))) {
                return null; // Not a manager, deny access
            }
            
            // Simple authentication: check if password is "123456" 
            // In production, you should hash passwords and store them properly
            if ("123456".equals(password)) {
                return employee; // Return manager employee
            }
            
            return null;
            
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đăng nhập: " + e.getMessage(), e);
        }
    }
}
