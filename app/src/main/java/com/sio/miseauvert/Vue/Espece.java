package com.sio.miseauvert.Vue;

public class Espece {
    private int idEspece;
    private String libelle;

    public Espece(int idEspece, String libelle) {
        this.idEspece = idEspece;
        this.libelle = libelle;
    }

    public int getIdEspece() {
        return idEspece;
    }

    public void setIdEspece(int idEspece) {
        this.idEspece = idEspece;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
