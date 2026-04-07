package com.tutorialspoint.jdbc.com.quanlygara.entity;

import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PhieuSuaChua")
public class PhieuSuaChua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhieu")
    private Integer maPhieu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaXe")
    private Xe xe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @Column(name = "NgayNhan")
    private LocalDate ngayNhan;

    @Column(name = "NgayTra")
    private LocalDate ngayTra;

    @Column(name = "TrangThai", length = 50)
    private String trangThai;

    @Column(name = "GhiChu", columnDefinition = "TEXT")
    private String ghiChu;

    @OneToMany(mappedBy = "phieuSuaChua", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietDichVu> chiTietDichVuList;

    @OneToOne(mappedBy = "phieuSuaChua", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private HoaDon hoaDon;

    // Constructors
    public PhieuSuaChua() {}

    public PhieuSuaChua(Xe xe, NhanVien nhanVien, LocalDate ngayNhan, LocalDate ngayTra, String trangThai, String ghiChu) {
        this.xe = xe;
        this.nhanVien = nhanVien;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public Integer getMaPhieu() { return maPhieu; }
    public void setMaPhieu(Integer maPhieu) { this.maPhieu = maPhieu; }

    public Xe getXe() { return xe; }
    public void setXe(Xe xe) { this.xe = xe; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public LocalDate getNgayNhan() { return ngayNhan; }
    public void setNgayNhan(LocalDate ngayNhan) { this.ngayNhan = ngayNhan; }

    public LocalDate getNgayTra() { return ngayTra; }
    public void setNgayTra(LocalDate ngayTra) { this.ngayTra = ngayTra; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public List<ChiTietDichVu> getChiTietDichVuList() { return chiTietDichVuList; }
    public void setChiTietDichVuList(List<ChiTietDichVu> chiTietDichVuList) { this.chiTietDichVuList = chiTietDichVuList; }

    public HoaDon getHoaDon() { return hoaDon; }
    public void setHoaDon(HoaDon hoaDon) { this.hoaDon = hoaDon; }

    @Override
    public String toString() {
        return maPhieu + " - " + xe.getBienSo() + " (" + trangThai + ")";
    }
}
