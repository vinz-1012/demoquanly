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
@Table(name = "DichVu")
public class DichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDV")
    private Integer maDV;

    @Column(name = "TenDV", length = 100)
    private String tenDV;

    @Column(name = "Gia", precision = 10, scale = 2)
    private BigDecimal gia;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    @OneToMany(mappedBy = "dichVu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietDichVu> chiTietDichVuList;

    // Constructors
    public DichVu() {}

    public DichVu(String tenDV, BigDecimal gia, String moTa) {
        this.tenDV = tenDV;
        this.gia = gia;
        this.moTa = moTa;
    }

    // Getters and Setters
    public Integer getMaDV() { return maDV; }
    public void setMaDV(Integer maDV) { this.maDV = maDV; }

    public String getTenDV() { return tenDV; }
    public void setTenDV(String tenDV) { this.tenDV = tenDV; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public List<ChiTietDichVu> getChiTietDichVuList() { return chiTietDichVuList; }
    public void setChiTietDichVuList(List<ChiTietDichVu> chiTietDichVuList) { this.chiTietDichVuList = chiTietDichVuList; }

    @Override
    public String toString() {
        return maDV + " - " + tenDV;
    }
}
