/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.category;
import com.mycompany.services.ServiceCategory;

/**
 *
 * @author MSI
 */
public class ModifierCategoryForm extends BaseForm {
    
    Form current;
    public ModifierCategoryForm (Resources res, category cat){
    super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout categories");
        getContentPane().setScrollVisible(false);
    
    super.addSideMenu(res);
    
    
    
    
    // BEFORE
    
    
     TextField nom = new TextField(cat.getNom() , "nameCategorie" , 20 , TextField.ANY);
               
 
     
        
        
        
        
     
        
        nom.setUIID("NewsTopLine");
    
        
        nom.setSingleLineTextArea(true);
        
        
        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //categorie onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           cat.setNom(nom.getText());

       
       //appel fonction modfier reclamation men service
       
       if(ServiceCategory.getInstance().modifiercat(cat)) { // if true
           new ListCategoryForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(r -> {
           new ListCategoryForm(res).show();
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
              
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        show();
    
    
    
    }
    
    
}
