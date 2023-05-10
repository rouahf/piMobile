/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

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
import com.mycompany.entities.Reclamation;
import com.mycompany.services.ServiceReclamation;
/**
 *
 * @author USER
 */
public class ModifierReclamationForm extends BaseForm{
     Form current;
   
     public ModifierReclamationForm (Resources res, Reclamation e){
    super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
   
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Reclamation");
        getContentPane().setScrollVisible(false);
   
    super.addSideMenu(res);
   
   
   
   
    // BEFORE
   
   
    // TextField id_user = new TextField(e.getId_user(), "id_user" , 20 , TextField.ANY);
        TextField email = new TextField(e.getEmail(), "email" , 20 , TextField.ANY);
      TextField categorie = new TextField(e.getCategorie() , "categorie" , 20 , TextField.ANY);
       TextField etat_reclamation = new TextField(e.getEtat_reclamation(), "etat_reclamation" , 20 , TextField.ANY);
        TextField priorite = new TextField(e.getPriorite() , "priorite" , 20 , TextField.ANY);
 
     
       
       
       
       
       
        email.setUIID("NewsTopLine");
        categorie.setUIID("NewsTopLine");
         etat_reclamation.setUIID("NewsTopLine");
          priorite.setUIID("NewsTopLine");
   
       
        email.setSingleLineTextArea(true);
        categorie.setSingleLineTextArea(true);
        etat_reclamation.setSingleLineTextArea(true);
        priorite.setSingleLineTextArea(true);
       
       
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   {
           
           e.setEmail(email.getText());
           e.setCategorie(categorie.getText());
            e.setEtat_reclamation(etat_reclamation.getText());
             e.setPriorite(priorite.getText());
           
           
       
       //appel fonction modfier reclamation men service
       
       if(ServiceReclamation.getInstance().modifierReclamation(e)) { // if true
           new ListReclamationForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(r -> {
           new ListReclamationForm(res).show();
       });
       
       
       Label l2 = new Label("");
       
       Label l3 = new Label("");
       
       Label l4 = new Label("");
       
       Label l5 = new Label("");
       
        Label l1 = new Label();
       
        Container content = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(categorie),
                createLineSeparator(),
                 new FloatingHint(etat_reclamation),
                createLineSeparator(),
                 new FloatingHint(priorite),
                createLineSeparator(),
               
                btnModifier,
                btnAnnuler
               
               
        );
       
        add(content);
        show();
   
   
   
    }
     
}