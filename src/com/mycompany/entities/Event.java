/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;



/**
 *
 * @author mizoj
 */
public class Event {
 
    private int id ; 
    private String nom, type ; 
    private String dateEvent ; 

    public Event() {
    }

    public Event(String nom, String type, String dateEvent) {
        this.nom = nom;
        this.type = type;
        this.dateEvent = dateEvent;
    }

    public Event(int id, String nom, String type, String dateEvent) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dateEvent = dateEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }



}
