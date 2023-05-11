package com.blog.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class Login extends Form {

    public static Form loginForm;
     private Resources theme;

    public Login() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {
  theme = UIManager.initFirstTheme("/theme");

        
        Button frontendBtn = new Button("Back");
        frontendBtn.addActionListener(l -> new com.codename1.uikit.cleanmodern.WalkthruForm(theme).show());
        this.add(frontendBtn);
        
        
        Button backendBtn = new Button("Front");
   backendBtn.addActionListener(l -> new com.blog.gui.front.AccueilFront().show());

        this.add(backendBtn);
    }
    
}

    