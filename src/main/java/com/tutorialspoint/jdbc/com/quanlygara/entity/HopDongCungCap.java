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
import jakarta.persistence.Table;

@Entity
@Table(name = "HopDongCungCap")
public class HopDongCungCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHopDong")
    private Integer maHopDong;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaNCC")
    private NhaCungCap nhaCungCap;

    @Column(name = "NgayKy")
    private LocalDate ngayKy;

    @Column(name = "NgayHetHan")
    private LocalDate ngayHetHan;

    @OneToMany(mappedBy = "hopDongCungCap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietNhap> chiTietNhapList;

    // Constructors
    public HopDongCungCap() {}

    public HopDongCungCap(NhaCungCap nhaCungCap, LocalDate ngayKy, LocalDate ngayHetHan) {
        this.nhaCungCap = nhaCungCap;
        this.ngayKy = ngayKy;
        this.ngayHetHan = ngayHetHan;
    }

    // Getters and Setters
    public Integer getMaHopDong() { return maHopDong; }
    public void setMaHopDong(Integer maHopDong) { this.maHopDong = maHopDong; }

    public NhaCungCap getNhaCungCap() { return nhaCungCap; }
    public void setNhaCungCap(NhaCungCap nhaCungCap) { this.nhaCungCap = nhaCungCap; }

    public LocalDate getNgayKy() { return ngayKy; }
    public void setNgayKy(LocalDate ngayKy) { this.ngayKy = ngayKy; }

    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }

    public List<ChiTietNhap> getChiTietNhapList() { return chiTietNhapList; }
    public void setChiTietNhapList(List<ChiTietNhap> chiTietNhapList) { this.chiTietNhapList = chiTietNhapList; }

    @Override
    public String toString() {
        return maHopDong + " - " + nhaCungCap.getTenNCC();
    }
}
