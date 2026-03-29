package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.KhachHang;

public class KhachHangDAO extends GenericDAO<KhachHang> {
    public KhachHangDAO() {
        super(KhachHang.class);
    }

    // Additional methods
    public List<KhachHang> findByTenKH(String tenKH) {
        return executeQuery("SELECT k FROM KhachHang k WHERE k.tenKH LIKE ?1", "%" + tenKH + "%");
    }

    public KhachHang findByDienThoai(String dienThoai) {
        List<KhachHang> result = executeQuery("SELECT k FROM KhachHang k WHERE k.dienThoai = ?1", dienThoai);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<KhachHang> findAll() {
        return executeQuery("SELECT k FROM KhachHang k ORDER BY k.maKH");
    }
}