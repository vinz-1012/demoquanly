package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietDichVu")
public class ChiTietDichVu {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhieu")
    private PhieuSuaChua phieuSuaChua;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDV")
    private DichVu dichVu;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 10, scale = 2)
    private BigDecimal donGia;

    // Constructors
    public ChiTietDichVu() {}

    public ChiTietDichVu(PhieuSuaChua phieuSuaChua, DichVu dichVu, Integer soLuong, BigDecimal donGia) {
        this.phieuSuaChua = phieuSuaChua;
        this.dichVu = dichVu;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters and Setters
    public PhieuSuaChua getPhieuSuaChua() { return phieuSuaChua; }
    public void setPhieuSuaChua(PhieuSuaChua phieuSuaChua) { this.phieuSuaChua = phieuSuaChua; }

    public DichVu getDichVu() { return dichVu; }
    public void setDichVu(DichVu dichVu) { this.dichVu = dichVu; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
}
