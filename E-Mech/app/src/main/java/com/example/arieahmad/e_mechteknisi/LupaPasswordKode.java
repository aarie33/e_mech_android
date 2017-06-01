package com.example.arieahmad.e_mechteknisi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LupaPasswordKode extends Activity {

    private EditText edtKode;
    private Button btnVerifikasi, btnKirimKode;
    private TextView txtLogin, txtDaftar;
    private ProgressDialog progressDialog;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_kode);

        edtKode = (EditText)findViewById(R.id.edtKode);
        btnVerifikasi = (Button)findViewById(R.id.btnVerifikasi);
        btnKirimKode = (Button)findViewById(R.id.btnKirimKode);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        txtDaftar = (TextView)findViewById(R.id.txtDaftar);
        progressDialog = new ProgressDialog(this);
        email = SharedPrefManager.getInstance(this).getEmail();

        verifikasi();
        btnKirimKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iKirimKode = new Intent(getApplicationContext(), LupaPassword.class);
                startActivity(iKirimKode);
                finish();
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(iLogin);
                finish();
            }
        });
        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDaftar = new Intent(getApplicationContext(), DaftarTeknisi.class);
                startActivity(iDaftar);
                finish();
            }
        });
    }

    private void verifikasi(){
        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtKode.getText())) {
                    final String kode = edtKode.getText().toString();

                    progressDialog.setMessage("Verifikasi akun ...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_VERIFIKASI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                                Toast.LENGTH_LONG).show();
                                        if (jsonObject.getBoolean("error") == false) {
                                            Intent intMasuk = new Intent(getApplicationContext(), LupaPasswordGantiPassword.class);
                                            startActivity(intMasuk);
                                            finish();
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
                            params.put("email", email);
                            params.put("kode", kode);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
