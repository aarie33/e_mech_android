package com.example.arieahmad.e_mechteknisi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeTeknisiBaru extends AppCompatActivity {

    private TextView txtNamaTeknisi;
    private AlertDialog.Builder dialogKonf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teknisi_baru);

        txtNamaTeknisi = (TextView) findViewById(R.id.txtNamaTeknisi);
        txtNamaTeknisi.setText(SharedPrefManager.getInstance(this).getNama());
        dialogKonf = new AlertDialog.Builder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teknisi_baru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bantuan:

                break;
            case R.id.action_keluar:
                dialogKonf.setTitle("Logout");
                dialogKonf.setMessage("Apakah Anda yakin akan keluar dari akun ini ?");
                dialogKonf.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                        dialog.dismiss();
                    }
                });
                dialogKonf.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog alertDialog = dialogKonf.create();
                alertDialog.show();

                break;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }
}
