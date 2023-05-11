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
import com.mycompany.entities.Product;
import com.mycompany.entities.category;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MSI
 */
public class ServiceProduct {
 
   //singleton
    public static ServiceProduct instance = null ;
    
     public static boolean resultOk = true;
    //initilisation connection request
    private ConnectionRequest req ;
    
     public static ServiceProduct getInstance() {
     if (instance == null)
             instance = new ServiceProduct ();
         return instance;
     }
    
     
     public ServiceProduct(){
     
     req = new ConnectionRequest();
     }
     
     
      //ajout 
    public void ajouterpr( Product p) {
        
        String url = Statics.BASE_URL+"/addrJSONProd?category="+p.getCategory_id().getId()+"&nameProduct="+p.getNameProduct()+"&description="+p.getDescription()+"&price="+p.getPrice()+"&image="+p.getImage()+"&quantity="+p.getQuantity();

        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    //affichage 
    
    public ArrayList<Product> affichagepr()
    {
    ArrayList<Product> result = new ArrayList<>();
    
    String url = Statics.BASE_URL+"/displayprod";
    
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
                        Product ev = new Product();
                        
                        //dima id fi codename one float 
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String nom = obj.get("nameProduct").toString();
                        String desc= obj.get("description").toString();
                        float price= Float.parseFloat(obj.get("price").toString());
                        String image=obj.get("image").toString();
                        float quantity = Float.parseFloat(obj.get("quantity").toString());
                        
                        ev.setId((int)id);
                        ev.setNameProduct(nom);
                        ev.setDescription(desc);
                        ev.setPrice(price);
                        ev.setImage(image);
                        ev.setQuantity((int)quantity);

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
    public boolean deletepr(int id ) {
        String url = Statics.BASE_URL +"/deleteprod/"+id;
        
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
    public boolean modifierpr(Product cat) {
       String url = Statics.BASE_URL + "/updateprod/" + cat.getId();
        url += "?category=" + cat.getCategory_id().getId();
        url += "&nameProduct=" + cat.getNameProduct();
        url += "&description=" + cat.getDescription();
        url += "&price=" + cat.getPrice();
        url += "&image=" + cat.getImage();
        url += "&quantity=" + cat.getQuantity();

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
}




   
         
 