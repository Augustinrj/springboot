package com.example.demo.web.controller;

import com.example.demo.model.Retrait;
import com.example.demo.model.Transfert;
import com.example.demo.model.Versement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TransferController {
    RetraitController retraitCtrl;
    VersementController versementCtrl;
    @PostMapping(value = "/transfert")
    public String ajouterTransfert(@RequestBody Transfert transfert){
        Retrait retrait = new Retrait();
        retrait.setNumCompte(transfert.getNumEnvoi());
        retrait.setDateRetrait(new Date().toLocaleString());
        retrait.setMontantRetrait(transfert.getSolde());
        System.out.println("---------------OK----------------");
        String resultRetrait = retraitCtrl.operationRetrait(retrait);//.ajouterRetrait(retrait);
        System.out.println("---------------1----------------");
        if (resultRetrait.contains("Retrait de")){
            System.out.println("---------------2----------------");
            Versement versement = new Versement();
            versement.setNumCompte(transfert.getNumReceiver());
            versement.setDateVersement(new Date().toLocaleString());
            versement.setMontantVersement(transfert.getSolde());
            return "Tranfert effectuer";
        }
        else return "Erreur de Tranfert";
    }
}
