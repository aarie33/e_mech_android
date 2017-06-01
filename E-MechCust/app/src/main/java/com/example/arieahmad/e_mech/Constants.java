package com.example.arieahmad.e_mech;

/**
 * Created by Arie Ahmad on 4/30/2017.
 */

public class Constants {
    private static final String ROOT_URL = "http://emech.pe.hu/";
    public static final String URL_REGISTER = ROOT_URL + "LoginRegistrasiCust.php?daftarCust=1";
    public static final String URL_LOGIN = ROOT_URL + "LoginRegistrasiCust.php?loginCust=1";
    public static final String URL_LUPA_PASSWORD = ROOT_URL + "LoginRegistrasiCust.php?lupaPasswordCust=1";
    public static final String URL_VERIFIKASI = ROOT_URL + "LoginRegistrasiCust.php?verifikasiCust=1";
    public static final String URL_GANTI_PASSWORD = ROOT_URL + "LoginRegistrasiCust.php?gantiPassCust=1";
    public static final String URL_ORDER_BARU = ROOT_URL + "OrderOperationCust.php?orderBaru=1";
    public static final String URL_DATA_ORDER_PROSES = ROOT_URL + "OrderOperationCust.php?dataProses=1";

    public static boolean cekPerubahanOrderProses = false;
}
