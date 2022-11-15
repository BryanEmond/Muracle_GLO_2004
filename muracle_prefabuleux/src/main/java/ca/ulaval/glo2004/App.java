package ca.ulaval.glo2004;


import ca.ulaval.glo2004.gui.Afficheur;
import ca.ulaval.glo2004.gui.DrawingPanel;
import ca.ulaval.glo2004.gui.MainMenu;
import ca.ulaval.glo2004.gui.MainWindow;

import javax.swing.*;
import java.awt.*;


public class App {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Muracle Prefabuleux");

        MainWindow mainWindow = new MainWindow();
        MainMenu menu = new MainMenu();

        frame.setMinimumSize(new Dimension(800, 500));
        frame.setContentPane(mainWindow.rootPanel);
        frame.setJMenuBar(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DrawingPanel panel = new DrawingPanel(mainWindow);
        mainWindow.mainPanel.add(panel);
    }
}

