package com.example.arieahmad.e_mechteknisi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LupaPasswordGantiPassword extends Activity {

    private EditText edtPassword, edtUlangiPassword;
    private Button btnSimpanPassword;
    private ProgressDialog progressDialog;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_ganti_password);

        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtUlangiPassword = (EditText)findViewById(R.id.edtUlangiPassword);
        btnSimpanPassword = (Button)findViewById(R.id.btnSimpanPassword);
        progressDialog = new ProgressDialog(this);
        email = SharedPrefManager.getInstance(this).getEmail();

        btnSimpanPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtPassword.getText()) && !TextUtils.isEmpty(edtUlangiPassword.getText())){

                    final String pass1 = edtPassword.getText().toString();
                    final String pass2 = edtUlangiPassword.getText().toString();

                    if (!pass1.equals(pass2)) {
                        Toast.makeText(getApplicationContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
                        edtUlangiPassword.requestFocus();
                    }else {
                        progressDialog.setMessage("Menyimpan password ...");
                        progressDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GANTI_PASSWORD,
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
                                                        .teknisiLogin(jsonObject.getString("email"),
                                                                jsonObject.getString("nama"),
                                                                jsonObject.getString("status"));
                                                Intent intMasuk = new Intent(getApplicationContext(),
                                                        LupaPasswordSukses.class);
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
                                params.put("password", edtPassword.getText().toString());
                                return params;
                            }
                        };
                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
