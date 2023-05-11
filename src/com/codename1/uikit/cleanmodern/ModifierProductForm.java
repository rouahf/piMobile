/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.entities.Product;
import com.mycompany.entities.category;
import com.mycompany.services.ServiceCategory;
import com.mycompany.services.ServiceEvent;
import com.mycompany.services.ServiceProduct;
import java.util.ArrayList;

/**
 *
 * @author MSI
 */
public class ModifierProductForm extends BaseForm {
     ArrayList<category> listCategories;
    PickerComponent categoriePC;
    category selectedCategorie = null;
    Form current;
    public ModifierProductForm (Resources res, Product e){
    super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Event");
        getContentPane().setScrollVisible(false);
    
    super.addSideMenu(res);
    
    
    
    
    // BEFORE
    
    
     TextField name = new TextField(e.getNameProduct(), "nameProduct" , 20 , TextField.ANY);
     TextField desc = new TextField(e.getDescription(), "Description" , 20 , TextField.ANY);
     TextField price = new TextField(String.valueOf(e.getPrice()), "price", 20, TextField.ANY);
     TextField image = new TextField(e.getImage(), "image" , 20 , TextField.ANY);
     TextField quantity = new TextField(String.valueOf(e.getQuantity()), "quantity" , 20 , TextField.ANY);
        
        
        
        
        
        name.setUIID("NewsTopLine");
        desc.setUIID("NewsTopLine");
        price.setUIID("NewsTopLine");
        image.setUIID("NewsTopLine");
        quantity.setUIID("NewsTopLine");

        name.setSingleLineTextArea(true);
        desc.setSingleLineTextArea(true);
        price.setSingleLineTextArea(true);
        image.setSingleLineTextArea(true);
        quantity.setSingleLineTextArea(true);

           String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("Categorie");
        listCategories = ServiceCategory.getInstance().affichagecat();
        categorieStrings = new String[listCategories.size()];
        categorieIndex = 0;
        for (category categorie : listCategories) {
            categorieStrings[categorieIndex] = categorie.getNom();
            categorieIndex++;
        }
        if (listCategories.size() > 0) {
            categoriePC.getPicker().setStrings(categorieStrings);
            categoriePC.getPicker().addActionListener(l -> selectedCategorie = listCategories.get(categoriePC.getPicker().getSelectedStringIndex()));
            // si une catégorie est déjà sélectionnée pour l'article, on la sélectionne dans le picker
            if (e.getCategory_id()!= null) {
                categoriePC.getPicker().setSelectedString(e.getCategory_id().getNom());
                selectedCategorie = e.getCategory_id();
            }
        } else {
            categoriePC.getPicker().setStrings("");
        }

        Button btnModifier = new Button("Modifier");
       btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           e.setNameProduct(name.getText());
           e.setDescription(desc.getText());
           e.setPrice(Float.parseFloat(price.getText()));
           e.setImage(desc.getText());           
           e.setQuantity(Integer.parseInt(quantity.getText()));
           e.setCategory_id(selectedCategorie);
           

//appel fonction modfier reclamation men service
       
       if(ServiceProduct.getInstance().modifierpr(e)) { // if true
           new ListProductForm(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(r -> {
           new ListProductForm(res).show();
       });
       
//       
//       Label l2 = new Label("");
//       
//       Label l3 = new Label("");
//       
//       Label l4 = new Label("");
//       
//       Label l5 = new Label("");
//       
//        Label l1 = new Label();
//                Container contentt = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
//
//        Container content = BoxLayout.encloseY(
//                l1, l2, 
//                new FloatingHint(name),
//                createLineSeparator(),
//                new FloatingHint(desc),
//                createLineSeparator(),
//                new FloatingHint(price),
//                createLineSeparator(),
//                 new FloatingHint(image),
//                createLineSeparator(),
//                 new FloatingHint(quantity),
//               contentt.add(categoriePC),
//                createLineSeparator(),
//                btnModifier,
//                btnAnnuler
//                
//               
//        );
//        
//        add(content);
//        show();
//    
//    
//    
//    }
    
      
      Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
content.setUIID("ModifierProductForm");
content.getStyle().setMargin(10, 10, 10, 10);
content.getStyle().setPadding(10, 10, 10, 10);

Label labelname = new Label("nameProduct:");
labelname.setUIID("labelname");
content.add(BoxLayout.encloseY(labelname, name));

Label labeldesc = new Label("description:");
labeldesc.setUIID("labeldesc");
content.add(BoxLayout.encloseY(labeldesc, desc));

Label labelprice = new Label("price:");
labelprice.setUIID("labelprice");
content.add(BoxLayout.encloseY(labelprice, price));


Label labelimage = new Label("image:");
labelimage.setUIID("labelimage");
content.add(BoxLayout.encloseY(labelimage, image));

Label labelquantity = new Label("quantity:");
labelquantity.setUIID("labelquantity");
content.add(BoxLayout.encloseY(labelquantity, quantity));

content.add(categoriePC);

btnModifier.setUIID("BtnModifier");
btnAnnuler.setUIID("BtnAnnuler");

content.add(BoxLayout.encloseX(btnModifier, btnAnnuler));
//BoxLayout.setSameWidth(btnModifier, btnAnnuler);



add(content);
show();


    }
}
