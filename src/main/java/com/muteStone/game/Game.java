package main.java.com.muteStone.game;

import main.java.com.muteStone.buildings.Building;
import main.java.com.muteStone.buildings.Factory;
import main.java.com.muteStone.buildings.Farm;
import main.java.com.muteStone.economy.ResourceChange;
import main.java.com.muteStone.economy.ResourceStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Hauptklasse für die Spielverwaltung.
 * Hält den Geldstand, alle Gebäude und steuert die Simulation.
 */
public class Game {
    private ResourceStorage resourceStorage;
    private List<Building> buildings;
    private int money;

    public Game() {
        buildings = new ArrayList<>();
        money = 1000; //Startgeld
        resourceStorage = new ResourceStorage();
    }

    public String buildBuilding(Building building) {
        if (money >= building.getCost()) {
            buildings.add(building);
            money -= building.getCost();
            return building.getClass().getSimpleName() + " gebaut! Verbleibendes Geld: " + money;
        } else {
            return "Nicht genug Geld für " + building.getClass().getSimpleName() + ".";
        }
    }

    public List<String> simulateDayAndGetLog() {
        List<String> events = new ArrayList<>();

        for (Building b : buildings) {
            List<ResourceChange> changes = b.produce();
            for (ResourceChange change : changes) {
                boolean success = change.apply(resourceStorage);
                if (success) {
                    events.add(b.getName() + " produziert: " +
                            change.getAmount() + " " + change.getType());
                } else {
                    events.add(b.getName() + " konnte nicht produzieren: " +
                            change.getAmount() + " " + change.getType() +
                            " (Nicht genug Ressourcen)");
                }
            }
        }

        return events;
    }

    public int getMoney() {
        return money;
    }

    public List<String> getBuildings() {
        List<String> names = new ArrayList<>();
        for (Building b : buildings) {
            names.add(b.getClass().getSimpleName());
        }
        return names;
    }

    public ResourceStorage getResourceStorage() {
        return resourceStorage;
    }
}
