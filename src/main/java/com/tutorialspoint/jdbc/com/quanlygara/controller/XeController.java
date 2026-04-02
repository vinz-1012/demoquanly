package com.tutorialspoint.jdbc.com.quanlygara.controller;

import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dto.XeDTO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HieuXe;
import com.tutorialspoint.jdbc.com.quanlygara.entity.KhachHang;
import com.tutorialspoint.jdbc.com.quanlygara.entity.Xe;
import com.tutorialspoint.jdbc.com.quanlygara.service.HieuXeService;
import com.tutorialspoint.jdbc.com.quanlygara.service.KhachHangService;
import com.tutorialspoint.jdbc.com.quanlygara.service.XeService;

public class XeController {
    private XeService xeService = new XeService();
    private KhachHangService khachHangService = new KhachHangService();
    private HieuXeService hieuXeService = new HieuXeService();

    public void addXe(String bienSo, String mauXe, Integer namSanXuat, Integer maKH, Integer maHieuXe) {
        KhachHang khachHang = khachHangService.findById(maKH);
        HieuXe hieuXe = hieuXeService.findById(maHieuXe);
        Xe xe = new Xe(bienSo, mauXe, namSanXuat, khachHang, hieuXe);
        xeService.save(xe);
    }

    public XeDTO getXeById(Integer id) {
        Xe xe = xeService.findById(id);
        return xe != null ? xeService.toDTO(xe) : null;
    }

    public List<XeDTO> getAllXe() {
        List<Xe> xes = xeService.findAll();
        return xeService.toDTOList(xes);
    }

    public void updateXe(Integer maXe, String bienSo, String mauXe, Integer namSanXuat, Integer maKH, Integer maHieuXe) {
        Xe xe = xeService.findById(maXe);
        if (xe != null) {
            xe.setBienSo(bienSo);
            xe.setMauXe(mauXe);
            xe.setNamSanXuat(namSanXuat);
            KhachHang khachHang = khachHangService.findById(maKH);
            xe.setKhachHang(khachHang);
            HieuXe hieuXe = hieuXeService.findById(maHieuXe);
            xe.setHieuXe(hieuXe);
            xeService.update(xe);
        }
    }

    public void deleteXe(Integer maXe) {
        Xe xe = xeService.findById(maXe);
        if (xe != null) {
            if (xeService.canDelete(xe)) {
                // Use the new direct delete method
                xeService.deleteById(maXe);
            } else {
                throw new RuntimeException("Không thể xóa xe vì có phiếu sửa chữa liên quan.");
            }
        } else {
            throw new RuntimeException("Không tìm thấy xe có mã: " + maXe);
        }
    }

    public List<XeDTO> searchByBienSo(String bienSo) {
        List<Xe> xes = xeService.findByBienSo(bienSo);
        return xeService.toDTOList(xes);
    }

    public List<XeDTO> getXeByMaKH(Integer maKH) {
        List<Xe> xes = xeService.findByMaKH(maKH);
        return xeService.toDTOList(xes);
    }

    public List<XeDTO> getXeByMaHieuXe(Integer maHieuXe) {
        List<Xe> xes = xeService.findByMaHieuXe(maHieuXe);
        return xeService.toDTOList(xes);
    }
}
