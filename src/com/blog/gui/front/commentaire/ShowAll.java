package com.blog.gui.front.commentaire;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.codename1.io.*;
import com.mycompany.entities.Commentaire;
import com.mycompany.services.CommentaireService;


import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class ShowAll extends Form {

    Form previous; 
    
    public static Commentaire currentCommentaire = null;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    

    public ShowAll(Form previous) {
        super("Commentaires", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

//        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);
        

        ArrayList<Commentaire> listCommentaires = CommentaireService.getInstance().getAll();
        componentModels = new ArrayList<>();
        
        searchTF = new TextField("", "Chercher commentaire par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Commentaire commentaire : listCommentaires) {
                if (commentaire.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCommentaireModel(commentaire);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);
        
        
        if (listCommentaires.size() > 0) {
            for (Commentaire commentaire : listCommentaires) {
                Component model = makeCommentaireModel(commentaire);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCommentaire = null;
            new Manage(this).show();
        });
        
    }
    Label articleLabel   , contenuLabel   , dateLabel   , nomLabel   , emailLabel  ;
    

    private Container makeModelWithoutButtons(Commentaire commentaire) {
        Container commentaireModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commentaireModel.setUIID("containerRounded");
        
        
        articleLabel = new Label("Article : " + commentaire.getArticle());
        articleLabel.setUIID("labelDefault");
        
        contenuLabel = new Label("Contenu : " + commentaire.getContenu());
        contenuLabel.setUIID("labelDefault");
        
        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(commentaire.getDate()));
        dateLabel.setUIID("labelDefault");
        
        nomLabel = new Label("Nom : " + commentaire.getNom());
        nomLabel.setUIID("labelDefault");
        
        emailLabel = new Label("Email : " + commentaire.getEmail());
        emailLabel.setUIID("labelDefault");
        
        articleLabel = new Label("Article : " + commentaire.getArticle().getTitre());
        articleLabel.setUIID("labelDefault");
        

        commentaireModel.addAll(
                
                articleLabel, contenuLabel, dateLabel, nomLabel, emailLabel
        );

        return commentaireModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeCommentaireModel(Commentaire commentaire) {

        Container commentaireModel = makeModelWithoutButtons(commentaire);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCommentaire = commentaire;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commentaire ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommentaireService.getInstance().delete(commentaire.getId());

                if (responseCode == 200) {
                    currentCommentaire = null;
                    dlg.dispose();
                    commentaireModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commentaire. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        commentaireModel.add(btnsContainer);

        return commentaireModel;
    }
    
}