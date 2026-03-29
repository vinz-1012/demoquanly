package com.tutorialspoint.jdbc.com.quanlygara.dto;

public class XeDTO {
    private Integer maXe;
    private String bienSo;
    private String mauXe;
    private Integer namSanXuat;
    private Integer maKH;
    private String tenKH;
    private Integer maHieuXe;
    private String tenHieuXe;

    // Constructors
    public XeDTO() {}

    public XeDTO(Integer maXe, String bienSo, String mauXe, Integer namSanXuat, Integer maKH, String tenKH, Integer maHieuXe, String tenHieuXe) {
        this.maXe = maXe;
        this.bienSo = bienSo;
        this.mauXe = mauXe;
        this.namSanXuat = namSanXuat;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.maHieuXe = maHieuXe;
        this.tenHieuXe = tenHieuXe;
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

    public Integer getMaKH() { return maKH; }
    public void setMaKH(Integer maKH) { this.maKH = maKH; }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }

    public Integer getMaHieuXe() { return maHieuXe; }
    public void setMaHieuXe(Integer maHieuXe) { this.maHieuXe = maHieuXe; }

    public String getTenHieuXe() { return tenHieuXe; }
    public void setTenHieuXe(String tenHieuXe) { this.tenHieuXe = tenHieuXe; }

    @Override
    public String toString() {
        return bienSo != null ? bienSo : "[Chua dat bien so]";
    }
}
