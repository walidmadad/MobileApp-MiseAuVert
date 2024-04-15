package com.sio.miseauvert.Vue;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sio.miseauvert.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupprimerAnimal extends AppCompatActivity {

    private Spinner animalAsupprimer;
    private Button supprimerAnimal, btn_gestionAnimaux;
    String id_proprietaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_animal);

        animalAsupprimer = findViewById(R.id.animal_a_suprimmer_txt);

        Intent intent = getIntent();
        id_proprietaire = intent.getStringExtra("id_proprietaire");
        afficherAnimal();

    }
    public void afficherAnimal(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.4/api_Android/AfficherAnimals.php";


        // Analyse de la réponse JSON
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<String> animals = new ArrayList<>();
                        try {
                            Log.d("Response", response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nomAnimal = jsonObject.getString("nom_animal");
                                animals.add(nomAnimal);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SupprimerAnimal.this,
                                    android.R.layout.simple_spinner_item, animals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            animalAsupprimer.setAdapter(adapter);

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
        supprimerAnimal = findViewById(R.id.btn_supprimerAnimal);

        supprimerAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerAnimal();

            }
        });
        btn_gestionAnimaux = findViewById(R.id.btn_gestionAnimaux);
        btn_gestionAnimaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SupprimerAnimal.this, GestionCompte.class);
                startActivity(intent1);
                finish();
            }
        });
    }
    public void supprimerAnimal(){
        String nomAnimal = animalAsupprimer.getSelectedItem().toString();
        String url = "http://172.29.104.4/api_Android/SupprimerAnimal.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SupprimerAnimal.this, response, Toast.LENGTH_SHORT).show();
                        afficherAnimal();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SupprimerAnimal.this, "Une erreur s'est produite lors de la suppression.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nom_animal", nomAnimal);
                params.put("id_proprietaire", id_proprietaire);
                return params;
            }
        };
        Volley.newRequestQueue(SupprimerAnimal.this).add(stringRequest);


    }

}