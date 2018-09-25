package lws.banksystem.client.network;

import lws.banksystem.server.log.Logger;

import java.io.*;
import java.net.Socket;

public class NetworkHandler {



    public NetworkHandler(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    private Socket socket;
    private String ipAddress;
    private int port;

    public void connect() {
        try {
            Logger.log("Verbinde mit Server...");
            socket = new Socket(ipAddress, port);
            Logger.log("Verbunden!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            Logger.log("Trenne Verbindung...");
            socket.close();
            socket = null;
            Logger.log("Verbindung getrennt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        if (socket == null) {
            return false;
        } else {
            return true;
        }
    }

    public void send(String message) {
        try {
            Logger.log("Bereite Daten zum Versenden vor...");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(message);
            writer.newLine();
            Logger.log("Sende Daten...");
            writer.flush();
            Logger.log("Daten gesendet: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recive() {
        String message = null;
        try {
            Logger.log("Warte auf Daten...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = reader.readLine();
            Logger.log("Daten bekommen: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

}
