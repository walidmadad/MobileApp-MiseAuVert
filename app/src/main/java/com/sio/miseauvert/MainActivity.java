package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String nom, prenom, email, id_proprietaire;
    private EditText txt_mdp, txt_email;
    private TextView txt_error;
    private Button btn_connexion, btn_creer;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_mdp = (EditText) findViewById(R.id.txt_mdp);
        txt_error = (TextView) findViewById(R.id.error);

        btn_connexion = findViewById(R.id.btn_connexion);
        btn_creer = findViewById(R.id.btn_create);

        progressBar = findViewById(R.id.loadingConnexion);
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txt_email.getText().toString();
                String mdp = txt_mdp.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://172.20.10.2/api_Android/Login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        txt_error.setText(message);
                                        nom = jsonObject.getString("nom");
                                        prenom =  jsonObject.getString("prenom");
                                        id_proprietaire =  jsonObject.getString("id");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("nom", nom);
                                        editor.putString("prenom", prenom);
                                        editor.putString("email", email);
                                        editor.putString("id", id_proprietaire);
                                        editor.apply();
                                        Intent intent = new Intent(MainActivity.this, GestionCompte.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        txt_error.setText(message);
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(error.toString());
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            txt_error.setText(error.getLocalizedMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }) {
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("password", mdp);

                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
        btn_creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreationCompte.class);
                startActivity(intent);
            }
        });
    }

}