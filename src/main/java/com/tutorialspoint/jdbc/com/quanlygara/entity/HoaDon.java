package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHD")
    private Integer maHD;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MaPhieu", unique = true)
    private PhieuSuaChua phieuSuaChua;

    @Column(name = "TongTien", precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "NgayThanhToan")
    private LocalDate ngayThanhToan;

    @Column(name = "PhuongThucThanhToan", length = 50)
    private String phuongThucThanhToan;

    // Constructors
    public HoaDon() {}

    public HoaDon(PhieuSuaChua phieuSuaChua, BigDecimal tongTien, LocalDate ngayThanhToan, String phuongThucThanhToan) {
        this.phieuSuaChua = phieuSuaChua;
        this.tongTien = tongTien;
        this.ngayThanhToan = ngayThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    // Getters and Setters
    public Integer getMaHD() { return maHD; }
    public void setMaHD(Integer maHD) { this.maHD = maHD; }

    public PhieuSuaChua getPhieuSuaChua() { return phieuSuaChua; }
    public void setPhieuSuaChua(PhieuSuaChua phieuSuaChua) { this.phieuSuaChua = phieuSuaChua; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public LocalDate getNgayThanhToan() { return ngayThanhToan; }
    public void setNgayThanhToan(LocalDate ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }

    public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }
}
