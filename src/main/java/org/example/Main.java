package org.example;

import View.MainScreen;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //JOptionPane.showMessageDialog(null, "INCA IN LUCRU! NU DERANJA!", "Informatie", JOptionPane.INFORMATION_MESSAGE);
        EventQueue.invokeLater(() -> {
            try {
                MainScreen mainScreen = new MainScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}