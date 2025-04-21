package com.example.btl1.models;

import java.io.Serializable;

public class Exam implements Serializable {
    private String ma_de;
    private String ten_de;
    private int thoi_gian_thi;
    private int so_cau;

    public Exam() {} // Bắt buộc có constructor rỗng cho Firebase

    public Exam(String ma_de, String ten_de, int thoi_gian_thi, int so_cau) {
        this.ma_de = ma_de;
        this.ten_de = ten_de;
        this.thoi_gian_thi = thoi_gian_thi;
        this.so_cau = so_cau;
    }

    public String getMa_de() {
        return ma_de;
    }

    public String getTen_de() {
        return ten_de;
    }

    public int getThoi_gian_thi() {
        return thoi_gian_thi;
    }

    public int getSo_cau() {
        return so_cau;
    }
}