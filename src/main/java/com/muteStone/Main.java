package main.java.com.muteStone;

import main.java.com.muteStone.gui.GameGUI;


/**
 * Einstiegspunkt der Anwendung.
 * Startet die grafische Benutzeroberfläche (GUI) für MiniCityTycoon.
 */
public class Main {
    public static void main(String[] args) {
        // GUI-Start auf dem Event Dispatch Thread (EDT) - best practice in Swing
        GameGUI gui = new GameGUI();
        gui.setVisible(true);
    }
}
