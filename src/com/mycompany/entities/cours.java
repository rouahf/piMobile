/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;


/**
 *
 * @author MaherKaroui
 */
public class cours {
     private int id;
     private String nom_cours,activite,imageC;
     private String  date_cours;
     
           public cours(int id, String date_cours, String nom_cours, String activite, String imageC) {
        this.id = id;
        this.date_cours = date_cours;
        this.nom_cours = nom_cours;
        this.activite = activite;
        this.imageC = imageC;           
    }
       public cours ( String date_cours, String nom_cours, String activite) {
        this.date_cours = date_cours;
        this.nom_cours = nom_cours;
        this.activite = activite;
     
    }

    public cours() {
    }
       
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom_cours() {
        return nom_cours;
    }

    public void setNom_cours(String nom_cours) {
        this.nom_cours = nom_cours;
    }
    
    public String getActivite() {
        return nom_cours;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
    public String getImage() {
        return imageC;
    }

    public void setImage(String image) {
        this.imageC = image;
    }
     public String getDatecours() {
        return date_cours;
    }
     
      public void setDate_cours(String date_cours) {
        this.date_cours = date_cours;
    }

   
    
 
     
    @Override
    public String toString() {
        return "cours{" + "id=" + id + ", nom=" + nom_cours + ", activite=" + activite + ", image=" + imageC + ", date=" + date_cours + "\n";
    }
    
}
