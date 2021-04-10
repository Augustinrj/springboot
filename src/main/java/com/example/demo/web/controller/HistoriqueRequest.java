package com.example.demo.web.controller;

import com.example.demo.dao.HistoriqueDao;
import com.example.demo.model.Client;
import com.example.demo.model.Historique;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoriqueRequest {
    @Autowired
    private HistoriqueDao historiqueDao;
    @GetMapping(value = "/historiques")
    public Iterable<Historique> getAllHistorique(){
        Iterable<Historique> historiques = historiqueDao.findAll();
        return historiques;
    }
    @GetMapping(value = "/historique/{numCompte}")
    public Iterable<Historique> afficherAllHistorique(@PathVariable String numCompte){
        Iterable<Historique> historique = historiqueDao.findAllByNumCompte(numCompte);//findTopByOrderByNumChequeDesc
        if(historique==null) return historique;
        return historique;
    }
}
