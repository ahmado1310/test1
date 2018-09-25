package lws.banksystem.server.network;

import lws.banksystem.server.log.Logger;
import sun.security.jca.GetInstance;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;

public class AntiDos {

    private static AntiDos antiDos;

    public static AntiDos getInstance() {
        if(antiDos == null) {
            antiDos = new AntiDos();
        }
        return antiDos;
    }

    ArrayList<String> ipAddresses = new ArrayList<>();

    public boolean addAddress(String address) {
        Logger.log("Überprüfe IP-Adresse \"" + address + "\"...");
        if(checkAddressInList(address)) {
                Logger.log("IP-Addresse registiert sich zu oft in kürzester Zeit!");
                return false;
            } else {
            ipAddresses.add(address);
            Logger.log("IP-Addresse ohne Auffälligkeiten geprüft!");
            return true;
        }
    }

    private boolean checkAddressInList(String address) {
        for (String adrs : ipAddresses) {
            if(address.equals(adrs)) {
                return true;
            }
        }
        return false;
    }

}
