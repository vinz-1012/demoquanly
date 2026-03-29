package com.tutorialspoint.jdbc.com.quanlygara.service;

import java.util.List;
import java.util.stream.Collectors;

import com.tutorialspoint.jdbc.com.quanlygara.dao.HieuXeDAO;
import com.tutorialspoint.jdbc.com.quanlygara.dto.HieuXeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HieuXe;

public class HieuXeService {
    private HieuXeDAO hieuXeDAO = new HieuXeDAO();

    public void save(HieuXe hieuXe) {
        hieuXeDAO.save(hieuXe);
    }

    public HieuXe findById(Integer id) {
        return hieuXeDAO.findById(id);
    }

    public List<HieuXe> findAll() {
        return hieuXeDAO.findAll();
    }

    public void update(HieuXe hieuXe) {
        hieuXeDAO.update(hieuXe);
    }

    public void delete(HieuXe hieuXe) {
        hieuXeDAO.delete(hieuXe);
    }

    public List<HieuXe> findByTenHieuXe(String tenHieuXe) {
        return hieuXeDAO.findByTenHieuXe(tenHieuXe);
    }

    // Convert to DTO
    public HieuXeDTO toDTO(HieuXe hieuXe) {
        return new HieuXeDTO(
            hieuXe.getMaHieuXe(),
            hieuXe.getTenHieuXe(),
            hieuXe.getQuocGia()
        );
    }

    public List<HieuXeDTO> toDTOList(List<HieuXe> hieuXes) {
        return hieuXes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
