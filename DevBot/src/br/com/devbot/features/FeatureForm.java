package br.com.devbot.features;

import br.com.devbot.bt.BluetoothClientServer;
import br.com.devbot.core.MainForm;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import java.io.ByteArrayOutputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author triangulum
 */
public abstract class FeatureForm extends Form {

    private MainForm previousForm;
    private String headerCode;

    public FeatureForm(MainForm previousForm, String title) {
        super(title);
        this.previousForm = previousForm;
        Command exitComm = new Command("Back") {

            public void actionPerformed(ActionEvent ev) {
                goBack();
            }
        };
        this.addCommand(exitComm);
    }

    public void goBack() {
        previousForm.showBack();
    }

    public void sendMessage(String message) {
        message = headerCode + message;
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        for (int i = 0; i < message.length(); i++) {
            bous.write((int) message.charAt(i));
        }
        BluetoothClientServer.getInstance().sendMessage(bous.toByteArray());
        System.out.println(message);
    }

    public String getHeaderCode() {
        return headerCode;
    }

    public void setHeaderCode(String headerCode) {
        this.headerCode = headerCode;
    }

    public MainForm getPreviousForm() {
        return previousForm;
    }

    public void setPreviousForm(MainForm previousForm) {
        this.previousForm = previousForm;
    }
}
