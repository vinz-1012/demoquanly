package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.Xe;

public class XeDAO extends GenericDAO<Xe> {
    public XeDAO() {
        super(Xe.class);
    }

    // Additional methods
    public List<Xe> findByBienSo(String bienSo) {
        return executeQuery("SELECT x FROM Xe x LEFT JOIN FETCH x.khachHang LEFT JOIN FETCH x.hieuXe WHERE x.bienSo LIKE ?1", "%" + bienSo + "%");
    }

    public List<Xe> findByMaKH(Integer maKH) {
        return executeQuery("SELECT x FROM Xe x LEFT JOIN FETCH x.khachHang LEFT JOIN FETCH x.hieuXe WHERE x.khachHang.maKH = ?1", maKH);
    }

    public List<Xe> findByMaHieuXe(Integer maHieuXe) {
        return executeQuery("SELECT x FROM Xe x LEFT JOIN FETCH x.khachHang LEFT JOIN FETCH x.hieuXe WHERE x.hieuXe.maHieuXe = ?1", maHieuXe);
    }

    @Override
    public List<Xe> findAll() {
        return executeQuery("SELECT x FROM Xe x LEFT JOIN FETCH x.khachHang LEFT JOIN FETCH x.hieuXe ORDER BY x.maXe");
    }
}