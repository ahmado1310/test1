package lws.banksystem.client;

import lws.banksystem.client.view.View;

import javax.swing.*;

public class Window extends JFrame {

    private static Window window = null;
    public static Window getInstance(){
        if(window == null){
            window = new Window();
        }
        return window;
    }
    String currentState = null;
    Window(){
        super();
        setSize(1000, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void applyView(View viewModel) {
        getContentPane().removeAll();
        add(viewModel.getJPanel());
        currentState = viewModel.getClass().getSimpleName();
        setVisible(true);
    }
}
