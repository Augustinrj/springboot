package com.example.demo.web.controller;

import com.example.demo.dao.HistoriqueDao;
import com.example.demo.model.Historique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoriqueController {
    @Autowired
    private static HistoriqueDao historiqueDao;

    public static void addHistorique(String type,int index,String numCompte,String desc){
        Historique historique=null;
        historique.setType(type);
        historique.setIndice(index);
        historique.setNumCompte(numCompte);
        historique.setDescription(desc);
        historiqueDao.save(historique);
    }

    public static Historique[] getAllHistorique(){
        Historique [] historiques = historiqueDao.findAll().toArray(new Historique[0]);
        return historiques;
    }
    public static boolean deleteHistorique(String type,int index){
        Historique historique = historiqueDao.deleteByTypeAndIndice(type,index);
        if (historique!=null) return true;
        return false;
    }
}
