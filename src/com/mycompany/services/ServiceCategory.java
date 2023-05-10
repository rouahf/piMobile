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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Event;
import com.mycompany.entities.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MSI
 */
public class ServiceCategory {
    
   //singleton
    public static ServiceCategory instance = null ;
    
     public static boolean resultOk = true;
    //initilisation connection request
    private ConnectionRequest req ;
    
     public static ServiceCategory getInstance() {
     if (instance == null)
             instance = new ServiceCategory ();
         return instance;
     }
    
     
     public ServiceCategory(){
     
     req = new ConnectionRequest();
     }
     
     
      //ajout 
    public void ajoutercat( category cat) {
        
    //    String url = Statics.BASE_URL+"/ajout?nom="+event.getNom()+"&type="+event.getType();
        String url = Statics.BASE_URL+"/addcatJ?nameCategorie="+cat.getNom();

        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    //affichage 
    
    public ArrayList<category> affichagecat()
    {
    ArrayList<category> result = new ArrayList<>();
    
    String url = Statics.BASE_URL+"/getallcat";
    
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
                        category ev = new category();
                        
                        //dima id fi codename one float 
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nom = obj.get("nameCategorie").toString();
                        
                        
                        ev.setId((int)id);
                        ev.setNom(nom);
                        
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
    public boolean deletecat(int id ) {
        String url = Statics.BASE_URL +"/deletecat/"+id;
        
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
    public boolean modifiercat(category cat) {
        String url = Statics.BASE_URL +"/updatecat/"+cat.getId()+"?nameCategorie="+cat.getNom();
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
            
            
            
            
            
            }




   
         
 