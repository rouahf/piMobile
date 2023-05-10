package com.mycompany.entities;

import java.util.Date;

public class Commentaire {
    
    private int id;
    private Article article;
     private String contenu;
     private Date date;
     private String nom;
     private String email;
    
    public Commentaire() {}

    public Commentaire(int id, Article article, String contenu, Date date, String nom, String email) {
        this.id = id;
        this.article = article;
        this.contenu = contenu;
        this.date = date;
        this.nom = nom;
        this.email = email;
    }

    public Commentaire(Article article, String contenu, Date date, String nom, String email) {
        this.article = article;
        this.contenu = contenu;
        this.date = date;
        this.nom = nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}