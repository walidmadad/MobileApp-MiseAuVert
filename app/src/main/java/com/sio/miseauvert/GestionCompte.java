package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GestionCompte extends AppCompatActivity {
    TextView nom_txtView;
    Button ajouterBtn, modifierBtn, supprimerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_compte);
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String nom = sharedPreferences.getString("nom", "");
        String prenom = sharedPreferences.getString("prenom", "");
        String id = sharedPreferences.getString("id", "");
        nom_txtView = findViewById(R.id.txtView_nom);
        nom_txtView.setText(nom.toUpperCase() + " " + prenom.toUpperCase());

        ajouterBtn = findViewById(R.id.btn_ajouter);
        modifierBtn = findViewById(R.id.btn_modifier);
        supprimerBtn = findViewById(R.id.btn_supprimer);
        ajouterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GestionCompte.this, AjouterAnimal.class);
                startActivity(intent1);
                intent1.putExtra("id_proprietaire", id);
                startActivity(intent1);
            }
        });

        modifierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        supprimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GestionCompte.this, SupprimerAnimal.class);
                intent1.putExtra("id_proprietaire", id);
                startActivity(intent1);
            }
        });
    }
}