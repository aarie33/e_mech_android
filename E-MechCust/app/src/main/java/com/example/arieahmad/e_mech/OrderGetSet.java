package com.example.arieahmad.e_mech;

import java.io.Serializable;

/**
 * Created by Arie Ahmad on 5/12/2017.
 */

public class OrderGetSet implements Serializable {
    String no_pemesanan, tgl_pemesanan, jam_pemesanan, deskripsi, biaya, tempat, keahlian, id_teknisi, nm_teknisi;

    public String getNo_pemesanan() {
        return no_pemesanan;
    }

    public void setNo_pemesanan(String no_pemesanan) {
        this.no_pemesanan = no_pemesanan;
    }

    public String getTgl_pemesanan() {
        return tgl_pemesanan;
    }

    public void setTgl_pemesanan(String tgl_pemesanan) {
        this.tgl_pemesanan = tgl_pemesanan;
    }

    public String getJam_pemesanan() {
        return jam_pemesanan;
    }

    public void setJam_pemesanan(String jam_pemesanan) {
        this.jam_pemesanan = jam_pemesanan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(String id_teknisi) {
        this.id_teknisi = id_teknisi;
    }

    public String getNm_teknisi() {
        return nm_teknisi;
    }

    public void setNm_teknisi(String nm_teknisi) {
        this.nm_teknisi = nm_teknisi;
    }
}
