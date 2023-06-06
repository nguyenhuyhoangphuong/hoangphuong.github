package com.example.pizza_app.doi_tuong;

public class tinnhan {
    public tinnhan(String noidung, String magui, String manhan, String matinnhan) {
        this.noidung = noidung;
        this.magui = magui;
        this.manhan = manhan;
        this.matinnhan = matinnhan;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getMagui() {
        return magui;
    }

    public void setMagui(String magui) {
        this.magui = magui;
    }

    public String getManhan() {
        return manhan;
    }

    public void setManhan(String manhan) {
        this.manhan = manhan;
    }

    public String getMatinnhan() {
        return matinnhan;
    }

    public void setMatinnhan(String matinnhan) {
        this.matinnhan = matinnhan;
    }

    String noidung,magui,manhan,matinnhan;
}
