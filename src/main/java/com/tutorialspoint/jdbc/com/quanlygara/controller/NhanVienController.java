package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.math.BigDecimal;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dto.NhanVienDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;
import com.tutorialspoint.jdbc.com.quanlygara.service.NhanVienService;

public class NhanVienController {
    private NhanVienService nhanVienService = new NhanVienService();

    public void addNhanVien(String tenNV, String dienThoai, String chucVu, BigDecimal luong) {
        NhanVien nhanVien = new NhanVien(tenNV, dienThoai, chucVu, luong);
        nhanVienService.save(nhanVien);
    }

    public NhanVienDTO getNhanVienById(Integer id) {
        NhanVien nhanVien = nhanVienService.findById(id);
        return nhanVien != null ? nhanVienService.toDTO(nhanVien) : null;
    }

    public List<NhanVienDTO> getAllNhanVien() {
        List<NhanVien> nhanViens = nhanVienService.findAll();
        return nhanVienService.toDTOList(nhanViens);
    }

    public void updateNhanVien(Integer maNV, String tenNV, String dienThoai, String chucVu, BigDecimal luong) {
        NhanVien nhanVien = nhanVienService.findById(maNV);
        if (nhanVien != null) {
            nhanVien.setTenNV(tenNV);
            nhanVien.setDienThoai(dienThoai);
            nhanVien.setChucVu(chucVu);
            nhanVien.setLuong(luong);
            nhanVienService.update(nhanVien);
        }
    }

    public void deleteNhanVien(Integer maNV) {
        NhanVien nhanVien = nhanVienService.findById(maNV);
        if (nhanVien != null) {
            nhanVienService.delete(nhanVien);
        }
    }

    public List<NhanVienDTO> searchByTenNV(String tenNV) {
        List<NhanVien> nhanViens = nhanVienService.findByTenNV(tenNV);
        return nhanVienService.toDTOList(nhanViens);
    }

    public List<NhanVienDTO> getNhanVienByChucVu(String chucVu) {
        List<NhanVien> nhanViens = nhanVienService.findByChucVu(chucVu);
        return nhanVienService.toDTOList(nhanViens);
    }
}
