package com.tutorialspoint.jdbc.com.quanlygara.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "HieuXe")
public class HieuXe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHieuXe")
    private Integer maHieuXe;

    @Column(name = "TenHieuXe", length = 100)
    private String tenHieuXe;

    @Column(name = "QuocGia", length = 50)
    private String quocGia;

    @OneToMany(mappedBy = "hieuXe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Xe> xeList;

    // Constructors
    public HieuXe() {}

    public HieuXe(String tenHieuXe, String quocGia) {
        this.tenHieuXe = tenHieuXe;
        this.quocGia = quocGia;
    }

    // Getters and Setters
    public Integer getMaHieuXe() { return maHieuXe; }
    public void setMaHieuXe(Integer maHieuXe) { this.maHieuXe = maHieuXe; }

    public String getTenHieuXe() { return tenHieuXe; }
    public void setTenHieuXe(String tenHieuXe) { this.tenHieuXe = tenHieuXe; }

    public String getQuocGia() { return quocGia; }
    public void setQuocGia(String quocGia) { this.quocGia = quocGia; }

    public List<Xe> getXeList() { return xeList; }
    public void setXeList(List<Xe> xeList) { this.xeList = xeList; }
}
