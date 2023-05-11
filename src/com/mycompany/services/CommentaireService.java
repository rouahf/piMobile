package com.mycompany.services;

import Utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Article;
import com.mycompany.entities.Commentaire;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentaireService {

    public static CommentaireService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commentaire> listCommentaires;

    

    private CommentaireService() {
        cr = new ConnectionRequest();
    }

    public static CommentaireService getInstance() {
        if (instance == null) {
            instance = new CommentaireService();
        }
        return instance;
    }
    
    public ArrayList<Commentaire> getAll() {
        listCommentaires = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/displaycomJson");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommentaires = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCommentaires;
    }

    private ArrayList<Commentaire> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commentaire commentaire = new Commentaire(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makeArticle((Map<String, Object>) obj.get("article")),
                        (String) obj.get("contenu"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("date")),
                        (String) obj.get("nom"),
                        (String) obj.get("email")
                        
                );

                listCommentaires.add(commentaire);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listCommentaires;
    }
    
    public Article makeArticle(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Article article = new Article();
        article.setId((int) Float.parseFloat(obj.get("id").toString()));
        article.setTitre((String) obj.get("titre"));
        return article;
    }
    
    public int add(Commentaire commentaire) {
        return manage(commentaire, false);
    }

    public int edit(Commentaire commentaire) {
        return manage(commentaire, true );
    }

    public int manage(Commentaire commentaire, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/mobile/commentaire/edit");
            cr.addArgument("id", String.valueOf(commentaire.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/mobile/commentaire/add");
        }
        
        cr.addArgument("article", String.valueOf(commentaire.getArticle().getId()));
        cr.addArgument("contenu", commentaire.getContenu());
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        cr.addArgument("nom", commentaire.getNom());
        cr.addArgument("email", commentaire.getEmail());
        
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int commentaireId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/deleteCom/"+commentaireId);
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commentaireId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
