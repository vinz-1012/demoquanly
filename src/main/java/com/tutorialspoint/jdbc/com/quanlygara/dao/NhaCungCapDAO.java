package com.tutorialspoint.jdbc.com.quanlygara.dao;

import com.tutorialspoint.jdbc.com.quanlygara.entity.NhaCungCap;
import java.util.List;

public class NhaCungCapDAO extends GenericDAO<NhaCungCap> {
    public NhaCungCapDAO() {
        super(NhaCungCap.class);
    }

    public List<NhaCungCap> findByTenNCC(String tenNCC) {
        return executeQuery("SELECT n FROM NhaCungCap n WHERE n.tenNCC LIKE ?1", "%" + tenNCC + "%");
    }
}
