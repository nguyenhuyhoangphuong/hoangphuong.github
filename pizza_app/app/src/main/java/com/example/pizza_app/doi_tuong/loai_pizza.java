package com.example.pizza_app.doi_tuong;

public class loai_pizza {
    public loai_pizza(String tenloai, String hinhanh) {
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    String tenloai, hinhanh;
}
