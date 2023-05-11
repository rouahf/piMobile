/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import Utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.cours;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MaherKaroui
 */
public class coursServices {

    //singleton 
    public static coursServices instance = null;

    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;

    public static coursServices getInstance() {
        if (instance == null) {
            instance = new coursServices();
        }
        return instance;
    }

    public coursServices() {
        req = new ConnectionRequest();

    }

    //ajout 
    public void ajoutCours(cours cours) {

        String url = Statics.BASE_URL + "/mobilemaher/cours/ajout?nom_cours=" + cours.getNom_cours() + "&activite=" + cours.getActivite() + "&date_cours=" + cours.getDatecours() + "&image=" + cours.getImage(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

    }

    public void statCours() {

        String url = Statics.BASE_URL + "/static";

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

    }
     
     public ArrayList<cours> order_By_NomJSON() {
        ArrayList<cours> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/ordnomMob";
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);

        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp = new JSONParser();

            try {
                Map<String, Object> mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamations.get("root");

                for (Map<String, Object> obj : listOfMaps) {
                    cours re = new cours();

                    double id = Double.parseDouble(obj.get("id").toString());
                    String nom_cours = obj.get("nom_cours").toString();
                    String activite = obj.get("activite").toString();

                    // String imageC = obj.get("image").toString();
                    re.setId((int) id);
                    re.setNom_cours(nom_cours);
                    re.setActivite(activite);
                    //re.setImage(imageC);

                    /* String DateConverter =  obj.get("date").toString().substring(obj.get("date_cours").toString().indexOf("timestamp") + 10 , obj.get("date_cours").toString().lastIndexOf("}"));
                    
                    Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(currentTime);
                    re.setDate_cours(dateString);*/
// Insert data into ArrayList result
                    result.add(re);
                }

            } catch (IOException | NumberFormatException ex) {
                ex.printStackTrace();
                // Handle the exception here
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
    }

    //affichage
    public ArrayList<cours> affichageCours() {
        ArrayList<cours> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mobilemaher/cours/All";
        ConnectionRequest req = new ConnectionRequest();
        req.setUrl(url);

        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp = new JSONParser();

            try {
                Map<String, Object> mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamations.get("root");

                for (Map<String, Object> obj : listOfMaps) {
                    cours re = new cours();

                    double id = Double.parseDouble(obj.get("id").toString());

                    String nom_cours = obj.get("nom_cours").toString();

                    String activite = obj.get("activite").toString();

                    // String imageC = obj.get("image").toString();
                    re.setId((int) id);
                    re.setNom_cours(nom_cours);
                    re.setActivite(activite);
                    //re.setImage(imageC);

                    /* String DateConverter =  obj.get("date").toString().substring(obj.get("date_cours").toString().indexOf("timestamp") + 10 , obj.get("date_cours").toString().lastIndexOf("}"));
                    
                    Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(currentTime);
                    re.setDate_cours(dateString);*/
// Insert data into ArrayList result
                    result.add(re);
                }

            } catch (IOException | NumberFormatException ex) {
                ex.printStackTrace();
                // Handle the exception here
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
    }

    //Update 
    public boolean modifierCours(cours cours) {
        String url = Statics.BASE_URL + "/mobilemaher/cours/update/" + cours.getId() + "&nom_cours=" + cours.getNom_cours() + "&activite=" + cours.getActivite();

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

    /* public ArrayList<cours> affichageCourses() {
        ArrayList<cours> result = new ArrayList<>();
        String url = statics.BASE_URL + "/All";
        req.setUrl(url);
        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp;
            jsonp = new JSONParser();
            try {
                //renvoi une map avec clé = root et valeur le reste
                Map<String, Object> mapVoyage = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapVoyage.get("root");

                for (Map<String, Object> obj : listOfMaps) {
                    cours re = new cours();
                    int id = (int) Float.parseFloat(obj.get("id").toString());
                    String activite = obj.get("activite").toString();
                    String nom_cours = obj.get("nom_cours").toString();
                    String date_cours = obj.get("date_cours").toString();
                    String imageC = obj.get("ImageC").toString();

                    re.setId((int) id);
                    re.setNom_cours(nom_cours);
                    re.setActivite(activite);
                    re.setImage(imageC);

                    String DateConverter = obj.get("date").toString().substring(obj.get("Date").toString().indexOf("timestamp") + 10, obj.get("Date").toString().lastIndexOf("}"));
                    Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = formatter.format(currentTime);
                    re.setDate_cours(dateString);
                    result.add(re);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
    }*/
    public boolean deleteCours(int id) {
        String url = Statics.BASE_URL + "/mobilemaher/cours/delete/" + id;

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

  public Map<String, Integer> getEventStatsByType() {
    Map<String, Integer> coursStats = new HashMap<>();
     
        String url = Statics.BASE_URL + "/mobilemaher/cours/All";
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
                List<Map<String, Object>> cours = (List<Map<String, Object>>) jsonResponse.get("root");
                for (Map<String, Object> cour : cours) {
                    String coursNom = (String) cour.get("nom_cours");

                    // Increment the count for the eventType
                    coursStats.put(coursNom, coursStats.containsKey(coursNom) ? coursStats.get(coursNom) + 1 : 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(request);
    return coursStats;
}


}
