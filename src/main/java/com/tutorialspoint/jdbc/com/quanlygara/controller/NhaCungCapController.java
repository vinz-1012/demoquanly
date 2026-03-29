package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.NhaCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HopDongCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.service.NhaCungCapService;
import com.tutorialspoint.jdbc.com.quanlygara.service.ThongKeService;

public class NhaCungCapController {
    private final NhaCungCapService nhaCungCapService = new NhaCungCapService();

    public void addNhaCungCap(String tenNCC, String dienThoai, String diaChi) {
        NhaCungCap nhaCungCap = new NhaCungCap(tenNCC, dienThoai, diaChi);
        nhaCungCapService.save(nhaCungCap);

        // Tạo hợp đồng cung cấp mặc định cho NCC vừa tạo
        // Lấy lại NCC vừa lưu để đảm bảo có mã NCC (nếu cần)
        List<NhaCungCap> allNCC = nhaCungCapService.findAll();
        NhaCungCap nccMoi = allNCC.get(allNCC.size() - 1); // NCC mới nhất

        HopDongCungCap hopDong = new HopDongCungCap();
        hopDong.setNhaCungCap(nccMoi);
        hopDong.setNgayKy(java.time.LocalDate.now());
        hopDong.setNgayHetHan(java.time.LocalDate.now().plusYears(1));

        // Lưu hợp đồng qua ThongKeService
        ThongKeService thongKeService = new ThongKeService();
        thongKeService.saveHopDongCungCap(hopDong);
    }

    public List<NhaCungCap> getAllNhaCungCap() {
        return nhaCungCapService.findAll();
    }

    public void updateNhaCungCap(Integer maNCC, String tenNCC, String dienThoai, String diaChi) {
        NhaCungCap nhaCungCap = nhaCungCapService.findById(maNCC);
        if (nhaCungCap != null) {
            nhaCungCap.setTenNCC(tenNCC);
            nhaCungCap.setDienThoai(dienThoai);
            nhaCungCap.setDiaChi(diaChi);
            nhaCungCapService.update(nhaCungCap);
        }
    }

    public void deleteNhaCungCap(Integer maNCC) {
        NhaCungCap nhaCungCap = nhaCungCapService.findById(maNCC);
        if (nhaCungCap != null) {
            nhaCungCapService.delete(nhaCungCap);
        }
    }

    public List<NhaCungCap> searchByTenNCC(String tenNCC) {
        return nhaCungCapService.findByTenNCC(tenNCC);
    }
}
