package com.tutorialspoint.jdbc.com.quanlygara.dto;

public class HieuXeDTO {
    private Integer maHieuXe;
    private String tenHieuXe;
    private String quocGia;

    // Constructors
    public HieuXeDTO() {}

    public HieuXeDTO(Integer maHieuXe, String tenHieuXe, String quocGia) {
        this.maHieuXe = maHieuXe;
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

    @Override
    public String toString() {
        return tenHieuXe != null ? tenHieuXe : "[Chua dat ten]";
    }
}
