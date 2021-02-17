package com.example.demo.web.controller;

import com.example.demo.dao.RetraitDao;
import com.example.demo.model.Retrait;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@RestController
public class RetraitController {
    @Autowired
    private RetraitDao retraitDao;
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
    public ResponseEntity<Void> ajouterRetrait(@RequestBody Retrait retrait){
        Retrait retraitAdded = retraitDao.save(retrait);
        if (retraitAdded==null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(retraitAdded.getNumCheque())
                .toUri();
        return ResponseEntity.created(location).build();
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
