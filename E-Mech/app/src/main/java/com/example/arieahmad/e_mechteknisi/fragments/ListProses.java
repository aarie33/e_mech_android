package com.example.arieahmad.e_mechteknisi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.arieahmad.e_mechteknisi.Constants;
import com.example.arieahmad.e_mechteknisi.R;
import com.example.arieahmad.e_mechteknisi.RequestHandler;
import com.example.arieahmad.e_mechteknisi.SharedPrefManager;

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
    private static final String NAMA_CUST ="nama_cust";
    private static final String TELP_CUST ="telp_cust";

    private JSONArray orders = null;
    private ArrayList<HashMap<String, String>> listItem;
    //private ArrayList listItem;

    private ListView listProses;
    private ProgressBar progressBar;
    private Button btnCobaLagi;
    private TextView txtJlhData;

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
        txtJlhData = (TextView)view.findViewById(R.id.txtJlhData);
        listItem = new ArrayList<HashMap<String,String>>();
        //sqlite = new SQLiteHelper(getActivity());
        getDataOrderOnline();

        invisible();
        btnCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataOrderOnline();
            }
        });

        return view;
    }

    private void getDataOrderOnline(){
        final String email = SharedPrefManager.getInstance(getContext()).getEmail();
        progressBar.setVisibility(View.VISIBLE);
        invisible();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DATA_ORDER_PROSES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error") == false) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (jsonObject.getInt("jlh_data") == 0){
                                    txtJlhData.setVisibility(View.VISIBLE);
                                }else {
                                    invisible();
                                    ///show list
                                    try {
                                        orders = jsonObject.getJSONArray(TAG_RESULTS);

                                        for (int i = 0; i < orders.length(); i++) {
                                            JSONObject c = orders.getJSONObject(i);
                                            String no_pemesanan = c.getString("no_pemesanan");
                                            String tgl_pemesanan = c.getString("tgl_pemesanan");
                                            String jam_pemesanan = c.getString("jam_pemesanan");
                                            String deskripsi = c.getString("deskripsi");
                                            String tempat = c.getString("tempat");
                                            String biaya = c.getString("biaya");
                                            String keahlian = c.getString("keahlian");
                                            String nama_cust = c.getString("nama_cust");
                                            String telp_cust = c.getString("telp_cust");

                                            HashMap<String, String> dataOrder = new HashMap<String, String>();

                                            dataOrder.put(NO_PEMESANAN, no_pemesanan);
                                            dataOrder.put(TGL_PEMESANAN, tgl_pemesanan);
                                            dataOrder.put(JAM_PEMESANAN, jam_pemesanan);
                                            dataOrder.put(DESKRIPSI, deskripsi);
                                            dataOrder.put(TEMPAT, tempat);
                                            dataOrder.put(BIAYA, biaya);
                                            dataOrder.put(KEAHLIAN, keahlian);
                                            dataOrder.put(NAMA_CUST, nama_cust);
                                            dataOrder.put(TELP_CUST, telp_cust);

                                            listItem.add(dataOrder);
                                        }

                                        ListAdapter adapter = new SimpleAdapter(
                                                ListProses.this.getContext(), listItem, R.layout.fragment_list_proses_item,
                                                new String[]{TGL_PEMESANAN, JAM_PEMESANAN, DESKRIPSI,
                                                        TEMPAT, KEAHLIAN, NAMA_CUST, TELP_CUST},
                                                new int[]{R.id.txtTanggal, R.id.txtJam, R.id.txtDeskripsi,
                                                        R.id.txtTempat, R.id.txtKeahlian, R.id.txtNamaCust, R.id.txtNoTelp}
                                        );

                                        listProses.setAdapter(adapter);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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

    private void invisible(){
        btnCobaLagi.setVisibility(View.INVISIBLE);
        txtJlhData.setVisibility(View.INVISIBLE);
    }

    private void visible(){
        btnCobaLagi.setVisibility(View.VISIBLE);
        txtJlhData.setVisibility(View.VISIBLE);
    }

}
