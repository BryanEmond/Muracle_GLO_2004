package ca.ulaval.glo2004;


import ca.ulaval.glo2004.gui.*;

import javax.swing.*;
import java.awt.*;


public class App {

    public static void main(String[] args) {

        JFrame frame = new JFrame("");

        MainWindow mainWindow = new MainWindow();
        MainMenu menu = new MainMenu();

        frame.setMinimumSize(new Dimension(800, 500));
        frame.setContentPane(mainWindow.starterPanel);
        frame.setJMenuBar(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

