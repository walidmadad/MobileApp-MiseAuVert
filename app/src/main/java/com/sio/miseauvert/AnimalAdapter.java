package com.sio.miseauvert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {

    public AnimalAdapter(Context context, List<Animal> animals) {
        super(context, 0, animals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Animal animal = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView nomAnimalTextView = convertView.findViewById(android.R.id.text1);
        TextView especeAnimalTextView = convertView.findViewById(android.R.id.text2);


        nomAnimalTextView.setText(animal.getNom());
        especeAnimalTextView.setText("  Espece : " + animal.getEspece() +
                "\n  Pension : " + animal.getPension() +
                "\n  Type de gardiennage : " + animal.getTypeGardiennage() +
                "\n  Poids : " + animal.getPoids() +
                "\n  Age : " + animal.getAge() +
                "\n  Carnet : " + animal.getCarnet() +
                "\n  Vaccin : " + animal.getVaccin() +
                "\n  Vermifuge : " + animal.getVermifuge() +
                "\n  Règle : " + animal.getRegle());

        return convertView;
    }
}
