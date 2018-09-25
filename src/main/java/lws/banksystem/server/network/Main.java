package lws.banksystem.server.network;

import lws.banksystem.server.MySQL.SQLHandler;
import lws.banksystem.server.listener.ConnectionListener;
import lws.banksystem.server.log.Logger;

public class Main {

    public static NetworkHandler networkHandler;

    private static int buildNumber = 1;

    public static void main(String[] args) {
        Logger.log("Starte Server...");
        Logger.log("Start Nummer: " + buildNumber);
        Thread thread = new Thread(new ConnectionSheduler());
        thread.start();
        SQLHandler.connect();
        networkHandler = new NetworkHandler();
        networkHandler.start(7347);
        Logger.log("Server wurde gestartet!");
        while (true) {
            ConnectionListener.run();
        }
    }

    public static void startThread(Thread thread) {
        thread.start();
    }

}
