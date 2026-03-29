package com.tutorialspoint.jdbc.com.quanlygara.dto;

import java.time.LocalDate;

public class PhieuSuaChuaDTO {
    private Integer maPhieu;
    private Integer maXe;
    private String bienSo;
    private String tenKH;
    private Integer maNV;
    private String tenNV;
    private LocalDate ngayNhan;
    private LocalDate ngayTra;
    private String trangThai;
    private String ghiChu;

    // Constructors
    public PhieuSuaChuaDTO() {}

    public PhieuSuaChuaDTO(Integer maPhieu, Integer maXe, String bienSo, String tenKH, Integer maNV, String tenNV, LocalDate ngayNhan, LocalDate ngayTra, String trangThai, String ghiChu) {
        this.maPhieu = maPhieu;
        this.maXe = maXe;
        this.bienSo = bienSo;
        this.tenKH = tenKH;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public Integer getMaPhieu() { return maPhieu; }
    public void setMaPhieu(Integer maPhieu) { this.maPhieu = maPhieu; }

    public Integer getMaXe() { return maXe; }
    public void setMaXe(Integer maXe) { this.maXe = maXe; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public Integer getMaNV() { return maNV; }
    public void setMaNV(Integer maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public LocalDate getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(LocalDate ngayNhan) { this.ngayNhan = ngayNhan; }

    public LocalDate getNgayTra() { return ngayTra; }
    public void setNgayTra(LocalDate ngayTra) { this.ngayTra = ngayTra; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
