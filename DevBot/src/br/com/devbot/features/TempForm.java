package br.com.devbot.features;

import br.com.devbot.core.MainForm;
import com.sun.lwuit.Button;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import java.io.IOException;

public class TempForm extends FeatureForm {

    private Button tempButton;

    public TempForm(MainForm previousForm) {
        super(previousForm,"Temperature Sensor");
        setHeaderCode("st");
        this.setLayout(new BorderLayout());
        Image imgButton = null;
        try {
            imgButton = Image.createImage("/temp.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        tempButton = new Button("1000", imgButton);
        tempButton.setTextPosition(Label.BOTTOM);
        tempButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                sendMessage("");
            }
        });
        this.addComponent(BorderLayout.CENTER, tempButton);
    }
}