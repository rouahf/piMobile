/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import Utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import com.mycompany.services.CategorieService;
import com.mycompany.services.ServiceArticle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.UIManager;

/**
 *
 * @author mizoj
 */
public class ListAForm extends BaseForm {

    Resources theme = UIManager.initFirstTheme("/theme");

    Form current;
    ImageViewer imageIV;

    public ListAForm(Resources res) {

        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Article");
        getContentPane().setScrollVisible(false);
  super.addSideMenu(res);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("back-logo.jpeg"), "", "", res);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Articles", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Stats", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            AjoutArticle a = new AjoutArticle(res);
            a.show();
            refreshTheme();
        });

        liste.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         StatsArticle b = new StatsArticle(res);
           b.show();
            refreshTheme();
        });
        
        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            ListAForm a = new ListAForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

//fin extra
// APPEL AFFICHAGE
        ArrayList<Article> list = ServiceArticle.getInstance().getAllArticles();
//        
        for (Article ev : list) {
            String urlImage = "back-logo.jpeg";//image statique pour le moment 

            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(urlim, ev, res);

            //yaffichi juste fel nom 
            ScaleImageLabel image = new ScaleImageLabel(urlim);

            Container containerImg = new Container();

            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            
        }

    }

    ListAForm() {
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Image img, Article ev, Resources res) {
        // Créer un bouton d'image pour afficher l'article
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Label imageLabel;

        // Créer un conteneur pour afficher le titre, l'auteur, le contenu et la date de l'article
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label titleLabel = new Label(ev.getTitre(), "NewsTopLine2");
        Label authorLabel = new Label("Auteur: " + ev.getAuteur(), "NewsTopLine2");
        Label contentLabel = new Label("Contenu: " + ev.getContenu(), "NewsTopLine2");
        Label dateLabel = new Label("Date: " + ev.getDate(), "NewsTopLine2");



        imageLabel = new Label("Image : " + ev.getImage());
//imageLabel.setUIID("labelDefault");

            String url = Statics.ARTICLE_IMAGE_URL  + ev.getImage();
            Image imagee = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
            imageIV = new ImageViewer(imagee);
       
        content.addAll( titleLabel, authorLabel, contentLabel, dateLabel, imageIV);

        // Créer un conteneur horizontal pour les boutons de modification et de suppression
        Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Button editButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");
        deleteButton.addActionListener(deleteEvt -> {
            // Code pour ouvrir le formulaire de modification de l'article
            Dialog dig = new Dialog("Suppression");

            if (dig.show("Suppression", "Vous voulez supprimer ce post ?", "Annuler", "Oui")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //appel delete li fel serviceEvent
            if (dig.show("Suppression", "Vous voulez supprimer ce post ?", "Annuler", "Oui")) {
                dig.dispose();
            } else {
                dig.dispose();
            }
            //appel delete li fel serviceEvent
            if (ServiceArticle.getInstance().deleteEvent(ev.getId())) {
                new ListAForm(res).show();
            }

        });
        editButton.addActionListener(deleteEvt -> {
            // Code pour ouvrir le formulaire de modification de l'article
            System.out.println("hello update");
            new ModifierArtcileForm(res, ev).show();

        });
        buttonContainer.addAll(editButton, deleteButton);

        // Ajouter un écouteur de clic pour le bouton d'image pour afficher les informations de l'article
        image.addActionListener(e -> {
            Dialog.show("Informations", ev.getTitre() + " : " + ev.getContenu(), "OK", null);
        });

        // Ajouter les composants à un conteneur principal
        Container cnt = BorderLayout.west(image);
        cnt.add(BorderLayout.CENTER, content);
        cnt.add(BorderLayout.SOUTH, buttonContainer);

        // Ajouter le conteneur principal à la forme
        this.add(cnt);
    }

//    private void addButton(Image img, Article ev, Resources res) {
//
//        int height = Display.getInstance().convertToPixels(11.5f);
//        int width = Display.getInstance().convertToPixels(14f);
//
//        Button image = new Button(img.fill(width, height));
//        image.setUIID("Label");
//        Container cnt = BorderLayout.west(image);
//
////        TextArea ta = new TextArea(nom);
////        ta.setUIID("NewsTopLine");
////        ta.setEditable(false);
////        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(ta));
//        // BOUTON AJOUT
//        // Label nomTxt = new Label("nom : "+ev.getNom(),"NewsTopLine2");
//        Label typeTxt = new Label("titre : " + ev.getTitre(), "NewsTopLine2");
//        Label auteurTxt = new Label("auteur: " + ev.getAuteur(), "NewsTopLine2");
//        Label contenuTxt = new Label("contenu : " + ev.getContenu(), "NewsTopLine2");
//        Label dateTxt = new Label("date: " + ev.getDate(), "NewsTopLine2");
//    
//        createLineSeparator();
//        // DELETE  ICON
//           Label lSupprimer = new Label(" ");
//        lSupprimer.setUIID("NewsTopLine");
//        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
//        supprmierStyle.setFgColor(0xf21f1f);
//        
//        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
//        lSupprimer.setIcon(suprrimerImage);
//        lSupprimer.setTextPosition(RIGHT);
//        
//        //click delete
//        lSupprimer.addPointerPressedListener(l -> {
//            
//            Dialog dig = new Dialog("Suppression");
//            
//            if(dig.show("Suppression","Vous voulez supprimer ce reclamation ?","Annuler","Oui")) {
//                dig.dispose();
//            }
//            else {
//                dig.dispose();
//                 }
//                //appel delete li fel serviceEvent
//                int responseCode = CategorieService.getInstance().delete(ev.getId());
//
//if(responseCode >= 200 && responseCode < 300) {
//                    new ListEventForm(res).show();
//                }
//           
//        });
    //Update icon 
//        Label lModifier = new Label(" ");
//        lModifier.setUIID("NewsTopLine");
//        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
//        modifierStyle.setFgColor(0xf7ad02);
//        
//        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
//        lModifier.setIcon(mFontImage);
//        lModifier.setTextPosition(LEFT);
//        
//        
//        lModifier.addPointerPressedListener(l -> {
//            System.out.println("hello update");
//            new ModifierEventForm(res,ev).show();
//        });
//        
//        
//        
//        
//        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
//                BoxLayout.encloseX(typeTxt),
//                BoxLayout.encloseX(lModifier,lSupprimer)
//                
//                ));
//        
//        add(cnt);
//
//    }
}
