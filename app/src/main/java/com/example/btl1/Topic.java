package com.example.btl1;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {
    private String ma_nhom_cau_hoi;
    private String ten_nhom_cau_hoi;
    private int so_cau_hoi;
    private String hinh_anh;

    // Constructor mặc định (yêu cầu bởi Firebase)
    public Topic() {
    }

    public Topic(String ma_nhom_cau_hoi, String ten_nhom_cau_hoi, int so_cau_hoi, String hinh_anh) {
        this.ma_nhom_cau_hoi = ma_nhom_cau_hoi;
        this.ten_nhom_cau_hoi = ten_nhom_cau_hoi;
        this.so_cau_hoi = so_cau_hoi;
        this.hinh_anh = hinh_anh;
    }

    // Getters và Setters
    public String getMa_nhom_cau_hoi() {
        return ma_nhom_cau_hoi;
    }

    public void setMa_nhom_cau_hoi(String ma_nhom_cau_hoi) {
        this.ma_nhom_cau_hoi = ma_nhom_cau_hoi;
    }

    public String getTen_nhom_cau_hoi() {
        return ten_nhom_cau_hoi;
    }

    public void setTen_nhom_cau_hoi(String ten_nhom_cau_hoi) {
        this.ten_nhom_cau_hoi = ten_nhom_cau_hoi;
    }

    public int getSo_cau_hoi() {
        return so_cau_hoi;
    }

    public void setSo_cau_hoi(int so_cau_hoi) {
        this.so_cau_hoi = so_cau_hoi;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    // Implement Parcelable
    protected Topic(Parcel in) {
        ma_nhom_cau_hoi = in.readString();
        ten_nhom_cau_hoi = in.readString();
        so_cau_hoi = in.readInt();
        hinh_anh = in.readString();
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ma_nhom_cau_hoi);
        dest.writeString(ten_nhom_cau_hoi);
        dest.writeInt(so_cau_hoi);
        dest.writeString(hinh_anh);
    }
}