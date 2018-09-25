package lws.banksystem.server.network;

import lws.banksystem.client.network.Network;
import lws.banksystem.client.network.NetworkResponse;
import lws.banksystem.server.MySQL.MySQL;

public class Action {

    public static void execute(String action, Connections connection) {
        if (!action.equals("Konto-Login") && !action.equals("System-Register") && !connection.loggedIn) {
            NetworkHandler.send(connection.socket, "NO-Action");
            NetworkHandler.disconnect(connection);
        }
        if(action.equals("Konto-Login")) {
            String userID = NetworkHandler.recive(connection.socket);
            String password = NetworkHandler.recive(connection.socket);
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
            NetworkResponse response = MySQL.login(userID,password);
            if(response == NetworkResponse.allow) {
                NetworkHandler.send(connection.socket, "Login-TRUE");
                connection.loggedIn = true;
                connection.id = userID;
            } else if(response == NetworkResponse.deny) {
                NetworkHandler.send(connection.socket, "Login-FALSE");
                NetworkHandler.disconnect(connection);
            } else {
                NetworkHandler.send(connection.socket, "Login-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if(action.equals("System-Register")) {
            if (AntiDos.getInstance().addAddress(new String(connection.socket.getInetAddress().getHostAddress()))) {
                String firstName = NetworkHandler.recive(connection.socket);
                String lastName = NetworkHandler.recive(connection.socket);
                String birthdate = NetworkHandler.recive(connection.socket);
                String mail = NetworkHandler.recive(connection.socket);
                String street = NetworkHandler.recive(connection.socket);
                String houseNumber = NetworkHandler.recive(connection.socket);
                String zipCode = NetworkHandler.recive(connection.socket);
                String city = NetworkHandler.recive(connection.socket);
                String password = NetworkHandler.recive(connection.socket);
                int tmp = MySQL.register(firstName,lastName,mail,birthdate,street,houseNumber,city,zipCode,password);
                NetworkHandler.send(connection.socket,String.valueOf(tmp));
                NetworkHandler.disconnect(connection);
            } else {
                try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                NetworkHandler.send(connection.socket,"-2");
                NetworkHandler.disconnect(connection);
            }
        } else if(action.equals("System-Remove")) {
            String id = NetworkHandler.recive(connection.socket);
            NetworkResponse response = MySQL.remove(id);
            if(response == NetworkResponse.allow) {
                NetworkHandler.send(connection.socket, "Remove-TRUE");
            } else if(response == NetworkResponse.deny) {
                NetworkHandler.send(connection.socket, "Remove-FALSE");
            } else {
                NetworkHandler.send(connection.socket, "Remove-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if(action.equals("System-Disconnect")) {
            NetworkHandler.disconnect(connection);
        } else if(action.equals("Konto-Status")) {
            String balance = String.valueOf(MySQL.getBalance(connection.id));
            NetworkHandler.send(connection.socket, balance);
        } else if(action.equals("Konto-AddMoney")) {
            String ammount = NetworkHandler.recive(connection.socket);
            NetworkResponse response = MySQL.addMoney(connection.id,ammount);
            if(response == NetworkResponse.allow) {
                NetworkHandler.send(connection.socket, "Add-TRUE");
            } else {
                NetworkHandler.send(connection.socket, "Add-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if(action.equals("Konto-GetMoney")) {
            String ammount = NetworkHandler.recive(connection.socket);
            NetworkResponse response = MySQL.getMoney(connection.id, ammount);
            if (response == NetworkResponse.allow) {
                NetworkHandler.send(connection.socket, "Get-TRUE");
            } else if(response == NetworkResponse.deny) {
                NetworkHandler.send(connection.socket,"Get-FALSE");
            } else {
                NetworkHandler.send(connection.socket, "Get-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else if(action.equals("Konto-TransferMoney")) {
            String targetID = NetworkHandler.recive(connection.socket);
            String amount = NetworkHandler.recive(connection.socket);
            NetworkResponse response = MySQL.sendMoney(connection.id, targetID, amount);
            if (response == NetworkResponse.allow) {
                NetworkHandler.send(connection.socket, "Transfer-TRUE");
            } else if(response == NetworkResponse.deny) {
                NetworkHandler.send(connection.socket,"Transfer-FALSE");
            } else {
                NetworkHandler.send(connection.socket, "Transfer-ERROR");
                NetworkHandler.disconnect(connection);
            }
        } else {
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
            NetworkHandler.send(connection.socket, "NO-Action");
        }
    }

}
