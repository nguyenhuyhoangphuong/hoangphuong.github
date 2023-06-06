package com.example.DATEST.models;

public class taikhoan {
    int idtk;
    String taikhoan;
    String hoten;
    String mk;
    String sdt;
    String diachi;

    public taikhoan(int idtk, String taikhoan, String hoten, String mk, String sdt, String diachi) {
        this.idtk = idtk;
        this.taikhoan = taikhoan;
        this.hoten = hoten;
        this.mk = mk;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public int getIdtk() {
        return idtk;
    }

    public String getHoten() {
        return hoten;
    }
    public String getTaikhoan() {
        return taikhoan;
    }

    public String getMk() {
        return mk;
    }
}
