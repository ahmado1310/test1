package lws.banksystem.server.listener;

import lws.banksystem.server.network.Main;

public class ConnectionListener {

    public static void run() {
        Main.networkHandler.connect();
    }

}
