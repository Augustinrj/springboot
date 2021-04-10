package com.example.demo.dao;

import com.example.demo.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HistoriqueDao extends JpaRepository <Historique,Integer> {

    @Transactional
    Historique deleteByTypeAndIndice(String type, int indice);

    Iterable<Historique> findAllByNumCompte(String numCompte);
}
