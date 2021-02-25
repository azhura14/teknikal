package com.example.myapplication.model;

public class Barang {
    public String id;
    public String namaBarang;
    public String qty;
    public String expdate;
    public String harga;

    public int getId() {
        return Integer.parseInt(id);
    }
    public void setId(int id) {
        this.id = String.valueOf(id);
    }
    public String getNamaBarang() {
        return namaBarang;
    }
    public void setNamaBarang(String name) {
        this.namaBarang = namaBarang;
    }
    public String getQty() {
        return qty;
    }
    public void setQty(String qty) {
        this.qty = qty;
    }
    public String getExpdate() {
        return expdate;
    }
    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
}
