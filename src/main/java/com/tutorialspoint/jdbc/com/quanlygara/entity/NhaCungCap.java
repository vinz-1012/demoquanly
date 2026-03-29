package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "NhaCungCap")
public class NhaCungCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNCC")
    private Integer maNCC;

    @Column(name = "TenNCC", length = 100)
    private String tenNCC;

    @Column(name = "DienThoai", length = 20)
    private String dienThoai;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HopDongCungCap> hopDongCungCapList;

    // Constructors
    public NhaCungCap() {}

    public NhaCungCap(String tenNCC, String dienThoai, String diaChi) {
        this.tenNCC = tenNCC;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    // Getters and Setters
    public Integer getMaNCC() { return maNCC; }
    public void setMaNCC(Integer maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public List<HopDongCungCap> getHopDongCungCapList() { return hopDongCungCapList; }
    public void setHopDongCungCapList(List<HopDongCungCap> hopDongCungCapList) { this.hopDongCungCapList = hopDongCungCapList; }
}
