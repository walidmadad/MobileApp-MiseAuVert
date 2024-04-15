package com.sio.miseauvert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class ModifierAnimal extends AppCompatActivity {
    private String id_animal, id_proprietaire, nom_animal, nomAnimal, espece, typeGardiennage, regle, carnet, vaccin, vermifuge, dateFin, age, poids, pension, idTypeGardiennage, idPension;
    private EditText nom_txt, poids_txt, age_txt, dateFin_txt;
    private TextView error_txt;
    private Spinner espece_txt, pension_txt, typeGardiennage_txt;
    private RadioGroup regle_txt, carnet_txt, vaccin_txt, vermifuge_txt;
    private Spinner especeSpinner, pensionSpinner, typeGardiennageSpinner;
    private Button btn_modifierAnimal, btn_gestionAnimaux;
    private List<String> pensionsList = new ArrayList<>();
    private List<String> especeList = new ArrayList<>();
    private List<String> typeGardiennageList = new ArrayList<>();
    private String getregleSelected() {
        RadioGroup txt = findViewById(R.id.regle_txt1);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getVermifugeSelected() {
        RadioGroup txt = findViewById(R.id.vermifuge_txt1);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getVaccinSelected() {
        RadioGroup txt = findViewById(R.id.vaccin_txt1);
        int id = txt.getCheckedRadioButtonId();

        if (id != -1) {
            RadioButton selected = findViewById(id);
            return selected.getText().toString();
        } else {
            return "";
        }
    }
    private String getCarnetSelected() {
        RadioGroup txt = findViewById(R.id.carnet_txt1);
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

        Log.d("ModifierAnimal", "id_proprietaire: " + id_proprietaire);
        Log.d("ModifierAnimal", "nom_animal: " + nom_animal);

        especeSpinner = findViewById(R.id.espece_txt1);
        pensionSpinner = findViewById(R.id.pension_txt1);
        typeGardiennageSpinner = findViewById(R.id.typeGardiennage_txt1);

        nom_txt = findViewById(R.id.nom_txt1);
        poids_txt = findViewById(R.id.poids_txt1);
        age_txt = findViewById(R.id.age_txt1);
        dateFin_txt = findViewById(R.id.dateFin_txt1);

        regle_txt = findViewById(R.id.regle_txt1);
        carnet_txt =findViewById(R.id.carnet_txt1);
        vermifuge_txt = findViewById(R.id.vermifuge_txt1);
        vaccin_txt = findViewById(R.id.vaccin_txt1);

        afficherPensionTypeGardiennageEspece();
        afficherDonnees();




        btn_modifierAnimal = findViewById(R.id.btn_modiferAnimal);

        btn_modifierAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomAnimal = nom_txt.getText().toString();
                poids = poids_txt.getText().toString();
                age = age_txt.getText().toString();
                espece = especeSpinner.getSelectedItem().toString();
                pension = pensionSpinner.getSelectedItem().toString();
                typeGardiennage = typeGardiennageSpinner.getSelectedItem().toString();
                regle = getregleSelected();
                carnet = getCarnetSelected();
                vaccin = getVaccinSelected();
                vermifuge = getVermifugeSelected();
                dateFin = dateFin_txt.getText().toString();
                modifierAnimal(nomAnimal, poids, age, espece, pension, typeGardiennage,regle, carnet,vaccin,vermifuge, dateFin);
            }
        });

        btn_gestionAnimaux = findViewById(R.id.btn_gestionAnimaux);
        btn_gestionAnimaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ModifierAnimal.this, GestionCompte.class);
                startActivity(intent1);
                finish();
            }
        });

    }
    public void afficherPensionTypeGardiennageEspece() {
         // Déclarer la liste en dehors de la méthode

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.4/api_Android/GAP/AfficherEspece.php";
        String url2 = "http://172.29.104.4/api_Android/GAP/AfficherPension.php";
        String url3 = "http://172.29.104.4/api_Android/GAP/AfficherTypeGardiennage.php";

        // Analyse de la réponse JSON pour les espèces
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String libelle = jsonObject.getString("libelle");
                                especeList.add(libelle);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ModifierAnimal.this, android.R.layout.simple_spinner_item, especeList);
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

        // Analyse de la réponse JSON pour les pensions
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String nomPension = jsonObject.getString("nom_pension");
                                idPension = jsonObject.getString("id_pension");
                                pensionsList.add(nomPension);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ModifierAnimal.this, android.R.layout.simple_spinner_item, pensionsList);
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

        // Analyse de la réponse JSON pour les types de gardiennage
        JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String libelle = jsonObject.getString("libelle");
                                idTypeGardiennage = jsonObject.getString("id_TypeGardiennage");
                                typeGardiennageList.add(libelle);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ModifierAnimal.this, android.R.layout.simple_spinner_item, typeGardiennageList);
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
    }
    public void afficherDonnees(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://172.29.104.4/api_Android/GAP/informationsSelected.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("Response", response);
                            JSONObject jsonObject = new JSONObject(response);

                            nomAnimal = jsonObject.getString("nom");
                            poids = jsonObject.getString("poids");
                            age = jsonObject.getString("age");
                            dateFin = jsonObject.getString("dateFin");
                            pension = jsonObject.getString("pension");
                            typeGardiennage = jsonObject.getString("typegardiennage");
                            regle = jsonObject.getString("regle");
                            carnet = jsonObject.getString("carnet");
                            vaccin = jsonObject.getString("vaccin");
                            vermifuge = jsonObject.getString("vermifuge");
                            espece = jsonObject.getString("espece");

                            id_animal = jsonObject.getString("id_animal");

                            nom_txt.setText(nomAnimal);
                            poids_txt.setText(poids);
                            age_txt.setText(age);
                            dateFin_txt.setText(dateFin);

                            if(regle.equals("Oui")){

                                regle_txt.check(R.id.radioButton4);
                            }
                            else{
                                regle_txt.check(R.id.radioButton);
                            }

                            if(carnet.equals("Oui")){

                                carnet_txt.check(R.id.radioButton3);
                            }
                            else{
                                carnet_txt.check(R.id.radioButton2);
                            }

                            if(vermifuge.equals("Oui")){

                                vermifuge_txt.check(R.id.radioButton7);
                            }
                            else{
                                vermifuge_txt.check(R.id.radioButton8);
                            }

                            if(vaccin.equals("Oui")){

                                vaccin_txt.check(R.id.radioButton5);
                            }
                            else{
                                vaccin_txt.check(R.id.radioButton6);
                            }


                            pensionsList.add(pension);

                            ArrayAdapter<String> pensionAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, pensionsList);
                            pensionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pensionSpinner.setAdapter(pensionAdapter);
                            pensionSpinner.setSelection(pensionAdapter.getPosition(pension));

                            typeGardiennageList.add(typeGardiennage);

                            ArrayAdapter<String> typeGardiennageAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, typeGardiennageList);
                            typeGardiennageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            typeGardiennageSpinner.setAdapter(typeGardiennageAdapter);
                            typeGardiennageSpinner.setSelection(typeGardiennageAdapter.getPosition(typeGardiennage));


                            especeList.add(espece);

                            ArrayAdapter<String> especeAdapter = new ArrayAdapter<>(ModifierAnimal.this,
                                    android.R.layout.simple_spinner_item, especeList);
                            especeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            especeSpinner.setAdapter(especeAdapter);
                            especeSpinner.setSelection(especeAdapter.getPosition(espece));

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
        queue.add(stringRequest);
    }

    private void modifierAnimal(String nomAnimal,String poids, String age, String espece, String pension, String typeGardiennage, String regle, String vaccin, String carnet, String vermifuge, String dateFin) {
        String url = "http://172.29.104.4/api_Android/GAP/ModifierAnimal.php";



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ModifierAnimal.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gérer les erreurs de la requête
                error.printStackTrace();
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Paramètres de la requête POST
                Map<String, String> params = new HashMap<>();
                params.put("id_proprietaire",id_proprietaire);
                params.put("id_animal", id_animal);
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
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}