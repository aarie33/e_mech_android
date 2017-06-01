package com.example.arieahmad.e_mech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arie Ahmad on 5/11/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "e_mech_cust.db";
    public static final String TABLE_ORDER = "tbl_order";
    public static final String NO_ORDER = "no_order";
    public static final String TGL_ORDER = "tgl_order";
    public static final String JAM_ORDER = "jam_order";
    public static final String DESKRIPSI_ORDER = "deskripsi_order";
    public static final String BIAYA_ORDER = "biaya_order";
    public static final String TEMPAT_ORDER = "tempat_order";
    public static final String SERVIS_ORDER = "servis_order";
    public static final String ID_TEKNISI = "id_teknisi";
    public static final String NAMA_TEKNISI = "nama_teknisi";

    public static final int DB_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ORDER + "(" + NO_ORDER + " INTEGER PRIMARY KEY," + TGL_ORDER + " DATE," +
                JAM_ORDER + " TIME," + DESKRIPSI_ORDER + " TEXT," + BIAYA_ORDER + " INTEGER," + TEMPAT_ORDER + " TEXT," +
                SERVIS_ORDER + " TEXT," + ID_TEKNISI + " INTEGER," + NAMA_TEKNISI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
    }

    public boolean insertOrder(String no_order,String tgl, String jam, String deskripsi, String biaya,
                               String tempat, String servis, String id_teknisi, String nama_teknisi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NO_ORDER, no_order);
        contentValues.put(TGL_ORDER, tgl);
        contentValues.put(JAM_ORDER, jam);
        contentValues.put(DESKRIPSI_ORDER, deskripsi);
        contentValues.put(BIAYA_ORDER, biaya);
        contentValues.put(TEMPAT_ORDER, tempat);
        contentValues.put(SERVIS_ORDER, servis);
        contentValues.put(ID_TEKNISI, id_teknisi);
        contentValues.put(NAMA_TEKNISI, nama_teknisi);

        long result = db.insert(TABLE_ORDER, null, contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateOrder(String no_order,String tgl, String jam, String deskripsi, String biaya,
                                   String tempat, String servis, String id_teknisi, String nama_teknisi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NO_ORDER, no_order);
        contentValues.put(TGL_ORDER, tgl);
        contentValues.put(JAM_ORDER, jam);
        contentValues.put(DESKRIPSI_ORDER, deskripsi);
        contentValues.put(BIAYA_ORDER, biaya);
        contentValues.put(TEMPAT_ORDER, tempat);
        contentValues.put(SERVIS_ORDER, servis);
        contentValues.put(ID_TEKNISI, id_teknisi);
        contentValues.put(NAMA_TEKNISI, nama_teknisi);
        db.update(TABLE_ORDER, contentValues, NO_ORDER + " = ? ", new String[]{no_order});
        return true;
    }

    public Integer deleteOrder(String no_order){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORDER, NO_ORDER + " = ?", new String[]{no_order});
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        return res;
    }
}
