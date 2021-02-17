package com.example.demo.dao;

import com.example.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientDao extends JpaRepository<Client,String> {
    Client findByNumCompte(String numCompte);// ovana ny num compte
    void deleteByNumCompte(String numCompte);
}
