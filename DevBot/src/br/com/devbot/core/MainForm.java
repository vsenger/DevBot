package br.com.devbot.core;

import br.com.devbot.features.DriveForm;
import br.com.devbot.features.LightForm;
import br.com.devbot.features.ServoForm;
import br.com.devbot.features.TempForm;
import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author triangulum
 */
public class MainForm extends Form {

    private Button robotButton;
    private Button servoButton;
    private Button lightButton;
    private Button tempButton;
    private MainMidlet mainMidlet;
    private static MainForm instance;

    public MainForm(MainMidlet mainMidlet) {
        super("DevBot Java ME");
        instance = this;
        this.mainMidlet = mainMidlet;
        setLayout(new GridLayout(2, 2));

        //Robot button
        Image imgBotao = null;
        try {
            imgBotao = Image.createImage("/bot.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        robotButton = new Button("Drive", imgBotao);
        robotButton.setTextPosition(Label.BOTTOM);
        robotButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                DriveForm formDrive = new DriveForm(getMyself());
                formDrive.show();
            }
        });
        addComponent(robotButton);

        //Servo button
        Image imgBotaoServo = null;
        try {
            imgBotaoServo = Image.createImage("/servo.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        servoButton = new Button("Servo", imgBotaoServo);
        servoButton.setTextPosition(Label.BOTTOM);
        servoButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                ServoForm formServo = new ServoForm(getMyself());
                formServo.show();
            }
        });
        addComponent(servoButton);

        //Light button
        Image imgBotaoluz = null;
        try {
            imgBotaoluz = Image.createImage("/lamp.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        lightButton = new Button("Light", imgBotaoluz);
        lightButton.setTextPosition(Label.BOTTOM);
        lightButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                LightForm formLight = new LightForm(getMyself());
                formLight.show();
            }
        });
        addComponent(lightButton);

        //Temp button
        Image imgBotaotemp = null;
        try {
            imgBotaotemp = Image.createImage("/temp.png");
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        tempButton = new Button("Temp", imgBotaotemp);
        tempButton.setTextPosition(Label.BOTTOM);
        tempButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                TempForm formTemp = new TempForm(getMyself());
                formTemp.show();
            }
        });
        addComponent(tempButton);

        Command exitComm = new Command("Exit") {

            public void actionPerformed(ActionEvent ev) {
                destroy();
            }
        };
        addCommand(exitComm);
    }

    public MainForm getMyself() {
        return this;
    }

    public void destroy() {
        mainMidlet.destroyApp(true);
    }

    public static MainForm getInstance() {
        return instance;
    }
    
    public void updateStatus(String status){
        setTitle(status);
    }
}
