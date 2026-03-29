package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.math.BigDecimal;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dao.ThongKeDAO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.DichVu;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HopDongCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhuTung;

public class ThongKeService {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    public void saveHopDongCungCap(HopDongCungCap hopDong) {
        thongKeDAO.saveHopDongCungCap(hopDong);
    }

    public BigDecimal getTongDoanhThu() {
        return thongKeDAO.getTongDoanhThu();
    }

    public Long getTongKhachHang() {
        return thongKeDAO.getTongKhachHang();
    }

    public List<Object[]> getSoLuongPhieuTheoTrangThai() {
        return thongKeDAO.getSoLuongPhieuTheoTrangThai();
    }

    public List<PhieuSuaChua> timPhieuTheoTrangThai(String trangThai) {
        return thongKeDAO.timPhieuTheoTrangThai(trangThai);
    }

    public List<Object[]> getChiTietNhapHang() {
        return thongKeDAO.getChiTietNhapHang();
    }

    public List<Object[]> getTongTienNhapHang() {
        return thongKeDAO.getTongTienNhapHang();
    }

    public BigDecimal getChiPhiNhanVien() {
        return thongKeDAO.getChiPhiNhanVien();
    }

    public BigDecimal getChiPhiPhuTung() {
        return thongKeDAO.getChiPhiPhuTung();
    }

    public BigDecimal getLoiNhuanTong() {
        return thongKeDAO.getLoiNhuanTong();
    }

    public List<Object[]> getDanhSachHoaDon() {
        return thongKeDAO.getDanhSachHoaDon();
    }

    public List<Object[]> getPhieuSuaVaDichVu() {
        return thongKeDAO.getPhieuSuaVaDichVu();
    }

    public List<Object[]> getTongTienTuChiTiet() {
        return thongKeDAO.getTongTienTuChiTiet();
    }

    public List<Object[]> getAllChiTietDichVu() {
        return thongKeDAO.getAllChiTietDichVu();
    }

    public void addChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.addChiTietDichVu(maPhieu, maDV, soLuong, donGia);
    }

    public void addChiTietDichVuWithServiceName(Integer maPhieu, String tenDV, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.addChiTietDichVuWithServiceName(maPhieu, tenDV, soLuong, donGia);
    }

    public void updateChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.updateChiTietDichVu(maPhieu, maDV, soLuong, donGia);
    }

    public void deleteChiTietDichVu(Integer maPhieu, Integer maDV) {
        thongKeDAO.deleteChiTietDichVu(maPhieu, maDV);
    }

    public List<PhieuSuaChua> getAllPhieuSuaChua() {
        return thongKeDAO.getAllPhieuSuaChua();
    }

    public List<DichVu> getAllDichVu() {
        return thongKeDAO.getAllDichVu();
    }

    public List<Object[]> getAllChiTietNhapHang() {
        return thongKeDAO.getAllChiTietNhapHang();
    }

    public void addChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.addChiTietNhapHang(maHopDong, maPT, soLuong, donGia);
    }

    public void addChiTietNhapHangWithPartName(Integer maHopDong, String tenPT, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.addChiTietNhapHangWithPartName(maHopDong, tenPT, soLuong, donGia);
    }

    public void updateChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        thongKeDAO.updateChiTietNhapHang(maHopDong, maPT, soLuong, donGia);
    }

    public void deleteChiTietNhapHang(Integer maHopDong, Integer maPT) {
        thongKeDAO.deleteChiTietNhapHang(maHopDong, maPT);
    }

    public void deleteHoaDon(Integer maHD) {
        thongKeDAO.deleteHoaDon(maHD);
    }

    public List<HopDongCungCap> getAllHopDongCungCap() {
        return thongKeDAO.getAllHopDongCungCap();
    }

    public List<PhuTung> getAllPhuTung() {
        return thongKeDAO.getAllPhuTung();
    }
}
