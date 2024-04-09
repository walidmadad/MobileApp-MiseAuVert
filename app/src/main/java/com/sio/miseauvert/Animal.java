package com.sio.miseauvert;

public class Animal {
    private String id;
    private String nom;
    private String pension;
    private String typeGardiennage;
    private String poids;
    private String age;
    private String carnet;
    private String vaccin;
    private String vermifuge;
    private String regle;
    private String espece;

    public Animal(String id, String nom, String pension, String typeGardiennage, String poids, String age, String carnet, String vaccin, String vermifuge, String regle, String espece) {
        this.id = id;
        this.nom = nom;
        this.pension = pension;
        this.typeGardiennage = typeGardiennage;
        this.poids = poids;
        this.age = age;
        this.carnet = carnet;
        this.vaccin = vaccin;
        this.vermifuge = vermifuge;
        this.regle = regle;
        this.espece = espece;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPension() {
        return pension;
    }

    public String getTypeGardiennage() {
        return typeGardiennage;
    }

    public String getPoids() {
        return poids;
    }

    public String getAge() {
        return age;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getVaccin() {
        return vaccin;
    }

    public String getVermifuge() {
        return vermifuge;
    }

    public String getRegle() {
        return regle;
    }

    public String getEspece() {
        return espece;
    }
}
