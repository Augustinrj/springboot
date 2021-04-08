package com.example.demo.web.controller;

import com.example.demo.dao.ClientDao;
import com.example.demo.dao.HistoriqueDao;
import com.example.demo.dao.VersementDao;
import com.example.demo.model.Client;
import com.example.demo.model.Historique;
import com.example.demo.model.Versement;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
public class VersementController {

    @Autowired
    private VersementDao versementDao;
    @Autowired
    private HistoriqueDao historiqueDao;
    @Autowired
    private ClientDao clientDao;

    @GetMapping(value = "/versement")
    public Iterable<Versement> listeVersement(){
        Iterable<Versement> versement = versementDao.findAll();
        return versement;
    }

    @GetMapping(value = "/versement/{id}")
    public Versement afficherUnVersement(@PathVariable String id){
        Versement versement = versementDao.findByNumVersement(id);
        if(versement==null) throw new ProductIntrouvableException("le produit " + id +" est introuvable");
        return versement;
    }
    @PostMapping(value = "/versement")
    public String ajouterVersement(@RequestBody Versement versement){

        String numVers = versementDao.findTopByOrderByNumVersementDesc().getNumVersement();
        if(numVers==null)numVers="00000";
        int nouveauVers =(Integer.valueOf(numVers.substring(2, 5)).intValue())+1;
        versement.setNumVersement("BE"+Numerotation.enchainer(nouveauVers));

        Client client = clientDao.findByNumCompte(versement.getNumCompte());
        if(versement.getMontantVersement()<0) return "Solde est negatif donc n'est pas possible de verser";
        client.setSolde(client.getSolde()+versement.getMontantVersement());

        versementDao.save(versement);
        Versement versementAdded = versement;
        if (versementAdded==null) return "Erreur de versement";//ResponseEntity.noContent().build()
        String lastVersement = versementAdded.getNumVersement();
                //Ajouter Historique
        int dernier =Integer.valueOf(lastVersement.substring(2, 5)).intValue();
        Historique historique = new Historique();
        historique.setType("V");
        historique.setIndice(dernier);
        historique.setNumCompte(versementAdded.getNumCompte());
        historique.setDescription("Versement du "+ new Date());
        historiqueDao.save(historique);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(versementAdded.getNumVersement())
                .toUri();
        return "Versement de "+versement.getNumVersement()+" au compte "+versement.getNumCompte() +" de "+client.getNom();//ResponseEntity.created(location).build();
    }
    @DeleteMapping(value = "/versement/{id}")
    public void deleteVersement(@PathVariable String numVersement){
        versementDao.deleteByNumVersement(numVersement);
    }
    @PutMapping(value = "/versement")
    public Versement updateVersement(@RequestBody Versement versement){
        Versement versementAdded = versementDao.save(versement);
        if (versementAdded==null) return null;
        return versementAdded;
    }
}
