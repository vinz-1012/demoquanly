package com.tutorialspoint.jdbc.com.quanlygara.dto;

import java.time.LocalDateTime;

public class KhachHangDTO {
    private Integer maKH;
    private String tenKH;
    private String dienThoai;
    private String email;
    private String diaChi;
    private LocalDateTime ngayDangKy;

    // Constructors
    public KhachHangDTO() {}

    public KhachHangDTO(Integer maKH, String tenKH, String dienThoai, String email, String diaChi, LocalDateTime ngayDangKy) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.ngayDangKy = ngayDangKy;
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

    @Override
    public String toString() {
        return tenKH != null ? tenKH : "[Chua dat ten]";
    }
}
