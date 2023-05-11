/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;

/**
 *
 * @author mizoj
 */
public class ModifierEventForm extends BaseForm {
    
    Form current;
    public ModifierEventForm (Resources res, Event e){
    super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Event");
        getContentPane().setScrollVisible(false);
    
    super.addSideMenu(res);
    
    
    
    
    // BEFORE
    
    
     TextField nom = new TextField(e.getNom() , "Nom" , 20 , TextField.ANY);
        TextField type = new TextField(e.getType() , "Description" , 20 , TextField.ANY);
               
 
     
        
        
        
        
        
        nom.setUIID("NewsTopLine");
        type.setUIID("NewsTopLine");
    
        
        nom.setSingleLineTextArea(true);
        type.setSingleLineTextArea(true);
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           e.setNom(nom.getText());
           e.setType(type.getText());
           
           
       
       //appel fonction modfier reclamation men service
       
       if(ServiceEvent.getInstance().modifierEvent(e)) { // if true
           new ListEventForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(r -> {
           new ListEventForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
        
        Container content = BoxLayout.encloseY(
                l1, l2, 
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(type),
                createLineSeparator(),
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
    
    
    
    }
    
    
}
