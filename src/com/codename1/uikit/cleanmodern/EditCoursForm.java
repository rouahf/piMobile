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
import com.codename1.uikit.cleanmodern.BaseForm;
import com.codename1.uikit.cleanmodern.ListCoursForm;
import com.mycompany.entities.cours;

import com.mycompany.services.coursServices;

public class EditCoursForm extends BaseForm {

    Form current;

    public EditCoursForm(Resources res, cours r) {
        super("Produits", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField nom_cours = new TextField(r.getNom_cours(), "Nom", 20, TextField.ANY);
nom_cours.getUnselectedStyle().setFgColor(0x000000);
       

        TextField activite = new TextField(r.getActivite(), "Description", 20, TextField.ANY);
        activite.getUnselectedStyle().setFgColor(0x000000);

       

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        // Event onclick btnModifer
        btnModifier.addActionListener(e -> {
            // call the ProduitService method to modify the produit
            r.setNom_cours(nom_cours.getText());
            
            r.setActivite(activite.getText());
            
            if (coursServices.getInstance().modifierCours(r)) {
                new ListCoursForm(res).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {
            new ListCoursForm(res).show();
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label("");

        Container content = BoxLayout.encloseY(
                l1, l2,
                new FloatingHint(nom_cours),
                createLineSeparator(),
               
                new FloatingHint(activite),
                createLineSeparator(),
             
                createLineSeparator(),//ligne de séparation
                btnModifier,
                btnAnnuler
        );

        add(content);
        show();
    }
}
