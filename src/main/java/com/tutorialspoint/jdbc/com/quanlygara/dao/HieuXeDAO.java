package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.HieuXe;

public class HieuXeDAO extends GenericDAO<HieuXe> {
    public HieuXeDAO() {
        super(HieuXe.class);
    }

    // Additional methods
    public List<HieuXe> findByTenHieuXe(String tenHieuXe) {
        return executeQuery("SELECT h FROM HieuXe h WHERE h.tenHieuXe LIKE ?1", "%" + tenHieuXe + "%");
    }
}