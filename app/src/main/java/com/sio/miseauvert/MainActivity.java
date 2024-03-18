package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private EditText txt_mdp, txt_email;
    private TextView txt_error;
    private Button btn_connexion, btn_creer;
    Connection connection;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_mdp = (EditText) findViewById(R.id.txt_mdp);
        txt_error = (TextView) findViewById(R.id.error);

        btn_connexion = findViewById(R.id.btn_connexion);
        btn_creer = findViewById(R.id.btn_create);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_email.getText().toString();
                String mdp = txt_mdp.getText().toString();
                if(GetConnectionFromSQL(email,mdp)){
                    Intent intent = new Intent(MainActivity.this, gestion_compte.class);
                    startActivity(intent);

                }
                else{
                    txt_error.setText(mdp);
                }
            }
        });
        btn_creer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public boolean GetConnectionFromSQL(String email, String password){
        try{
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.CONN();
            if(connection!=null){
                String query = "SELECT password_proprietaire FROM proprietaire WHERE email_Proprietaire = ?";
                PreparedStatement st = connection.prepareStatement(query);
                st.setString(1, email);
                ResultSet rs = st.executeQuery(query);
                if(rs.next()){
                    String hashedPassword = rs.getString("password_proprietaire");
                    boolean passwordsMatch = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
                    return passwordsMatch;
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        } finally {

            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}