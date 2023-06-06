package com.example.pizza_app.doi_tuong;

public class giohang {
    public giohang(int ma, String masp, String soluong, String hinhanh, String tensp, String giasp) {
        this.ma = ma;
        this.masp = masp;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.tensp = tensp;
        this.giasp = giasp;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    int ma;
    String masp;
    String soluong;
    String hinhanh, tensp,giasp;
}
