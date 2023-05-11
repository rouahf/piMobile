/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import Utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Map;
//manuelle
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//fin

/**
 *
 * @author mizoj
 */
public class ServiceArticle {

    //singleton
      

    public static ServiceArticle instance = null;
    private ConnectionRequest cr;

    public static boolean resultOk = true;
    //initilisation connection request
    private ConnectionRequest req;

    public static ServiceArticle getInstance() {
        if (instance == null) {
            instance = new ServiceArticle();
        }
        return instance;
    }

    public ServiceArticle() {

        req = new ConnectionRequest();
    }

    //ajout 
    public void ajouter(Article event) {

        String url = Statics.BASE_URL + "/new?categorie=" + event.getCategorie().getId()
                + "&titre_article=" + event.getTitre()
                + "&auteur_article=" + event.getAuteur()
                + "&contenu_article=" + event.getContenu()
                + "&image_article=" + event.getImage();

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req); //execution ta3 request sinon yet3ada chy dima nal9awha

    }

 // affichage 
public ArrayList<Article> getAllArticles() {
    ArrayList<Article> articles = new ArrayList<>();
    String url = Statics.BASE_URL + "/displayJson";

    ConnectionRequest request = new ConnectionRequest();
    request.setUrl(url);
    request.setHttpMethod("GET");

    request.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser parser = new JSONParser();
            try {
                Map<String, Object> response = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
                ArrayList<Map<String, Object>> articlesJson = (ArrayList<Map<String, Object>>) response.get("root");

                for (Map<String, Object> articleJson : articlesJson) {
                    Article article = new Article();
                    float id = Float.parseFloat(articleJson.get("id").toString());
                    String titre = articleJson.get("titre").toString();
                    String auteur = articleJson.get("auteur").toString();
                    String contenu = articleJson.get("contenu").toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = dateFormat.parse(articleJson.get("date").toString());

                    article.setId((int) id);
                    article.setTitre(titre);
                    article.setAuteur(auteur);
                    article.setContenu(contenu);
                    article.setDate(date);

                    articles.add(article);
                }
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(request);
    return articles;
}
   //Delete 
    public boolean deleteEvent(int id ) {
        String url = Statics.BASE_URL +"/deleteArticle/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
         
    public boolean modifierEvent(Article event) {
    ConnectionRequest req = new ConnectionRequest();
    String url = Statics.BASE_URL + "/updateArticle/" + event.getId();
    if (event.getCategorie() != null) {
        url += "?categorie=" + event.getCategorie().getId();
    }
    if (event.getTitre() != null) {
        url += "&titre_article=" + event.getTitre();
    }
    if (event.getAuteur() != null) {
        url += "&auteur_article=" + event.getAuteur();
    }
    if (event.getContenu() != null) {
        url += "&contenu_article=" + event.getContenu();
    }
    if (event.getImage() != null) {
        url += "&image_article=" + event.getImage();
    }

    req.setUrl(url);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOk = req.getResponseCode() == 200;  // Code response Http 200 ok
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
}



public Map<String, Integer> getEventStatsByType() {
    Map<String, Integer> articleStats = new HashMap<>();
String url = Statics.BASE_URL + "/displayJson";
    ConnectionRequest request = new ConnectionRequest(url);

    request.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            try {
                JSONParser parser = new JSONParser();
                Map<String, Object> jsonResponse = parser.parseJSON(new InputStreamReader(
                        new ByteArrayInputStream(request.getResponseData()), "UTF-8"));

                // Add logging statement to check the JSON response
                Log.p("JSON Response: " + jsonResponse);
                List<Map<String, Object>> articles = (List<Map<String, Object>>) jsonResponse.get("root");
                for (Map<String, Object> article : articles) {
                    String articleAuteur = (String) article.get("auteur");

                    // Increment the count for the eventType
                    articleStats.put(articleAuteur, articleStats.containsKey(articleAuteur) ? articleStats.get(articleAuteur) + 1 : 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(request);
    return articleStats;
}



}
