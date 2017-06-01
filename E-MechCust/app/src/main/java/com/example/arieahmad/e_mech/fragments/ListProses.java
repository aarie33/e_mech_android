package com.example.arieahmad.e_mech.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.arieahmad.e_mech.Constants;
import com.example.arieahmad.e_mech.OrderGetSet;
import com.example.arieahmad.e_mech.R;
import com.example.arieahmad.e_mech.RequestHandler;
import com.example.arieahmad.e_mech.SQLiteHelper;
import com.example.arieahmad.e_mech.SharedPrefManager;
import com.example.arieahmad.e_mech.adapters.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListProses extends Fragment {

    private static final String TAG_RESULTS="data";
    private static final String NO_PEMESANAN = "no_pemesanan";
    private static final String TGL_PEMESANAN = "tgl_pemesanan";
    private static final String JAM_PEMESANAN ="jam_pemesanan";
    private static final String DESKRIPSI ="deskripsi";
    private static final String TEMPAT ="tempat";
    private static final String BIAYA ="biaya";
    private static final String KEAHLIAN ="keahlian";
    private static final String ID_TEKNISI ="id_teknisi";

    private JSONArray orders = null;
    //private ArrayList<HashMap<String, String>> listItem;
    private ArrayList listItem;
    private ArrayList listItemSQLite;

    private ListView listProses;
    private ProgressBar progressBar;
    private Button btnCobaLagi;
    private SQLiteHelper sqlite;

    public ListProses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_proses, container, false);
        listProses = (ListView) view.findViewById(R.id.listProses);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        btnCobaLagi = (Button)view.findViewById(R.id.btnCobaLagi);
        listItem = new ArrayList<HashMap<String,String>>(); //aktif untuk getonline
        sqlite = new SQLiteHelper(getActivity());
        getDataOrderOnline();

        btnCobaLagi.setVisibility(View.INVISIBLE);
        btnCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getDataOffline();
                getDataOrderOnline();
            }
        });
        return view;
    }

    private void getDataOffline(){
        Cursor data = sqlite.getData("SELECT * FROM " + sqlite.TABLE_ORDER +
                " ORDER BY " + sqlite.TGL_ORDER);

        listItemSQLite = new ArrayList<>();

        OrderGetSet varGetSet = null;
        int i=0;
        while (data.moveToNext()){

            varGetSet = new OrderGetSet();
            varGetSet.setNo_pemesanan(String.valueOf(0));
            varGetSet.setTgl_pemesanan(String.valueOf(data.getString(1)));
            varGetSet.setJam_pemesanan(String.valueOf(data.getString(2)));
            varGetSet.setDeskripsi(String.valueOf(data.getString(3)));
            varGetSet.setBiaya(String.valueOf(data.getString(4)));
            varGetSet.setTempat(String.valueOf(data.getString(5)));
            varGetSet.setKeahlian(String.valueOf(data.getString(6)));
            varGetSet.setId_teknisi(String.valueOf(data.getString(7)));
            varGetSet.setNo_pemesanan(String.valueOf(data.getString(8)));

            listItemSQLite.add(varGetSet);
        }

        OrderAdapter adapter = new OrderAdapter(getActivity(), listItemSQLite);
        listProses.setAdapter(adapter);

        progressBar.setVisibility(View.INVISIBLE);
        btnCobaLagi.setVisibility(View.INVISIBLE);

        listProses.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //OrderGetSet dataPemasukan = (OrderGetSet) listItem.get(position);
                        //Intent intent = new Intent(getActivity(), PemasukanEdit.class);
                        //intent.putExtra(PemasukanEdit.KEY_ITEM, dataPemasukan);
                        //startActivityForResult(intent, 0);
                        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void getDataOrderOnline(){
        final String email = SharedPrefManager.getInstance(getContext()).getEmail();
        progressBar.setVisibility(View.VISIBLE);
        btnCobaLagi.setVisibility(View.INVISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DATA_ORDER_PROSES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error") == false) {
                                progressBar.setVisibility(View.INVISIBLE);
                                btnCobaLagi.setVisibility(View.INVISIBLE);
                                ///show list
                                try {
                                    orders = jsonObject.getJSONArray(TAG_RESULTS);
                                    OrderGetSet varGetSet = null;
                                    listItem = new ArrayList<>();

                                    for(int i=0;i<orders.length();i++){
                                        JSONObject c = orders.getJSONObject(i);
                                        /*
                                        for sqlite
                                        String no_pemesanan = c.getString("no_pemesanan");
                                        String tgl_pemesanan = c.getString("tgl_pemesanan");
                                        String jam_pemesanan = c.getString("jam_pemesanan");
                                        String deskripsi = c.getString("deskripsi");
                                        String tempat = c.getString("tempat");
                                        String biaya = c.getString("biaya");
                                        String keahlian = c.getString("keahlian");
                                        String id_teknisi = c.getString("id_teknisi");

                                        HashMap<String,String> dataOrder = new HashMap<String,String>();

                                        dataOrder.put(NO_PEMESANAN, no_pemesanan);
                                        dataOrder.put(TGL_PEMESANAN, tgl_pemesanan);
                                        dataOrder.put(JAM_PEMESANAN, jam_pemesanan);
                                        dataOrder.put(DESKRIPSI, deskripsi);
                                        dataOrder.put(TEMPAT, tempat);
                                        dataOrder.put(BIAYA, biaya);
                                        dataOrder.put(KEAHLIAN, keahlian);
                                        dataOrder.put(ID_TEKNISI, id_teknisi);
                                        listItem.add(dataOrder);*/

                                        varGetSet = new OrderGetSet();
                                        varGetSet.setNo_pemesanan(c.getString("no_pemesanan"));
                                        varGetSet.setTgl_pemesanan(c.getString("tgl_pemesanan"));
                                        varGetSet.setJam_pemesanan(c.getString("jam_pemesanan"));
                                        varGetSet.setDeskripsi(c.getString("deskripsi"));
                                        varGetSet.setBiaya(c.getString("biaya"));
                                        varGetSet.setTempat(c.getString("tempat"));
                                        varGetSet.setKeahlian(c.getString("keahlian"));
                                        varGetSet.setId_teknisi(c.getString("id_teknisi"));

                                        listItem.add(varGetSet);
                                    }

                                    /*
                                    for sqlite
                                    ListAdapter adapter = new SimpleAdapter(

                                            ListProses.this.getContext(), listItem, R.layout.fragment_list_proses_item,
                                            new String[]{TGL_PEMESANAN, JAM_PEMESANAN, DESKRIPSI,
                                            TEMPAT, KEAHLIAN},
                                            new int[]{R.id.txtTanggal, R.id.txtJam, R.id.txtDeskripsi,
                                            R.id.txtTempat, R.id.txtKeahlian}
                                    ); */

                                    ListAdapter adapter = new OrderAdapter(getActivity(), listItem);

                                    listProses.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(getContext(), jsonObject.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        btnCobaLagi.setVisibility(View.VISIBLE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.cekPerubahanOrderProses == true){
            //getDataOffline();
            getDataOrderOnline();
            Constants.cekPerubahanOrderProses = false;
        }
    }
}
