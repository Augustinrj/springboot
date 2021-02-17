package com.example.demo.web.controller;

import com.example.demo.dao.VersementDao;
import com.example.demo.model.Client;
import com.example.demo.model.Versement;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class VersementController {

    @Autowired
    private VersementDao versementDao;

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
    public ResponseEntity<Void> ajouterVersement(@RequestBody Versement versement){
        Versement versementAdded = versementDao.save(versement);
        if (versementAdded==null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(versementAdded.getNumVersement())
                .toUri();
        return ResponseEntity.created(location).build();
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
