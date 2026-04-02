package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.tutorialspoint.jdbc.com.quanlygara.dao.PhieuSuaChuaDAO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.PhieuSuaChuaDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HoaDon;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

public class PhieuSuaChuaService {
    private PhieuSuaChuaDAO phieuSuaChuaDAO = new PhieuSuaChuaDAO();

    public void save(PhieuSuaChua phieuSuaChua) {
        phieuSuaChuaDAO.save(phieuSuaChua);
        DataChangeNotifier.notifyDataChanged("PhieuSuaChua");
    }

    public PhieuSuaChua findById(Integer id) {
        return phieuSuaChuaDAO.findById(id);
    }

    public List<PhieuSuaChua> findAll() {
        return phieuSuaChuaDAO.findAll();
    }

    public void update(PhieuSuaChua phieuSuaChua) {
        logToFile("=== Updating PhieuSuaChua: " + phieuSuaChua.getMaPhieu() + " with status: " + phieuSuaChua.getTrangThai() + " ===");
        
        phieuSuaChuaDAO.update(phieuSuaChua);
        
        // If status is "Hoan thanh", process HoaDon (create or update)
        if ("Hoan thanh".equals(phieuSuaChua.getTrangThai())) {
            logToFile("Status is Hoan thanh - processing HoaDon...");
            processHoaDonForPhieuSuaChua(phieuSuaChua);
        } else {
            logToFile("Status not Hoan thanh - skipping HoaDon processing");
        }
        
        // Notify data change
        DataChangeNotifier.notifyDataChanged("PhieuSuaChua");
    }
    
    private void processHoaDonForPhieuSuaChua(PhieuSuaChua phieuSuaChua) {
        try {
            System.out.println("=== Processing HoaDon for PhieuSuaChua: " + phieuSuaChua.getMaPhieu() + " ===");
            
            // Calculate total from service details
            BigDecimal tongTien = BigDecimal.ZERO;
            if (phieuSuaChua.getChiTietDichVuList() != null && !phieuSuaChua.getChiTietDichVuList().isEmpty()) {
                for (var ct : phieuSuaChua.getChiTietDichVuList()) {
                    if (ct.getDonGia() != null && ct.getSoLuong() != null) {
                        tongTien = tongTien.add(ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong())));
                    }
                }
            }
            
            System.out.println("Calculated total: " + tongTien);
            
            // Check if HoaDon already exists
            if (phieuSuaChua.getHoaDon() != null) {
                // Update existing HoaDon
                HoaDon hoaDon = phieuSuaChua.getHoaDon();
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayThanhToan(LocalDate.now());
                
                phieuSuaChuaDAO.getEntityManager().getTransaction().begin();
                phieuSuaChuaDAO.getEntityManager().merge(hoaDon);
                phieuSuaChuaDAO.getEntityManager().getTransaction().commit();
                
                System.out.println("HoaDon updated successfully with ID: " + hoaDon.getMaHD());
            } else {
                // Create new HoaDon
                HoaDon hoaDon = new HoaDon();
                hoaDon.setPhieuSuaChua(phieuSuaChua);
                hoaDon.setTongTien(tongTien);
                hoaDon.setNgayThanhToan(LocalDate.now());
                hoaDon.setPhuongThucThanhToan("Tien mat");
                
                phieuSuaChuaDAO.getEntityManager().getTransaction().begin();
                phieuSuaChuaDAO.getEntityManager().persist(hoaDon);
                phieuSuaChuaDAO.getEntityManager().getTransaction().commit();
                
                System.out.println("HoaDon created successfully with ID: " + hoaDon.getMaHD());
            }
            
            // Notify HoaDon change
            DataChangeNotifier.notifyDataChanged("HoaDon");
            
        } catch (Exception e) {
            System.err.println("Error processing HoaDon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(PhieuSuaChua phieuSuaChua) {
        logToFile("=== Deleting PhieuSuaChua: " + phieuSuaChua.getMaPhieu() + " ===");
        try {
            // First, delete associated HoaDon
            if (phieuSuaChua.getHoaDon() != null) {
                logToFile("Deleting associated HoaDon: " + phieuSuaChua.getHoaDon().getMaHD());
                phieuSuaChuaDAO.getEntityManager().getTransaction().begin();
                HoaDon hoaDon = phieuSuaChua.getHoaDon();
                phieuSuaChuaDAO.getEntityManager().remove(hoaDon);
                phieuSuaChuaDAO.getEntityManager().getTransaction().commit();
                logToFile("HoaDon deleted successfully");
            }
            
            // Then delete PhieuSuaChua
            phieuSuaChuaDAO.delete(phieuSuaChua);
            logToFile("PhieuSuaChua deleted successfully");
            DataChangeNotifier.notifyDataChanged("PhieuSuaChua");
            
        } catch (Exception e) {
            logToFile("Error deleting PhieuSuaChua: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public boolean canDelete(PhieuSuaChua phieuSuaChua) {
        // Cho phép xóa phiếu sửa dù có hóa đơn
        return true;
    }

    public List<PhieuSuaChua> findByTrangThai(String trangThai) {
        return phieuSuaChuaDAO.findByTrangThai(trangThai);
    }

    public List<PhieuSuaChua> findByMaXe(Integer maXe) {
        return phieuSuaChuaDAO.findByMaXe(maXe);
    }

    public List<PhieuSuaChua> findByMaNV(Integer maNV) {
        return phieuSuaChuaDAO.findByMaNV(maNV);
    }

    public List<PhieuSuaChua> findByTenKH(String tenKH) {
        return phieuSuaChuaDAO.findByTenKH(tenKH);
    }

    // Convert to DTO
    public PhieuSuaChuaDTO toDTO(PhieuSuaChua phieuSuaChua) {
        return new PhieuSuaChuaDTO(
            phieuSuaChua.getMaPhieu(),
            phieuSuaChua.getXe() != null ? phieuSuaChua.getXe().getMaXe() : null,
            phieuSuaChua.getXe() != null ? phieuSuaChua.getXe().getBienSo() : null,
            phieuSuaChua.getXe() != null && phieuSuaChua.getXe().getKhachHang() != null ? phieuSuaChua.getXe().getKhachHang().getTenKH() : null,
            phieuSuaChua.getNhanVien() != null ? phieuSuaChua.getNhanVien().getMaNV() : null,
            phieuSuaChua.getNhanVien() != null ? phieuSuaChua.getNhanVien().getTenNV() : null,
            phieuSuaChua.getNgayNhan(),
            phieuSuaChua.getNgayTra(),
            phieuSuaChua.getTrangThai(),
            phieuSuaChua.getGhiChu()
        );
    }

    public List<PhieuSuaChuaDTO> toDTOList(List<PhieuSuaChua> phieuSuaChuas) {
        return phieuSuaChuas.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    private void logToFile(String message) {
        try (FileWriter fw = new FileWriter("debug.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + message);
            pw.flush();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
