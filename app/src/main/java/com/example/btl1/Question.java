package com.example.btl1;

import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class Question implements Serializable {
    private String maCauHoi;
    private String maNhomCauHoi;
    private String maDe;
    private String noiDungCauHoi;
    private String dapAn1;
    private String dapAn2;
    private String dapAn3;
    private String dapAn4; // Không thấy trong ví dụ nhưng nên giữ lại
    private String dapAnDung;
    private String hinhAnh;
    private String giaiThichCauHoi;

    public Question() {
    }

    // Getter và Setter có @PropertyName ánh xạ đúng Firebase

    @PropertyName("ma_cau_hoi")
    public String getMaCauHoi() {
        return maCauHoi;
    }

    @PropertyName("ma_cau_hoi")
    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    @PropertyName("ma_nhom_cau_hoi")
    public String getMaNhomCauHoi() {
        return maNhomCauHoi;
    }

    @PropertyName("ma_nhom_cau_hoi")
    public void setMaNhomCauHoi(String maNhomCauHoi) {
        this.maNhomCauHoi = maNhomCauHoi;
    }

    @PropertyName("ma_de")
    public String getMaDe() {
        return maDe;
    }

    @PropertyName("ma_de")
    public void setMaDe(String maDe) {
        this.maDe = maDe;
    }

    @PropertyName("noi_dung_cau_hoi")
    public String getNoiDungCauHoi() {
        return noiDungCauHoi;
    }

    @PropertyName("noi_dung_cau_hoi")
    public void setNoiDungCauHoi(String noiDungCauHoi) {
        this.noiDungCauHoi = noiDungCauHoi;
    }

    @PropertyName("dap_an_1")
    public String getDapAn1() {
        return dapAn1;
    }

    @PropertyName("dap_an_1")
    public void setDapAn1(String dapAn1) {
        this.dapAn1 = dapAn1;
    }

    @PropertyName("dap_an_2")
    public String getDapAn2() {
        return dapAn2;
    }

    @PropertyName("dap_an_2")
    public void setDapAn2(String dapAn2) {
        this.dapAn2 = dapAn2;
    }

    @PropertyName("dap_an_3")
    public String getDapAn3() {
        return dapAn3;
    }

    @PropertyName("dap_an_3")
    public void setDapAn3(String dapAn3) {
        this.dapAn3 = dapAn3;
    }

    // Nếu có dap_an_4, vẫn nên giữ
    @PropertyName("dap_an_4")
    public String getDapAn4() {
        return dapAn4;
    }

    @PropertyName("dap_an_4")
    public void setDapAn4(String dapAn4) {
        this.dapAn4 = dapAn4;
    }

    @PropertyName("dap_an_dung")
    public String getDapAnDung() {
        return dapAnDung;
    }

    @PropertyName("dap_an_dung")
    public void setDapAnDung(String dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    @PropertyName("hinh_anh")
    public String getHinhAnh() {
        return hinhAnh;
    }

    @PropertyName("hinh_anh")
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @PropertyName("giai_thich_cau_hoi")
    public String getGiaiThichCauHoi() {
        return giaiThichCauHoi;
    }

    @PropertyName("giai_thich_cau_hoi")
    public void setGiaiThichCauHoi(String giaiThichCauHoi) {
        this.giaiThichCauHoi = giaiThichCauHoi;
    }
}
