package com.example.pizza_app.doi_tuong;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taikhoan")
public class taikhoan  {
    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public taikhoan(int makh, String tenkh, String tk, String mk, String gioitinh, String namsinh, String diachi, String sdt, String vaitro) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.tk = tk;
        this.mk = mk;
        this.gioitinh = gioitinh;
        this.namsinh = namsinh;
        this.diachi = diachi;
        this.sdt = sdt;
        this.vaitro = vaitro;
    }

    @PrimaryKey()
    private int makh;
    private String tenkh,tk,mk,gioitinh,namsinh,diachi,sdt,vaitro;


}
