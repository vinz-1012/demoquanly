package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dto.HieuXeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HieuXe;
import com.tutorialspoint.jdbc.com.quanlygara.service.HieuXeService;

public class HieuXeController {
    private HieuXeService hieuXeService = new HieuXeService();

    public void addHieuXe(String tenHieuXe, String quocGia) {
        HieuXe hieuXe = new HieuXe(tenHieuXe, quocGia);
        hieuXeService.save(hieuXe);
    }

    public HieuXeDTO getHieuXeById(Integer id) {
        HieuXe hieuXe = hieuXeService.findById(id);
        return hieuXe != null ? hieuXeService.toDTO(hieuXe) : null;
    }

    public List<HieuXeDTO> getAllHieuXe() {
        List<HieuXe> hieuXes = hieuXeService.findAll();
        return hieuXeService.toDTOList(hieuXes);
    }

    public void updateHieuXe(Integer maHieuXe, String tenHieuXe, String quocGia) {
        HieuXe hieuXe = hieuXeService.findById(maHieuXe);
        if (hieuXe != null) {
            hieuXe.setTenHieuXe(tenHieuXe);
            hieuXe.setQuocGia(quocGia);
            hieuXeService.update(hieuXe);
        }
    }

    public void deleteHieuXe(Integer maHieuXe) {
        HieuXe hieuXe = hieuXeService.findById(maHieuXe);
        if (hieuXe != null) {
            hieuXeService.delete(hieuXe);
        }
    }

    public List<HieuXeDTO> searchByTenHieuXe(String tenHieuXe) {
        List<HieuXe> hieuXes = hieuXeService.findByTenHieuXe(tenHieuXe);
        return hieuXeService.toDTOList(hieuXes);
    }
}
