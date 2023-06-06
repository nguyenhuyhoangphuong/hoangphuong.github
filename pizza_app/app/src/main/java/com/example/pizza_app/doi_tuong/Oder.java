package com.example.pizza_app.doi_tuong;

public class Oder {
    public Oder(String mahdb, String tinhtrang, String ghichu, String diachi, String ngaydat, int makh) {
        this.mahdb = mahdb;
        this.tinhtrang = tinhtrang;
        this.ghichu = ghichu;
        this.diachi = diachi;
        this.ngaydat = ngaydat;
        this.makh = makh;
    }

    public String getMahdb() {
        return mahdb;
    }

    public void setMahdb(String mahdb) {
        this.mahdb = mahdb;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    String mahdb,tinhtrang,ghichu,diachi,ngaydat;
    int makh;
}
