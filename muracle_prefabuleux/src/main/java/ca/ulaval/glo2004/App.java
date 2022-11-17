package ca.ulaval.glo2004;


import ca.ulaval.glo2004.gestion.GestionnaireSalle;
import ca.ulaval.glo2004.gui.*;

import javax.swing.*;
import java.awt.*;


public class App {
    private static GestionnaireSalle gestionnaireSalle;
    public static void main(String[] args) {
        gestionnaireSalle = new GestionnaireSalle();
        JFrame frame = new JFrame("");
        MainWindow mainWindow = new MainWindow(gestionnaireSalle);
        MainMenu menu = new MainMenu();
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setContentPane(mainWindow.starterPanel);
        frame.setJMenuBar(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

