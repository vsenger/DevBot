package br.com.devbot.bt;

import br.com.devbot.core.MainForm;
import br.com.devbot.core.MainMidlet;
import com.sun.lwuit.Label;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Neto Marin
 */
public class BluetoothClientServer implements DiscoveryListener {

    UUID RFCOMM_UUID = new UUID(0x1101);
    private StreamConnection streamConn = null;
    private LocalDevice localDevice = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private OutputStream os = null;
    private OutputStreamWriter osw = null;
    private DiscoveryAgent discoveryAgent = null;
    private RemoteDevice remoteDevice;
    private Vector services;
    private static BluetoothClientServer instance;
    private boolean connected = false;

    private BluetoothClientServer() {
    }

    public static BluetoothClientServer getInstance() {
        if (instance == null) {
            instance = new BluetoothClientServer();
        }
        return instance;
    }

    public void InitClient() {
        SearchAvailDevices();

    }

    public void SearchAvailDevices() {
        try {
            //First get the local device and obtain the discovery agent.
            this.remoteDevice = null;
            localDevice = LocalDevice.getLocalDevice();
            discoveryAgent = localDevice.getDiscoveryAgent();
            MainForm.getInstance().updateStatus("Starting search...");
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
        } catch (BluetoothStateException ex) {
            System.out.println("Problem in searching the blue tooth devices");
            MainForm.getInstance().updateStatus("Error searching devics!!");
            MainForm.getInstance().addComponent(new Label(ex.getMessage()));
        }
    }

    public void sendMessage(byte[] messageToSend) {
        try {
            //osw.write(messageToSend);
            //osw.flush();
            os.write(messageToSend);
            os.flush();
        } catch (IOException ex) {
        }
    }

    public String recieveMessages() {
        byte buffer[] = new byte[80];
        int bytes_read;
        try {
            bytes_read = is.read( buffer );
        } catch (IOException ex) {
            return "error";
        }
        return new String(buffer, 0, bytes_read);
        
////        byte[] data = null;
////
////        try {
////            int length = is.read();
////            data = new byte[length];
////            length = 0;
////
////            while (length != data.length) {
////                int ch = is.read(data, length, data.length - length);
////
////                if (ch == -1) {
////                    throw new IOException("Can't read data");
////                }
////                length += ch;
////            }
////        } catch (IOException e) {
////            return "-1";
////        }
////        return new String(data);
    }

    /**
     * *******************************************************************************************
     * below are the pure virtual methods of discoverlistern
     *
     *
     ******************************************************************************************
     */
    //Called when device is found during inquiry
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        try {
            // Get Device Info
            MainForm.getInstance().updateStatus("Device found: " + btDevice.getBluetoothAddress());

            if (btDevice.getBluetoothAddress().equals(MainMidlet.getCentralMacAddress())) {
                MainForm.getInstance().updateStatus("Central FOUND!");
                this.remoteDevice = btDevice;
            }
        } catch (Exception e) {
            System.out.println("Device Discovered Error: " + e);
            MainForm.getInstance().updateStatus("Error on device discovered");
            MainForm.getInstance().addComponent(new Label(e.getMessage()));
        }
    }

    public void inquiryCompleted(int discType) {
        if (remoteDevice != null) {
            services = new Vector();
            UUID[] query = new UUID[1];
            query[0] = RFCOMM_UUID;
            try {
                discoveryAgent.searchServices(null, query, remoteDevice, this);
            } catch (BluetoothStateException ex) {
                ex.printStackTrace();
            }
        } else {
            MainForm.getInstance().updateStatus("Central not found!");
        }
    }

    //called when service found during service search
    public void servicesDiscovered(int transID, ServiceRecord[] records) {
        MainForm.getInstance().updateStatus("Looking for RFCOMM channel!");
        for (int i = 0; i < records.length; i++) {
            this.services.addElement(records[i]);
        }
        MainForm.getInstance().updateStatus("Service search completed!");
    }

    //called when service search gets complete
    public void serviceSearchCompleted(int transID, int respCode) {
        MainForm.getInstance().updateStatus("callback serviceSearchCompleted");
        if (remoteDevice != null) {
            try {
                MainForm.getInstance().updateStatus("Starting connection...");
                //lets the communication start by setting the url and send client reponse
                streamConn = (StreamConnection) Connector.open(((ServiceRecord) (services.elementAt(0))).getConnectionURL(0, false));
                MainForm.getInstance().updateStatus("Opening communication...");
                os = streamConn.openOutputStream();
                osw = new OutputStreamWriter(os, "US-ASCII");
                is = streamConn.openInputStream();
                isr = new InputStreamReader(is, "US-ASCII");

                this.connected = true;

                MainForm.getInstance().updateStatus("Connection Stablished!");
                MainForm.getInstance().updateStatus("Devbot Java ME");
                //MainForm.getInstance().setEnabled(true);
                //NESTE PONTO, CONEXAO ESTA OK
            } catch (IOException ex) {
                MainForm.getInstance().updateStatus("Connection error!!");
                MainForm.getInstance().addComponent(new Label(ex.getMessage()));
            }

        } else {
            MainForm.getInstance().updateStatus("Central not found...");
        }
    }

    public void CloseAll() {
        try {
            if (os != null) {
                os.close();
            }

            if (is != null) {
                is.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}