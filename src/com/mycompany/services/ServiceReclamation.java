/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import Utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.TextSelection.Char;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Reclamation;
import java.util.ArrayList;
import java.util.Map;
//manuelle
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//fin

/**
 *
 * @author mizoj
 */
public class ServiceReclamation {

    //singleton
    public static ServiceReclamation instance = null;

    public static boolean resultOk = true;
    //initilisation connection request
    private ConnectionRequest req;

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public ServiceReclamation() {

        req = new ConnectionRequest();
    }

    //ajout
    public void ajouter(Reclamation rec) {

        String url = Statics.BASE_URL + "/mobileRoua/Reclamation/ajout?id_user=" + rec.getId_user() + "&email=" + rec.getEmail() + "&categorie=" + rec.getCategorie() + "&etat_reclamation=" + rec.getEtat_reclamation() + "&priorite=" + rec.getPriorite();
        System.out.println(url);

        req.setUrl(url);
        req.addResponseListener((e) -> {

            //    String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            //    System.out.println("data == "+str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req); //execution ta3 request sinon yet3ada chy dima nal9awha

    }

    //affichage
    public ArrayList<Reclamation> affichageReclamation() {
        ArrayList<Reclamation> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mobileRoua/Reclamation/aff";

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String, Object> mapReclamation = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamation.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Reclamation rec = new Reclamation();

                        //dima id fi codename one float
                        float id = Float.parseFloat(obj.get("id").toString());

                        //  float id_user = Float.parseFloat(obj.get("id_user").toString());
                        String email = obj.get("email").toString();
                        String categorie = obj.get("categorie").toString();
                        String etat_reclamation = obj.get("etat_reclamation").toString();
                        String priorite = obj.get("priorite").toString();

                        rec.setId((int) id);
                        //     rec.setId_user((int)id_user);
                        rec.setEmail(email);
                        rec.setCategorie(categorie);
                        rec.setEtat_reclamation(etat_reclamation);
                        rec.setPriorite(priorite);

//                        //Date
//                        String DateConverter =  obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}"));
//                        
//                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
//                        
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        String dateString = formatter.format(currentTime);
//                        ev.setDateEvent(dateString);
                        //insert data into ArrayList result
                        result.add(rec);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return result;
    }

    //Delete
    public boolean deleteReclamation(int id) {
        String url = Statics.BASE_URL + "/mobileRoua/Reclamation/delete/" + id;

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                req.removeResponseCodeListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    //Update
    public boolean modifierReclamation(Reclamation rec) {
        String url = Statics.BASE_URL + "/mobileRoua/Reclamation/update/" + rec.getId() + "?id_user=" + rec.getId_user() + "&email=" + rec.getEmail() + "&categorie=" + rec.getCategorie() + "&etat_reclamation=" + rec.getEtat_reclamation() + "&priorite=" + rec.getPriorite();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        return resultOk;

    }
}
