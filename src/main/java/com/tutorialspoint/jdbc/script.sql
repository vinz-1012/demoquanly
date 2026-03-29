-- =========================================
-- RESET DATABASE
-- =========================================
DROP DATABASE IF EXISTS quanlygara;
CREATE DATABASE quanlygara CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quanlygara;

-- =========================================
-- TABLES
-- =========================================

CREATE TABLE KhachHang (
    MaKH INT AUTO_INCREMENT PRIMARY KEY,
    TenKH VARCHAR(100) NOT NULL,
    DienThoai VARCHAR(20),
    Email VARCHAR(100),
    DiaChi VARCHAR(255),
    NgayDangKy DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE HieuXe (
    MaHieuXe INT AUTO_INCREMENT PRIMARY KEY,
    TenHieuXe VARCHAR(100) NOT NULL,
    QuocGia VARCHAR(50)
);

CREATE TABLE Xe (
    MaXe INT AUTO_INCREMENT PRIMARY KEY,
    BienSo VARCHAR(20) UNIQUE,
    MauXe VARCHAR(50),
    NamSanXuat INT,
    MaKH INT,
    MaHieuXe INT,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH) ON DELETE CASCADE,
    FOREIGN KEY (MaHieuXe) REFERENCES HieuXe(MaHieuXe)
);

CREATE TABLE NhanVien (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,
    TenNV VARCHAR(100) NOT NULL,
    DienThoai VARCHAR(20),
    ChucVu VARCHAR(50),
    Luong DECIMAL(10,2)
);

CREATE TABLE PhieuSuaChua (
    MaPhieu INT AUTO_INCREMENT PRIMARY KEY,
    MaXe INT,
    MaNV INT,
    NgayNhan DATE,
    NgayTra DATE,
    TrangThai VARCHAR(50) DEFAULT 'Dang sua',
    GhiChu TEXT,
    FOREIGN KEY (MaXe) REFERENCES Xe(MaXe) ON DELETE CASCADE,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE DichVu (
    MaDV INT AUTO_INCREMENT PRIMARY KEY,
    TenDV VARCHAR(100),
    Gia DECIMAL(10,2),
    MoTa TEXT
);

CREATE TABLE ChiTietDichVu (
    MaPhieu INT,
    MaDV INT,
    SoLuong INT,
    DonGia DECIMAL(10,2),
    PRIMARY KEY (MaPhieu, MaDV),
    FOREIGN KEY (MaPhieu) REFERENCES PhieuSuaChua(MaPhieu) ON DELETE CASCADE,
    FOREIGN KEY (MaDV) REFERENCES DichVu(MaDV)
);

CREATE TABLE HoaDon (
    MaHD INT AUTO_INCREMENT PRIMARY KEY,
    MaPhieu INT UNIQUE,
    TongTien DECIMAL(10,2),
    NgayThanhToan DATE,
    TrangThai VARCHAR(50) DEFAULT 'Hop le',
    PhuongThucThanhToan VARCHAR(50),
    FOREIGN KEY (MaPhieu) REFERENCES PhieuSuaChua(MaPhieu) ON DELETE CASCADE
);

CREATE TABLE NhaCungCap (
    MaNCC INT AUTO_INCREMENT PRIMARY KEY,
    TenNCC VARCHAR(100),
    DienThoai VARCHAR(20),
    DiaChi VARCHAR(255)
);

CREATE TABLE HopDongCungCap (
    MaHopDong INT AUTO_INCREMENT PRIMARY KEY,
    MaNCC INT,
    NgayKy DATE,
    NgayHetHan DATE,
    TrangThai VARCHAR(50) DEFAULT 'Da nhap',
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC) ON DELETE CASCADE
);

CREATE TABLE PhuTung (
    MaPT INT AUTO_INCREMENT PRIMARY KEY,
    TenPT VARCHAR(100),
    Gia DECIMAL(10,2),
    TonKho INT DEFAULT 0
);

CREATE TABLE ChiTietNhap (
    MaHopDong INT,
    MaPT INT,
    SoLuong INT,
    DonGia DECIMAL(10,2),
    PRIMARY KEY (MaHopDong, MaPT),
    FOREIGN KEY (MaHopDong) REFERENCES HopDongCungCap(MaHopDong) ON DELETE CASCADE,
    FOREIGN KEY (MaPT) REFERENCES PhuTung(MaPT)
);

-- =========================================
-- TRIGGERS (FIX CHUẨN)
-- =========================================
DROP TRIGGER IF EXISTS trg_tao_hoadon;

DELIMITER $$

CREATE TRIGGER trg_tao_hoadon
AFTER UPDATE ON PhieuSuaChua
FOR EACH ROW
BEGIN
    IF NEW.TrangThai = 'Hoan thanh' AND OLD.TrangThai <> 'Hoan thanh' THEN
        
        IF NOT EXISTS (SELECT 1 FROM HoaDon WHERE MaPhieu = NEW.MaPhieu) THEN
            INSERT INTO HoaDon (MaPhieu, TongTien, NgayThanhToan, PhuongThucThanhToan)
            VALUES (
                NEW.MaPhieu,
                (SELECT IFNULL(SUM(SoLuong * DonGia),0)
                 FROM ChiTietDichVu
                 WHERE MaPhieu = NEW.MaPhieu),
                CURDATE(),
                'Tien mat'
            );
        ELSE
            UPDATE HoaDon
            SET TongTien = (
                SELECT IFNULL(SUM(SoLuong * DonGia),0)
                FROM ChiTietDichVu
                WHERE MaPhieu = NEW.MaPhieu
            ),
            TrangThai = 'Hop le'
            WHERE MaPhieu = NEW.MaPhieu;
        END IF;

    END IF;

    IF OLD.TrangThai = 'Hoan thanh' AND NEW.TrangThai <> 'Hoan thanh' THEN
        UPDATE HoaDon
        SET TrangThai = 'Huy'
        WHERE MaPhieu = NEW.MaPhieu;
    END IF;

END$$

DELIMITER ;

-- =========================================
-- VIEW
-- =========================================

CREATE VIEW ThongKeDoanhThu AS
SELECT 
    DATE(NgayThanhToan) AS Ngay,
    SUM(TongTien) AS DoanhThu
FROM HoaDon
WHERE TrangThai = 'Hop le'
GROUP BY DATE(NgayThanhToan);
SELECT hd.MaHopDong, ncc.TenNCC, hd.NgayKy, hd.TrangThai
FROM HopDongCungCap hd
JOIN NhaCungCap ncc ON hd.MaNCC = ncc.MaNCC;
-- =========================================
-- INSERT DATA
-- =========================================

-- =========================================
-- KhachHang (10 dòng)
-- =========================================
INSERT INTO KhachHang (TenKH, DienThoai, Email, DiaChi) VALUES
('Nguyen Van A', '0901', 'a@gmail.com', 'HCM'),
('Tran Thi B', '0902', 'b@gmail.com', 'Ha Noi'),
('Le Van C', '0903', 'c@gmail.com', 'Da Nang'),
('Pham Van D', '0904', 'd@gmail.com', 'HCM'),
('Hoang Thi E', '0905', 'e@gmail.com', 'Can Tho'),
('Do Van F', '0906', 'f@gmail.com', 'Hue'),
('Vo Thi G', '0907', 'g@gmail.com', 'HCM'),
('Dang Van H', '0908', 'h@gmail.com', 'Ha Noi'),
('Bui Thi I', '0909', 'i@gmail.com', 'Da Nang'),
('Nguyen Van K', '0910', 'k@gmail.com', 'HCM');

-- =========================================
-- HieuXe
-- =========================================
INSERT INTO HieuXe (TenHieuXe, QuocGia) VALUES
('Toyota', 'Nhat'),
('Honda', 'Nhat'),
('BMW', 'Duc'),
('Mercedes', 'Duc'),
('Kia', 'Han'),
('Hyundai', 'Han');

-- =========================================
-- Xe (15 xe)
-- =========================================
INSERT INTO Xe (BienSo, MauXe, NamSanXuat, MaKH, MaHieuXe) VALUES
('51A-11111','Do',2020,1,1),
('51A-22222','Den',2021,2,2),
('51A-33333','Trang',2019,3,1),
('51A-44444','Xanh',2022,4,3),
('51A-55555','Xam',2018,5,4),
('51A-66666','Do',2021,6,5),
('51A-77777','Den',2020,7,6),
('51A-88888','Trang',2019,8,1),
('51A-99999','Xanh',2022,9,2),
('51A-10101','Xam',2023,10,3),
('51A-20202','Do',2021,1,4),
('51A-30303','Den',2020,2,5),
('51A-40404','Trang',2018,3,6),
('51A-50505','Xanh',2022,4,1),
('51A-60606','Xam',2023,5,2);

-- =========================================
-- NhanVien
-- =========================================
INSERT INTO NhanVien (TenNV, DienThoai, ChucVu, Luong) VALUES
('NV1','0911','Ky thuat',8000000),
('NV2','0912','Ky thuat',8500000),
('NV3','0913','Quan ly',12000000),
('NV4','0914','Ky thuat',7800000),
('NV5','0915','Ky thuat',8200000);

-- =========================================
-- DichVu
-- =========================================
INSERT INTO DichVu (TenDV, Gia, MoTa) VALUES
('Thay dau',500000,''),
('Rua xe',100000,''),
('Thay lop',800000,''),
('Kiem tra may',300000,''),
('Can chinh lop',200000,''),
('Bao duong tong the',1500000,'');

-- =========================================
-- PhieuSuaChua (20 phiếu)
-- =========================================
INSERT INTO PhieuSuaChua (MaXe, MaNV, NgayNhan, NgayTra, TrangThai, GhiChu) VALUES
(1,1,'2026-03-01','2026-03-02','Hoan thanh',''),
(2,2,'2026-03-02','2026-03-03','Hoan thanh',''),
(3,1,'2026-03-03',NULL,'Dang sua',''),
(4,3,'2026-03-04',NULL,'Dang sua',''),
(5,2,'2026-03-05','2026-03-06','Hoan thanh',''),
(6,1,'2026-03-06','2026-03-07','Hoan thanh',''),
(7,4,'2026-03-07',NULL,'Dang sua',''),
(8,5,'2026-03-08','2026-03-09','Hoan thanh',''),
(9,1,'2026-03-09',NULL,'Dang sua',''),
(10,2,'2026-03-10','2026-03-11','Hoan thanh',''),
(11,3,'2026-03-11',NULL,'Dang sua',''),
(12,4,'2026-03-12','2026-03-13','Hoan thanh',''),
(13,5,'2026-03-13',NULL,'Dang sua',''),
(14,1,'2026-03-14','2026-03-15','Hoan thanh',''),
(15,2,'2026-03-15',NULL,'Dang sua',''),
(1,3,'2026-03-16','2026-03-17','Hoan thanh',''),
(2,4,'2026-03-17',NULL,'Dang sua',''),
(3,5,'2026-03-18','2026-03-19','Hoan thanh',''),
(4,1,'2026-03-19',NULL,'Dang sua',''),
(5,2,'2026-03-20','2026-03-21','Hoan thanh','');

-- =========================================
-- ChiTietDichVu (random nhiều)
-- =========================================
INSERT INTO ChiTietDichVu VALUES
(1,1,1,500000),(1,2,1,100000),
(2,2,2,100000),
(3,3,1,800000),
(4,4,1,300000),
(5,1,1,500000),(5,3,1,800000),
(6,2,2,100000),
(7,4,1,300000),
(8,6,1,1500000),
(9,3,2,800000),
(10,1,1,500000),
(11,2,1,100000),
(12,5,1,200000),
(13,4,1,300000),
(14,6,1,1500000),
(15,1,2,500000),
(16,2,1,100000),
(17,3,1,800000),
(18,4,1,300000),
(19,5,1,200000),
(20,6,1,1500000);

-- =========================================
-- HoaDon (cho phiếu hoàn thành)
-- =========================================
INSERT INTO HoaDon (MaPhieu, TongTien, NgayThanhToan, PhuongThucThanhToan) VALUES
(1,600000,'2026-03-02','Tien mat'),
(2,200000,'2026-03-03','Chuyen khoan'),
(5,1300000,'2026-03-06','Tien mat'),
(6,200000,'2026-03-07','Tien mat'),
(8,1500000,'2026-03-09','Chuyen khoan'),
(10,500000,'2026-03-11','Tien mat'),
(12,200000,'2026-03-13','Tien mat'),
(14,1500000,'2026-03-15','Chuyen khoan'),
(16,100000,'2026-03-17','Tien mat'),
(18,300000,'2026-03-19','Tien mat'),
(20,1500000,'2026-03-21','Chuyen khoan');

-- =========================================
-- NhaCungCap
-- =========================================
INSERT INTO NhaCungCap VALUES
(NULL,'NCC A','0921','HCM'),
(NULL,'NCC B','0922','Ha Noi'),
(NULL,'NCC C','0923','Da Nang'),
(NULL,'NCC D','0924','Can Tho');

-- =========================================
-- HopDongCungCap (quan trọng cho combobox)
-- =========================================
INSERT INTO HopDongCungCap (MaNCC, NgayKy, NgayHetHan) VALUES
(1,'2026-01-01','2027-01-01'),
(2,'2026-01-05','2027-01-05'),
(3,'2026-01-10','2027-01-10'),
(4,'2026-01-15','2027-01-15');

-- =========================================
-- PhuTung
-- =========================================
INSERT INTO PhuTung VALUES
(NULL,'Loc dau',200000,0),
(NULL,'Buggi',150000,0),
(NULL,'Lop xe',800000,0),
(NULL,'Ac quy',1200000,0),
(NULL,'Phanh',500000,0);

-- =========================================
-- ChiTietNhap (trigger sẽ tự cộng kho)
-- =========================================
INSERT INTO ChiTietNhap VALUES
(1,1,20,180000),
(1,2,15,140000),
(2,3,10,750000),
(2,4,5,1100000),
(3,5,8,450000),
(4,1,12,190000),
(4,3,7,780000);