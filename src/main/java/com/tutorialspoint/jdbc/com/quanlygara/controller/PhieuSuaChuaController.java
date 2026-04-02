package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.time.LocalDate;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dto.PhieuSuaChuaDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhanVien;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.Xe;
import com.tutorialspoint.jdbc.com.quanlygara.service.NhanVienService;
import com.tutorialspoint.jdbc.com.quanlygara.service.PhieuSuaChuaService;
import com.tutorialspoint.jdbc.com.quanlygara.service.XeService;

public class PhieuSuaChuaController {
    private PhieuSuaChuaService phieuSuaChuaService = new PhieuSuaChuaService();
    private XeService xeService = new XeService();
    private NhanVienService nhanVienService = new NhanVienService();

    public void addPhieuSuaChua(Integer maXe, Integer maNV, LocalDate ngayNhan, LocalDate ngayTra, String trangThai, String ghiChu) {
        Xe xe = xeService.findById(maXe);
        NhanVien nhanVien = nhanVienService.findById(maNV);
        PhieuSuaChua phieuSuaChua = new PhieuSuaChua(xe, nhanVien, ngayNhan, ngayTra, trangThai, ghiChu);
        phieuSuaChuaService.save(phieuSuaChua);
    }

    public PhieuSuaChuaDTO getPhieuSuaChuaById(Integer id) {
        PhieuSuaChua phieuSuaChua = phieuSuaChuaService.findById(id);
        return phieuSuaChua != null ? phieuSuaChuaService.toDTO(phieuSuaChua) : null;
    }

    public List<PhieuSuaChuaDTO> getAllPhieuSuaChua() {
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaService.findAll();
        return phieuSuaChuaService.toDTOList(phieuSuaChuas);
    }

    public void updatePhieuSuaChua(Integer maPhieu, Integer maXe, Integer maNV, LocalDate ngayNhan, LocalDate ngayTra, String trangThai, String ghiChu) {
        PhieuSuaChua phieuSuaChua = phieuSuaChuaService.findById(maPhieu);
        if (phieuSuaChua != null) {
            Xe xe = xeService.findById(maXe);
            phieuSuaChua.setXe(xe);
            NhanVien nhanVien = nhanVienService.findById(maNV);
            phieuSuaChua.setNhanVien(nhanVien);
            phieuSuaChua.setNgayNhan(ngayNhan);
            phieuSuaChua.setNgayTra(ngayTra);
            phieuSuaChua.setTrangThai(trangThai);
            phieuSuaChua.setGhiChu(ghiChu);
            phieuSuaChuaService.update(phieuSuaChua);
        }
    }

    public void deletePhieuSuaChua(Integer maPhieu) {
        System.out.println("=== Controller: deletePhieuSuaChua called with maPhieu: " + maPhieu + " ===");
        PhieuSuaChua phieuSuaChua = phieuSuaChuaService.findById(maPhieu);
        if (phieuSuaChua != null) {
            System.out.println("Controller: Found PhieuSuaChua, calling service delete...");
            phieuSuaChuaService.delete(phieuSuaChua);
            System.out.println("Controller: Service delete completed");
        } else {
            System.out.println("Controller: PhieuSuaChua not found with maPhieu: " + maPhieu);
        }
    }

    public List<PhieuSuaChuaDTO> getPhieuSuaChuaByTrangThai(String trangThai) {
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaService.findByTrangThai(trangThai);
        return phieuSuaChuaService.toDTOList(phieuSuaChuas);
    }

    public List<PhieuSuaChuaDTO> getPhieuSuaChuaByMaXe(Integer maXe) {
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaService.findByMaXe(maXe);
        return phieuSuaChuaService.toDTOList(phieuSuaChuas);
    }

    public List<PhieuSuaChuaDTO> getPhieuSuaChuaByMaNV(Integer maNV) {
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaService.findByMaNV(maNV);
        return phieuSuaChuaService.toDTOList(phieuSuaChuas);
    }

    public List<PhieuSuaChuaDTO> getPhieuSuaChuaByMaPhieu(Integer maPhieu) {
        PhieuSuaChua phieuSuaChua = phieuSuaChuaService.findById(maPhieu);
        if (phieuSuaChua != null) {
            return List.of(phieuSuaChuaService.toDTO(phieuSuaChua));
        }
        return List.of();
    }

    public List<PhieuSuaChuaDTO> getPhieuSuaChuaByTenKH(String tenKH) {
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaService.findByTenKH(tenKH);
        return phieuSuaChuaService.toDTOList(phieuSuaChuas);
    }
}
