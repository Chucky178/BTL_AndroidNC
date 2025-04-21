package com.example.btl1.models;
public class ResultDetail {
    private String ma_ket_qua;
    private String ma_cau_hoi;
    private String dap_an_chon;
    private String dap_an_dung;
    private String trang_thai;

    public ResultDetail() {}

    // Getters và Setters

    public String getMa_ket_qua() {
        return ma_ket_qua;
    }

    public void setMa_ket_qua(String ma_ket_qua) {
        this.ma_ket_qua = ma_ket_qua;
    }

    public String getMa_cau_hoi() { return ma_cau_hoi; }
    public void setMa_cau_hoi(String ma_cau_hoi) { this.ma_cau_hoi = ma_cau_hoi; }
    public String getDap_an_chon() { return dap_an_chon; }
    public void setDap_an_chon(String dap_an_chon) { this.dap_an_chon = dap_an_chon; }
    public String getDap_an_dung() { return dap_an_dung; }
    public void setDap_an_dung(String dap_an_dung) { this.dap_an_dung = dap_an_dung; }
    public String getTrang_thai() { return trang_thai; }
    public void setTrang_thai(String trang_thai) { this.trang_thai = trang_thai; }
}