package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Versement {
    @Id
    private String numVersement;
    private String numCompte;
    private int montantVersement;
    private String dateVersement;

    public Versement() {
    }

    public Versement(String numVersement, String numCompte, int montantVersement, String dateVersement) {
        this.numVersement = numVersement;
        this.numCompte = numCompte;
        this.montantVersement = montantVersement;
        this.dateVersement = dateVersement;
    }

    public String getNumVersement() {
        return numVersement;
    }

    public void setNumVersement(String numVersement) {
        this.numVersement = numVersement;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public int getMontantVersement() {
        return montantVersement;
    }

    public void setMontantVersement(int montantVersement) {
        this.montantVersement = montantVersement;
    }

    public String getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(String dateVersement) {
        this.dateVersement = dateVersement;
    }
}
