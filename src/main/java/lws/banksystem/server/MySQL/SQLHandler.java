package lws.banksystem.server.MySQL;

import lws.banksystem.server.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler {

    private static String host = "localhost";
    private static String port = "3306";
    private static String database = "Bank-System";
    private static String user = "1and1";
    private static String password = "bankSystem";
    private static Connection connection = null;

    //jjiqegfuzewgudxgwd7ibxf7tsg

    public static void connect() {
        if(!isConnected()) {
            connection = null;
            try {
                Logger.log("Lade SQL-Treiber...");
                Class.forName("org.mariadb.jdbc.Driver");
                Logger.log("Baue eine Verbindung zur Datenabank auf...");
                connection = DriverManager.getConnection(
                        "jdbc:mariadb://" + host + ":" + port + "/" + database, user, password
                );
                Logger.log("Erstelle Tabelle, falls diese nicht existiert...");
                SQLHandler.update(
                        "CREATE TABLE IF NOT EXISTS `Accounts` (`ID` int(255) NOT NULL AUTO_INCREMENT,`Vorname` VARCHAR(100) NOT NULL," +
                        "`Nachname` VARCHAR(100) NOT NULL,`Geburtsdatum` VARCHAR(100) NOT NULL,`Mail` VARCHAR(200) NOT NULL,`Straße` VARCHAR(100) NOT NULL," +
                        "`Hausnummer` VARCHAR(50) NOT NULL,`Postleitzahl` VARCHAR(100) NOT NULL,`Stadt` VARCHAR(200) NOT NULL,`Passwort` VARCHAR(250) NOT NULL," +
                                "Kontostand INT(255) NOT NULL, PRIMARY KEY (`ID`)) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Logger.log("Datenbankverbindung steht!");
        }
    }

    public static void disconnect() {
        try {
            Logger.log("Trenne Verbindung zur Datenbank...");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
        Logger.log("Verbindung getrennt!");
    }

    public static boolean isConnected() {
        if(connection == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void update(String querry) {
        try {
            Logger.log("Bereite Datenbank abfrage vor...");
            connection.prepareStatement(querry).executeQuery();
            Logger.log("Datenbankabfrage erfolgreich durchgeführt!");
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log("Datenbankabfrage wieß einen Fehler auf!");
        }
    }


    public static ResultSet getResultSet(String querry) {
        try {
            Logger.log("Bereite Datenbank abfrage vor...");
            ResultSet rs = connection.prepareStatement(querry).executeQuery();
            Logger.log("Datenbankabfrage erfolgreich durchgeführt!");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.log("Datenbankabfrage wieß einen Fehler auf!");
        return null;
    }

}
