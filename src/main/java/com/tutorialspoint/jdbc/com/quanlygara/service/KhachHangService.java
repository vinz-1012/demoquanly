package com.tutorialspoint.jdbc.com.quanlygara.service;

import com.tutorialspoint.jdbc.com.quanlygara.dao.KhachHangDAO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.KhachHang;
import com.tutorialspoint.jdbc.com.quanlygara.entity.Xe;
import com.tutorialspoint.jdbc.com.quanlygara.dto.KhachHangDTO;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;
import java.util.List;
import java.util.stream.Collectors;

public class KhachHangService {
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    public XeService xeService = new XeService();

    public void save(KhachHang khachHang) {
        {
            System.out.println("=== KhachHangService: save called for " + khachHang.getTenKH() + " ===");
            khachHangDAO.save(khachHang);
            DataChangeNotifier.notifyDataChanged("KhachHang");
            System.out.println("KhachHangService: save completed, notified DataChangeNotifier");
        }
    }

    public KhachHang findById(Integer id) {
        return khachHangDAO.findById(id);
    }

    public List<KhachHang> findAll() {
        return khachHangDAO.findAll();
    }

    public void update(KhachHang khachHang) {
        khachHangDAO.update(khachHang);
    }

    public void delete(KhachHang khachHang) {
        System.out.println("=== KhachHangService: delete called for " + khachHang.getTenKH() + " ===");
        try {
            // Use DAO directly to avoid circular dependency
            khachHangDAO.delete(khachHang);
            System.out.println("Deleted KhachHang: " + khachHang.getMaKH());
            
            // Notify changes
            DataChangeNotifier.notifyDataChanged("KhachHang");
            
        } catch (Exception e) {
            System.err.println("Error deleting KhachHang: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<KhachHang> findByTenKH(String tenKH) {
        return khachHangDAO.findByTenKH(tenKH);
    }

    public KhachHang findByDienThoai(String dienThoai) {
        return khachHangDAO.findByDienThoai(dienThoai);
    }
    
    public List<com.tutorialspoint.jdbc.com.quanlygara.entity.Xe> getXeByMaKH(Integer maKH) {
        return xeService.findByMaKH(maKH);
    }
    
    public boolean canDelete(KhachHang khachHang) {
        // Check if there are any Xe for this KhachHang
        List<Xe> xeList = xeService.findByMaKH(khachHang.getMaKH());
        
        // Check if any Xe has PhieuSuaChua
        for (Xe xe : xeList) {
            if (!xeService.canDelete(xe)) {
                return false; // Found Xe with PhieuSuaChua
            }
        }
        
        return true; // All Xe can be deleted
    }

    // Convert to DTO
    public KhachHangDTO toDTO(KhachHang khachHang) {
        return new KhachHangDTO(
            khachHang.getMaKH(),
            khachHang.getTenKH(),
            khachHang.getDienThoai(),
            khachHang.getEmail(),
            khachHang.getDiaChi(),
            khachHang.getNgayDangKy()
        );
    }

    public List<KhachHangDTO> toDTOList(List<KhachHang> khachHangs) {
        return khachHangs.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
