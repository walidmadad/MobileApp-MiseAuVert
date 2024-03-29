package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private String idTypeGardiennage, idPension;
    private EditText nom_txt, poids_txt, age_txt, dateFin_txt;
    private TextView error_txt;
    private Spinner espece_txt, pension_txt, typeGardiennage_txt;
    private RadioGroup regle_txt, carnet_txt, vaccin_txt, vermifuge_txt;
    private Spinner especeSpinner, pensionSpinner, typeGardiennageSpinner;
    private Button btn_ajouterAnimal, btn_gestionAnimaux;
    SharedPreferences sharedPreferences;

    private String getregleSelected() {
        RadioGroup txt = findViewById(R.id.regle_txt);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getVermifugeSelected() {
        RadioGroup txt = findViewById(R.id.vermifuge_txt);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getVaccinSelected() {
        RadioGroup txt = findViewById(R.id.vaccin_txt);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getCarnetSelected() {
        RadioGroup txt = findViewById(R.id.carnet_txt);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_animal);

        especeSpinner = findViewById(R.id.espece_txt);
        pensionSpinner = findViewById(R.id.pension_txt);
        typeGardiennageSpinner = findViewById(R.id.typeGardiennage_txt);

        nom_txt = findViewById(R.id.nom_txt);
        poids_txt = findViewById(R.id.poids_txt);
        age_txt = findViewById(R.id.age_txt);
        dateFin_txt = findViewById(R.id.dateFin_txt);

        Intent intent = getIntent();
        String id_proprietaire = intent.getStringExtra("id_proprietaire");



        // Réponse JSON simulée pour le test
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.1/api_Android/AfficherEspece.php";
        String url2 = "http://172.29.104.1/api_Android/AfficherPension.php";
        String url3 = "http://172.29.104.1/api_Android/AfficherTypeGardiennage.php";


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
                                idPension = jsonObject.getString("id_pension");
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
                                idTypeGardiennage = jsonObject.getString("id_TypeGardiennage");
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
                String nomAnimal = nom_txt.getText().toString();
                String poids = poids_txt.getText().toString();
                String age = age_txt.getText().toString();
                String espece = especeSpinner.getSelectedItem().toString();
                String pension = pensionSpinner.getSelectedItem().toString();
                String typeGardiennage = typeGardiennageSpinner.getSelectedItem().toString();
                String regle = getregleSelected();
                String carnet = getCarnetSelected();
                String vaccin = getVaccinSelected();
                String vermifuge = getVermifugeSelected();
                String dateFin = dateFin_txt.getText().toString();


                String url = "http://172.29.21.134/api_Android/CreateAnimal.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(AjouterAnimal.this, response, Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("nom_animal", nomAnimal);
                        params.put("poids", poids);
                        params.put("age", age);
                        params.put("espece", espece);
                        params.put("pension", pension);
                        params.put("typeGardiennage", typeGardiennage);
                        params.put("regle", regle);
                        params.put("carnet", carnet);
                        params.put("vaccin", vaccin);
                        params.put("vermifuge", vermifuge);
                        params.put("dateFin", dateFin);
                        params.put("pension",pension);


                        params.put("id_proprietaire", id_proprietaire);

                        return params;
                    }
                };

                // Ajouter la requête à la file de requêtes Volley
                Volley.newRequestQueue(AjouterAnimal.this).add(stringRequest);

            }
        });

        btn_gestionAnimaux = findViewById(R.id.btn_gestionAnimaux);
        btn_gestionAnimaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjouterAnimal.this, GestionCompte.class);
                startActivity(intent1);
                finish();
            }
        });
    }

}