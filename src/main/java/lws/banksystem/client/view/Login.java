package lws.banksystem.client.view;

import lws.banksystem.client.Window;
import lws.banksystem.client.model.UiContainer;
import lws.banksystem.client.network.Network;
import lws.banksystem.client.network.NetworkResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class Login implements View, ActionListener {

    private HashMap<String, UiContainer> ui = new HashMap<>();
    BiConsumer<String, HashMap<String, String>> callBack;

    public Login(BiConsumer<String, HashMap<String, String>> callBack) {

        ui.put("id-label", new UiContainer(new JLabel("NutzerID:"), 0, 0));
        ui.put("id", new UiContainer(new JTextField(), 1, 0));

        ui.put("password-label", new UiContainer(new JLabel("Passwort:"), 0, 1));
        ui.put("password", new UiContainer(new JTextField(), 1, 1));

        JButton regist = new JButton("Registrieren");
        regist.addActionListener(this);
        ui.put("regist-button", new UiContainer(regist, 1, 5));

        JButton submit = new JButton("Anmelden");
        submit.addActionListener(this::handleLogin);
        ui.put("submit-button", new UiContainer(submit, 1, 4));

    }

    public Login(HashMap<String, UiContainer> ui) {
        this.ui = ui;
    }

    @Override
    public JPanel getJPanel() {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = 1;
        c.insets = new Insets(10, 10, 10, 10);

        ui.values().forEach(uiContainer -> {
            c.gridx = uiContainer.getX();
            c.gridy = uiContainer.getY();
            jPanel.add(uiContainer.getComponent(), c);
        });

        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.ui.get("regist-button").getComponent()){
            Window.getInstance().applyView(new Register(callBack));
        }

    }
    private void handleLogin(ActionEvent actionEvent) {
        if (!validateUserInput()) {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine valide Anmeldung ein.");
            return;
        } else { //--------------------------------------------------------------------------------------------------------------------------------
            String id = ""; //Bitte Variable definieren (aus dem passendem TextFeld
            String password = ""; //Bitte Variable definieren (aus dem passendem TextFeld
            NetworkResponse response = Network.login(id,password);
            if(response == NetworkResponse.allow) {
                //zeige Fenster nach Login erfolgreich
            } else if(response == NetworkResponse.deny) {
                //Anmeldedaten ungültig
            } else {
                //Fehler vom Server: "InternalServerError"
            }
        } //---------------------------------------------------------------------------------------------------------------------------------------
        HashMap<String, String> result = new HashMap<>();
        ui.forEach((name, uiContainer) -> {
            if (name.split("-").length == 1)
                result.put(name, ((JTextField) uiContainer.getComponent()).getText());
        });
        callBack.accept("login", result);
    }

    private boolean validateUserInput() {
        AtomicBoolean returnValue = new AtomicBoolean(true);
        ui.forEach((name, uiContainer) -> {
            if (!name.contains("-") && ((JTextField) uiContainer.getComponent()).getText().equals(""))
                returnValue.set(false);
        });
        return returnValue.get();

    }
}
