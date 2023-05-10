package com.mycompany.entities;

public class Reclamation {
 
    private int id, id_user;
    private String email, categorie, etat_reclamation,priorite;

    public Reclamation() {
    }

    public Reclamation(int id, int in_user, String email, String categorie, String etat_reclamation,String priorite) {
        this.id = id;
        this.id_user = in_user;
        this.email = email;
        this.categorie = categorie;
        this.etat_reclamation = etat_reclamation;
        this.priorite = priorite;
    }

    public Reclamation(int in_user, String email, String categorie, String etat_reclamation, String priorite) {
        this.id_user = in_user;
        this.email = email;
        this.categorie = categorie;
        this.etat_reclamation = etat_reclamation;
        this.priorite = priorite;
    }

   
    /* public Reclamation(String valueOf, String valueOf0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int in_user) {
        this.id_user = in_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getEtat_reclamation() {
        return etat_reclamation;
    }

    public void setEtat_reclamation(String etat_reclamation) {
        this.etat_reclamation = etat_reclamation;
    }
   
     public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.etat_reclamation = etat_reclamation;
    }
}