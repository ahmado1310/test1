package lws.banksystem.server.network;

public class ConnectionSheduler extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NetworkHandler.disconnctInActiveConnections();
            System.out.println("Ermögliche erneuten Login...");
            AntiDos.getInstance().ipAddresses.clear();
            System.out.println("Erneuter Login wurde ermöglicht!");
        }
    }

}
