package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText txt_mdp, txt_email;
    private Button btn_connexion, btn_creer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_mdp = (EditText) findViewById(R.id.txt_mdp);

        btn_connexion = findViewById(R.id.btn_connexion);
        btn_creer = findViewById(R.id.btn_create);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gestion_compte.class);
                startActivity(intent);
            }
        });
    }
}