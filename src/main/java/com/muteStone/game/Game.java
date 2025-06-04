package main.java.com.muteStone.game;

import main.java.com.muteStone.buildings.Building;
import main.java.com.muteStone.buildings.Factory;
import main.java.com.muteStone.buildings.Farm;
import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Die Hauptklasse für die Spielverwaltung.
 * Hält den Geldstand, alle Gebäude und steuert die Simulation.
 */
public class Game {
    private ResourceStorage resourceStorage;
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
        resourceStorage = new ResourceStorage();
    }

    /**
     * Startet das Spiel - baut Beispielgebäude und simuliert einige Tage.
     */

    /*public void start() {
        System.out.println("Spiel startet mit " + money + " Geld.");

        //Gebäude bauen (testweise)
        buildBuilding(new Farm());
        buildBuilding(new Factory());
        buildBuilding(new Factory()); //Sollte evtl. fehlschlagen

        //Simulation für mehrere Tage
        simulateDays(5);
    }*/

    /**
     * Versucht ein Gebäude zu bauen.
     *
     * @param building Das Gebäude, das gebaut werden soll.
     * @return true, wenn erfolgreich gebaut wurde; false bei zu wenig Geld.
     */

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Willkommen bei MiniCityTycoon!");

        while (running) {
            System.out.println("\n=== Menü ===");
            System.out.println("1. Gebäude bauen");
            System.out.println("2. Nächsten Tag simulieren");
            System.out.println("3. Alle Gebäude anzeigen");
            System.out.println("4. Ressourcen anzeigen");
            System.out.println("5. Spiel beenden");
            System.out.print("Wähle eine Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //Puffer leeren

            switch (choice) {
                case 1:
                    showBuildingMenu(scanner);
                    break;
                case 2:
                    simulateDays(1);
                    break;
                case 3:
                    listBuildings();
                    break;
                case 4:
                    resourceStorage.printStatus();
                    break;
                case 5:
                    running = false;
                    System.out.println("Spiel wird beendet.");
                    break;
                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }
        scanner.close();
    }

    private void showBuildingMenu(Scanner scanner) {
        System.out.println("\n=== Gebäude bauen ===");
        System.out.println("1. Farm (100$)");
        System.out.println("2. Fabrik (500$)");
        System.out.print("Wähle ein Gebäude: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                buildBuilding(new Farm());
                break;
            case 2:
                buildBuilding(new Factory());
                break;
            default:
                System.out.println("Ungültige Auswahl.");
        }
    }

    private void listBuildings(){
        if (buildings.isEmpty()) {
            System.out.println("Du hast noch keine Gebäude.");
            return;
        }

        System.out.println("Deine Gebäude:");
        for (int i = 0; i < buildings.size(); i++) {
            Building b = buildings.get(i);
            System.out.println((i + 1) + ". " + b.getClass().getSimpleName());
        }
    }

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
                    resourceStorage.add(r);
                }
            }
        }
    }

    public int getMoney() {
        return money;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public ResourceStorage getResourceStorage() {
        return resourceStorage;
    }

    public List<String> simulateDayAndGetLog() {
        List<String> events = new ArrayList<>();

        for (Building b : buildings) {
            List<Resource> produced = b.produce();

            for (Resource r : produced) {
                resourceStorage.add(r);
                events.add(b.getClass().getSimpleName() + " produziert: " +
                        r.getAmount() + " " + r.getType());
            }
        }

        return events;
    }
}
