package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupprimerAnimal extends AppCompatActivity {

    private Spinner animalAsupprimer;
    private Button supprimerAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_animal);

        animalAsupprimer = findViewById(R.id.animal_a_suprimmer_txt);

        Intent intent = getIntent();
        String id_proprietaire = intent.getStringExtra("id_proprietaire");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.10.2/api_Android/AfficherAnimals.php";


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

            }
        });
    }

}