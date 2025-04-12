package com.example.btl1;

import java.io.Serializable;
public class Question implements Serializable {
    private String maCauHoi;
    private String maNhomCauHoi;
    private String maDe;
    private String noiDungCauHoi;
    private String dapAn1;
    private String dapAn2;
    private String dapAn3;
    private String dapAn4;
    private String dapAnDung;
    private String hinhAnh;
    private String giaiThichCauHoi;

    // Constructor mặc định (Cần thiết cho Firebase)
    public Question() {
    }

    // Constructor có tham số
    public Question(String maCauHoi, String maNhomCauHoi, String maDe, String noiDungCauHoi,
                    String dapAn1, String dapAn2, String dapAn3, String dapAn4,
                    String dapAnDung, String hinhAnh, String giaiThichCauHoi) {
        this.maCauHoi = maCauHoi;
        this.maNhomCauHoi = maNhomCauHoi;
        this.maDe = maDe;
        this.noiDungCauHoi = noiDungCauHoi;
        this.dapAn1 = dapAn1;
        this.dapAn2 = dapAn2;
        this.dapAn3 = dapAn3;
        this.dapAn4 = dapAn4;
        this.dapAnDung = dapAnDung;
        this.hinhAnh = hinhAnh;
        this.giaiThichCauHoi = giaiThichCauHoi;
    }

    // Getter
    public String getMaCauHoi() { return maCauHoi; }
    public String getMaNhomCauHoi() { return maNhomCauHoi; }
    public String getMaDe() { return maDe; }
    public String getNoiDungCauHoi() { return noiDungCauHoi; }
    public String getDapAn1() { return dapAn1; }
    public String getDapAn2() { return dapAn2; }
    public String getDapAn3() { return dapAn3; }
    public String getDapAn4() { return dapAn4; }
    public String getDapAnDung() { return dapAnDung; }
    public String getHinhAnh() { return hinhAnh; }
    public String getGiaiThichCauHoi() { return giaiThichCauHoi; }

    // Setter (CẦN THIẾT)
    public void setMaCauHoi(String maCauHoi) { this.maCauHoi = maCauHoi; }
    public void setMaNhomCauHoi(String maNhomCauHoi) { this.maNhomCauHoi = maNhomCauHoi; }
    public void setMaDe(String maDe) { this.maDe = maDe; }
    public void setNoiDungCauHoi(String noiDungCauHoi) { this.noiDungCauHoi = noiDungCauHoi; }
    public void setDapAn1(String dapAn1) { this.dapAn1 = dapAn1; }
    public void setDapAn2(String dapAn2) { this.dapAn2 = dapAn2; }
    public void setDapAn3(String dapAn3) { this.dapAn3 = dapAn3; }
    public void setDapAn4(String dapAn4) { this.dapAn4 = dapAn4; }
    public void setDapAnDung(String dapAnDung) { this.dapAnDung = dapAnDung; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public void setGiaiThichCauHoi(String giaiThichCauHoi) { this.giaiThichCauHoi = giaiThichCauHoi; }
}
