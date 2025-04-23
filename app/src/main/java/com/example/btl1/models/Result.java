package com.example.btl1.models;

import java.io.Serializable;
import java.util.Map;

public class Result implements Serializable {
    private String ma_ket_qua;
    private String ma_de;
    private String trang_thai;
    private int diem_so;
    private int tong_so_cau;
    private String thoi_gian_hoan_thanh;
    private String ngay_lam;
    private Map<String, ResultDetail> chi_tiet_cau_hoi;  // Lưu chi tiết của từng câu hỏi


    public Result() {}

    // Getters và Setters
    public String getMa_ket_qua() { return ma_ket_qua; }
    public void setMa_ket_qua(String ma_ket_qua) { this.ma_ket_qua = ma_ket_qua; }
    public String getMa_de() { return ma_de; }
    public void setMa_de(String ma_de) { this.ma_de = ma_de; }
    public String getTrang_thai() { return trang_thai; }
    public void setTrang_thai(String trang_thai) { this.trang_thai = trang_thai; }
    public int getDiem_so() { return diem_so; }
    public void setDiem_so(int diem_so) { this.diem_so = diem_so; }
    public int getTong_so_cau() { return tong_so_cau; }
    public void setTong_so_cau(int tong_so_cau) { this.tong_so_cau = tong_so_cau; }
    public String getThoi_gian_hoan_thanh() {
        return thoi_gian_hoan_thanh;
    }

    public void setThoi_gian_hoan_thanh(String thoi_gian_hoan_thanh) {
        this.thoi_gian_hoan_thanh = thoi_gian_hoan_thanh;
    }
    public String getNgay_lam() { return ngay_lam; }
    public void setNgay_lam(String ngay_lam) { this.ngay_lam = ngay_lam; }

    public Map<String, ResultDetail> getChi_tiet_cau_hoi() {
        return chi_tiet_cau_hoi;
    }

    public void setChi_tiet_cau_hoi(Map<String, ResultDetail> chi_tiet_cau_hoi) {
        this.chi_tiet_cau_hoi = chi_tiet_cau_hoi;
    }
   }

