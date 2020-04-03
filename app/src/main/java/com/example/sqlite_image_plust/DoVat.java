package com.example.sqlite_image_plust;

public class DoVat {
    private int Id;
    private String Ten,Mota;
    private byte[] Hinh;

    public DoVat(String ten, String mota, byte[] hinh) {
        Ten = ten;
        Mota = mota;
        Hinh = hinh;
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getTen() {
        return Ten;
    }
    public void setTen(String ten) {
        Ten = ten;
    }
    public String getMota() {
        return Mota;
    }
    public void setMota(String mota) {
        Mota = mota;
    }
    public byte[] getHinh() {
        return Hinh;
    }
    public void setHinh(byte[] hinh) {
        Hinh = hinh;
    }
}
