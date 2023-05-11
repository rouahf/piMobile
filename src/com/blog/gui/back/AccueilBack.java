package com.blog.gui.back;

import com.codename1.social.Login;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilBack() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Admin"/*MainApp.getSession().getEmail()*/);
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            com.blog.gui.Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeArticlesButton(), 
                makeCategoriesButton(), 
                makeCommentairesButton()
                
        );

        this.add(menuContainer);
    }

    private Button makeArticlesButton() {
        Button button = new Button("Articles");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
       // button.addActionListener(action -> new com.blog.gui.back.article.ShowAll(this).show());
        return button;
    }
    private Button makeCategoriesButton() {
        Button button = new Button("Categories");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
      //  button.addActionListener(action -> new com.blog.gui.back.categorie.ShowAll(this).show());
        return button;
    }
    private Button makeCommentairesButton() {
        Button button = new Button("Commentaires");
        button.setUIID("buttonMenu");
        //button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
      //  button.addActionListener(action -> new com.blog.gui.back.commentaire.ShowAll(this).show());
        return button;
    }
    
}
