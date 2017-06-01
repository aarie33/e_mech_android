package com.example.arieahmad.e_mechteknisi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class DaftarTeknisi extends Activity {

    private EditText edtNama, edtEmail, edtTelp, edtPassword, edtUlangiPassword;
    private RadioButton rdLaki, rdPerempuan;
    private CheckBox checkAhliLaptop, checkAhliKomputer;
    private TextView txtMasuk;
    private Button btnDaftar;
    private ProgressDialog progressDialog;
    private String jkel, ahliLaptop="", ahliKomputer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_teknisi);

        edtNama = (EditText)findViewById(R.id.edtNama);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtTelp = (EditText)findViewById(R.id.edtTelp);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtUlangiPassword = (EditText)findViewById(R.id.edtUlangiPassword);
        rdLaki = (RadioButton)findViewById(R.id.rdLaki);
        rdPerempuan = (RadioButton)findViewById(R.id.rdPerempuan);
        checkAhliLaptop = (CheckBox)findViewById(R.id.checkAhliLaptop);
        checkAhliKomputer = (CheckBox)findViewById(R.id.checkAhliKomputer);
        txtMasuk = (TextView)findViewById(R.id.txtMasuk);
        btnDaftar = (Button)findViewById(R.id.btnDaftar);
        progressDialog = new ProgressDialog(this);

        Daftar();
        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(intLogin);
                finish();
            }
        });

        rdLaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioJkel(v);
            }
        });
        rdPerempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioJkel(v);
            }
        });
    }

    private void Daftar(){
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = edtNama.getText().toString();
                final String email = edtEmail.getText().toString();
                final String telp = edtTelp.getText().toString();
                final String password = edtPassword.getText().toString();

                if (nama != "" && email != "" && telp != "" && jkel != "" && password != "" &&
                        edtUlangiPassword.getText().toString() != "" &&
                        (checkAhliLaptop.isChecked() || checkAhliKomputer.isChecked())) {

                    if (!password.equals(edtUlangiPassword.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
                        edtUlangiPassword.requestFocus();
                    }else {

                        if (checkAhliLaptop.isChecked()) {
                            ahliLaptop = checkAhliLaptop.getText().toString();
                        }
                        if (checkAhliKomputer.isChecked()) {
                            ahliKomputer = checkAhliKomputer.getText().toString();
                        }

                        progressDialog.setMessage("Mendaftar teknisi ...");
                        progressDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
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
                                                        .teknisiLogin(email, jsonObject.getString("nama"),
                                                                jsonObject.getString("status"));
                                                Intent intMasuk = new Intent(getApplicationContext(), HomeTeknisi.class);
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
                                params.put("nama", nama);
                                params.put("email", email);
                                params.put("jkel", jkel);
                                params.put("no_telp", telp);
                                params.put("password", password);
                                params.put("ahliLaptop", ahliLaptop);
                                params.put("ahliKomputer", ahliKomputer);
                                return params;
                            }
                        };
                        //Digantikan dengan class terpisah (Google recommended)
                        //RequestQueue requestQueue = Volley.newRequestQueue(this);
                        //requestQueue.add(stringRequest);

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void radioJkel(View v){
        switch(v.getId()) {
            case R.id.rdLaki:
                jkel = "Laki-laki";
                break;
            case R.id.rdPerempuan:
                jkel = "Perempuan";
                break;
        }
    }

}
