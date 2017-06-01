package com.example.arieahmad.e_mechteknisi.GetterSetter;

import java.io.Serializable;

/**
 * Created by Arie Ahmad on 5/16/2017.
 */

public class ListBaruGetSet implements Serializable {
    String no_pemesanan;
    String tanggal;
    String jam;
    String deskripsi;
    String tempat;
    String keahlian;
    String namaCust;
    String noTelp;

    public String getNo_pemesanan() {
        return no_pemesanan;
    }

    public void setNo_pemesanan(String no_pemesanan) {
        this.no_pemesanan = no_pemesanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public String getNamaCust() {
        return namaCust;
    }

    public void setNamaCust(String namaCust) {
        this.namaCust = namaCust;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
