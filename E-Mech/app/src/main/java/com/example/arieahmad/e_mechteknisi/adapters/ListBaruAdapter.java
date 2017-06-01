package com.example.arieahmad.e_mechteknisi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.arieahmad.e_mechteknisi.Constants;
import com.example.arieahmad.e_mechteknisi.GetterSetter.ListBaruGetSet;
import com.example.arieahmad.e_mechteknisi.R;
import com.example.arieahmad.e_mechteknisi.RequestHandler;
import com.example.arieahmad.e_mechteknisi.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.arieahmad.e_mechteknisi.R.id.btnAmbil;

/**
 * Created by Arie Ahmad on 5/16/2017.
 */

public class ListBaruAdapter extends BaseAdapter {

    ArrayList listItem;
    Activity activity;

    public ListBaruAdapter(Activity activity, ArrayList items) {
        this.listItem = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View v = convertView;
        ViewHolder holder = null;

        if (v == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.fragment_list_baru_item, null);

            holder.txtKeahlian = (TextView)v.findViewById(R.id.txtKeahlian);
            holder.txtTanggal = (TextView)v.findViewById(R.id.txtTanggal);
            holder.txtJam = (TextView)v.findViewById(R.id.txtJam);
            holder.txtDeskripsi = (TextView)v.findViewById(R.id.txtDeskripsi);
            holder.txtNamaCust = (TextView)v.findViewById(R.id.txtNamaCust);
            holder.txtNoTelp = (TextView)v.findViewById(R.id.txtNoTelp);
            holder.txtTempat = (TextView)v.findViewById(R.id.txtTempat);
            holder.imgServis = (ImageView)v.findViewById(R.id.imgServis);

            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }

        final ListBaruGetSet data = (ListBaruGetSet) getItem(position);
        final AlertDialog.Builder dialogKonf = new AlertDialog.Builder(parent.getContext());

        //Button btnAmbil = (Button) v.findViewById(R.id.btnAmbil);
        TextView btnAmbil = (TextView) v.findViewById(R.id.btnAmbil);
        final ViewHolder finalHolder = holder;
        btnAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKonf.setTitle("Ambil Job");
                dialogKonf.setMessage("Anda akan menerima job ini ?");
                dialogKonf.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ambilJob(data.getNo_pemesanan(), parent);
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
            }
        });

        holder.txtKeahlian.setText(data.getKeahlian());
        holder.txtTanggal.setText(data.getTanggal());
        holder.txtJam.setText(data.getJam());
        holder.txtDeskripsi.setText(data.getDeskripsi());
        holder.txtNamaCust.setText(data.getNamaCust());
        holder.txtNoTelp.setText(data.getNoTelp());
        holder.txtTempat.setText(data.getTempat());

        if (data.getKeahlian().toString().equals("Servis Laptop")){
            holder.imgServis.setImageResource(R.drawable.laptop_icon);
        }else {
            holder.imgServis.setImageResource(R.drawable.pc_icon);
        }

        return v;
    }

    static class ViewHolder{
        TextView txtTanggal, txtJam, txtDeskripsi, txtTempat, txtKeahlian, txtNamaCust, txtNoTelp;
        ImageView imgServis;
    }

    private void ambilJob(final String no_pemesanan, final ViewGroup parent){
        final String email_tekn = SharedPrefManager.getInstance(parent.getContext()).getEmail();
        //progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_AMBIL_JOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(parent.getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getBoolean("error") == false) {
                                //ListBaru listBaru = new ListBaru();
                                //HomeTeknisi.PlaceholderFragment.newInstance(1);
                                //listBaru.getContext().getClass().
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(parent.getContext(), "Koneksi bermasalah", Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.INVISIBLE);
                        //btnCobaLagi.setVisibility(View.VISIBLE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email_tekn);
                params.put("no_pemesanan", no_pemesanan);
                return params;
            }
        };
        RequestHandler.getInstance(parent.getContext()).addToRequestQueue(stringRequest);
    }
}