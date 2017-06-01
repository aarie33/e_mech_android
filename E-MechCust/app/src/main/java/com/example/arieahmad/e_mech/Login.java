package com.example.arieahmad.e_mech;

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

public class Login extends Activity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView txtDaftar, txtLupaPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtDaftar = (TextView)findViewById(R.id.txtDaftar);
        txtLupaPassword = (TextView)findViewById(R.id.txtLupapassword);
        progressDialog = new ProgressDialog(this);

        loginTeknisi();
        txtLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLupaPassword = new Intent(getApplicationContext(), LupaPassword.class);
                startActivity(iLupaPassword);
                finish();
            }
        });
        txtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDaftar = new Intent(getApplicationContext(), DaftarCust.class);
                startActivity(iDaftar);
                finish();
            }
        });
    }

    private void loginTeknisi(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edtUsername.getText()) && !TextUtils.isEmpty(edtPassword.getText())) {
                    final String username = edtUsername.getText().toString();
                    final String password = edtPassword.getText().toString();

                    progressDialog.setMessage("Proses login ...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
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
                                                    .custLogin(username, jsonObject.getString("nama"),
                                                            jsonObject.getString("telp"), jsonObject.getString("jkel"),
                                                            jsonObject.getString("alamat"));
                                            Intent intMasuk = new Intent(getApplicationContext(), Home.class);
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
                            params.put("username", username);
                            params.put("password", password);
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
