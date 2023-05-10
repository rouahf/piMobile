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
import com.mycompany.entities.Event;
import java.util.ArrayList;
import java.util.Map;
//manuelle
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.Log;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
//fin


/**
 *
 * @author mizoj
 */
public class ServiceEvent {
    
     //singleton
    public static ServiceEvent instance = null ;
    
     public static boolean resultOk = true;
    //initilisation connection request
    private ConnectionRequest req ;
    
     public static ServiceEvent getInstance() {
     if (instance == null)
             instance = new ServiceEvent ();
         return instance;
     }
    
     
     public ServiceEvent(){
     
     req = new ConnectionRequest();
     }
     
     
      //ajout 
    public void ajouter( Event event) {
        
        String url = Statics.BASE_URL+"/mobilehamza/event/ajout?nom="+event.getNom()+"&type="+event.getType();
                
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    //affichage 
    
    public ArrayList<Event> affichageEvent()
    {
    ArrayList<Event> result = new ArrayList<>();
    
    String url = Statics.BASE_URL+"/mobilehamza/event/all";
    
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
        JSONParser jsonp ;
        jsonp = new JSONParser();
        try{
            Map<String,Object> mapEvent = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
             List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapEvent.get("root");
       
        for(Map<String, Object> obj : listOfMaps) {
                        Event ev = new Event();
                        
                        //dima id fi codename one float 
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nom = obj.get("nom").toString();
                        
                        String type = obj.get("type").toString();
                        
                        ev.setId((int)id);
                        ev.setNom(nom);
                        ev.setType(type);
                        
//                        //Date 
//                        String DateConverter =  obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}"));
//                        
//                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
//                        
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        String dateString = formatter.format(currentTime);
//                        ev.setDateEvent(dateString);
                        
                        //insert data into ArrayList result
                        result.add(ev);
        
        
        
        
        }}
        catch(Exception ex){
       ex.printStackTrace();}
        
           
           }
    } );
    
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return result;
    }

            
            
            
            
            //Delete 
    public boolean deleteEvent(int id ) {
        String url = Statics.BASE_URL +"/mobilehamza/event/delete/"+id;
        
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
            
            
            
            
               //Update 
    public boolean modifierEvent(Event event) {
        String url = Statics.BASE_URL +"/mobilehamza/event/update/"+event.getId()+"?nom="+event.getNom()+"&type="+event.getType();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
            
            
            
            
    
    public Map<String, Integer> getEventStatsByType() {
    Map<String, Integer> eventStats = new HashMap<>();
     String url = Statics.BASE_URL+"/mobilehamza/event/all";
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
                List<Map<String, Object>> events = (List<Map<String, Object>>) jsonResponse.get("root");
                for (Map<String, Object> event : events) {
                    String eventType = (String) event.get("type");

                    // Increment the count for the eventType
                    eventStats.put(eventType, eventStats.containsKey(eventType) ? eventStats.get(eventType) + 1 : 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(request);
    return eventStats;
}
    
            
            }




   
         
 