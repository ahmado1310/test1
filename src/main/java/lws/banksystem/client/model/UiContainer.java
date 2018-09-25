package lws.banksystem.client.model;

import java.awt.*;

public class UiContainer {
    private Component component;
    private int x,y;

    public UiContainer(Component component, int x, int y) {
        this.component = component;
        this.x = x;
        this.y = y;
    }

    public Component getComponent() {
        return component;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
