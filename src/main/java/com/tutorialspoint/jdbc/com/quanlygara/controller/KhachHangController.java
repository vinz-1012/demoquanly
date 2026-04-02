package com.tutorialspoint.jdbc.com.quanlygara.controller;

import com.tutorialspoint.jdbc.com.quanlygara.service.KhachHangService;
import com.tutorialspoint.jdbc.com.quanlygara.entity.KhachHang;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import java.util.List;

public class KhachHangController {
    private KhachHangService khachHangService = new KhachHangService();

    public void addKhachHang(String tenKH, String dienThoai, String email, String diaChi) {
        KhachHang khachHang = new KhachHang(tenKH, dienThoai, email, diaChi);
        khachHangService.save(khachHang);
    }

    public KhachHangDTO getKhachHangById(Integer id) {
        KhachHang khachHang = khachHangService.findById(id);
        return khachHang != null ? khachHangService.toDTO(khachHang) : null;
    }

    public List<KhachHangDTO> getAllKhachHang() {
        List<KhachHang> khachHangs = khachHangService.findAll();
        return khachHangService.toDTOList(khachHangs);
    }

    public void updateKhachHang(Integer maKH, String tenKH, String dienThoai, String email, String diaChi) {
        KhachHang khachHang = khachHangService.findById(maKH);
        if (khachHang != null) {
            khachHang.setTenKH(tenKH);
            khachHang.setDienThoai(dienThoai);
            khachHang.setEmail(email);
            khachHang.setDiaChi(diaChi);
            khachHangService.update(khachHang);
        }
    }

    public void deleteKhachHang(Integer maKH) {
        KhachHang khachHang = khachHangService.findById(maKH);
        if (khachHang == null) {
            throw new RuntimeException("Không tìm thấy khách hàng có mã: " + maKH);
        }
        
        if (!khachHangService.canDelete(khachHang)) {
            // Get detailed information about why cannot delete
            List<com.tutorialspoint.jdbc.com.quanlygara.entity.Xe> xeList = khachHangService.getXeByMaKH(maKH);
            StringBuilder errorMsg = new StringBuilder("Không thể xóa khách hàng '" + khachHang.getTenKH() + "' vì:\n");
            
            for (com.tutorialspoint.jdbc.com.quanlygara.entity.Xe xe : xeList) {
                if (!khachHangService.xeService.canDelete(xe)) {
                    errorMsg.append("- Có xe '").append(xe.getBienSo()).append("' đang có phiếu sửa chữa liên quan\n");
                } else {
                    errorMsg.append("- Có xe '").append(xe.getBienSo()).append("' chưa được xóa\n");
                }
            }
            
            throw new RuntimeException(errorMsg.toString());
        }
        
        khachHangService.delete(khachHang);
    }

    public List<KhachHangDTO> searchByTenKH(String tenKH) {
        List<KhachHang> khachHangs = khachHangService.findByTenKH(tenKH);
        return khachHangService.toDTOList(khachHangs);
    }
}
