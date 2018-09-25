package lws.banksystem.server.network;

import lws.banksystem.client.network.NetworkConfig;
import lws.banksystem.server.log.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkHandler {

    private static Socket socket;
    private static ServerSocket serverSocket;
    private static int port;
    public static List<Connections> connections = new ArrayList<Connections>();

    public static void start(int port) {
        try {
            Logger.log("Starte Netzwerk-Server...");
            serverSocket = new ServerSocket(port);
            Logger.log("Netzwerk-Server gestartet! Port=" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            Logger.log("Warte auf Client Verbindung...");
            socket = serverSocket.accept();
            Connections connection = new Connections(socket);
            connections.add(connection);
            Main.startThread(new Thread(connection));
            Logger.log("Client mit der IP: \"" + socket.getInetAddress().getHostAddress() + "\" hat sich verbunden!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(Connections connection) {
        try {
            Logger.log("Trenne Verbindung von einem Client...");
            connection.socket.close();
            connection.socket = null;
            connection.continu = false;
            connections.remove(connection);
            Logger.log("Verbindung getrennt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnected(Connections connection) {
        return connection.continu;
    }

    public static void disconnctInActiveConnections() {
        Logger.log("Schließe alle Inaktiven Verbindungen...");
        int counter = 0;
        for (Connections connection : connections) {
            if(isConnected(connection)) {
                counter++;
                connection.continu = false;
                connections.remove(connection);
            }
        }
        if(counter == 0) {
            Logger.log("Es wurden " + counter + " Verbindungen geschlossen!");
        } else {
            Logger.log("Es wurden keine Verbindungen geschlossen!");
        }
    }

    public static void send(Socket socket, String message) {
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

    public static String recive(Socket socket) {
        String message = null;
        try {
            Logger.log("Warte auf Daten...");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = reader.readLine();
            Logger.log("Daten bekommen: " + message);
        } catch (NullPointerException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
