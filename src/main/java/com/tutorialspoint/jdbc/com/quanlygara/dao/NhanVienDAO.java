package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;

public class NhanVienDAO extends GenericDAO<NhanVien> {
    public NhanVienDAO() {
        super(NhanVien.class);
    }

    // Additional methods
    public List<NhanVien> findByTenNV(String tenNV) {
        return executeQuery("SELECT n FROM NhanVien n WHERE n.tenNV LIKE ?1", "%" + tenNV + "%");
    }

    public List<NhanVien> findByChucVu(String chucVu) {
        return executeQuery("SELECT n FROM NhanVien n WHERE n.chucVu = ?1", chucVu);
    }

    @Override
    public List<NhanVien> findAll() {
        return executeQuery("SELECT n FROM NhanVien n ORDER BY n.maNV");
    }
}