package com.tutorialspoint.jdbc.com.quanlygara.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhuTung")
public class PhuTung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPT")
    private Integer maPT;

    @Column(name = "TenPT", length = 100)
    private String tenPT;

    @Column(name = "Gia", precision = 10, scale = 2)
    private BigDecimal gia;

    @Column(name = "TonKho")
    private Integer tonKho;

    @OneToMany(mappedBy = "phuTung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietNhap> chiTietNhapList;

    // Constructors
    public PhuTung() {}

    public PhuTung(String tenPT, BigDecimal gia, Integer tonKho) {
        this.tenPT = tenPT;
        this.gia = gia;
        this.tonKho = tonKho;
    }

    // Getters and Setters
    public Integer getMaPT() { return maPT; }
    public void setMaPT(Integer maPT) { this.maPT = maPT; }

    public String getTenPT() { return tenPT; }
    public void setTenPT(String tenPT) { this.tenPT = tenPT; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public Integer getTonKho() { return tonKho; }
    public void setTonKho(Integer tonKho) { this.tonKho = tonKho; }

    public List<ChiTietNhap> getChiTietNhapList() { return chiTietNhapList; }
    public void setChiTietNhapList(List<ChiTietNhap> chiTietNhapList) { this.chiTietNhapList = chiTietNhapList; }

    @Override
    public String toString() {
        return maPT + " - " + tenPT;
    }
}
