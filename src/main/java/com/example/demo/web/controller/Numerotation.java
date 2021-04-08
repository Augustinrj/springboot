package com.example.demo.web.controller;

public class Numerotation {
    public static String enchainer (int numero){
        if(numero>=0 && numero<10)return "00"+numero;
        if(numero>=10 && numero<100) return "0"+numero;
        else return ""+numero;
    }
}
