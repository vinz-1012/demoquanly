package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;

public class PhieuSuaChuaDAO extends GenericDAO<PhieuSuaChua> {
    public PhieuSuaChuaDAO() {
        super(PhieuSuaChua.class);
    }

    // Additional methods
    public List<PhieuSuaChua> findByTrangThai(String trangThai) {
        return executeQuery("SELECT p FROM PhieuSuaChua p LEFT JOIN FETCH p.xe LEFT JOIN FETCH p.xe.khachHang LEFT JOIN FETCH p.nhanVien WHERE p.trangThai = ?1", trangThai);
    }

    public List<PhieuSuaChua> findByMaXe(Integer maXe) {
        return executeQuery("SELECT p FROM PhieuSuaChua p LEFT JOIN FETCH p.xe LEFT JOIN FETCH p.xe.khachHang LEFT JOIN FETCH p.nhanVien WHERE p.xe.maXe = ?1", maXe);
    }

    public List<PhieuSuaChua> findByMaNV(Integer maNV) {
        return executeQuery("SELECT p FROM PhieuSuaChua p LEFT JOIN FETCH p.xe LEFT JOIN FETCH p.xe.khachHang LEFT JOIN FETCH p.nhanVien WHERE p.nhanVien.maNV = ?1", maNV);
    }

    @Override
    public List<PhieuSuaChua> findAll() {
        return executeQuery("SELECT p FROM PhieuSuaChua p LEFT JOIN FETCH p.xe LEFT JOIN FETCH p.xe.khachHang LEFT JOIN FETCH p.nhanVien ORDER BY p.maPhieu");
    }
    public List<PhieuSuaChua> findByTenKH(String tenKH) {
        return executeQuery("SELECT p FROM PhieuSuaChua p LEFT JOIN FETCH p.xe LEFT JOIN FETCH p.xe.khachHang LEFT JOIN FETCH p.nhanVien WHERE p.xe.khachHang.tenKH LIKE ?1", "%" + tenKH + "%");
    }
}

