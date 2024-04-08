package com.sio.miseauvert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifierAnimal extends AppCompatActivity {
    private String id_proprietaire, nom_animal,nomAnimal, espece, typeGardiennage, regle, carnet, vaccin, vermifuge, age, poids, pension, idTypeGardiennage, idPension;
    private EditText nom_txt, poids_txt, age_txt, dateFin_txt;
    private TextView error_txt;
    private Spinner espece_txt, pension_txt, typeGardiennage_txt;
    private RadioGroup regle_txt, carnet_txt, vaccin_txt, vermifuge_txt;
    private Spinner especeSpinner, pensionSpinner, typeGardiennageSpinner;
    private Button btn_modifierAnimal, btn_gestionAnimaux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modifier_animal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        id_proprietaire = intent.getStringExtra("id_proprietaire");
        nom_animal = intent.getStringExtra("nom_animal");

        especeSpinner = findViewById(R.id.espece_txt);
        pensionSpinner = findViewById(R.id.pension_txt);
        typeGardiennageSpinner = findViewById(R.id.typeGardiennage_txt);

        nom_txt = findViewById(R.id.nom_txt);
        poids_txt = findViewById(R.id.poids_txt);
        age_txt = findViewById(R.id.age_txt);
        dateFin_txt = findViewById(R.id.dateFin_txt);
        afficherDonnees();
    }
    public void afficherDonnees(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.20.10.2/api_Android/informationsSelected.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);
                            JSONObject jsonObject = new JSONObject(response);

                            String nom = jsonObject.getString("nom");
                            String poids = jsonObject.getString("poids");
                            String age = jsonObject.getString("age");
                            String dateFin = jsonObject.getString("dateFin");
                            String pension = jsonObject.getString("pension");
                            String typeGardiennage = jsonObject.getString("typegardiennage");
                            String regle = jsonObject.getString("regle");
                            String carnet = jsonObject.getString("carnet");
                            String vaccin = jsonObject.getString("vaccin");
                            String vermifuge = jsonObject.getString("vermifuge");
                            String espece = jsonObject.getString("espece");

                            nom_txt.setText(nom);
                            poids_txt.setText(poids);
                            age_txt.setText(age);
                            dateFin_txt.setText(dateFin);

                            List<String> pensionsList = new ArrayList<>();
                            pensionsList.add(pension);

                            ArrayAdapter<String> pensionAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, pensionsList);
                            pensionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pensionSpinner.setAdapter(pensionAdapter);

                            List<String> typeGardiennageList = new ArrayList<>();
                            typeGardiennageList.add(typeGardiennage);

                            ArrayAdapter<String> typeGardiennageAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, typeGardiennageList);
                            typeGardiennageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeGardiennageSpinner.setAdapter(typeGardiennageAdapter);

                            List<String> especeList = new ArrayList<>();
                            especeList.add(typeGardiennage);

                            ArrayAdapter<String> especeAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, especeList);
                            especeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            especeSpinner.setAdapter(typeGardiennageAdapter);

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
                params.put("nom_animal", nom_animal);
                return params;
            }
        };
    }
}