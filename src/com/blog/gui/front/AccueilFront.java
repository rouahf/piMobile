package com.blog.gui.front;

import com.Front.ListEventFormFront;
import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.blog.gui.Login;

public class AccueilFront extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;

    public AccueilFront() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        label = new Label("Front");
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            Login.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeProfileButton(),
                makeEventButton(),
                makeCoursButton(),
                makeProductButton(),
                makeReclamationButton(),
                makeArticlesButton()
        );

        this.add(menuContainer);
    }

    private Button makeArticlesButton() {
        Button button = new Button("Articles");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.codename1.uikit.cleanmodern.ListAFormFront(theme).show());
        return button;
    }

    private Button makeEventButton() {
        Button button = new Button("Event");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new ListEventFormFront(theme).show());
        return button;
    }

    private Button makeCoursButton() {
        Button button = new Button("Cours");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.Front.ListeCoursFront(theme).show());
        return button;
    }

    private Button makeProductButton() {
        Button button = new Button("Product");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.Front.ListProductFrontForm(theme).show());
        return button;
    }

    private Button makeReclamationButton() {
        Button button = new Button("Reclamation");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        button.addActionListener(action -> new com.Front.ReclamationFRont(theme).show());
        return button;
    }

    private Button makeProfileButton() {
        Button button = new Button("Mon Profile");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_BOOKMARK);
        //button.addActionListener(action -> new com.Front.ReclamationFRont(theme).show());
        return button;
    }

}
