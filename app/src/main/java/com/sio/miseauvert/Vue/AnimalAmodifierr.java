package com.sio.miseauvert.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class AnimalAmodifierr extends AppCompatActivity {

    private Spinner aniamlAmodifier;
    private Button btn_modifier,btn_gestionAnimaux;
    private String id_proprietaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animal_amodifierr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_modifier = (Button) findViewById(R.id.btn_AnimalaModifier);

        aniamlAmodifier = findViewById(R.id.animal_a_modifier_txt);

        Intent intent = getIntent();
        id_proprietaire = intent.getStringExtra("id_proprietaire");
        afficherAnimal();
        btn_gestionAnimaux = findViewById(R.id.btn_gestionAnimaux);
        btn_gestionAnimaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AnimalAmodifierr.this, GestionCompte.class);
                startActivity(intent1);
                finish();
            }
        });
        btn_modifier = findViewById(R.id.btn_AnimalaModifier);

        btn_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalAmodifierr.this, ModifierAnimal.class);

                String nom_animal = aniamlAmodifier.getSelectedItem().toString();
                intent.putExtra("id_proprietaire", id_proprietaire);
                intent.putExtra("nom_animal", nom_animal);
                startActivity(intent);
            }
        });
    }
    public void afficherAnimal(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.4/api_Android/AfficherAnimals.php";

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

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AnimalAmodifierr.this,
                                    android.R.layout.simple_spinner_item, animals);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            aniamlAmodifier.setAdapter(adapter);

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