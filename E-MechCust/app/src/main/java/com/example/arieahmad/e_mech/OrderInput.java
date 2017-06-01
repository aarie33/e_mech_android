package com.example.arieahmad.e_mech;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderInput extends AppCompatActivity {

    private Spinner spnServis;
    private EditText edtDeskripsi, edtLokasi;
    private Button btnSimpan;
    private ProgressDialog progressDialog;
    private SQLiteHelper sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_input);
        setTitle("Input Order Servis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnServis = (Spinner)findViewById(R.id.spnServis);
        edtDeskripsi = (EditText)findViewById(R.id.edtDeskripsi);
        edtLokasi = (EditText)findViewById(R.id.edtLokasi);
        btnSimpan = (Button)findViewById(R.id.btnSimpan);
        progressDialog = new ProgressDialog(this);
        sqlite = new SQLiteHelper(this);

        simpan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpan(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String deskripsi = edtDeskripsi.getText().toString();
                final String lokasi = edtLokasi.getText().toString();
                final String servis = spnServis.getSelectedItem().toString();
                final String email = SharedPrefManager.getInstance(getApplicationContext()).getEmail();

                if (deskripsi.isEmpty()){
                    edtDeskripsi.requestFocus();
                    Toast.makeText(getApplicationContext(), "Harap isi deskripsi", Toast.LENGTH_SHORT).show();
                }else if (lokasi.isEmpty()) {
                    edtLokasi.requestFocus();
                    Toast.makeText(getApplicationContext(), "Harap isi lokasi", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("Meminta order ...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ORDER_BARU,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                                Toast.LENGTH_LONG).show();
                                        if (jsonObject.getBoolean("error") == false) {
                                            JSONArray data = jsonObject.getJSONArray("data");
                                            JSONObject dataDetail = data.getJSONObject(0);
                                            finish();

                                                /*boolean isInserted = sqlite.insertOrder(dataDetail.getString("no_pemesanan"),
                                                        dataDetail.getString("tgl_pemesanan"),
                                                        dataDetail.getString("jam_pemesanan"),
                                                        dataDetail.getString("deskripsi"),
                                                        dataDetail.getString("biaya"),
                                                        dataDetail.getString("tempat"),
                                                        dataDetail.getString("keahlian"),
                                                        dataDetail.getString("id_teknisi"),
                                                        dataDetail.getString("nm_teknisi"));*/

                                            /*Cursor dataOrder = sqlite.getData("SELECT * FROM " + sqlite.TABLE_ORDER);
                                            Toast.makeText(getApplicationContext(), String.valueOf(dataOrder.getCount()),
                                                    Toast.LENGTH_SHORT).show();*/

                                                /*if(isInserted == true){
                                                    Toast.makeText(getApplicationContext(), "SQLite success",
                                                            Toast.LENGTH_SHORT).show();

                                                    Constants.cekPerubahanOrderProses = true;
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "SQLite failed",
                                                            Toast.LENGTH_SHORT).show();
                                                }*/
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Else",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.hide();
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("deskripsi", deskripsi);
                            params.put("lokasi", lokasi);
                            params.put("servis", servis);
                            params.put("email", email);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }
            }
        });
    }
}
