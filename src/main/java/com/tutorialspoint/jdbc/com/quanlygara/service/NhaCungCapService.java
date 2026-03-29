
package com.tutorialspoint.jdbc.com.quanlygara.service;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.dao.NhaCungCapDAO;
import com.tutorialspoint.jdbc.com.quanlygara.entity.NhaCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.util.DataChangeNotifier;

public class NhaCungCapService {
    private final NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    public void save(NhaCungCap nhaCungCap) {
        nhaCungCapDAO.save(nhaCungCap);
        DataChangeNotifier.notifyDataChanged("NhaCungCap");
        DataChangeNotifier.notifyDataChanged("HopDongCungCap");
    }

    public NhaCungCap findById(Integer id) {
        return nhaCungCapDAO.findById(id);
    }

    public List<NhaCungCap> findAll() {
        return nhaCungCapDAO.findAll();
    }

    public void update(NhaCungCap nhaCungCap) {
        nhaCungCapDAO.update(nhaCungCap);
        DataChangeNotifier.notifyDataChanged("NhaCungCap");
        DataChangeNotifier.notifyDataChanged("HopDongCungCap");
    }

    public void delete(NhaCungCap nhaCungCap) {
        nhaCungCapDAO.delete(nhaCungCap);
        DataChangeNotifier.notifyDataChanged("deleteNhaCungCap");
        DataChangeNotifier.notifyDataChanged("HopDongCungCap");
    }

    public List<NhaCungCap> findByTenNCC(String tenNCC) {
        return nhaCungCapDAO.findByTenNCC(tenNCC);
    }
}
