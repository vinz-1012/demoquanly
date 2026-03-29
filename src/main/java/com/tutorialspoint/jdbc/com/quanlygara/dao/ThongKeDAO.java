package com.tutorialspoint.jdbc.com.quanlygara.dao;

import java.math.BigDecimal;
import java.util.List;

import com.tutorialspoint.jdbc.com.quanlygara.entity.ChiTietDichVu;
import com.tutorialspoint.jdbc.com.quanlygara.entity.ChiTietNhap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.DichVu;
import com.tutorialspoint.jdbc.com.quanlygara.entity.HopDongCungCap;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhieuSuaChua;
import com.tutorialspoint.jdbc.com.quanlygara.entity.PhuTung;

import com.tutorialspoint.jdbc.com.quanlygara.entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ThongKeDAO extends GenericDAO<PhieuSuaChua> {
    public ThongKeDAO() {
        super(PhieuSuaChua.class);
    }

    public void saveHopDongCungCap(HopDongCungCap hopDong) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(hopDong);
        em.getTransaction().commit();
        em.close();
    }

    public BigDecimal getTongDoanhThu() {
        EntityManager em = getEntityManager();
        BigDecimal value = em.createQuery("SELECT COALESCE(SUM(h.tongTien), 0) FROM HoaDon h", BigDecimal.class)
            .getSingleResult();
        em.close();
        return value;
    }

    public Long getTongKhachHang() {
        EntityManager em = getEntityManager();
        Long value = em.createQuery("SELECT COUNT(k) FROM KhachHang k", Long.class).getSingleResult();
        em.close();
        return value;
    }

    public List<Object[]> getSoLuongPhieuTheoTrangThai() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
                "SELECT p.trangThai, COUNT(p) FROM PhieuSuaChua p GROUP BY p.trangThai ORDER BY COUNT(p) DESC",
                Object[].class
            )
            .getResultList();
        em.close();
        return result;
    }

    public List<PhieuSuaChua> timPhieuTheoTrangThai(String trangThai) {
        EntityManager em = getEntityManager();
        TypedQuery<PhieuSuaChua> query = em.createQuery(
            "SELECT p FROM PhieuSuaChua p WHERE p.trangThai = :trangThai ORDER BY p.maPhieu DESC",
            PhieuSuaChua.class
        );
        query.setParameter("trangThai", trangThai);
        List<PhieuSuaChua> result = query.getResultList();
        em.close();
        return result;
    }

    // 1. Xem danh sách hóa đơn
    public List<Object[]> getDanhSachHoaDon() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT hd.maHD, kh.tenKH, x.bienSo, hd.tongTien, hd.ngayThanhToan " +
            "FROM HoaDon hd " +
            "JOIN hd.phieuSuaChua p " +
            "JOIN p.xe x " +
            "JOIN x.khachHang kh " +
            "ORDER BY hd.maHD",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    // 2. Xem phiếu sửa + dịch vụ
    public List<Object[]> getPhieuSuaVaDichVu() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT ct.phieuSuaChua.maPhieu, dv.tenDV, ct.soLuong, ct.donGia " +
            "FROM ChiTietDichVu ct " +
            "JOIN ct.dichVu dv " +
            "ORDER BY ct.phieuSuaChua.maPhieu",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    // 3. Tính tổng tiền từ chi tiết
    // 3. Tính tổng tiền từ chi tiết (mục cũ, vẫn giữ nếu cần nhưng tab đã bỏ)
    public List<Object[]> getTongTienTuChiTiet() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT ct.phieuSuaChua.maPhieu, SUM(ct.soLuong * ct.donGia) " +
            "FROM ChiTietDichVu ct " +
            "GROUP BY ct.phieuSuaChua.maPhieu " +
            "ORDER BY ct.phieuSuaChua.maPhieu",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    // == CHI TIET DICH VU CRUD ==
    public List<Object[]> getAllChiTietDichVu() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT ct.phieuSuaChua.maPhieu, ct.dichVu.maDV, ct.dichVu.tenDV, ct.soLuong, ct.donGia " +
            "FROM ChiTietDichVu ct ORDER BY ct.phieuSuaChua.maPhieu",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    public List<PhieuSuaChua> getAllPhieuSuaChua() {
        EntityManager em = getEntityManager();
        List<PhieuSuaChua> result = em.createQuery("SELECT p FROM PhieuSuaChua p ORDER BY p.maPhieu", PhieuSuaChua.class).getResultList();
        em.close();
        return result;
    }

    public List<DichVu> getAllDichVu() {
        EntityManager em = getEntityManager();
        List<DichVu> result = em.createQuery("SELECT d FROM DichVu d ORDER BY d.maDV", DichVu.class).getResultList();
        em.close();
        return result;
    }

    public void addChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        PhieuSuaChua p = em.find(PhieuSuaChua.class, maPhieu);
        DichVu dv = em.find(DichVu.class, maDV);
        if (p != null && dv != null) {
            ChiTietDichVu ct = new ChiTietDichVu(p, dv, soLuong, donGia);
            em.persist(ct);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void addChiTietDichVuWithServiceName(Integer maPhieu, String tenDV, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        PhieuSuaChua p = em.find(PhieuSuaChua.class, maPhieu);
        if (p != null) {
            // Create a new DichVu with the provided name or find existing one
            DichVu dv = findOrCreateDichVu(em, tenDV);
            ChiTietDichVu ct = new ChiTietDichVu(p, dv, soLuong, donGia);
            em.persist(ct);
        }
        em.getTransaction().commit();
        em.close();
    }

    private DichVu findOrCreateDichVu(EntityManager em, String tenDV) {
        try {
            TypedQuery<DichVu> query = em.createQuery("SELECT d FROM DichVu d WHERE d.tenDV = :tenDV", DichVu.class);
            query.setParameter("tenDV", tenDV);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Create new DichVu if not found
            DichVu newDv = new DichVu();
            newDv.setTenDV(tenDV);
            newDv.setGia(BigDecimal.ZERO); // Default price
            newDv.setMoTa(""); // Default description
            em.persist(newDv);
            return newDv;
        }
    }

    public void updateChiTietDichVu(Integer maPhieu, Integer maDV, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<ChiTietDichVu> query = em.createQuery(
                "SELECT ct FROM ChiTietDichVu ct WHERE ct.phieuSuaChua.maPhieu = :maPhieu AND ct.dichVu.maDV = :maDV",
                ChiTietDichVu.class
            );
            query.setParameter("maPhieu", maPhieu);
            query.setParameter("maDV", maDV);
            ChiTietDichVu ct = query.getSingleResult();
            ct.setSoLuong(soLuong);
            ct.setDonGia(donGia);
            em.merge(ct);
        } catch (NoResultException ignored) {
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteChiTietDichVu(Integer maPhieu, Integer maDV) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<ChiTietDichVu> query = em.createQuery(
                "SELECT ct FROM ChiTietDichVu ct WHERE ct.phieuSuaChua.maPhieu = :maPhieu AND ct.dichVu.maDV = :maDV",
                ChiTietDichVu.class
            );
            query.setParameter("maPhieu", maPhieu);
            query.setParameter("maDV", maDV);
            ChiTietDichVu ct = query.getSingleResult();
            em.remove(ct);
        } catch (NoResultException ignored) {
        }
        em.getTransaction().commit();
        em.close();
    }

    // == CHI TIET NHAP HANG CRUD ==
    public List<Object[]> getAllChiTietNhapHang() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT ct.hopDongCungCap.maHopDong, ct.phuTung.maPT, ct.phuTung.tenPT, ct.soLuong, ct.donGia " +
            "FROM ChiTietNhap ct ORDER BY ct.hopDongCungCap.maHopDong",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    public List<HopDongCungCap> getAllHopDongCungCap() {
        EntityManager em = getEntityManager();
        List<HopDongCungCap> result = em.createQuery("SELECT h FROM HopDongCungCap h ORDER BY h.maHopDong", HopDongCungCap.class).getResultList();
        em.close();
        return result;
    }

    public List<PhuTung> getAllPhuTung() {
        EntityManager em = getEntityManager();
        List<PhuTung> result = em.createQuery("SELECT p FROM PhuTung p ORDER BY p.maPT", PhuTung.class).getResultList();
        em.close();
        return result;
    }

    public void addChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        HopDongCungCap hd = em.find(HopDongCungCap.class, maHopDong);
        PhuTung pt = em.find(PhuTung.class, maPT);
        if (hd != null && pt != null) {
            ChiTietNhap ct = new ChiTietNhap(hd, pt, soLuong, donGia);
            em.persist(ct);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void addChiTietNhapHangWithPartName(Integer maHopDong, String tenPT, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        HopDongCungCap hd = em.find(HopDongCungCap.class, maHopDong);
        if (hd != null) {
            // Create a new PhuTung with the provided name or find existing one
            PhuTung pt = findOrCreatePhuTung(em, tenPT);
            ChiTietNhap ct = new ChiTietNhap(hd, pt, soLuong, donGia);
            em.persist(ct);
        }
        em.getTransaction().commit();
        em.close();
    }

    private PhuTung findOrCreatePhuTung(EntityManager em, String tenPT) {
        try {
            TypedQuery<PhuTung> query = em.createQuery("SELECT p FROM PhuTung p WHERE p.tenPT = :tenPT", PhuTung.class);
            query.setParameter("tenPT", tenPT);
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Create new PhuTung if not found
            PhuTung newPt = new PhuTung();
            newPt.setTenPT(tenPT);
            newPt.setGia(BigDecimal.ZERO); // Default price
            newPt.setTonKho(0); // Default stock
            em.persist(newPt);
            return newPt;
        }
    }

    public void updateChiTietNhapHang(Integer maHopDong, Integer maPT, Integer soLuong, BigDecimal donGia) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<ChiTietNhap> query = em.createQuery(
                "SELECT ct FROM ChiTietNhap ct WHERE ct.hopDongCungCap.maHopDong = :maHopDong AND ct.phuTung.maPT = :maPT",
                ChiTietNhap.class
            );
            query.setParameter("maHopDong", maHopDong);
            query.setParameter("maPT", maPT);
            ChiTietNhap ct = query.getSingleResult();
            ct.setSoLuong(soLuong);
            ct.setDonGia(donGia);
            em.merge(ct);
        } catch (NoResultException ignored) {
        }
        em.getTransaction().commit();
        em.close();
    }

    public void deleteChiTietNhapHang(Integer maHopDong, Integer maPT) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            TypedQuery<ChiTietNhap> query = em.createQuery(
                "SELECT ct FROM ChiTietNhap ct WHERE ct.hopDongCungCap.maHopDong = :maHopDong AND ct.phuTung.maPT = :maPT",
                ChiTietNhap.class
            );
            query.setParameter("maHopDong", maHopDong);
            query.setParameter("maPT", maPT);
            ChiTietNhap ct = query.getSingleResult();
            em.remove(ct);
        } catch (NoResultException ignored) {
        }
        em.getTransaction().commit();
        em.close();
    }

    // 6. Nhập hàng - Chi tiết nhập
    public List<Object[]> getChiTietNhapHang() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT ncc.tenNCC, hd.maHopDong, pt.tenPT, ct.soLuong, ct.donGia " +
            "FROM ChiTietNhap ct " +
            "JOIN ct.hopDongCungCap hd " +
            "JOIN hd.nhaCungCap ncc " +
            "JOIN ct.phuTung pt ",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    public List<Object[]> getTongTienNhapHang() {
        EntityManager em = getEntityManager();
        List<Object[]> result = em.createQuery(
            "SELECT hd.maHopDong, SUM(ct.soLuong * ct.donGia) " +
            "FROM ChiTietNhap ct " +
            "JOIN ct.hopDongCungCap hd " +
            "GROUP BY hd.maHopDong",
            Object[].class
        ).getResultList();
        em.close();
        return result;
    }

    // 7. Thống kê lợi nhuận
    public BigDecimal getChiPhiNhanVien() {
        EntityManager em = getEntityManager();
        BigDecimal value = em.createQuery("SELECT COALESCE(SUM(nv.luong), 0) FROM NhanVien nv", BigDecimal.class)
            .getSingleResult();
        em.close();
        return value;
    }

    public BigDecimal getChiPhiPhuTung() {
        EntityManager em = getEntityManager();
        BigDecimal value = em.createQuery(
            "SELECT COALESCE(SUM(ct.soLuong * ct.donGia), 0) FROM ChiTietNhap ct",
            BigDecimal.class
        ).getSingleResult();
        em.close();
        return value;
    }

    public BigDecimal getLoiNhuanTong() {
        BigDecimal doanhThu = getTongDoanhThu();
        BigDecimal chiPhiNV = getChiPhiNhanVien();
        BigDecimal chiPhiPT = getChiPhiPhuTung();
        return doanhThu.subtract(chiPhiNV).subtract(chiPhiPT);
    }

    public void deleteHoaDon(Integer maHD) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Find the HoaDon entity
            HoaDon hoaDon = em.find(HoaDon.class, maHD);
            if (hoaDon != null) {
                em.remove(hoaDon);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // 4. Xe đang sửa (already have timPhieuTheoTrangThai)

    // 5. Doanh thu (already have getTongDoanhThu)
}
