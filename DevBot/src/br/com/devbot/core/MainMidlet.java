package br.com.devbot.core;

/*
 * x = velocidade "frente?x re?x gh?x ga?x" gh= girar horário 
 * ga=girar anti-horário 
 * "servo?angulo"
 * "sl" sensor luz 
 * "st" temp
 */

import br.com.devbot.bt.BluetoothClientServer;
import com.sun.lwuit.Display;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import javax.microedition.midlet.*;

/**
 * @author triangulum
 */
public class MainMidlet extends MIDlet {

    private static MainMidlet instance;
    private static String centralMacAddress;
    
    public void startApp() {
         if ( instance == null )
            instance = this;
         centralMacAddress  = "000666434582";
        Display.init(this);
        try {
            Resources r = Resources.open("/devbot.res");
            UIManager.getInstance().setThemeProps(
                    r.getTheme(r.getThemeResourceNames()[0]));
        } catch (java.io.IOException e) {
            System.out.println("Theme not found");
        }
        MainForm dashboard = new MainForm(this);
        dashboard.show();
        BluetoothClientServer.getInstance().InitClient();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
    
    public static MainMidlet getInstance() {
        return instance;
    }

    public static String getCentralMacAddress() {
        return centralMacAddress;
    }
}