package com.example.demo.model;

public class Transfert {
    private String numEnvoi;
    private String numReceiver;
    private int solde;

    public Transfert() {
    }

    public Transfert(String numEnvoi, String numReceiver, int solde) {
        this.numEnvoi = numEnvoi;
        this.numReceiver = numReceiver;
        this.solde = solde;
    }

    public String getNumEnvoi() {
        return numEnvoi;
    }

    public void setNumEnvoi(String numEnvoi) {
        this.numEnvoi = numEnvoi;
    }

    public String getNumReceiver() {
        return numReceiver;
    }

    public void setNumReceiver(String numReceiver) {
        this.numReceiver = numReceiver;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }
}
