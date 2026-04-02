package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;

import com.tutorialspoint.jdbc.com.quanlygara.dao.PhieuSuaChuaDAO;
import com.tutorialspoint.jdbc.com.quanlygara.dao.XeDAO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.XeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.Xe;

public class XeService {
    private XeDAO xeDAO = new XeDAO();
    private PhieuSuaChuaDAO phieuSuaChuaDAO = new PhieuSuaChuaDAO();

    public void save(Xe xe) {
        xeDAO.save(xe);
    }

    public Xe findById(Integer id) {
        return xeDAO.findById(id);
    }

    public List<Xe> findAll() {
        return xeDAO.findAll();
    }

    public void update(Xe xe) {
        xeDAO.update(xe);
    }

    public boolean canDelete(Xe xe) {
        // Check if there are any PhieuSuaChua for this Xe
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaDAO.findByMaXe(xe.getMaXe());
        System.out.println("Checking canDelete for xe maXe: " + xe.getMaXe() + ", found " + phieuSuaChuas.size() + " related PhieuSuaChua");
        return phieuSuaChuas.isEmpty();
    }

    public void delete(Xe xe) {
        System.out.println("Deleting xe: " + xe.getMaXe() + " - " + xe.getBienSo());
        xeDAO.delete(xe);
        System.out.println("Xe deleted successfully");
    }

    // Alternative delete method that directly uses EntityManager
    public void deleteById(Integer maXe) {
        System.out.println("Deleting xe by ID: " + maXe);
        EntityManager em = xeDAO.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Xe xe = em.find(Xe.class, maXe);
            if (xe != null) {
                em.remove(xe);
                System.out.println("Xe entity removed: " + xe.getMaXe());
            } else {
                System.out.println("Xe not found with ID: " + maXe);
            }
            
            em.getTransaction().commit();
            System.out.println("Delete transaction committed");
        } catch (Exception e) {
            System.err.println("Error deleting xe by ID: " + e.getMessage());
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back");
            }
            throw new RuntimeException("Failed to delete xe: " + e.getMessage(), e);
        } finally {
            em.close();
            System.out.println("EntityManager closed");
        }
    }

    public List<Xe> findByBienSo(String bienSo) {
        return xeDAO.findByBienSo(bienSo);
    }

    public List<Xe> findByMaKH(Integer maKH) {
        return xeDAO.findByMaKH(maKH);
    }

    public List<Xe> findByMaHieuXe(Integer maHieuXe) {
        return xeDAO.findByMaHieuXe(maHieuXe);
    }

    // Convert to DTO
    public XeDTO toDTO(Xe xe) {
        return new XeDTO(
            xe.getMaXe(),
            xe.getBienSo(),
            xe.getMauXe(),
            xe.getNamSanXuat(),
            xe.getKhachHang() != null ? xe.getKhachHang().getMaKH() : null,
            xe.getKhachHang() != null ? xe.getKhachHang().getTenKH() : null,
            xe.getHieuXe() != null ? xe.getHieuXe().getMaHieuXe() : null,
            xe.getHieuXe() != null ? xe.getHieuXe().getTenHieuXe() : null
        );
    }

    public List<XeDTO> toDTOList(List<Xe> xes) {
        return xes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
