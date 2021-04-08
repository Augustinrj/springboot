package com.example.demo.web.controller;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.HistoriqueDao;
import com.example.demo.dao.RetraitDao;
import com.example.demo.model.Client;
import com.example.demo.model.Historique;
import com.example.demo.model.Retrait;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
public class RetraitController {
    @Autowired
    private RetraitDao retraitDao;
    @Autowired
    private HistoriqueDao historiqueDao;
    @Autowired
    private ClientDao clientDao;
    @GetMapping(value = "/retrait")
    public Iterable<Retrait> listeRetrait(){
        Iterable<Retrait> retrait = retraitDao.findAll();
        return retrait;
    }

    @GetMapping(value = "/retrait/{id}")
    public Retrait afficherUnRetrait(@PathVariable String id){
        Retrait retrait = retraitDao.findByNumCheque(id);
        if(retrait==null) throw new ProductIntrouvableException("le retrait " + id +" est introuvable");
        return retrait;
    }
    @PostMapping(value = "/retrait")
    public String ajouterRetrait(@RequestBody Retrait retrait){
        String numCheque = retraitDao.findTopByOrderByNumChequeDesc().getNumCheque();
        if(numCheque==null)numCheque="00000";
        int nouveauRetrait =(Integer.valueOf(numCheque.substring(2, 5)).intValue())+1;
        retrait.setNumCheque("BS"+Numerotation.enchainer(nouveauRetrait));

        Client client = clientDao.findByNumCompte(retrait.getNumCompte());
        int solde = client.getSolde();
        if(solde<retrait.getMontantRetrait()) return "Solde insuffisant pour cette operation";
        client.setSolde(client.getSolde()-retrait.getMontantRetrait());

        Retrait retraitAdded = retraitDao.save(retrait);
        String lastVersement = retraitAdded.getNumCheque();
        //Ajouter Historique
        int dernier =Integer.valueOf(lastVersement.substring(2, 5)).intValue();
        Historique historique = new Historique();
        historique.setType("R");
        historique.setIndice(dernier);
        historique.setNumCompte(retraitAdded.getNumCompte());
        historique.setDescription("Retrait du "+ new Date());
        historiqueDao.save(historique);

        if (retraitAdded==null) return "Erreur de retrait : Veuillez verifier votre solde";//return ResponseEntity.noContent().build().toString();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(retraitAdded.getNumCheque())
                .toUri();
//        return ResponseEntity.created(location).build();
        return "Retrait de "+retrait.getMontantRetrait()+" au compte "+client.getNumCompte()+" de "+ client.getNom();
    }
    @DeleteMapping(value = "/retrait/{id}")
    public void deleteRetrait(@PathVariable String numCheque){
        retraitDao.deleteByNumCheque(numCheque);
    }
    @PutMapping(value = "/retrait")
    public Retrait updateRetrait(@RequestBody Retrait retrait){
        Retrait retraitAdded = retraitDao.save(retrait);
        if (retraitAdded==null) return null;
        return retraitAdded;
    }
}
