package com.example.demo.dao;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface ClientDao extends JpaRepository<Client,String> {
    Client findByNumCompte(String numCompte);// ovana ny num compte
//    void deleteClient(String numCompte);
    @Transactional
    long deleteByNumCompte(String numCompte);

    Iterable<Client> findByNumCompteLikeOrNomLike(String searchtext,String searchtext2);
}
