package com.tutorialspoint.jdbc.com.quanlygara.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Xe")
public class Xe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXe")
    private Integer maXe; // (PK)

    @Column(name = "BienSo", length = 20)
    private String bienSo;

    @Column(name = "MauXe", length = 50)
    private String mauXe;

    @Column(name = "NamSanXuat")
    private Integer namSanXuat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaHieuXe")
    private HieuXe hieuXe;

    @OneToMany(mappedBy = "xe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PhieuSuaChua> phieuSuaChuaList;

    // Constructors
    public Xe() {}

    public Xe(String bienSo, String mauXe, Integer namSanXuat, KhachHang khachHang, HieuXe hieuXe) {
        this.bienSo = bienSo;
        this.mauXe = mauXe;
        this.namSanXuat = namSanXuat;
        this.khachHang = khachHang;
        this.hieuXe = hieuXe;
    }

    // Getters and Setters
    public Integer getMaXe() { return maXe; }
    public void setMaXe(Integer maXe) { this.maXe = maXe; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public String getMauXe() { return mauXe; }
    public void setMauXe(String mauXe) { this.mauXe = mauXe; }

    public Integer getNamSanXuat() { return namSanXuat; }
    public void setNamSanXuat(Integer namSanXuat) { this.namSanXuat = namSanXuat; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public HieuXe getHieuXe() { return hieuXe; }
    public void setHieuXe(HieuXe hieuXe) { this.hieuXe = hieuXe; }

    public List<PhieuSuaChua> getPhieuSuaChuaList() { return phieuSuaChuaList; }
    public void setPhieuSuaChuaList(List<PhieuSuaChua> phieuSuaChuaList) { this.phieuSuaChuaList = phieuSuaChuaList; }
}
