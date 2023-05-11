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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Categorie;
import com.mycompany.services.CategorieService;

/**
 *
 * @author mizoj
 */
public class ModifierCategForm extends BaseForm {

    Form current;

    public ModifierCategForm(Resources res, Categorie e) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Event");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        // BEFORE
        TextField type = new TextField(e.getType(), "type", 20, TextField.ANY);
        type.setSingleLineTextArea(false);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick btnModifer
        btnModifier.addActionListener(l -> {
            e.setType(type.getText());
            //appel fonction modfier reclamation men service
            int responseCode = CategorieService.getInstance().edit(e);

            if (responseCode >= 200 && responseCode < 300) {
                new ListcategForm(res).show();
            }
        });

        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(r -> {
            new ListcategForm(res).show();
        });

//      Container content = BoxLayout.encloseY(
//                new Label("Modifier un événement", "WelcomeWhite"),
//                createLineSeparator(),
//                BoxLayout.encloseX(new Label("Type: "), type),
//                createLineSeparator(),
//                createLineSeparator(),
//                btnModifier,
//                btnAnnuler);
Label l2 = new Label("");
Label l3 = new Label("");
Label l4 = new Label("");
Label l5 = new Label("");

Font f = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
type.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge
type.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge


Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
content.setUIID("ModifierEventForm");
content.add(new Label("Modifier un événement", "WelcomeWhite"));
content.add(createLineSeparator());
content.add(createLineSeparator());
content.add(BoxLayout.encloseX(new Label("Type: "), type));
content.add(createLineSeparator());
content.add(createLineSeparator());
content.add(btnModifier);
content.add(createLineSeparator());
content.add(btnAnnuler);
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
