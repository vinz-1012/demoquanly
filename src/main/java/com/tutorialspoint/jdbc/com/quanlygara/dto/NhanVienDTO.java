package com.tutorialspoint.jdbc.com.quanlygara.dto;

import java.math.BigDecimal;

public class NhanVienDTO {
    private Integer maNV;
    private String tenNV;
    private String dienThoai;
    private String chucVu;
    private BigDecimal luong;

    // Constructors
    public NhanVienDTO() {}

    public NhanVienDTO(Integer maNV, String tenNV, String dienThoai, String chucVu, BigDecimal luong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.dienThoai = dienThoai;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    // Getters and Setters
    public Integer getMaNV() { return maNV; }
    public void setMaNV(Integer maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public BigDecimal getLuong() { return luong; }
    public void setLuong(BigDecimal luong) { this.luong = luong; }

    @Override
    public String toString() {
        return tenNV != null ? tenNV : "[Chua dat ten]";
    }
}
