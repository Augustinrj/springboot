package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Retrait {
    @Id
    private String numCheque;
    private String numCompte;
    private int montantRetrait;
    private String dateRetrait;

    public Retrait() {
    }

    public Retrait(String numCheque, String numCompte, int montantRetrait, String dateRetrait) {
        this.numCheque = numCheque;
        this.numCompte = numCompte;
        this.montantRetrait = montantRetrait;
        this.dateRetrait = dateRetrait;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public int getMontantRetrait() {
        return montantRetrait;
    }

    public void setMontantRetrait(int montantRetrait) {
        this.montantRetrait = montantRetrait;
    }

    public String getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(String dateRetrait) {
        this.dateRetrait = dateRetrait;
    }
}
