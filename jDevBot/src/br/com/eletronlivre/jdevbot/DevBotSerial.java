package br.com.eletronlivre.jdevbot;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vsenger
 */
public class DevBotSerial implements SerialPortEventListener {

  public int sensorLuz;
  public int sensorTemperatura;
  public int sensorDistancia1;
  byte buffer[] = new byte[255];
  int bufferCounter = 0;
  CommPortIdentifier portId;
  SerialPort serialPort;
  OutputStream outputStream;
  InputStream inputStream;

  public DevBotSerial(String args) {
    Enumeration portList;
    CommPortIdentifier portId;

    portList = CommPortIdentifier.getPortIdentifiers();
    Logger.getLogger(DevBotSerial.class.getName()).log(Level.INFO, "Iniciando DevBot 1.0");

    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();

      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        if (portId.getName().equals(args)) {
          try {
            this.portId = portId;
            open();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

  }

  public boolean isConnected() {
    return connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  String name;
  String description;
  boolean connected;
  //Map<String, Component> components;
  //Collection<Component> componentsList;

  public boolean connected() {
    return connected;
  }

  public void close() throws IOException {
    Logger.getLogger(DevBotSerial.class.getName()).log(Level.INFO,
            "Closing device on {0}", serialPort.getName());
    //send("X");
    connected = false;
    try {
      inputStream.close();
    } catch (Exception e) {
    }
    try {
      outputStream.close();
    } catch (Exception e) {
    }
    try {
      serialPort.close();
    } catch (Exception e) {
    }
  }

  public void open() throws IOException {
    try {
      serialPort =
              (SerialPort) portId.open(portId.getName(), 115200);
      serialPort.setSerialPortParams(115200,
              SerialPort.DATABITS_8,
              SerialPort.STOPBITS_1,
              SerialPort.PARITY_NONE);
      serialPort.addEventListener(this);
      serialPort.notifyOnDataAvailable(true);
      outputStream = serialPort.getOutputStream();
      inputStream = serialPort.getInputStream();
      Logger.getLogger(DevBotSerial.class.getName()).log(Level.INFO,
              "Connection Stabilished with {0}", serialPort.getName());
    } catch (Exception e) {
      Logger.getLogger(DevBotSerial.class.getName()).log(Level.SEVERE,
              "Could not init the device on " + serialPort.getName(), e);
      serialPort.close();
    }

  }

  public void send(String s) throws IOException {
    if (outputStream == null) {
      Logger.getLogger(DevBotSerial.class.getName()).log(Level.SEVERE,
              "This device ({0}) is not working because IO objects are null. "
              + "You should restart the device!", this.getName());
    } else {
      outputStream.write(s.getBytes());
      outputStream.flush();

    }
  }

  public String receive() throws IOException {
    if (inputStream == null) {
      String msg = "This device (" + this.getName()
              + ") is not working because IO objects are null. "
              + "You should restart the device!";
      Logger.getLogger(DevBotSerial.class.getName()).log(Level.SEVERE, msg);
      throw new IOException(msg);
    } else {
      while (inputStream.available() == 0);

      int available = inputStream.available();
      byte chunk[] = new byte[available];
      inputStream.read(chunk, 0, available);
      String retorno = new String(chunk);
      //inputStream.close();
      return retorno;

    }
  }

  public void sensorLuz() throws IOException {

    send("sl");
    delay(20);
    //String receive = receive();
    //System.out.println("Luz: " + receive);

    //return Integer.parseInt("0");
  }

  public void sensorTemperatura() throws IOException {
    send("st");
    delay(20);

    //String receive = receive();
    //System.out.println("Temperatura: " + receive);
    //return Integer.parseInt("0");
  }

  public void sensorDistancia1() throws IOException {
    send("sd1");
    delay(20);
    //String receive = receive();
    //System.out.println("Temperatura: " + receive);
    //return Integer.parseInt("0");
  }

  public static void delay(long milis) {
    try {
      Thread.sleep(milis);
    } catch (InterruptedException ex) {
    }

  }

  public void servo(int pos) {
    try {
      send("servo?" + pos);
    } catch (IOException ex) {
      Logger.getLogger(DevBotSerial.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void tratar() {
    String r = new String(buffer);
    if (r.startsWith("t")) {
      this.sensorTemperatura =
              Integer.parseInt(r.substring(2, r.length() - 1).trim());
    } else if (r.startsWith("l")) {
      this.sensorLuz = Integer.parseInt(r.substring(2,
              r.length() - 1).trim());
    } else if (r.startsWith("d")) {
      this.sensorDistancia1 = Integer.parseInt(r.substring(2,
              r.length() - 1).trim());
    }
    resetBuffer();
  }

  public void resetBuffer() {
    bufferCounter = 0;
    buffer = new byte[255];

  }

  /**
   * Handle an event on the serial port. Read the data and print it.
   */
  public synchronized void serialEvent(SerialPortEvent oEvent) {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
      try {
        int available = inputStream.available();
        while (inputStream.available() > 0) {
          buffer[bufferCounter++] = (byte) inputStream.read();
          if (buffer[bufferCounter - 1] == '\n') {
            System.out.println(new String(buffer));
            tratar();
          }
          if (bufferCounter > 255) {
            resetBuffer();
          }
        }


        // Displayed results are codepage dependent
      } catch (Exception e) {
        System.err.println(e.toString());
        e.printStackTrace();
      }
    }
    // Ignore all the other eventTypes, but you should consider the other ones.
  }
}
