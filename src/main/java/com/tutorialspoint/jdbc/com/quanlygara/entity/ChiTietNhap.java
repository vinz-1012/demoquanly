package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietNhap")
public class ChiTietNhap {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHopDong")
    private HopDongCungCap hopDongCungCap;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPT")
    private PhuTung phuTung;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 10, scale = 2)
    private BigDecimal donGia;

    // Constructors
    public ChiTietNhap() {}

    public ChiTietNhap(HopDongCungCap hopDongCungCap, PhuTung phuTung, Integer soLuong, BigDecimal donGia) {
        this.hopDongCungCap = hopDongCungCap;
        this.phuTung = phuTung;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters and Setters
    public HopDongCungCap getHopDongCungCap() { return hopDongCungCap; }
    public void setHopDongCungCap(HopDongCungCap hopDongCungCap) { this.hopDongCungCap = hopDongCungCap; }

    public PhuTung getPhuTung() { return phuTung; }
    public void setPhuTung(PhuTung phuTung) { this.phuTung = phuTung; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
}
