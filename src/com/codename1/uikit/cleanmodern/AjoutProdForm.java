/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.capture.Capture;
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
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Product;
import com.mycompany.entities.category;
import com.mycompany.services.ServiceCategory;
import com.mycompany.services.ServiceProduct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author MSI
 */
public class AjoutProdForm extends BaseForm {

    Label imageLabel;
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;

    Form current;
    ImageViewer image;
    ArrayList<category> listCategories;
    PickerComponent categoriePC;
    category selectedCategorie = null;
    ImageViewer imageIV;
    Button selectImageButton;
    Product currentArticle;

    public AjoutProdForm(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Event");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);

        //extra 
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
        RadioButton mesListes = RadioButton.createToggle("Mes produits", barGroup);
        mesListes.setUIID("SelectBar");

        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            ListProductForm a = new ListProductForm(res);
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

        //
//fin extra
        TextField nameProduct = new TextField("", "entrer nom de produit!!");
        nameProduct.setUIID("TextFieldBlack");
        addStringValue("nameProduct", nameProduct);

        TextField description = new TextField("", "entrer la description!!");
        description.setUIID("TextFieldBlack");
        addStringValue("description", description);

        TextField price = new TextField("", "entrer le price!!");
        price.setUIID("TextFieldBlack");
        addStringValue("price", price);

        TextField image = new TextField("", "entrer le image!!");
        image.setUIID("TextFieldBlack");
        addStringValue("image", image);

        TextField quantity = new TextField("", "entrer le quantity!!");
        quantity.setUIID("TextFieldBlack");
        addStringValue("quantity", quantity);

        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);

        //ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));

// Récupérer la liste des catégories depuis le service
        String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("category");
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
        } else {
            categoriePC.getPicker().setStrings("");
        }
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });
        //onclick button event 
        btnAjouter.addActionListener(action -> {
            try {

                if (nameProduct.getText().equals("") || description.getText().equals("") || price.getText().equals("") || quantity.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données", "", "Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data

                    final Dialog iDialog = ip.showInfiniteBlocking();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Product ev = new Product(
                            String.valueOf(nameProduct.getText()).toString(),
                            selectedCategorie,
                            String.valueOf(description.getText()).toString(),
                            Float.parseFloat(String.valueOf(price.getText())),
                            selectedImage,
                            Integer.parseInt(String.valueOf(quantity.getText()))
                    );
                    System.out.println("data event == " + ev);
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceProduct.getInstance().ajouterpr(ev);

                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout

                    // une fois ajout kmel on passe affichage
                    new ListProductForm(res).show();

                    refreshTheme();//Actualisation

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

// Ajout des composants au container
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        container.add(nameProduct);
        container.add(description);
        container.add(price);
        container.add(quantity);
        container.add(categoriePC);
        container.add(imageLabel);
        container.add(imageIV);
        container.add(selectImageButton);
        container.add(btnAjouter);

// Ajout du container au form
        this.add(container);

    }

    private void addStringValue(String s, Component v) {
        // add(BorderLayout.west(new Label(s, "PaddedLabel")))
//        .add(BorderLayout.CENTER, v);
        add(createLineSeparator(0xeeeeee));
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

}
