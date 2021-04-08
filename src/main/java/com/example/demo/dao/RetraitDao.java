package com.example.demo.dao;

import com.example.demo.model.Retrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetraitDao extends JpaRepository<Retrait,String> {
    Retrait findByNumCheque(String id);

    void deleteByNumCheque(String numCheque);

    Retrait findTopByOrderByNumChequeDesc();
}
