/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.Front;
import com.codename1.uikit.cleanmodern.*;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.BaseForm;
import com.codename1.uikit.cleanmodern.ListCoursForm;
import com.mycompany.entities.cours;
import com.mycompany.services.coursServices;
import java.util.List;

/**
 *
 * @author HP
 */
public class recSearch extends BaseForm {
     Form current;
    public  recSearch(Resources previous) {
        current = this;
        
       
      coursServices sp = new coursServices();
      add(new InfiniteProgress());
                Display.getInstance().scheduleBackgroundTask(()-> {
                    
                    Display.getInstance().callSerially(() -> {
                           
                        removeAll();
                      setLayout(BoxLayout.y());
                     Button searchButton = new Button();
FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Search Icon", 4);
searchButton.setIcon(searchIcon);
       
         
       add(searchButton);

       
       
             
      

                      
                      
                      
                      
                      
                
                      
                       
        Button refreshButton = new Button();
FontImage icon1 = FontImage.createMaterial(FontImage.MATERIAL_REFRESH, UIManager.getInstance().getComponentStyle("Button"));
refreshButton.setIcon(icon1);

       
       refreshButton.addActionListener(e-> new ListCoursForm(previous).show());
        add(refreshButton);

                        List<cours> listerec = sp.affichageCours();
                       
                            
                            
                   for (cours c : listerec) {
    MultiButton m = new MultiButton();
    
    m.setTextLine1("Contenu: " + c.getNom_cours());
    m.setTextLine2("Nom: " + c.getActivite());
  
    
    add(m);
}
                            
                            
                          
                           
                            
                         
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                     revalidate() ;   
                    });
                });
      // getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
       
       
       
    
                        }
}

