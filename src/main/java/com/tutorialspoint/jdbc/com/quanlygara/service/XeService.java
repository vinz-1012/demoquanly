package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void delete(Xe xe) {
        xeDAO.delete(xe);
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

    public boolean canDelete(Xe xe) {
        // Check if there are any PhieuSuaChua for this Xe
        List<PhieuSuaChua> phieuSuaChuas = phieuSuaChuaDAO.findByMaXe(xe.getMaXe());
        return phieuSuaChuas.isEmpty();
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
