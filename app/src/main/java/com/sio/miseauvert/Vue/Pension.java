package com.sio.miseauvert.Vue;

public class Pension {
    private int idPension;
    private String nomPension;

    public Pension(int idPension, String nomPension) {
        this.idPension = idPension;
        this.nomPension = nomPension;
    }

    public int getIdPension() {
        return idPension;
    }

    public void setIdPension(int idPension) {
        this.idPension = idPension;
    }

    public String getNomPension() {
        return nomPension;
    }

    public void setNomPension(String nomPension) {
        this.nomPension = nomPension;
    }

    @Override
    public String toString() {
        return nomPension;
    }
}
