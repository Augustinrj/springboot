package com.example.demo.web.controller;

import com.example.demo.dao.ClientDao;
import com.example.demo.model.Client;
import com.example.demo.model.Product;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ClientController {
    @Autowired
    private ClientDao clientDao;

    @GetMapping(value = "/clients")
    public Iterable<Client> listeClients(){
        Iterable<Client> clients = clientDao.findAll();
        return clients;
    }

    @GetMapping(value = "/client/{id}")
    public Client afficherUnClient(@PathVariable String id){
        Client client = clientDao.findByNumCompte(id);
        if(client==null) throw new ProductIntrouvableException("le client " + id +" est introuvable");
        return client;
    }
    @PostMapping(value = "/client")
    public ResponseEntity<Void> ajouterClient(@RequestBody Client client){
        Client clientAdded = clientDao.save(client);
        if (clientAdded==null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientAdded.getNumCompte())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(value = "/clientremove/{id}")
    public void deleteClient(@PathVariable String id){
        long clientdel = clientDao.deleteByNumCompte(id);
    }

    @PutMapping(value = "/clients")
    public Client updateClient(@RequestBody Client client){
        Client clientAdded = clientDao.save(client);
        if (clientAdded==null) return null;
        return clientAdded;
    }

//    @GetMapping(value = "/client/{id}")
//    public Client afficherUnClient(@PathVariable String id){
//        Client client = clientDao.findByNumCompte(id);
//        if(client==null) throw new ProductIntrouvableException("le client " + id +" est introuvable");
//        return client;
//    }
    @GetMapping(value = "/clients/{searchtext}")
    public Iterable<Client> listeClientsByText(@PathVariable String searchtext){
        Iterable<Client> clients = clientDao.findByNumCompteLikeOrNomLike("%"+searchtext+"%","%"+searchtext+"%");
        return clients;
    }

}
