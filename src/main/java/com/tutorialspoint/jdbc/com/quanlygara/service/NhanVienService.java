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
}
