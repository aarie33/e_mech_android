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

public class LupaPassword extends Activity {

    private EditText edtEmail, edtTelp;
    private Button btnKirimKode;
    private TextView txtLogin, txtDaftar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtTelp = (EditText)findViewById(R.id.edtTelp);
        btnKirimKode = (Button)findViewById(R.id.btnKirimKode);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        txtDaftar = (TextView)findViewById(R.id.txtDaftar);
        progressDialog = new ProgressDialog(this);

        KirimKode();
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

    private void KirimKode(){
        btnKirimKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtEmail.getText())) {
                    Toast.makeText(getApplicationContext(), "Harap mengisi Email", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                }else if (TextUtils.isEmpty(edtTelp.getText())) {
                    Toast.makeText(getApplicationContext(), "Harap mengisi Nomor Telepon", Toast.LENGTH_SHORT).show();
                    edtTelp.requestFocus();
                }else{
                    final String email = edtEmail.getText().toString();
                    final String telp = edtTelp.getText().toString();

                    progressDialog.setMessage("Mohon menunggu ...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LUPA_PASSWORD,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),
                                                Toast.LENGTH_LONG).show();
                                        if (jsonObject.getBoolean("error") == false) {
                                            SharedPrefManager.getInstance(getApplicationContext())
                                                    .setEmail(email);
                                            Intent intMasuk = new Intent(getApplicationContext(), LupaPasswordKode.class);
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
                            params.put("telp", telp);
                            return params;
                        }
                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }
            }
        });
    }
}
