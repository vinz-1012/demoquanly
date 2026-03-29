package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    @Column(name = "TenKH", length = 100)
    private String tenKH;

    @Column(name = "DienThoai", length = 20)
    private String dienThoai;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "NgayDangKy")
    private LocalDateTime ngayDangKy;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Xe> xeList;

    // Constructors
    public KhachHang() {}

    public KhachHang(String tenKH, String dienThoai, String email, String diaChi) {
        this.tenKH = tenKH;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.ngayDangKy = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getMaKH() { return maKH; }
    public void setMaKH(Integer maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public LocalDateTime getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(LocalDateTime ngayDangKy) { this.ngayDangKy = ngayDangKy; }

    public List<Xe> getXeList() { return xeList; }
    public void setXeList(List<Xe> xeList) { this.xeList = xeList; }
}
