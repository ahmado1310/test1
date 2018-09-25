package lws.banksystem.server.network;

import java.net.Socket;

public class Connections extends Thread {

    public Socket socket;

    public Connections(Socket socket) {
        this.socket = socket;
    }

    public boolean loggedIn = false;
    public boolean continu = true;
    public boolean first = true;

    public String id;

    @Override
    public void run() {
        while (continu) {
            if(this.continu) first = false;
            String action = NetworkHandler.recive(socket);
            if(action == null) {
                NetworkHandler.disconnect(this);
            }
            Action.execute(action, this);
        }
    }

}
