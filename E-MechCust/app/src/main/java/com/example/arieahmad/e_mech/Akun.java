  package com.example.arieahmad.e_mech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Akun extends AppCompatActivity {

    private BottomNavigationView navigation;
    private AlertDialog.Builder dialogKonf;
    private TextView txtNama, txtEmail, txtTelp, txtAlamat, txtJkel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent iHome = new Intent(getApplicationContext(), Home.class);
                    //iHome.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //iHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iHome);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.navigation_order:
                    Intent iOrder = new Intent(getApplicationContext(), Order.class);
                    //iOrder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //iOrder.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    iOrder.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(iOrder);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.navigation_cari_teknisi:
                    Intent iCari = new Intent(getApplicationContext(), CariTeknisi.class);
                    //iCari.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //iCari.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iCari);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.navigation_akun:
                    //Toast.makeText(getApplicationContext(), "Akun clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        dialogKonf = new AlertDialog.Builder(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        txtNama = (TextView)findViewById(R.id.txtNama);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtTelp = (TextView)findViewById(R.id.txtTelp);
        txtAlamat = (TextView)findViewById(R.id.txtAlamat);
        txtJkel = (TextView)findViewById(R.id.txtJkel);

        txtNama.setText(SharedPrefManager.getInstance(this).getNama());
        txtEmail.setText(SharedPrefManager.getInstance(this).getEmail());
        txtTelp.setText(SharedPrefManager.getInstance(this).getTelp());
        txtAlamat.setText(SharedPrefManager.getInstance(this).getAlamat());
        txtJkel.setText(SharedPrefManager.getInstance(this).getJkel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_akun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_bantuan) {
            startActivity(new Intent(getApplicationContext(), Bantuan.class));
            return true;
        }else if (id == R.id.menu_logout) {
            dialogKonf.setTitle("Logout");
            dialogKonf.setMessage("Apakah Anda yakin akan keluar dari akun ini ?");
            dialogKonf.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    overridePendingTransition(0, 0);
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
