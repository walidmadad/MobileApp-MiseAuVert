package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreationCompte extends AppCompatActivity {
    EditText txt_nom, txt_prenom, txt_adresse, txt_cp, txt_ville, txt_dateNaissance, txt_email, txt_mdp, txt_tel;
    String nom, prenom, adresse, cp, ville, dateNaissance, email,password,tel;
    TextView txt_reponse;
    Button btn_creation;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);

        txt_nom = findViewById(R.id.txt_nomCreate);
        txt_prenom =findViewById(R.id.txt_prenomCreate);
        txt_adresse = findViewById(R.id.txt_adresseCreate);
        txt_cp = findViewById(R.id.txt_cpCreate);
        txt_ville = findViewById(R.id.txt_villeCreate);
        txt_dateNaissance = findViewById(R.id.txt_dateNaissanceCreate);;
        txt_email = findViewById(R.id.txt_emailCreate);
        txt_mdp = findViewById(R.id.txt_mdpCreate);
        txt_tel = findViewById(R.id.txt_telCreate);
        txt_reponse = findViewById(R.id.reponse);

        progressBar = findViewById(R.id.loading);

        btn_creation = findViewById(R.id.btn_createCompte);
        btn_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = String.valueOf(txt_nom.getText());
                prenom = String.valueOf(txt_prenom.getText());
                adresse = String.valueOf(txt_adresse.getText());
                cp = String.valueOf(txt_cp.getText());
                ville = String.valueOf(txt_ville.getText());
                dateNaissance = String.valueOf(txt_dateNaissance.getText());
                email = String.valueOf(txt_email.getText());
                password = String.valueOf(txt_mdp.getText());
                tel = String.valueOf(txt_tel.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.29.104.4/api_Android/CreateAccount.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.equals("succes")){
                                    Toast.makeText(getApplicationContext(),"Compte crée", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    txt_reponse.setText(response);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        txt_reponse.setText(error.getLocalizedMessage());
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("nom", nom);
                        paramV.put("prenom", prenom);
                        paramV.put("adresse", adresse);
                        paramV.put("CP", cp);
                        paramV.put("ville", ville);
                        paramV.put("tel", tel);
                        paramV.put("email", email);
                        paramV.put("dateNaissance", dateNaissance);
                        paramV.put("password", password);

                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}