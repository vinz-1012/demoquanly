package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer maNV;

    @Column(name = "TenNV", length = 100)
    private String tenNV;

    @Column(name = "DienThoai", length = 20)
    private String dienThoai;

    @Column(name = "ChucVu", length = 50)
    private String chucVu;

    @Column(name = "Luong", precision = 10, scale = 2)
    private BigDecimal luong;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhieuSuaChua> phieuSuaChuaList;

    // Constructors
    public NhanVien() {}

    public NhanVien(String tenNV, String dienThoai, String chucVu, BigDecimal luong) {
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

    public List<PhieuSuaChua> getPhieuSuaChuaList() { return phieuSuaChuaList; }
    public void setPhieuSuaChuaList(List<PhieuSuaChua> phieuSuaChuaList) { this.phieuSuaChuaList = phieuSuaChuaList; }
}
