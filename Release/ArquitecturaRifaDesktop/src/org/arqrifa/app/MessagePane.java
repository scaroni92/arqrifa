package org.arqrifa.app;

import org.arqrifa.views.MessageFrame;

public class MessagePane {

    public static final int OK = 1;
    public static final int ALERT = 2;
    public static final int CONFLICT = 3;

    public void displayPane(String title, String message, int TYPE) {
        MessageFrame frame = new MessageFrame(title, message, TYPE);
        frame.setVisible(true);
    }

    public void displayPane(String message) {
        displayPane("", message, 0);
    }
}
