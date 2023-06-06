package com.example.pizza_app.doi_tuong;

public class Oder_ct {
    public Oder_ct(String masp, String mahdb, String dongia, String soluong) {
        this.masp = masp;
        this.mahdb = mahdb;
        this.dongia = dongia;
        this.soluong = soluong;
    }

    String masp;

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getMahdb() {
        return mahdb;
    }

    public void setMahdb(String mahdb) {
        this.mahdb = mahdb;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    String mahdb;
    String dongia;
    String soluong;
}
