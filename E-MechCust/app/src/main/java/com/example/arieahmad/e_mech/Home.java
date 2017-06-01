package com.example.arieahmad.e_mech;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView mTextMessage, btnOrderLaptop, btnOrderKomputer;
    private BottomNavigationView navigation;
    static boolean active = false;

     private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //Toast.makeText(getApplicationContext(), "Akun clicked", Toast.LENGTH_SHORT).show();
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
                    iCari.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //iCari.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iCari);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case R.id.navigation_akun:
                    Intent iAkun = new Intent(getApplicationContext(), Akun.class);
                    //iAkun.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //iAkun.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iAkun);
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        btnOrderLaptop = (TextView)findViewById(R.id.btnOrderLaptop);
        btnOrderKomputer = (TextView)findViewById(R.id.btnOrderKomputer);

        btnOrderLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderInput.class));
                overridePendingTransition(R.anim.bottom_slide, R.anim.fade_out);
            }
        });

        btnOrderKomputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderInput.class));
                overridePendingTransition(R.anim.bottom_slide, R.anim.fade_out);
            }
        });

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        active = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
    }

}
