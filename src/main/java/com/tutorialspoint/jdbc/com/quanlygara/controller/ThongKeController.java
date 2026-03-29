package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.math.BigDecimal;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.DichVu;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HopDongCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhuTung;
import com.tutorialspoint.jdbc.com.quanlygara.service.ThongKeService;

public class ThongKeController {
    private final ThongKeService thongKeService = new ThongKeService();

    public BigDecimal getTongDoanhThu() {
        return thongKeService.getTongDoanhThu();
    }

    public Long getTongKhachHang() {
        return thongKeService.getTongKhachHang();
    }

    public List<Object[]> getSoLuongPhieuTheoTrangThai() {
        return thongKeService.getSoLuongPhieuTheoTrangThai();
    }

    public List<PhieuSuaChua> timPhieuTheoTrangThai(String trangThai) {
        return thongKeService.timPhieuTheoTrangThai(trangThai);
    }

    public List<Object[]> getChiTietNhapHang() {
        return thongKeService.getChiTietNhapHang();
    }

    public List<Object[]> getTongTienNhapHang() {
        return thongKeService.getTongTienNhapHang();
    }

    public BigDecimal getChiPhiNhanVien() {
        return thongKeService.getChiPhiNhanVien();
    }

    public BigDecimal getChiPhiPhuTung() {
        return thongKeService.getChiPhiPhuTung();
    }

    public BigDecimal getLoiNhuanTong() {
        return thongKeService.getLoiNhuanTong();
    }

    public List<Object[]> getDanhSachHoaDon() {
        return thongKeService.getDanhSachHoaDon();
    }

    public List<Object[]> getPhieuSuaVaDichVu() {
        return thongKeService.getPhieuSuaVaDichVu();
    }

    public List<Object[]> getTongTienTuChiTiet() {
        return thongKeService.getTongTienTuChiTiet();
    }

    public List<Object[]> getAllChiTietDichVu() {
        return thongKeService.getAllChiTietDichVu();
    }

    public void addChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        thongKeService.addChiTietDichVu(maPhieu, maDV, soLuong, donGia);
    }

    public void addChiTietDichVuWithServiceName(Integer maPhieu, String tenDV, Integer soLuong, BigDecimal donGia) {
        thongKeService.addChiTietDichVuWithServiceName(maPhieu, tenDV, soLuong, donGia);
    }

    public void updateChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        thongKeService.updateChiTietDichVu(maPhieu, maDV, soLuong, donGia);
    }

    public void deleteChiTietDichVu(Integer maPhieu, Integer maDV) {
        thongKeService.deleteChiTietDichVu(maPhieu, maDV);
    }

    public List<PhieuSuaChua> getAllPhieuSuaChua() {
        return thongKeService.getAllPhieuSuaChua();
    }

    public List<DichVu> getAllDichVu() {
        return thongKeService.getAllDichVu();
    }

    public List<Object[]> getAllChiTietNhapHang() {
        return thongKeService.getAllChiTietNhapHang();
    }

    public void addChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        thongKeService.addChiTietNhapHang(maHopDong, maPT, soLuong, donGia);
    }

    public void addChiTietNhapHangWithPartName(Integer maHopDong, String tenPT, Integer soLuong, BigDecimal donGia) {
        thongKeService.addChiTietNhapHangWithPartName(maHopDong, tenPT, soLuong, donGia);
    }

    public void updateChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        thongKeService.updateChiTietNhapHang(maHopDong, maPT, soLuong, donGia);
    }

    public void deleteChiTietNhapHang(Integer maHopDong, Integer maPT) {
        thongKeService.deleteChiTietNhapHang(maHopDong, maPT);
    }

    public void deleteHoaDon(Integer maHD) {
        thongKeService.deleteHoaDon(maHD);
    }

    public List<HopDongCungCap> getAllHopDongCungCap() {
        return thongKeService.getAllHopDongCungCap();
    }

    public List<PhuTung> getAllPhuTung() {
        return thongKeService.getAllPhuTung();
    }
}
