package br.com.devbot.features;


import br.com.devbot.core.MainForm;
import com.sun.lwuit.Command;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
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
public class DriveForm extends FeatureForm {

    private int speed;
    private Label upArrow, downArrow, leftArrow, rightArrow;
    private final int UP_KEY = -1;
    private final int DOWN_KEY = -2;
    private final int LEFT_KEY = -3;
    private final int RIGHT_KEY = -4;
    private final int STOP_KEY = -5;

    public DriveForm(MainForm previousScreen) {
        super(previousScreen, "Drive");
        setLayout(new GridLayout(3, 3));

        try {
            upArrow = new Label(Image.createImage("/up.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }

        addComponent(new Label(""));
        addComponent(upArrow);
        addComponent(new Label(""));

        try {
            leftArrow = new Label(Image.createImage("/left.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        addComponent(leftArrow);
        addComponent(new Label(""));

        try {
            rightArrow = new Label(Image.createImage("/right.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        addComponent(rightArrow);
        addComponent(new Label(""));

        try {
            downArrow = new Label(Image.createImage("/down.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        addComponent(downArrow);
        addComponent(new Label(""));

        Command exitComm = new Command("Speed") {

            public void actionPerformed(ActionEvent ev) {
                SpeedForm speedControl = new SpeedForm(getMyself());
                speedControl.show();
            }
        };
        this.addCommand(exitComm);
    }

    public DriveForm getMyself() {
        return this;
    }

    public void keyPressed(int keyCode) {
        int speed = SpeedForm.loadSpeed();
        switch (keyCode) {
            case UP_KEY:
                setHeaderCode("frente?");
                sendMessage("" + speed);
                resetLabels();
                try {
                    upArrow.setIcon(Image.createImage("/up_sel.png"));
                } catch (IOException ex) {
                    System.out.println("Image not found");
                }
                break;
            case DOWN_KEY:
                setHeaderCode("re?");
                sendMessage("" + speed);
                resetLabels();
                try {
                    downArrow.setIcon(Image.createImage("/down_sel.png"));
                } catch (IOException ex) {
                    System.out.println("Image not found");
                }
                break;
            case LEFT_KEY:
                setHeaderCode("ga?");
                sendMessage("" + speed);
                resetLabels();
                try {
                    leftArrow.setIcon(Image.createImage("/left_sel.png"));
                } catch (IOException ex) {
                    System.out.println("Image not found");
                }
                break;
            case RIGHT_KEY:
                setHeaderCode("gh?");
                sendMessage("" + speed);
                resetLabels();
                try {
                    rightArrow.setIcon(Image.createImage("/right_sel.png"));
                } catch (IOException ex) {
                    System.out.println("Image not found");
                }
                break;
            case STOP_KEY:
                setHeaderCode("parar");
                sendMessage("");
                resetLabels();
                break;
        }
    }

    public void resetLabels() {
        //UP
        try {
            upArrow.setIcon(Image.createImage("/up.png"));
            downArrow.setIcon(Image.createImage("/down.png"));
            leftArrow.setIcon(Image.createImage("/left.png"));
            rightArrow.setIcon(Image.createImage("/right.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        repaint();
    }
}
