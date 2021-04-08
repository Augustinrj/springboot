package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Historique {
    @Id
    @GeneratedValue
    private int id;
    private String type; // R: retrait V : Versement
    private int indice;// 1 R001 ou V001
    private String numCompte;
    private String description; // Retrait de .....ou Transfert ...

    public Historique() {
    }

    public Historique(int id, String type, int indice, String numCompte, String desc) {
        this.id = id;
        this.type = type;
        this.indice = indice;
        this.numCompte = numCompte;
        this.description = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }
}
