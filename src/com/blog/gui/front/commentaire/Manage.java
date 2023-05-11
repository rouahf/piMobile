package com.blog.gui.front.commentaire;


import Utils.AlertUtils;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Commentaire;
import com.mycompany.services.CommentaireService;
import com.mycompany.services.ServiceArticle;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manage extends Form {

    

    Commentaire currentCommentaire;

    TextField contenuTF;TextField nomTF;TextField emailTF;
    Label contenuLabel;Label nomLabel;Label emailLabel;
    PickerComponent dateTF;
    
    ArrayList<Article> listArticles;
    PickerComponent articlePC;
    Article selectedArticle = null;
    
    
    Button manageButton;

    Form previous;
    

    public Manage(Form previous) {
        super(ShowAll.currentCommentaire == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCommentaire = ShowAll.currentCommentaire;

        addGUIs();
        addActions();

//        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
         Font f = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        contenuTF.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge
        nomTF.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge
        emailTF.getUnselectedStyle().setFgColor(0xFF0000); // change la couleur du texte en rouge

    }

    private void addGUIs() {
        
        String[] articleStrings;
        int articleIndex;
        articlePC = PickerComponent.createStrings("").label("Article");
        listArticles = ServiceArticle.getInstance().getAllArticles();
        articleStrings = new String[listArticles.size()];
        articleIndex = 0;
        for (Article article : listArticles) {
            articleStrings[articleIndex] = article.getTitre();
            articleIndex++;
        }
        if(listArticles.size() > 0) {
            articlePC.getPicker().setStrings(articleStrings);
            articlePC.getPicker().addActionListener(l -> selectedArticle = listArticles.get(articlePC.getPicker().getSelectedStringIndex()));
        } else {
            articlePC.getPicker().setStrings("");
        }
        

        
        
        
        
        contenuLabel = new Label("Contenu : ");
        contenuLabel.setUIID("labelDefault");
        contenuTF = new TextField();
        contenuTF.setHint("Tapez le contenu");
        
        
        dateTF = PickerComponent.createDate(null).label("Date");
        
        
        
        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        
        
        
        emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le email");
        
        
        

        if (currentCommentaire == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            contenuTF.setText(currentCommentaire.getContenu());
            dateTF.getPicker().setDate(currentCommentaire.getDate());
            nomTF.setText(currentCommentaire.getNom());
            emailTF.setText(currentCommentaire.getEmail());
            
            articlePC.getPicker().setSelectedString(currentCommentaire.getArticle().getTitre());
            selectedArticle = currentCommentaire.getArticle();
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            
            contenuLabel, contenuTF,
            dateTF,
            nomLabel, nomTF,
            emailLabel, emailTF,
            articlePC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentCommentaire == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommentaireService.getInstance().add(
                            new Commentaire(
                                    
                                    
                                    selectedArticle,
                                    contenuTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    nomTF.getText(),
                                    emailTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Commentaire ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de commentaire. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                
                if (controleDeSaisie()) {
                    int responseCode = CommentaireService.getInstance().edit(
                            new Commentaire(
                                    currentCommentaire.getId(),
                                    
                                    
                                    selectedArticle,
                                    contenuTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    nomTF.getText(),
                                    emailTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Commentaire modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de commentaire. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        
        
        
        if (contenuTF.getText().equals("")) {
            Dialog.show("Avertissement", "Contenu vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }
        
        
        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Email vide", new Command("Ok"));
            return false;
        }
        
        
        

        
        if (selectedArticle == null) {
            Dialog.show("Avertissement", "Veuillez choisir un article", new Command("Ok"));
            return false;
        }
        

        
             
        return true;
    }
}