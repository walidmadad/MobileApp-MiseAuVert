package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class AjouterAnimal extends AppCompatActivity {
    private String nomAnimal, espece, typeGardiennage, regle, carnet, vaccin, vermifuge, age, poids, pension;
    private EditText nom_txt, poids_txt, age_txt;
    private Spinner espece_txt, pension_txt, typeGardiennage_txt;
    private RadioGroup regle_txt, carnet_txt, vaccin_txt, vermifuge_txt;
    private Spinner especeSpinner, pensionSpinner, typeGardiennageSpinner;
    private Button btn_ajouterAnimal;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_animal);

        especeSpinner = findViewById(R.id.espece_txt);
        pensionSpinner = findViewById(R.id.pension_txt);
        typeGardiennageSpinner = findViewById(R.id.typeGardiennage_txt);

        // Réponse JSON simulée pour le test
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.10.2/api_Android/AfficherEspece.php";
        String url2 = "http://172.20.10.2/api_Android/AfficherPension.php";
        String url3 = "http://172.20.10.2/api_Android/AfficherTypeGardiennage.php";
        // Analyse de la réponse JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> especes = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String libelle = jsonObject.getString("libelle");
                                especes.add(libelle);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AjouterAnimal.this, android.R.layout.simple_spinner_item, especes);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            especeSpinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest);

        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> pensions = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String nomPension = jsonObject.getString("nom_pension");
                                pensions.add(nomPension);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AjouterAnimal.this, android.R.layout.simple_spinner_item, pensions);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pensionSpinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest2);

        JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> TypeGardiennage = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String libelle = jsonObject.getString("libelle");
                                TypeGardiennage.add(libelle);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(AjouterAnimal.this, android.R.layout.simple_spinner_item, TypeGardiennage);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeGardiennageSpinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest3);

        btn_ajouterAnimal = findViewById(R.id.btn_ajouterAnimal);

        btn_ajouterAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}