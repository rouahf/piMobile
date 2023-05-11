/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import com.mycompany.services.CategorieService;
import com.mycompany.services.ServiceArticle;
import java.util.ArrayList;

/**
 *
 * @author mizoj
 */
public class ModifierArtcileForm extends BaseForm {

    Form current;
    ArrayList<Categorie> listCategories;
    PickerComponent categoriePC;
    Categorie selectedCategorie = null;

    public ModifierArtcileForm(Resources res, Article e) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Article");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField titre = new TextField(e.getTitre(), "titre", 20, TextField.ANY);
        titre.setSingleLineTextArea(true);

        TextField auteur = new TextField(e.getAuteur(), "auteur", 20, TextField.ANY);
        auteur.setSingleLineTextArea(true);

        TextField contenu = new TextField(e.getContenu(), "contenu", 20, TextField.ANY);
        contenu.setSingleLineTextArea(true);

        String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("Categorie");
        listCategories = CategorieService.getInstance().getAll();
        categorieStrings = new String[listCategories.size()];
        categorieIndex = 0;
        for (Categorie categorie : listCategories) {
            categorieStrings[categorieIndex] = categorie.getType();
            categorieIndex++;
        }
        if (listCategories.size() > 0) {
            categoriePC.getPicker().setStrings(categorieStrings);
            categoriePC.getPicker().addActionListener(l -> selectedCategorie = listCategories.get(categoriePC.getPicker().getSelectedStringIndex()));
            // si une catégorie est déjà sélectionnée pour l'article, on la sélectionne dans le picker
            if (e.getCategorie() != null) {
                categoriePC.getPicker().setSelectedString(e.getCategorie().getType());
                selectedCategorie = e.getCategorie();
            }
        } else {
            categoriePC.getPicker().setStrings("");
        }

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btnModifer
        btnModifier.addActionListener(l -> {
            e.setTitre(titre.getText());
            e.setAuteur(auteur.getText());
            e.setContenu(contenu.getText());
            e.setCategorie(selectedCategorie);

            if (ServiceArticle.getInstance().modifierEvent(e)) {
                new ListAForm(res).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(r -> {
            new ListAForm(res).show();
        });

        Font f = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        titre.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge
        auteur.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge
        contenu.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge

      Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
content.setUIID("ModifierEventForm");
content.getStyle().setMargin(10, 10, 10, 10);
content.getStyle().setPadding(10, 10, 10, 10);

Label labelTitre = new Label("Titre:");
labelTitre.setUIID("LabelTitre");
content.add(BoxLayout.encloseY(labelTitre, titre));

Label labelAuteur = new Label("Auteur:");
labelAuteur.setUIID("LabelAuteur");
content.add(BoxLayout.encloseY(labelAuteur, auteur));

Label labelContenu = new Label("Contenu:");
labelContenu.setUIID("LabelContenu");
content.add(BoxLayout.encloseY(labelContenu, contenu));

content.add(categoriePC);
// récupérer le style des boutons
Style btnModifierStyle = btnModifier.getAllStyles();
Style btnAnnulerStyle = btnAnnuler.getAllStyles();

// changer la couleur de fond des boutons en rose
btnModifierStyle.setBgColor(0xf8a5c2);
btnAnnulerStyle.setBgColor(0xf8a5c2);


btnModifier.setUIID("BtnModifier");
btnAnnuler.setUIID("BtnAnnuler");

content.add(BoxLayout.encloseX(btnModifier, btnAnnuler));
//BoxLayout.setSameWidth(btnModifier, btnAnnuler);

content.getStyle().setMarginTop(20);
content.getStyle().setMarginLeft(20);
content.getStyle().setMarginRight(20);
content.getStyle().setMarginBottom(20);
content.getStyle().setPaddingTop(20);
content.getStyle().setPaddingLeft(20);
content.getStyle().setPaddingRight(20);
content.getStyle().setPaddingBottom(20);

add(content);
show();


    }
}
