package lws.banksystem.server.MySQL;

import lws.banksystem.client.network.Network;
import lws.banksystem.client.network.NetworkResponse;
import lws.banksystem.server.log.Logger;
import sun.nio.ch.Net;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public static NetworkResponse login(String userID, String password) {
        Logger.log("User ID ist: " + userID);
        ResultSet rs = SQLHandler.getResultSet("SELECT `Passwort` FROM `Accounts` WHERE `ID`=`"+userID+"`");
        try {
            if(rs.next()) {
                if(rs.getString("Passwort").equalsIgnoreCase(password)) {
                    return NetworkResponse.allow;
                } else {
                    return NetworkResponse.deny;
                }
            } else {
                return NetworkResponse.error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return NetworkResponse.error;
        }
    }

    public static int register(String firstName, String lastName, String mail, String birthdate, String street, String houseNumber, String city, String zipCode, String password)  {
        int idCounter = getAviableID();
        if(idCounter != -1) {
            SQLHandler.update(
                    "INSERT INTO `Accounts` " +
                            "(`ID`, `Vorname`, `Nachname`, `Geburtsdatum`, `Mail`, `Straße`, `Hausnummer`, " +
                            "`Postleitzahl`, `Stadt`, `Passwort`, `Kontostand`) " +
                            "VALUES " +
                            "('"+idCounter+"','"+firstName+"','"+lastName+"','"+birthdate+"','"+mail+"'," +
                            "'"+street+"','"+houseNumber+"','"+zipCode+"','"+city+"','"+password+"','0')");
        }
        return idCounter;
    }


    private static int getAviableID()  {
        ResultSet rs = SQLHandler.getResultSet("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME ='Accounts'");
        try {
            return rs.getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static NetworkResponse remove(String id) {
        ResultSet rs = SQLHandler.getResultSet("SELECT * FROM Accounts WHERE ID='"+id+"'");
        if(rs != null) {
            SQLHandler.update("DELETE FROM Accounts WHERE ID='"+id+"'");
            return NetworkResponse.allow;
        } else {
            return NetworkResponse.deny;
        }
        //return NetworkResponse.error;
    }

    public static int getBalance(String id) {
        int balance = -1;
        ResultSet rs = SQLHandler.getResultSet("SELECT Kontostand FROM Accounts WHERE ID='"+id+"'");
        try {
            balance = rs.getInt("Kontostand");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public static NetworkResponse sendMoney(String ownID, String targetID, String amount) {
        ResultSet rs = SQLHandler.getResultSet("SELECT Kontostand FROM Accounts WHERE ID='"+ownID+"'");
        int current = 0;
        try {
            current = rs.getInt("Kontostand");
        } catch (SQLException e) {
            e.printStackTrace();
            return NetworkResponse.error;
        }
        int newammount = current - Integer.valueOf(amount);
        if(newammount >= 0) {
            int targetBalance = 0;
            ResultSet rs2 = SQLHandler.getResultSet("SELECT Kontostand FROM Accounts WHERE ID='"+targetID+"'");
            try {
                targetBalance = rs2.getInt("Kontostand");
            } catch (SQLException e) {
                e.printStackTrace();
                return NetworkResponse.error;
            }
            int targetNewBalance = targetBalance + Integer.valueOf(amount);
            SQLHandler.update("INSERT INTO Accounts (Kontostand) VALUES ('"+targetNewBalance+"')");
            SQLHandler.update("INSERT INTO Accounts (Kontostand) VALUES ('"+newammount+"')");
            return NetworkResponse.allow;
        } else if(newammount < 0) {
            return NetworkResponse.deny;
        } else {
            return NetworkResponse.error;
        }
    }

    public static NetworkResponse getMoney(String id, String amount) {
        ResultSet rs = SQLHandler.getResultSet("SELECT Kontostand FROM Accounts WHERE ID='"+id+"'");
        int current = 0;
        try {
            current = rs.getInt("Kontostand");
        } catch (SQLException e) {
            e.printStackTrace();
            return NetworkResponse.error;
        }
        int newamount = current - Integer.valueOf(amount);
        if(newamount >= 0) {
            SQLHandler.update("INSERT INTO Accounts (Kontostand) VALUES ('"+newamount+"')");
            return NetworkResponse.allow;
        } else if(newamount < 0) {
            return NetworkResponse.deny;
        } else {
            return NetworkResponse.error;
        }
    }

    public static NetworkResponse addMoney(String id, String amount) {
        int balance = 0;
        ResultSet rs = SQLHandler.getResultSet("SELECT Kontostand FROM Accounts WHERE ID='"+id+"'");
        try {
            balance = rs.getInt("Kontostand");
        } catch (SQLException e) {
            e.printStackTrace();
            return NetworkResponse.error;
        }
        int newBalance = balance + Integer.valueOf(amount);
        SQLHandler.update("INSERT INTO Accounts (Kontostand) VALUES ('"+newBalance+"')");
        return NetworkResponse.allow;
    }

}
