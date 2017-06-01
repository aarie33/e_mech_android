package com.example.arieahmad.e_mechteknisi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LupaPasswordSukses extends Activity {

    private Button btnSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password_sukses);

        btnSelesai = (Button)findViewById(R.id.btnSelesai);

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iHome = new Intent(getApplicationContext(), HomeTeknisi.class);
                startActivity(iHome);
                finish();
            }
        });
    }
}
