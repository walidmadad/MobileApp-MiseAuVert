package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionCompte extends AppCompatActivity {
    TextView nom_txtView;
    Button ajouterBtn, modifierBtn, supprimerBtn;
    ListView listAnimal;
    String id_proprietaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_compte);
        Intent intent = getIntent();
        id_proprietaire = intent.getStringExtra("id_proprietaire");

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String nom = sharedPreferences.getString("nom", "");
        String prenom = sharedPreferences.getString("prenom", "");
        String id = sharedPreferences.getString("id", "");
        nom_txtView = findViewById(R.id.txtView_nom);
        nom_txtView.setText(nom.toUpperCase() + " " + prenom.toUpperCase());

        listAnimal = findViewById(R.id.lst_animal);

        ajouterBtn = findViewById(R.id.btn_ajouter);
        modifierBtn = findViewById(R.id.btn_modifier);
        supprimerBtn = findViewById(R.id.btn_supprimer);

        afficherAnimal(id);
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
                Intent intent = new Intent(GestionCompte.this, AnimalAmodifierr.class);
                intent.putExtra("id_proprietaire", id);
                startActivity(intent);
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
    public void afficherAnimal(String id_proprietaire){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.4/api_Android/GAP/AfficherAnimals.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<com.sio.miseauvert.Animal> animals = new ArrayList<>();
                        try {
                            Log.d("Response", response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject animalObject = jsonArray.getJSONObject(i);
                                String idAnimal = animalObject.getString("id_Animal");
                                String nomAnimal = animalObject.getString("nom_animal");
                                String pension = animalObject.getString("pension");
                                String typeGardiennage = animalObject.getString("typeGardiennage");
                                String poids = animalObject.getString("poids");
                                String age = animalObject.getString("age");
                                String carnet = animalObject.getString("carnet");
                                String vaccin = animalObject.getString("vaccin");
                                String vermifuge = animalObject.getString("vermifuge");
                                String regle = animalObject.getString("regle");
                                String espece = animalObject.getString("espece");
                                com.sio.miseauvert.Animal animal = new Animal(idAnimal, nomAnimal, pension, typeGardiennage, poids, age, carnet, vaccin, vermifuge, regle, espece);
                                animals.add(animal);
                            }


                            // Créer un adaptateur personnalisé pour afficher les noms d'animaux et leurs espèces
                            com.sio.miseauvert.AnimalAdapter adapter = new AnimalAdapter(GestionCompte.this, animals);
                            listAnimal.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_proprietaire", id_proprietaire);
                return params;
            }
        };

        queue.add(stringRequest);

    }
}