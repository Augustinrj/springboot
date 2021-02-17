package com.example.demo.dao;

import com.example.demo.model.Versement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersementDao extends JpaRepository<Versement,String> {

    Versement findByNumVersement(String id);

    void deleteByNumVersement(String numVersement);
}
