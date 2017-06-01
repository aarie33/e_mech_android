package com.example.arieahmad.e_mech;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arieahmad.e_mech.fragments.ListProses;
import com.example.arieahmad.e_mech.fragments.ListSelesai;

public class Order extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;

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
                    //finish();
                    break;
                case R.id.navigation_order:
                    //Toast.makeText(getApplicationContext(), "Order clicked", Toast.LENGTH_SHORT).show();
                    Intent iOrder = new Intent(getApplicationContext(), Order.class);
                    //iOrder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //iOrder.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //iOrder.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
                    //finish();
                    break;
                case R.id.navigation_akun:
                    Intent iAkun = new Intent(getApplicationContext(), Akun.class);
                    //iAkun.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    //iAkun.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(iAkun);
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
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
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
        getMenuInflater().inflate(R.menu.menu_order, menu);

        MenuItem item = menu.findItem(R.id.add_service);
        SpannableStringBuilder builder = new SpannableStringBuilder("* Login");
        // replace "*" with icon
        builder.setSpan(new ImageSpan(this, android.R.drawable.ic_menu_add), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_service) {
            startActivity(new Intent(getApplicationContext(), OrderInput.class));
            overridePendingTransition(R.anim.bottom_slide, R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_order, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position){
                case 0:
                    ListProses listProses = new ListProses();
                    return listProses;
                case 1:
                    ListSelesai listSelesai = new ListSelesai();
                    return listSelesai;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Proses";
                case 1:
                    return "Selesai";
            }
            return null;
        }
    }
}
