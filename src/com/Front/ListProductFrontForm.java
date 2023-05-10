/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Front;

import Utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.cleanmodern.ListProductForm;
import com.mycompany.entities.Product;
import com.mycompany.services.ServiceProduct;
import java.util.ArrayList;

/**
 *
 * @author MSI
 */
public class ListProductFrontForm extends BaseForm2 {

    Form current;

    public ListProductFrontForm(Resources res) {

        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des Produits");
        getContentPane().setScrollVisible(false);
        super.addSideMenu2(res);

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
        RadioButton mesListes = RadioButton.createToggle("Mes produit", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton listecat = RadioButton.createToggle("Autres", barGroup);
        listecat.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

      
       
        
 
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, listecat, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(listecat, arrow);
        bindButtonSelection(partage, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

//fin extra
// APPEL AFFICHAGE

        ArrayList<Product> list = ServiceProduct.getInstance().affichagepr();
                Container EKHDEM = new Container(BoxLayout.y());
         EKHDEM.setScrollableY(true);
EKHDEM.add(createLineSeparator(0xCCCCCC, 10));

        for (Product ev : list) {
            
            
//               EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0xffff0000), true);
//              Image i = URLImage.createToStorage(placeholder,ev.getImage(),Statics.BASE_URL+"/uploads/"+ev.getImage());
//             MultiButton sp = new MultiButton(ev.getNameProduct());
//             sp.setIcon(i.fill(200, 200));
//              sp.setTextLine1("description : "+ev.getDescription());
//              sp.setTextLine2("price : "+ev.getPrice());
//              sp.setTextLine3(" quantity : "+ev.getQuantity());
//
//                     EKHDEM.add(sp);
            String urlImage = "back-logo.jpeg";//image statique pour le moment 

            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(urlim, ev, res);

            //yaffichi juste fel nom 
            ScaleImageLabel image = new ScaleImageLabel(urlim);

            Container containerImg = new Container();

            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

//EncodedImage enc;
//            Image imgs;
//            ImageViewer imgv;
//            String url = "http://127.0.0.1:8000/assets/img/shop-img/"+ev.getImage();
//            enc = EncodedImage.create("/logo1.png");
//            imgs = URLImage.createToStorage(enc, url, url);
//            imgv = new ImageViewer(imgs);
//            add(imgv);
//


        }

    }

    ListProductFrontForm() {
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

    private void addButton(Image img, Product ev, Resources res) {
    // Créer un bouton d'image pour afficher l'article
    int height = Display.getInstance().convertToPixels(11.5f);
    int width = Display.getInstance().convertToPixels(14f);
    Button image = new Button(img.fill(width, height));
    image.setUIID("Label");

    // Créer un conteneur pour afficher le titre, l'auteur, le contenu et la date de l'article
    Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Label nameLabel = new Label(ev.getNameProduct(), "NewsTopLine2");
    Label descLabel = new Label("description: " + ev.getDescription(), "NewsTopLine2");
    Label priceLabel = new Label("price: " + ev.getPrice(), "NewsTopLine2");
    Label imageLabel = new Label("image :" + ev.getImage(), "NewsTopLine2");
    Label quantityLabel = new Label("quantity: " + ev.getQuantity(), "NewsTopLine2");
    content.addAll(nameLabel, descLabel, priceLabel, imageLabel,quantityLabel);

    // Créer un conteneur horizontal pour les boutons de modification et de suppression
    Container buttonContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
    Button editButton = new Button("Modifier");
    Button deleteButton = new Button("Supprimer");
     deleteButton.addActionListener(deleteEvt -> {
            // Code pour ouvrir le formulaire de modification de l'article
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce product ?","Annuler","Oui")) {
                dig.dispose();
            }
          
            else {
                dig.dispose();
                 }
                //appel delete li fel serviceEvent
                if(ServiceProduct.getInstance().deletepr(ev.getId())) {
                    new ListProductForm(res).show();
                }
           
        
        });
     
    // Ajouter un écouteur de clic pour le bouton d'image pour afficher les informations de l'article
    image.addActionListener(e -> {
        Dialog.show("Informations", ev.getNameProduct()+ " : " + ev.getDescription(), "OK", null);
    });

    // Ajouter les composants à un conteneur principal
    Container cnt = BorderLayout.west(image);
    cnt.add(BorderLayout.CENTER, content);
    cnt.add(BorderLayout.SOUTH, buttonContainer);

    // Ajouter le conteneur principal à la forme
    this.add(cnt);
}
private static Component createLineSeparator(int color, int thickness) {
    Container cnt = new Container();
    cnt.setUIID("Separator");
    cnt.getStyle().setBgColor(color);
    cnt.getStyle().setBgTransparency(255);
    cnt.getStyle().setMarginUnit(Style.UNIT_TYPE_PIXELS);
    cnt.getStyle().setMargin(Component.BOTTOM, thickness);
    cnt.getStyle().setPaddingUnit(Style.UNIT_TYPE_PIXELS);
    cnt.getStyle().setPadding(Component.TOP, thickness);
    return cnt;
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
