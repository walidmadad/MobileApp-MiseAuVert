package com.sio.miseauvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AjouterAnimal extends AppCompatActivity {

    private EditText nom_txt, poids_txt, age_txt;
    private Spinner espece_txt, pension_txt, typeGardiennage_txt;
    private RadioGroup regle_txt, carnet_txt, vaccin_txt, vermifuge_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_animal);

    }
}