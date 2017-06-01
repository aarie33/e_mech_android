package com.example.arieahmad.e_mech;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CariTeknisi extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    //finish();
                    break;
                case R.id.navigation_order:
                    Intent iOrder = new Intent(getApplicationContext(), Order.class);
                    //iOrder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //iOrder.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    iOrder.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(iOrder);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.navigation_cari_teknisi:
                    //Toast.makeText(getApplicationContext(), "Akun clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_akun:
                    startActivity(new Intent(getApplicationContext(), Akun.class));
                    overridePendingTransition(0, 0);
                    //finish();
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_teknisi);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
