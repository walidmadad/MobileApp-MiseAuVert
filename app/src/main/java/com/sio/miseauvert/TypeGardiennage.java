package com.sio.miseauvert;

public class TypeGardiennage {
    private int idTypeGardiennage;
    private String libelle;

    public TypeGardiennage(int idTypeGardiennage, String libelle) {
        this.idTypeGardiennage = idTypeGardiennage;
        this.libelle = libelle;
    }

    public int getIdTypeGardiennage() {
        return idTypeGardiennage;
    }

    public void setIdTypeGardiennage(int idTypeGardiennage) {
        this.idTypeGardiennage = idTypeGardiennage;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
