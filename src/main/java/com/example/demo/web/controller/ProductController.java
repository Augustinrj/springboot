package com.example.demo.web.controller;

import com.example.demo.dao.ProductDao;
import com.example.demo.model.Product;
import com.example.demo.web.exception.ProductIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @RequestMapping(value = "/Produits",method = RequestMethod.GET)
    public Iterable<Product> listeProduits(){
        Iterable<Product> products = productDao.findAll();
        return products;
    }
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){
        Product product = productDao.findById(id);
        if(product==null) throw new ProductIntrouvableException("le produit " + id +" est introuvable");
        return product;
    }
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product){
        Product productAdded = productDao.save(product);
        if (productAdded==null) return ResponseEntity.noContent().build();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productAdded.getId())
                    .toUri();
       return ResponseEntity.created(location).build();
    }
    @DeleteMapping(value = "/Produits/{id}")
    public void deleteProduit(@PathVariable int id){
        productDao.deleteById(id);
    }
    @PutMapping(value = "/Produits")
    public Product updateProduct(@RequestBody Product product){
        Product productAdded = productDao.save(product);
        if (productAdded==null) return null;
        return productAdded;
    }
}
