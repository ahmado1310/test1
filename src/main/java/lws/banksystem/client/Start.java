package lws.banksystem.client;

import lws.banksystem.client.view.Login;

import javax.swing.*;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class Start {
    public static void main(String[] args) {
        new Start();
    }

    private BiConsumer<String, HashMap<String, String>> callBack = (s, values) -> changeApplicationState(s, values);

    private Start() {
        Window.getInstance().applyView(new Login(callBack));
    }

    private void changeApplicationState(String state, HashMap<String, String> values){
        values.forEach((name, value)-> System.out.println(name + ": " + value));
        Window.getInstance().applyView(() -> new JPanel());
    }

}
