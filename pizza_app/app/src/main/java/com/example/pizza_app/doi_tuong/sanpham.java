package com.example.pizza_app.doi_tuong;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class sanpham implements Serializable {
    public sanpham(String masp, String tensp, String loaisp, String giasp, String mota, String hinhanh) {
        this.masp = masp;
        this.tensp = tensp;
        this.loaisp = loaisp;
        this.giasp = giasp;
        this.mota = mota;
        this.hinhanh = hinhanh;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(String loaisp) {
        this.loaisp = loaisp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    String masp,tensp,loaisp,giasp,mota,hinhanh;
}
