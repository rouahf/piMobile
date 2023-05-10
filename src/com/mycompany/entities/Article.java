package com.mycompany.entities;

import Utils.Statics;
import com.codename1.components.ImageViewer;
import java.util.Date;


public class Article implements Comparable<Article> {
    
    private int id;
    private Categorie categorie;
     private String titre;
     private String contenu;
     private String auteur;
     private String image;
     private Date date;
  private String  urlImage;
  private ImageViewer imageViewer;
    public Article() {}

    public Article(int id, Categorie categorie, String titre, String contenu, String auteur, String image, Date date) {
        this.id = id;
        this.categorie = categorie;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.image = image;
        this.date = date;
    }

    public Article(Categorie categorie, String titre, String contenu, String auteur, String image, Date date) {
        this.categorie = categorie;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.image = image;
        this.date = date;
    }

    public Article(Categorie categorie, String titre, String contenu, String auteur, Date now) {
         this.categorie = categorie;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.date = now;
    }

    public Article(Categorie categorie, String titre, String contenu, String auteur) { 
        this.categorie = categorie;
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }
public String getCategorieup() {
    if (categorie != null) {
        return categorie.getType();
    }
    return "";
}

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    @Override
    public String toString() {
        return "Article : " +
                "id=" + id
                 + ", Categorie=" + categorie
                 + ", Titre=" + titre
                 + ", Contenu=" + contenu
                 + ", Auteur=" + auteur
                 + ", Image=" + image
                 + ", Date=" + date
                ;
    }
    
    @Override
    public int compareTo(Article article) {
        switch (Statics.compareVar) {
            case "Categorie":
                return this.getCategorie().getType().compareTo(article.getCategorie().getType());
            case "Titre":
                 return this.getTitre().compareTo(article.getTitre());
            case "Contenu":
                 return this.getContenu().compareTo(article.getContenu());
            case "Auteur":
                 return this.getAuteur().compareTo(article.getAuteur());
            case "Image":
                 return this.getImage().compareTo(article.getImage());
           
            default:
                return 0;
        }
    }
    public String getImageUrl() {
        return urlImage;
    }

    public void setImageUrl(String urlImage) {
        this.urlImage = urlImage;
    }
      public void setImageViewer(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }
    
    public ImageViewer getImageViewer() {
        return imageViewer;
    }
}
    
