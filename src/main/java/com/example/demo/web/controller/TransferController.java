package com.example.demo.web.controller;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.HistoriqueDao;
import com.example.demo.dao.RetraitDao;
import com.example.demo.dao.VersementDao;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TransferController {
    @Autowired
    private HistoriqueDao historiqueDao;
    @Autowired
    private ClientDao clientDao,clientDao1;
    @Autowired
    private RetraitDao retraitDao;
    @Autowired
    private VersementDao versementDao;

    RetraitController retraitCtrl;
    VersementController versementCtrl;
    @PostMapping(value = "/transfert")
    public String ajouterTransfert(@RequestBody Transfert transfert){
        //TRANFERT = RETRAIT + VERSEMENT;
        String numCheque = retraitDao.findTopByOrderByNumChequeDesc().getNumCheque();
        Retrait retrait = new Retrait();
        retrait.setNumCompte(transfert.getNumEnvoi());
        retrait.setDateRetrait(new Date().toLocaleString());
        retrait.setMontantRetrait(transfert.getSolde());
        if(numCheque==null)numCheque="00000";
        int nouveauRetrait =(Integer.valueOf(numCheque.substring(2, 5)).intValue())+1;
        retrait.setNumCheque("BS"+Numerotation.enchainer(nouveauRetrait));

        Client client = clientDao.findByNumCompte(retrait.getNumCompte());
        int solde = client.getSolde();
        if(solde<retrait.getMontantRetrait()) return "Solde insuffisant pour cette operation";
        client.setSolde(client.getSolde()-transfert.getSolde());
        //clientDao.save(client);
        Retrait retraitAdded = retraitDao.save(retrait);
        String lastRetrait = retraitAdded.getNumCheque();
        //Ajouter Historique
        int dernier =Integer.valueOf(lastRetrait.substring(2, 5)).intValue();
        Historique historique = new Historique();
        historique.setType("R");
        historique.setIndice(dernier);
        historique.setNumCompte(retraitAdded.getNumCompte());
        historique.setDescription("Retrait du "+ new Date());
        historiqueDao.save(historique);

            if(retraitAdded!=null){
                Versement versement = new Versement();
                versement.setNumCompte(transfert.getNumReceiver());
                versement.setDateVersement(new Date().toLocaleString());
                versement.setMontantVersement(transfert.getSolde());

                String numVers = versementDao.findTopByOrderByNumVersementDesc().getNumVersement();
                if(numVers==null)numVers="00000";
                int nouveauVers =(Integer.valueOf(numVers.substring(2, 5)).intValue())+1;
                versement.setNumVersement("BE"+Numerotation.enchainer(nouveauVers));

                Client client1 = clientDao1.findByNumCompte(versement.getNumCompte());
                if(versement.getMontantVersement()<0) return "Solde est negatif donc n'est pas possible de verser";
                client1.setSolde(client1.getSolde()+transfert.getSolde());
                //clientDao1.save(client1);
                Versement versementAdded = versementDao.save(versement);;
                if (versementAdded==null) return "Erreur de versement";//ResponseEntity.noContent().build()
                String lastVersement = versementAdded.getNumVersement();
                //Ajouter Historique
                int dernierV =Integer.valueOf(lastVersement.substring(2, 5)).intValue();
                Historique historiqueV = new Historique();
                historiqueV.setType("V");
                historiqueV.setIndice(dernierV);
                historiqueV.setNumCompte(versementAdded.getNumCompte());
                historiqueV.setDescription("Versement du "+ new Date());
                historiqueDao.save(historiqueV);


                return "Tranfert effectuer";
        }
        else return "Erreur de Tranfert";
    }
}
