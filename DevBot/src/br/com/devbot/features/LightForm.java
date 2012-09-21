package br.com.devbot.features;

import br.com.devbot.core.MainForm;
import com.sun.lwuit.Button;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import java.io.IOException;

public class LightForm extends FeatureForm {

    private Button lightButton;

    public LightForm(MainForm previousForm) {
        super(previousForm, "Light Sensor");
        
        super.setHeaderCode("sl");
        this.setLayout(new BorderLayout());        
        Image imgButton = null;
        try {
            imgButton = Image.createImage("/lamp.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        
        lightButton = new Button("1023", imgButton);
        lightButton.setTextPosition(Label.BOTTOM);
        lightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String rcvd = sendAndReceiveMessage("sl");
                rcvd = rcvd.substring(rcvd.length()-5, rcvd.length()-1);
                lightButton.setText(rcvd);
            }
        });
        this.addComponent(BorderLayout.CENTER, lightButton);
    }
}