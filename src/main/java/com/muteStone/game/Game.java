package main.java.com.muteStone.game;

import main.java.com.muteStone.buildings.Building;
import main.java.com.muteStone.buildings.Factory;
import main.java.com.muteStone.buildings.Farm;
import main.java.com.muteStone.economy.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Die Hauptklasse für die Spielverwaltung.
 * Hält den Geldstand, alle Gebäude und steuert die Simulation.
 */
public class Game {
    //Liste aller Gebäude, die der Spieler besitzt
    private List<Building> buildings;

    //Startkapital des Spielers
    private int money;

    /**
     * Konstruktor - Initialisiert das Spiel.
     * Setzt Geld und erzeug eine leere Liste für Gebäude.
     */
    public Game() {
        buildings = new ArrayList<>();
        money = 1000; //Startgeld
    }

    /**
     * Startet das Spiel - baut Beispielgebäude und simuliert einige Tage.
     */

    public void start() {
        System.out.println("Spiel startet mit " + money + " Geld.");

        //Gebäude bauen (testweise)
        buildBuilding(new Farm());
        buildBuilding(new Factory());
        buildBuilding(new Factory()); //Sollte evtl. fehlschlagen

        //Simulation für mehrere Tage
        simulateDays(5);
    }

    /**
     * Versucht ein Gebäude zu bauen.
     *
     * @param building Das Gebäude, das gebaut werden soll.
     * @return true, wenn erfolgreich gebaut wurde; false bei zu wenig Geld.
     */
    public boolean buildBuilding(Building building) {
        if (money >= building.getCost()) {
            buildings.add(building);
            money -= building.getCost();
            System.out.println(building.getClass().getSimpleName() + " gebaut! Verbleibendes Geld: " + money);
            return true;
        } else {
            System.out.println("Nicht genug Geld für " + building.getClass().getSimpleName() + ".");
            return false;
        }
    }

    /**
     * Simuliert das Fortschreiten der Zeit (z. B. in Tagen).
     *
     * @param days Anzahl der Tage, die simuliert werden sollen.
     */
    public void simulateDays(int days) {
        if (buildings == null || buildings.isEmpty()) {
            System.out.println("Keine neuen Gebäude vorhanden. Nichts zu simulieren.");
            return;
        }

        for (int day = 1; day <= days; day++) {
            System.out.println("\n=== Tag " + day + " ===");

            for (Building b : buildings) {
                List<Resource> produced = b.produce();

                for (Resource r : produced) {
                    System.out.println(b.getClass().getSimpleName() + " produziert: " + r.getAmount() + " " + r.getType());
                }
            }
        }
    }
}
